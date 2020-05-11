package com.chisapp.modules.member.service.impl;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.member.bean.DepositRecord;
import com.chisapp.modules.member.bean.Member;
import com.chisapp.modules.member.bean.MemberType;
import com.chisapp.modules.member.dao.DepositRecordMapper;
import com.chisapp.modules.member.service.DepositRecordService;
import com.chisapp.modules.member.service.MemberService;
import com.chisapp.modules.member.service.MemberTypeService;
import com.chisapp.modules.nurseworkstation.bean.PaymentRecord;
import com.chisapp.modules.nurseworkstation.service.PaymentRecordService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/31 11:45
 * @Version 1.0
 */
@Service
public class DepositRecordServiceImpl implements DepositRecordService {

    private DepositRecordMapper depositRecordMapper;
    @Autowired
    public void setDepositRecordMapper(DepositRecordMapper depositRecordMapper) {
        this.depositRecordMapper = depositRecordMapper;
    }

    private MemberService memberService;
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    private MemberTypeService memberTypeService;
    @Autowired
    public void setMemberTypeService(MemberTypeService memberTypeService) {
        this.memberTypeService = memberTypeService;
    }

    private PaymentRecordService paymentRecordService;
    @Autowired
    public void setPaymentRecordService(PaymentRecordService paymentRecordService) {
        this.paymentRecordService = paymentRecordService;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void save(Integer mrmMemberId, String paymentRecordJson) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        String lsh = KeyUtils.getLSH(user.getId()); // 获取流水号
        Member member = memberService.getById(mrmMemberId); // 获取会员
        if (member == null) {
            throw new RuntimeException("获取会员失败");
        }
        MemberType memberType = memberTypeService.getById(member.getMrmMemberTypeId());
        if (memberType == null) {
            throw new RuntimeException("获取会员类型失败");
        }

        // 解析收款记录
        PaymentRecord paymentRecord = savePaymentRecord(paymentRecordJson, lsh, user);

        // 计算储值赠送金额
        BigDecimal zero = new BigDecimal("0");
        BigDecimal depositAmount = memberType.getDepositAmount(); // 储值金额需达到的条件
        BigDecimal givenAmount = memberType.getGivenAmount(); // 达到条件后赠送的金额
        BigDecimal totalGivenAmount = zero; // 合计赠送的金额
        // 如果实际储值金额大于0 并且 储值金额需达到的条件大于0 并且 达到条件后赠送的金额大于0 则进行赠送金额计算
        if ((paymentRecord.getActualAmount().compareTo(zero) > 0) && (depositAmount.compareTo(zero) > 0) && (givenAmount.compareTo(zero) > 0)) {
            BigDecimal fold = paymentRecord.getActualAmount().divide(depositAmount, 0, BigDecimal.ROUND_HALF_UP);
            totalGivenAmount = fold.multiply(givenAmount);
        }
        BigDecimal totalDepositAmount = paymentRecord.getActualAmount().add(totalGivenAmount); // 合计储值金额 = 实收金额 + 合计增送金额
        BigDecimal balance = member.getBalance().add(totalDepositAmount); // 会员余额 = 会员余额 + 合计储值金额

        // 创建一条储值记录
        DepositRecord depositRecord = new DepositRecord();
        depositRecord.setLsh(lsh);
        depositRecord.setMrmMemberId(mrmMemberId);
        depositRecord.setDepositAmount(paymentRecord.getActualAmount());
        depositRecord.setGivenAmount(totalGivenAmount);
        depositRecord.setBalance(balance);
        depositRecord.setSysClinicId(user.getSysClinicId());
        depositRecord.setCreatorId(user.getId());
        depositRecord.setCreationDate(new Date());
        depositRecord.setReturned(false);
        // 保存储值记录
        this.depositRecordMapper.insert(depositRecord);

        // 更新会员余额
        this.memberService.updateBalanceForDeposit(mrmMemberId, totalDepositAmount);
    }

    /**
     * 保存收款记录
     * @param paymentRecordJson
     * @param lsh
     * @param user
     * @return
     */
    private PaymentRecord savePaymentRecord(String paymentRecordJson, String lsh, User user) {
        // 将收款记录 JSON 转成对象 并进行赋值
        PaymentRecord paymentRecord = JSONUtils.parseJsonToObject(paymentRecordJson, new TypeReference<PaymentRecord>() {});
        paymentRecord.setLsh(lsh); // 流水号
        paymentRecord.setSysClinicId(user.getSysClinicId()); // 机构ID
        paymentRecord.setCreatorId(user.getId()); // 创建人
        paymentRecord.setCreationDate(new Date()); // 创建日期

        if (paymentRecord.getMemberBalance().compareTo(new BigDecimal("0")) > 0) {
            throw new RuntimeException("不能使用[会员卡]支付方式进行充值");
        }
        this.paymentRecordService.save(paymentRecord); // 保存收款记录

        return paymentRecord;
    }

