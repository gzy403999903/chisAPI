package com.chisapp.modules.doctorworkstation.service.impl;

import com.chisapp.common.enums.SellWayEnum;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.datareport.bean.SellRecord;
import com.chisapp.modules.datareport.service.SellRecordService;
import com.chisapp.modules.doctorworkstation.bean.ClinicalHistory;
import com.chisapp.modules.doctorworkstation.bean.SellPrescription;
import com.chisapp.modules.doctorworkstation.dao.SellPrescriptionMapper;
import com.chisapp.modules.doctorworkstation.service.ClinicalHistoryService;
import com.chisapp.modules.doctorworkstation.service.SellPrescriptionService;
import com.chisapp.modules.member.service.MemberService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Tandy
 * @Date: 2019/12/9 16:47
 * @Version 1.0
 */
@Service
public class SellPrescriptionServiceImpl implements SellPrescriptionService {

    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private SellPrescriptionMapper sellPrescriptionMapper;
    @Autowired
    public void setSellPrescriptionMapper(SellPrescriptionMapper sellPrescriptionMapper) {
        this.sellPrescriptionMapper = sellPrescriptionMapper;
    }

    private SellRecordService sellRecordService;
    @Autowired
    public void setSellRecordService(SellRecordService sellRecordService) {
        this.sellRecordService = sellRecordService;
    }

