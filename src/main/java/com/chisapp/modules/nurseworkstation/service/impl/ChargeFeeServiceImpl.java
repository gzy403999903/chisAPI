package com.chisapp.modules.nurseworkstation.service.impl;

import com.chisapp.common.enums.GoodsTypeEnum;
import com.chisapp.common.enums.ItemTypeEnum;
import com.chisapp.common.enums.SellTypeEnum;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.datareport.bean.SellRecord;
import com.chisapp.modules.datareport.service.SellRecordService;
import com.chisapp.modules.doctorworkstation.bean.PerformItem;
import com.chisapp.modules.doctorworkstation.bean.SellPrescription;
import com.chisapp.modules.doctorworkstation.service.ClinicalHistoryService;
import com.chisapp.modules.doctorworkstation.service.PerformItemService;
import com.chisapp.modules.doctorworkstation.service.SellPrescriptionService;
import com.chisapp.modules.inventory.bean.Inventory;
import com.chisapp.modules.inventory.service.InventoryService;
import com.chisapp.modules.member.bean.ExpendRecord;
import com.chisapp.modules.member.bean.Member;
import com.chisapp.modules.member.bean.MemberType;
import com.chisapp.modules.member.bean.PointsRecord;
import com.chisapp.modules.member.service.ExpendRecordService;
import com.chisapp.modules.member.service.MemberService;
import com.chisapp.modules.member.service.MemberTypeService;
import com.chisapp.modules.member.service.PointsRecordService;
import com.chisapp.modules.nurseworkstation.bean.PaymentRecord;
import com.chisapp.modules.nurseworkstation.bean.RegistrationRecord;
import com.chisapp.modules.nurseworkstation.service.ChargeFeeService;
import com.chisapp.modules.nurseworkstation.service.PaymentRecordService;
import com.chisapp.modules.nurseworkstation.service.RegistrationRecordService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: Tandy
 * @Date: 2019/12/23 15:29
 * @Version 1.0
 */
@Service
public class ChargeFeeServiceImpl implements ChargeFeeService {
    private PaymentRecordService paymentRecordService;
    @Autowired
    public void setPaymentRecordService(PaymentRecordService paymentRecordService) {
        this.paymentRecordService = paymentRecordService;
    }

    private MemberTypeService memberTypeService;
    @Autowired
    public void setMemberTypeService(MemberTypeService memberTypeService) {
        this.memberTypeService = memberTypeService;
    }

    private MemberService memberService;
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    private ExpendRecordService expendRecordService;
    @Autowired
    public void setExpendRecordService(ExpendRecordService expendRecordService) {
        this.expendRecordService = expendRecordService;
    }

    private PointsRecordService pointsRecordService;
    @Autowired
    public void setPointsRecordService(PointsRecordService pointsRecordService) {
        this.pointsRecordService = pointsRecordService;
    }

    private SellRecordService sellRecordService;
    @Autowired
    public void setSellRecordService(SellRecordService sellRecordService) {
        this.sellRecordService = sellRecordService;
    }

    private InventoryService inventoryService;
    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    private PerformItemService performItemService;
    @Autowired
    public void setPerformItemService(PerformItemService performItemService) {
        this.performItemService = performItemService;
    }

    private SellPrescriptionService sellPrescriptionService;
    @Autowired
    public void setSellPrescriptionService(SellPrescriptionService sellPrescriptionService) {
        this.sellPrescriptionService = sellPrescriptionService;
    }

    private ClinicalHistoryService clinicalHistoryService;
    @Autowired
    public void setClinicalHistoryService(ClinicalHistoryService clinicalHistoryService) {
        this.clinicalHistoryService = clinicalHistoryService;
    }