    @Override
    public void saveForReturned(String lsh) {
        // 根据流水号获取对应的储值记录
        List<DepositRecord> depositRecordList = this.getByLsh(lsh);
        if (depositRecordList.isEmpty()) {
            throw new RuntimeException("获取储值记录失败");
        }

        for (int i = 0; i < depositRecordList.size(); i++) {
            if (i > 1) {
                throw new RuntimeException("记录重复");
            }
        }

        // 更新该储值记录为退回状态
        DepositRecord depositRecord = depositRecordList.get(0);
        depositRecord.setReturned(true); // 修改其为已经退回状态
        this.depositRecordMapper.updateByPrimaryKey(depositRecord);

        // 获取会员信息
        Integer mrmMemberId = depositRecord.getMrmMemberId();
        Member member = this.memberService.getById(mrmMemberId);
        if (member == null) {
            throw new RuntimeException("获取会员失败");
        }

        // 判断是否会员余额不足
        BigDecimal depositAmount = depositRecord.getDepositAmount(); // 获取要退储值金额
        BigDecimal givenAmount = depositRecord.getGivenAmount(); // 获取要退赠送金额
        BigDecimal totalDepositAmount = depositAmount.add(givenAmount); // 合计退储值金额 = 要退储值金额 + 要退赠送金额
        BigDecimal balance = member.getBalance().subtract(totalDepositAmount); // 退后余额 = 会员余额 - 合计退储值金额
        // 如果会员余额小于0 (返回 -1) 则停止执行
        if (balance.compareTo(new BigDecimal("0")) < 0) {
            throw new RuntimeException("会员余额不足");
        }

        // 获取操作人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 设置退回记录属性
        BigDecimal minus1 = new BigDecimal("-1");
        depositRecord.setId(null); // 将 ID 重置为 null
        depositRecord.setDepositAmount(depositAmount.multiply(minus1)); // 储值金额(设置为负数)
        depositRecord.setGivenAmount(givenAmount.multiply(minus1)); // 赠送金额(设置为负数)
        depositRecord.setBalance(balance); // 剩余余额
        depositRecord.setSysClinicId(user.getSysClinicId()); // 机构ID
        depositRecord.setCreatorId(user.getId()); // 操作人ID
        depositRecord.setCreationDate(new Date()); // 操作日期
        // 保存储值退回记录
        this.depositRecordMapper.insert(depositRecord);
        // 更新会员余额(传入 金额 必须为负数)
        this.memberService.updateBalanceForDeposit(mrmMemberId, totalDepositAmount.multiply(minus1));
        // 保存退款记录
        this.savePaymentRecordForReturned(lsh);
    }

    private void savePaymentRecordForReturned (String lsh) {
        // 根据流水号获取对应的付款记录
        List<PaymentRecord> paymentRecordList = this.paymentRecordService.getByLsh(lsh);
        if (paymentRecordList.isEmpty()) {
            throw new RuntimeException("获取储值的付款方式失败");
        }
        if (paymentRecordList.size() > 1) {
            throw new RuntimeException("记录重复");
        }

        // 获取操作人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 获取付款记录 并设置退回记录属性
        PaymentRecord paymentRecord = paymentRecordList.get(0);
        if (user.getSysClinicId().intValue() != paymentRecord.getSysClinicId().intValue()) {
            throw new RuntimeException("操作人所在机构与储值机构不一致");
        }

        // 设置记录属性 并将收费方式设置对应的负数
        BigDecimal minus1 = new BigDecimal("-1");
        paymentRecord.setId(null); // 将 ID 重置为 null
        paymentRecord.setCash(paymentRecord.getCash().multiply(minus1)); // 现金
        paymentRecord.setUnionpay(paymentRecord.getUnionpay().multiply(minus1)); // 银联
        paymentRecord.setWechatpay(paymentRecord.getWechatpay().multiply(minus1)); // 微信
        paymentRecord.setAlipay(paymentRecord.getAlipay().multiply(minus1)); // 支付宝
        paymentRecord.setCmedicare(paymentRecord.getCmedicare().multiply(minus1)); // 市医保
        paymentRecord.setPmedicare(paymentRecord.getPmedicare().multiply(minus1)); // 省医保
        // paymentRecord.setMemberBalance(); // 不允许使用会员卡余额
        paymentRecord.setCreditpay(paymentRecord.getCreditpay().multiply(minus1)); // 信用卡
        paymentRecord.setCoupon(paymentRecord.getCoupon().multiply(minus1)); // 抵扣券
        paymentRecord.setSysPaymentWayAmount(paymentRecord.getSysPaymentWayAmount().multiply(minus1)); // 其他收费方式
        paymentRecord.setActualAmount(paymentRecord.getActualAmount().multiply(minus1)); // 实收金额
        paymentRecord.setDisparityAmount(paymentRecord.getDisparityAmount().multiply(minus1)); // 差额
        paymentRecord.setSysClinicId(user.getSysClinicId()); // 机构ID
        paymentRecord.setCreatorId(user.getId()); // 操作人ID
        paymentRecord.setCreationDate(new Date()); // 操作日期
        // 保存付款退回记录
        this.paymentRecordService.save(paymentRecord);
    }

    @Override
    public List<DepositRecord> getByLsh(String lsh) {
        return depositRecordMapper.selectByLsh(lsh);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer sysClinicId, String[] creationDate, String lsh, String mrmMemberName, Boolean returned) {
        return depositRecordMapper.selectByCriteria(sysClinicId, creationDate, lsh, mrmMemberName, returned);
    }
}