    private MemberService memberService;
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    private ClinicalHistoryService clinicalHistoryService;
    @Autowired
    public void setClinicalHistoryService(ClinicalHistoryService clinicalHistoryService) {
        this.clinicalHistoryService = clinicalHistoryService;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void saveOrUpdateToCache(String prescriptionJson) {
        // 将 JSON 解析成对应的对象集合
        List<SellPrescription> prescriptionList =
                JSONUtils.parseJsonToObject(prescriptionJson, new TypeReference<List<SellPrescription>>() {});

        // 初始化流水号、明细号
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        String lsh = this.getSellPrescriptionLsh(prescriptionList, user);
        int mxh = 1; // 初始化明细号

        // 获取会员信息
        Integer mrmMemberId = this.getSellPrescriptionMrmMemberId(prescriptionList);
        Map<String, Object> member = memberService.getByIdForTreatment(mrmMemberId); // 获取会员

        // 检查病例是否正确
        this.checkDwtClinicalHistoryId(prescriptionList, user, mrmMemberId);

        // 赋值对应的属性
        for (SellPrescription prescription : prescriptionList) {
            prescription.setLsh(lsh); // 写入流水号
            prescription.setMxh(String.valueOf(mxh++)); // 写入明细号
            prescription.setSysClinicId(user.getSysClinicId()); // 写入机构ID
            prescription.setSysDoctorId(user.getId()); // 医生ID
            prescription.setCreationDate(new Date()); // 操作时间
            this.setMemberInfo(prescription, member); // 设置会员信息
        }

        // 将赋值后的 list 转成 JSON 保存到缓存
        prescriptionJson = JSONUtils.parseObjectToJson(prescriptionList);
        // 向缓存插入一条记录(key, lsh, prescriptionJson)
        stringRedisTemplate.opsForHash().put(this.getRedisHashKey(), lsh, prescriptionJson);
        // 生成对应的销售记录
        this.saveOrUpdateToSellRecordCache(prescriptionList);
    }

    /**
     * 获取处方流水号
     * @param sellPrescriptionList
     * @param user
     * @return
     */
    private String getSellPrescriptionLsh (List<SellPrescription> sellPrescriptionList, User user) {
        String lsh = null;

        List<String> lshList = sellPrescriptionList.stream()
                .map(SellPrescription::getLsh)
                .distinct()
                .filter(filterLsh -> (filterLsh != null && !filterLsh.trim().equals("")))
                .collect(Collectors.toList());

        if (lshList.size() == 0) {
            lsh = KeyUtils.getLSH(user.getId());
        }

        if (lshList.size() == 1) {
            lsh = lshList.get(0);
        }

        if (lshList.size() > 1) {
            throw new RuntimeException("一个处方不能有多个流水号");
        }

        return lsh;
    }

    private Integer getSellPrescriptionMrmMemberId (List<SellPrescription> sellPrescriptionList) {
        Integer mrmMemberId = null;

        List<Integer> mrmMemberIdList = sellPrescriptionList.stream()
                .map(SellPrescription::getMrmMemberId)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (mrmMemberIdList.size() == 0) {
            throw new RuntimeException("未能获取会员信息");
        }

        if (mrmMemberIdList.size() == 1) {
            mrmMemberId = mrmMemberIdList.get(0);
        }

        if (mrmMemberIdList.size() > 1) {
            throw new RuntimeException("一个处方中不能有多个会员信息");
        }

        return mrmMemberId;
    }

    /**
     * 检查病例是否正确
     * @param sellPrescriptionList
     * @param user
     * @param mrmMemberId
     */
    private void checkDwtClinicalHistoryId(List<SellPrescription> sellPrescriptionList, User user, Integer mrmMemberId) {
        // 查看是否可以获取到病例 ID
        List<Integer> idList = sellPrescriptionList.stream()
                .map(SellPrescription::getDwtClinicalHistoryId)
                .distinct()
                .filter(id -> (id != null && id != 0))
                .collect(Collectors.toList());
        if (idList.size() == 0) {
            throw new RuntimeException("未能获取病例信息, 请刷新再次查看病例是否填写");
        }

        if (idList.size() > 1) {
            throw new RuntimeException("同一个处方中不能包含多个病例");
        }

        Integer id = idList.get(0);
        ClinicalHistory clinicalHistory = this.clinicalHistoryService.getById(id);

        if (clinicalHistory == null) {
            throw new RuntimeException("未能获取病例信息, 请刷新再次查看病例是否填写");
        }

        if (clinicalHistory.getSysDoctorId().intValue() != user.getId().intValue() ||
            clinicalHistory.getMrmMemberId().intValue() != mrmMemberId.intValue()) {
            throw new RuntimeException("不对应的病例信息");
        }

        if (clinicalHistory.getFinished()) {
            throw new RuntimeException("该病例已归档, 不能继续使用");
        }
    }

    private void saveOrUpdateToSellRecordCache(List<SellPrescription> prescriptionList) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        List<SellRecord> recordList = new ArrayList<>();
        for (SellPrescription prescription : prescriptionList) {
            SellRecord record = new SellRecord();

            // record.setCreationDate(); 结账操作对其赋值
            // record.setLsh(); 结账操作对其赋值
            // record.setMxh(); 结账操作对其赋值
            record.setDwtSellPrescriptionLsh(prescription.getLsh()); // 处方流水号
            record.setSysSellWayId(SellWayEnum.PRESCRIPTION.getIndex()); // 销售方式ID
            record.setSysSellTypeId(prescription.getSysSellTypeId()); // 销售类型ID
            record.setEntityTypeId(prescription.getEntityTypeId()); // 销售实体类型ID
            record.setEntityId(prescription.getEntityId()); // 销售实体ID
            record.setRetailPrice(prescription.getRetailPrice()); // 建议零售价
            record.setActualRetailPrice(prescription.getRetailPrice()); // 实际零售价
            // record.setPurchaseTaxRate(); 前台出库操作对其赋值
            // record.setSellTaxRate(); 前台出库操作对其赋值
            record.setDiscountable(prescription.getDiscountable()); // 是否可打折
            // record.setLossable(); 前台出库操作对其赋值
            // record.setIymInventoryId(); 前台出库操作对其赋值
            // record.setPh(); 前台出库操作对其赋值
            // record.setPch(); 前台出库操作对其赋值
            record.setSplitQuantity(prescription.getSplitQuantity()); // 库存拆分数量
            record.setQuantity(prescription.getQuantity() * prescription.getFoldQuantity()); // 销售数量
            // record.setCostPrice(); 前台出库操作对其赋值
            // record.setProducedDate(); 前台出库操作对其赋值
            // record.setExpiryDate(); 前台出库操作对其赋值
            // record.setPemSupplierId(); 前台出库操作对其赋值
            // record.setIymInventoryAddId(); 前台出库操作对其赋值
            record.setMrmMemberId(prescription.getMrmMemberId()); // 会员ID
            record.setMemberDiscountRate(prescription.getMemberDiscountRate()); // 会员折扣率
            record.setSysClinicId(user.getSysClinicId()); // 机构ID
            record.setSellerId(user.getId()); // 销售人、医生 ID
            // record.setOperatorId(); 前台出库操作对其赋值
            // record.setCashierId(); 结账操作对其赋值
            // record.setPayState(); 结账操作对其赋值

            // 记录到缓存但不记录到数据库的属性
            record.setDwtSellPrescriptionDate(prescription.getCreationDate());
            record.setOid(prescription.getOid());
            record.setName(prescription.getName());
            record.setSpecs(prescription.getSpecs());
            record.setUnitsName(prescription.getUnitsName());
            record.setMrmMemberOid(prescription.getMrmMemberOid());
            record.setMrmMemberName(prescription.getMrmMemberName());
            record.setGenderName(prescription.getGenderName());
            record.setPhone(prescription.getPhone());
            record.setOnceContainQuantity(prescription.getOnceContainQuantity());
            record.setRegistrationFeeFlag(false);
            record.setSysDoctorName(prescription.getSysDoctorName());

            recordList.add(record);
        }

        sellRecordService.saveOrUpdateToCache(JSONUtils.parseObjectToJson(recordList));
    }