    private RegistrationRecordService registrationRecordService;
    @Autowired
    public void setRegistrationRecordService(RegistrationRecordService registrationRecordService) {
        this.registrationRecordService = registrationRecordService;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public String saveForPrescription(Integer mrmMemberId, String paymentRecordJson, String sellRecordJson) {
        // 将 JSON 转成对应的对象
        PaymentRecord paymentRecord = JSONUtils.parseJsonToObject(paymentRecordJson, new TypeReference<PaymentRecord>() {});
        List<SellRecord> sellRecordList = JSONUtils.parseJsonToObject(sellRecordJson, new TypeReference<List<SellRecord>>() {});

        // 初始化用户信息、流水号、会员信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String lsh = KeyUtils.getLSH(user.getId());
        Member member = this.memberService.getById(mrmMemberId);

        // 1保存付款记录
        this.savePaymentRecord(user, lsh, member, paymentRecord);

        // 2保存会员消费记录并更新会员余额和积分
        this.saveMemberExpendAndPoints(user, lsh, member, paymentRecord);

        // 3更新商品库存
        this.updateInventoryQuantity(sellRecordList);

        // 4保存需要进行执行医嘱的项目
        this.savePerformItem(sellRecordList);

        // 5保存销售记录
        this.saveSellRecord(user, lsh, sellRecordList);

        // 6保存中药、西药、检查检验、理疗项目处方, 并更新处方所对应的病例状态
        this.saveSellPrescription(sellRecordList);

        // 7保存挂号记录
        this.saveRegistrationRecord(sellRecordList);

        // 8清除缓存
        this.clearCache(sellRecordList);

        return lsh;
    }

    @Override
    public String saveForPos(Integer mrmMemberId, String paymentRecordJson, String sellRecordJson) {
        // 将 JSON 转成对应的对象
        PaymentRecord paymentRecord = JSONUtils.parseJsonToObject(paymentRecordJson, new TypeReference<PaymentRecord>() {});
        List<SellRecord> sellRecordList = JSONUtils.parseJsonToObject(sellRecordJson, new TypeReference<List<SellRecord>>() {});

        // 初始化用户信息、流水号
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String lsh = KeyUtils.getLSH(user.getId());

        // 获取会员信息
        Member member = null;
        if (mrmMemberId != null) {
            member = this.memberService.getById(mrmMemberId);
        }

        // 1保存付款记录
        this.savePaymentRecord(user, lsh, member, paymentRecord);

        // 2保存会员消费记录并更新会员余额和积分
        if (mrmMemberId != null) {
            this.saveMemberExpendAndPoints(user, lsh, member, paymentRecord);
        }

        // 3更新商品库存
        this.updateInventoryQuantity(sellRecordList);

        // 4保存销售记录
        this.saveSellRecord(user, lsh, sellRecordList);

        return lsh;
    }

    @Override
    public void saveForReturned(Integer mrmMemberId,  Boolean neglectQuantity, String paymentRecordJson, String sellRecordJson) {
        // 将 JSON 转成对应的对象
        PaymentRecord paymentRecord = JSONUtils.parseJsonToObject(paymentRecordJson, new TypeReference<PaymentRecord>() {});
        List<SellRecord> sellRecordList = JSONUtils.parseJsonToObject(sellRecordJson, new TypeReference<List<SellRecord>>() {});

        // 初始化用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 获取会员信息
        Member member = null;
        if (mrmMemberId != null) {
            member = this.memberService.getById(mrmMemberId);
        }
        // 获取流水号
        String lsh = sellRecordList.get(0).getLsh(); // 获取流水号
        if (lsh == null) {
            throw new RuntimeException("无法从销售记录中获取对应的流水号");
        }

        // 保存付款记录
        this.savePaymentRecord(user, lsh, member, paymentRecord);

        // 保存会员消费记录并更新会员余额和积分
        if (member != null) {
            this.saveMemberExpendAndPoints(user, lsh, member, paymentRecord);
        }

        // 更新执行项目剩余次数(数量需为正数)
        this.updatePerformItemResidueQuantity(sellRecordList, neglectQuantity);

        // 修改销售记录为已退费状态
        this.updateSellRecordReturned(sellRecordList);

        /* 创建退回销售记录 执行该步骤后 sellRecordList 中 id 重置为 null, quantity 改为负数 */
        for (SellRecord record : sellRecordList) {
            record.setId(null); // 将 ID 重置为 null
            record.setQuantity(record.getQuantity() * -1); // 将数量修改为负数
            record.setReturned(true); // 将退回状态设置已退回
        }

        // 保存销售记录(数量需为负数)
        this.saveSellRecord(user, lsh, sellRecordList);

        // 更新商品库存(数量需为负数)
        this.updateInventoryQuantity(sellRecordList);

    }

    /**
     * 保存付款记录
     * @param user
     * @param lsh
     * @param member
     * @param paymentRecord
     */
    private void savePaymentRecord(User user, String lsh, Member member, PaymentRecord paymentRecord) {
        // 判断会员余额是否足够
        if (member != null) {
            BigDecimal balance = member.getBalance(); // 获取会员余额
            BigDecimal expendBalance = paymentRecord.getMemberBalance(); // 获取要扣除的会员金额
            if (balance.compareTo(expendBalance) < 0) { // 如果会员余额小于要扣除的会员金额 (也就是返回 -1), 则抛异常
                throw new RuntimeException("会员卡余额不足");
            }
        }

        // 赋值部分属性
        BigDecimal cash = paymentRecord.getCash().subtract(paymentRecord.getCashBackAmount()); // 计算要保存的实收现金 = 实收现金 - 找零现金

        paymentRecord.setLsh(lsh); // 流水号
        paymentRecord.setCash(cash.setScale(2, BigDecimal.ROUND_HALF_UP)); // 现金实收金额 保留两位小数
        paymentRecord.setSysClinicId(user.getSysClinicId()); // 机构ID
        paymentRecord.setCreatorId(user.getId()); // 创建人
        paymentRecord.setCreationDate(new Date()); // 创建时间
        // 保存付款记录
        this.paymentRecordService.save(paymentRecord);
    }

    /**
     * 保存会员消费记录并更新会员余额和积分
     * @param user
     * @param lsh
     * @param member
     * @param paymentRecord
     */
    private void saveMemberExpendAndPoints(User user, String lsh,  Member member, PaymentRecord paymentRecord) {
        // 获取会员类型
        MemberType memberType = this.memberTypeService.getById(member.getMrmMemberTypeId());
        if (memberType == null) {
            throw new RuntimeException("获取会员类型失败");
        }

        BigDecimal expendBalance = paymentRecord.getMemberBalance(); // 本次扣减的金额
        BigDecimal balance = member.getBalance().subtract(expendBalance); // 计算剩余余额 = 会员余额 - 本次扣减金额
        int givenPoints = paymentRecord.getActualAmount().divide(memberType.getPaymentAmount(), 0, BigDecimal.ROUND_HALF_UP).intValue();  // 计算本次积分
        int points = member.getPoints() + givenPoints;  // 计算剩余积分 = 会员当前积分 + 新赠送的积分

        // 更新会员余额与积分 (注意比较运算要 不等于0 , 因为在退费操作时传过来的是负数.....)
        BigDecimal zero = new BigDecimal("0");
        if (expendBalance.compareTo(zero) != 0 || givenPoints != 0) {
            this.memberService.updateBalanceAndPoints(member.getId(), expendBalance, givenPoints);
        }

        // 创建一条消费记录
        if (expendBalance.compareTo(zero) != 0) {
            ExpendRecord expendRecord = new ExpendRecord();
            expendRecord.setLsh(lsh);
            expendRecord.setMrmMemberId(member.getId());
            expendRecord.setExpendBalance(paymentRecord.getMemberBalance());
            expendRecord.setBalance(balance);
            expendRecord.setSysClinicId(user.getSysClinicId());
            expendRecord.setCreatorId(user.getId());
            expendRecord.setCreationDate(new Date());
            // 保存消费记录
            this.expendRecordService.save(expendRecord);
        }

        // 创建一条积分记录
        if (givenPoints != 0) {
            PointsRecord pointsRecord = new PointsRecord();
            pointsRecord.setLsh(lsh);
            pointsRecord.setMrmMemberId(member.getId());
            pointsRecord.setExchangePoints(0);
            pointsRecord.setGivenPoints(givenPoints);
            pointsRecord.setPoints(points);
            pointsRecord.setSysClinicId(user.getSysClinicId());
            pointsRecord.setCreatorId(user.getId());
            pointsRecord.setCreationDate(new Date());
            // 保存积分记录
            this.pointsRecordService.save(pointsRecord);
        }
    }

    /**
     * 更新商品库存
     * @param sellRecordList
     */
    private void updateInventoryQuantity(List<SellRecord> sellRecordList) {
        // 需要更新库存数量的集合
        List<Inventory> inventoryList = new ArrayList<>();

        // 遍历销售记录集合
        for (SellRecord sellRecord : sellRecordList) {
            // 如果销售记录为商品类型
            if (sellRecord.getSysSellTypeId().intValue() == SellTypeEnum.GOODS.getIndex().intValue()) {
                // 创建一条库存记录
                Inventory inventory = new Inventory();
                inventory.setGsmGoodsOid(sellRecord.getOid()); // 商品编码
                inventory.setGsmGoodsName(sellRecord.getName()); // 商品名称
                inventory.setPh(sellRecord.getPh()); // 批号
                inventory.setPch(sellRecord.getPch()); // 批次号
                inventory.setId(sellRecord.getIymInventoryId()); // 要修改库存ID
                inventory.setQuantity(sellRecord.getQuantity()); // 要修改的库存数量

                inventoryList.add(inventory);
            }
        }

        // 更新库存数量
        if (!inventoryList.isEmpty()) {
            this.inventoryService.updateQuantityByList(inventoryList);
        }
    }

    /**
     * 保存需要进行执行医嘱的项目
     * @param sellRecordList
     */
    private void savePerformItem(List<SellRecord> sellRecordList) {
        // 要进行保存的医嘱执行项目集合
        List<PerformItem> performItemList = new ArrayList<>();

        // 遍历所有销售项目
        for (SellRecord sellRecord : sellRecordList) {
            // 如果销售类型为收费项目 并且为医技和辅助治疗项目
            if (sellRecord.getSysSellTypeId().intValue() == SellTypeEnum.ITEM.getIndex().intValue()) {
                if (sellRecord.getEntityTypeId().intValue() == ItemTypeEnum.MEDICAL_ITEM.getIndex().intValue() ||
                        sellRecord.getEntityTypeId().intValue() == ItemTypeEnum.ADJUVANT_ITEM.getIndex().intValue()) {
                    // 创建医嘱执行项目
                    PerformItem performItem = new PerformItem();
                    performItem.setLsh(sellRecord.getDwtSellPrescriptionLsh()); // 处方流水号
                    performItem.setMrmMemberId(sellRecord.getMrmMemberId()); // 会员ID
                    performItem.setCimItemId(sellRecord.getEntityId()); // 项目ID
                    performItem.setQuantity(sellRecord.getQuantity()); // 销售数量
                    performItem.setOnceContainQuantity(sellRecord.getOnceContainQuantity()); // 单次可执行数量
                    performItem.setResidueQuantity(sellRecord.getQuantity() * sellRecord.getOnceContainQuantity()); // 剩余数量
                    performItem.setSellerId(sellRecord.getSellerId()); // 销售人ID
                    performItem.setCreationDate(sellRecord.getDwtSellPrescriptionDate()); // 处方创建时间

                    performItemList.add(performItem);
                }
            }
        }

        // 保存医嘱执行项目集合
        if (!performItemList.isEmpty()) {
            this.performItemService.saveList(performItemList);
        }
    }

    /**
     * 更新执行项目剩余次数(数量需为正数)
     * @param sellRecordList
     * @param neglectQuantity
     */
    private void updatePerformItemResidueQuantity(List<SellRecord> sellRecordList, Boolean neglectQuantity) {
        // 需要将剩余次数进行重置为 0 的医嘱执行项目
        List<PerformItem> performItemList = new ArrayList<>();

        // 遍历所有销售项目
        for (SellRecord sellRecord : sellRecordList) {
            // 如果销售类型为收费项目 并且为医技和辅助治疗项目
            if (sellRecord.getSysSellTypeId().intValue() == SellTypeEnum.ITEM.getIndex().intValue()) {
                if (sellRecord.getEntityTypeId().intValue() == ItemTypeEnum.MEDICAL_ITEM.getIndex().intValue() ||
                        sellRecord.getEntityTypeId().intValue() == ItemTypeEnum.ADJUVANT_ITEM.getIndex().intValue()) {
                    // 创建医嘱执行项目
                    PerformItem performItem = new PerformItem();
                    performItem.setLsh(sellRecord.getDwtSellPrescriptionLsh()); // 处方流水号
                    performItem.setCimItemId(sellRecord.getEntityId()); // 项目ID
                    performItem.setQuantity(neglectQuantity ? 0 : sellRecord.getQuantity()); // 销售数量

                    performItemList.add(performItem);
                }
            }
        }

        // 修改医嘱执行项目集合
        if (!performItemList.isEmpty() && neglectQuantity) {
            this.performItemService.resetResidueQuantityByList(performItemList);
        }
        if (!performItemList.isEmpty() && !neglectQuantity) {
            this.performItemService.updateResidueQuantityByList(performItemList);
        }
    }

    /**
     * 保存销售记录
     * @param user
     * @param lsh
     * @param sellRecordList
     */
    private void saveSellRecord(User user, String lsh, List<SellRecord> sellRecordList) {
        // 赋值部分属性
        int mxh = 1;
        for (SellRecord record : sellRecordList) {
            record.setLsh(lsh); // 流水号
            record.setMxh(String.valueOf(mxh++)); // 明细号
            record.setCreationDate(new Date()); // 创建日期
            record.setSysClinicId(user.getSysClinicId()); // 机构ID
            record.setCashierId(user.getId()); // 收银员ID
            record.setPayState(true); // 结账状态
        }

        // 保存销售记录
        if (!sellRecordList.isEmpty()) {
            this.sellRecordService.saveList(sellRecordList);
        }
    }

    /**
     * 修改销售记录为已退费状态
     * @param sellRecordList
     */
    private void updateSellRecordReturned(List<SellRecord> sellRecordList) {
        // 获取 ID 集合
        List<Integer> idList = new ArrayList<>();
        for (SellRecord sellRecord : sellRecordList) {
            idList.add(sellRecord.getId());
        }

        // 修改 ID 集合 对应的数据退费状态
        if (!idList.isEmpty()) {
            this.sellRecordService.updateReturnedByIdList(idList);
        }
    }

    /**
     * 保存中药、西药、检查检验、理疗项目处方, 并更新处方所对应的病例状态
     * @param sellRecordList
     */
    private void saveSellPrescription(List<SellRecord> sellRecordList) {
        // 汇总处方流水号
        HashSet<String> lshSet = new HashSet<>();
        for (SellRecord sellRecord : sellRecordList) {
            if (!sellRecord.getRegistrationFeeFlag()) { // 只汇总非挂号费
                lshSet.add(sellRecord.getDwtSellPrescriptionLsh());
            }
        }

        // 需要保存的处方
        List<SellPrescription> sellPrescriptionList = new ArrayList<>();
        // 需要更新归档状态的病例
        HashSet<Integer> dwtClinicalHistoryIdSet = new HashSet<>();

        for (String lsh : lshSet) {
            // 从缓存获取对应的处方集合
            List<SellPrescription> list = this.sellPrescriptionService.getByLshFromCache(lsh);

            // 判断是否可以从处方中获取明细
            SellPrescription sellPrescription = list.get(0);
            if (sellPrescription == null) {
                throw new RuntimeException("保存处方时无法获取对应明细");
            }

            // 汇总病例ID
            Integer dwtClinicalHistoryId = sellPrescription.getDwtClinicalHistoryId();
            dwtClinicalHistoryIdSet.add(dwtClinicalHistoryId);

            // 如果该处方为 西药、中药、医技项目、辅助理疗 则进行保存
            if (
                sellPrescription.getEntityTypeId().intValue() == GoodsTypeEnum.WESTERN_DRUGS.getIndex().intValue() ||
                sellPrescription.getEntityTypeId().intValue() == GoodsTypeEnum.CHINESE_DRUGS.getIndex().intValue() ||
                sellPrescription.getEntityTypeId().intValue() == ItemTypeEnum.MEDICAL_ITEM.getIndex().intValue() ||
                sellPrescription.getEntityTypeId().intValue() == ItemTypeEnum.ADJUVANT_ITEM.getIndex().intValue()
            ) {
                sellPrescriptionList.addAll(list);
            }
        }

        // 保存处方
        if (!sellPrescriptionList.isEmpty()) {
            this.sellPrescriptionService.saveList(sellPrescriptionList);
        }

        // 更新病例归档状态
        for (Integer id : dwtClinicalHistoryIdSet) {
            this.clinicalHistoryService.updateFinishedById(true, id);
        }
    }

    /**
     * 保存挂号记录
     * @param sellRecordList
     */
    private void saveRegistrationRecord(List<SellRecord> sellRecordList) {
        // 汇总挂号流水号
        HashSet<String> registrationRecordLshSet = new HashSet<>();
        for (SellRecord sellRecord : sellRecordList) {
            if (sellRecord.getRegistrationFeeFlag()) { // 只汇总挂号费
                registrationRecordLshSet.add(sellRecord.getDwtSellPrescriptionLsh());
            }
        }

        // 获取所有的挂号记录
        List<RegistrationRecord> registrationRecordList = new ArrayList<>();
        for (String lsh : registrationRecordLshSet) {
            RegistrationRecord registrationRecord = this.registrationRecordService.getByLshFromCache(lsh);
            registrationRecordList.add(registrationRecord);
        }

        // 保存挂号记录
        for (RegistrationRecord registrationRecord : registrationRecordList) {
            this.registrationRecordService.save(registrationRecord);
        }

    }

    /**
     * 清除销售对应的缓存记录
     * @param sellRecordList
     */
    private void clearCache (List<SellRecord> sellRecordList) {
        HashSet<String> registrationRecordLshSet = new HashSet<>();  // 挂号流水号
        HashSet<String> sellPrescriptionLshSet = new HashSet<>();  // 处方流水号
        HashSet<String> allLshSet = new HashSet<>();  // 所有流水号

        // 汇总各类型流水号
        for (SellRecord sellRecord : sellRecordList) {
            String lsh = sellRecord.getDwtSellPrescriptionLsh();
            if (sellRecord.getRegistrationFeeFlag()) {
                registrationRecordLshSet.add(lsh);
            } else {
                sellPrescriptionLshSet.add(lsh);
            }

            allLshSet.add(lsh);
        }

        // 清除挂号缓存
        for (String lsh : registrationRecordLshSet) {
            this.registrationRecordService.deleteByLshFromCache(lsh);
        }

        // 清除处方缓存
        for (String lsh : sellPrescriptionLshSet) {
            this.sellPrescriptionService.deleteByLshFromCache(lsh);
        }

        // 清除销售记录缓存
        for (String lsh : allLshSet) {
            this.sellRecordService.deleteByPrescriptionLshFromCache(lsh);
        }

    }

    @Override
    public Map<String, Object> getChargeFeeRecordByLsh(String lsh) {
        // 获取操作人员信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        // 获取销售记录
        List<Map<String, Object>> sellRecordList = this.sellRecordService.getClinicListByLsh(user.getSysClinicId(), lsh);
        if (sellRecordList.isEmpty()) {
            throw new RuntimeException("未能获取对应的销售记录");
        }

        // 获取付款记录 (多条记录进行合并)
        Map<String, Object> paymentRecord = this.paymentRecordService.getClinicGroupByLsh(user.getSysClinicId(), lsh);
        if (paymentRecord.isEmpty()) {
            throw new RuntimeException("未能获取对应的付款记录");
        }

        // 获取积分
        Integer points = this.pointsRecordService.getSumGivenPointsByLsh(lsh);

        Map<String, Object> chargeFeeRecord = new HashMap<>();
        chargeFeeRecord.put("sellRecordList", sellRecordList);
        chargeFeeRecord.put("paymentRecord", paymentRecord);
        chargeFeeRecord.put("points", points);
        return chargeFeeRecord;
    }


}