    @Override
    public void deleteByLshFromCache(String lsh) {
        // 清除销售记录中对应的缓存记录
        this.sellRecordService.deleteByPrescriptionLshFromCache(lsh);
        // 清除销售处方中对应的缓存记录
        stringRedisTemplate.opsForHash().delete(this.getRedisHashKey(), lsh);
    }

    @Override
    public List<SellPrescription> getClinicDoctorGroupListByCriteriaFromCache(Integer sysDoctorId, Integer mrmMemberId, Integer sysSellTypeId, Integer entityTypeId) {
        // 从缓存中获取本机构的所有处方
        List<Object> objectList = stringRedisTemplate.opsForHash().values(this.getRedisHashKey());
        // 用于返回的处方集合
        List<SellPrescription> groupList = new ArrayList<>();
        // 将获取到 List<Object> 转成 List<SellPrescription>
        for (Object o : objectList) {
            // 解析当前处方明细
            List<SellPrescription> prescriptionList =
                    JSONUtils.parseJsonToObject(o.toString(), new TypeReference<List<SellPrescription>>() {});

            // 获取当前解析后的处方List中一条进行过滤判断
            SellPrescription prescription = prescriptionList.get(0);
            if (
                    prescription.getSysDoctorId().intValue() == sysDoctorId.intValue() && // 符合指定医生ID
                    prescription.getMrmMemberId().intValue() == mrmMemberId.intValue() && // 符合指定会员ID
                    prescription.getSysSellTypeId().intValue() == sysSellTypeId.intValue() && // 符合指定销售类型
                    prescription.getEntityTypeId().intValue() == entityTypeId.intValue() // 符合指定处方类型
            ) {
                groupList.add(prescription);
            }
        }

        return groupList;
    }

    @Override
    public List<SellPrescription> getByLshFromCache(String lsh) {
        // 从缓存中获取目标流水号的处方
        Object o = stringRedisTemplate.opsForHash().get(this.getRedisHashKey(), lsh);
        if (o == null) {
            throw new RuntimeException("获取处方记录失败, 请刷新数据");
        }

        // 将获取的 Object 解析 对应的处方明细 List<SellPrescription>
        String prescriptionJson = o.toString();
        return JSONUtils.parseJsonToObject(prescriptionJson, new TypeReference<List<SellPrescription>>() {});
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void saveList(List<SellPrescription> sellPrescriptionList) {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        SellPrescriptionMapper mapper = batchSqlSession.getMapper(SellPrescriptionMapper.class);
        try {
            for (SellPrescription sellPrescription : sellPrescriptionList) {
                mapper.insert(sellPrescription);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public List<SellPrescription> getByClinicalHistoryId(Integer dwtClinicalHistoryId) {
        return sellPrescriptionMapper.selectByClinicalHistoryId(dwtClinicalHistoryId);
    }

    @Override
    public List<Map<String, Object>> getGroupListByCriteria(String [] creationDate, // 处方日期
                                                            String lsh, // 流水号
                                                            Integer sysClinicId, // 机构ID
                                                            Integer sysSellTypeId, // 销售类型 [商品、项目]
                                                            Integer entityTypeId, // 销售实体类型 [西药、中药、卫生材料]
                                                            Integer mrmMemberId, // 会员ID
                                                            String sysDoctorName){
        return sellPrescriptionMapper.selectGroupListByCriteria(creationDate, lsh, sysClinicId, sysSellTypeId, entityTypeId, mrmMemberId, sysDoctorName);
    }

    @Override
    public List<Map<String, Object>> getClinicListByLsh(String lsh, Integer sysClinicId) {
        return sellPrescriptionMapper.selectClinicListByLsh(lsh, sysClinicId);
    }

    @Override
    public List<Map<String, Object>> getByLshForTemplate(String lsh, Integer sysClinicId) {
        return sellPrescriptionMapper.selectByLshForTemplate(lsh, sysClinicId);
    }


}
