package com.chisapp.modules.nurseworkstation.service.impl;

import com.chisapp.common.enums.SellTypeEnum;
import com.chisapp.common.enums.SellWayEnum;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.datareport.bean.SellRecord;
import com.chisapp.modules.datareport.service.SellRecordService;
import com.chisapp.modules.item.bean.Item;
import com.chisapp.modules.item.service.ItemService;
import com.chisapp.modules.nurseworkstation.bean.RegistrationRecord;
import com.chisapp.modules.nurseworkstation.dao.RegistrationRecordMapper;
import com.chisapp.modules.nurseworkstation.service.RegistrationRecordService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/16 13:06
 * @Version 1.0
 */
@Service
public class RegistrationRecordServiceImpl implements RegistrationRecordService {

    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private RegistrationRecordMapper registrationRecordMapper;
    @Autowired
    public void setRegistrationRecordMapper(RegistrationRecordMapper registrationRecordMapper) {
        this.registrationRecordMapper = registrationRecordMapper;
    }

    private ItemService itemService;
    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    private SellRecordService sellRecordService;
    @Autowired
    public void setSellRecordService(SellRecordService sellRecordService) {
        this.sellRecordService = sellRecordService;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void saveOrUpdateToCache(RegistrationRecord registrationRecord) {
        // 获取流水号
        String lsh = registrationRecord.getLsh(); // 初始化流水号

        // 判断是否需要初始化部分属性
        if (registrationRecord.getLsh() == null || registrationRecord.getLsh().trim().equals("")) {
            User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
            lsh = KeyUtils.getLSH(user.getId());

            registrationRecord.setLsh(lsh); // 写入流水号
            registrationRecord.setSysClinicId(user.getSysClinicId()); // 写入机构ID
            registrationRecord.setCreatorId(user.getId()); // 操作人ID
            registrationRecord.setCreatorName(user.getName()); // 操作人姓名
            registrationRecord.setCreationDate(new Date()); // 操作时间
        }

        // 赋值后转成 JSON 保存到缓存
        String recordJson = JSONUtils.parseObjectToJson(registrationRecord);

        // 插入一条挂号记录(key, lsh, prescriptionJson)
        stringRedisTemplate.opsForHash().put(this.getRedisHashKey(), lsh, recordJson);

        // 插入一条收费记录
        this.saveToSellRecordCache(registrationRecord);
    }

    private void saveToSellRecordCache(RegistrationRecord registrationRecord) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 获取挂号费
        Item registrationItem = itemService.getById(registrationRecord.getCimItemId());

        // 创建一条收费记录
        SellRecord sellRecord = new SellRecord();
        // record.setCreationDate(); 结账操作对其赋值
        // record.setLsh(); 结账操作对其赋值
        // record.setMxh(); 结账操作对其赋值
        sellRecord.setDwtSellPrescriptionLsh(registrationRecord.getLsh()); // 处方流水号
        sellRecord.setSysSellWayId(SellWayEnum.PRESCRIPTION.getIndex()); // 销售方式ID
        sellRecord.setSysSellTypeId(SellTypeEnum.ITEM.getIndex()); // 销售类型ID
        sellRecord.setEntityTypeId(registrationItem.getCimItemTypeId()); // 销售实体类型ID
        sellRecord.setEntityId(registrationItem.getId()); // 销售实体ID
        sellRecord.setRetailPrice(registrationItem.getRetailPrice()); // 建议零售价
        sellRecord.setActualRetailPrice(registrationItem.getRetailPrice()); // 实际零售价
        // record.setPurchaseTaxRate(); 项目类型此属性为空
        // record.setSellTaxRate(); 项目类型此属性为空
        sellRecord.setDiscountable(registrationItem.getDiscountable()); // 是否可打折
        // record.setLossable(); 项目类型此属性为空
        // record.setIymInventoryId(); 项目类型此属性为空
        // record.setPh(); 项目类型此属性为空
        // record.setPch(); 项目类型此属性为空
        sellRecord.setSplitQuantity(1); // 库存拆分数量
        sellRecord.setQuantity(1); // 销售数量
        // record.setCostPrice(); 项目类型此属性为空
        // record.setProducedDate(); 项目类型此属性为空
        // record.setExpiryDate(); 项目类型此属性为空
        // record.setPemSupplierId(); 项目类型此属性为空
        // record.setIymInventoryAddId(); 项目类型此属性为空
        sellRecord.setMrmMemberId(registrationRecord.getMrmMemberId()); // 会员ID
        sellRecord.setMemberDiscountRate(registrationRecord.getMemberDiscountRate()); // 会员折扣率
        sellRecord.setSysClinicId(user.getSysClinicId()); // 机构ID
        sellRecord.setSellerId(user.getId()); // 销售人、医生 ID
        // record.setOperatorId(); 项目类型此属性为空
        // record.setCashierId(); 结账操作对其赋值
        // record.setPayState(); 结账操作对其赋值

        // 记录到缓存但不记录到数据库的属性
        sellRecord.setDwtSellPrescriptionDate(registrationRecord.getCreationDate());
        sellRecord.setOid(null);
        sellRecord.setName(registrationItem.getName());
        sellRecord.setSpecs(null);
        sellRecord.setUnitsName(null);
        sellRecord.setMrmMemberOid(registrationRecord.getMrmMemberOid());
        sellRecord.setMrmMemberName(registrationRecord.getMrmMemberName());
        sellRecord.setGenderName(registrationRecord.getGenderName());
        sellRecord.setPhone(registrationRecord.getPhone());
        sellRecord.setOnceContainQuantity(1);
        sellRecord.setRegistrationFeeFlag(true);
        sellRecord.setSysDoctorName(registrationRecord.getSysDoctorName());

        // 保存该条记录
        List<SellRecord> sellRecordList = new ArrayList<>();
        sellRecordList.add(sellRecord);
        sellRecordService.saveOrUpdateToCache(JSONUtils.parseObjectToJson(sellRecordList));
    }

    @Override
    public void deleteByLshFromCache(String lsh) {
        // 删除挂号记录中的记录
        stringRedisTemplate.opsForHash().delete(this.getRedisHashKey(), lsh);
        // 删除销售记录中的记录
        sellRecordService.deleteByPrescriptionLshFromCache(lsh);
    }

    @Override
    public RegistrationRecord getByLshFromCache(String lsh) {
        Object o = stringRedisTemplate.opsForHash().get(this.getRedisHashKey(), lsh);
        if (o == null) {
            throw new RuntimeException("获取挂号记录缓存失败");
        }
        return JSONUtils.parseJsonToObject(o.toString(), new TypeReference<RegistrationRecord>() {});
    }

    @Override
    public List<RegistrationRecord> getClinicListFromCache() {
        // 从缓存中获取本机构的所有挂号记录
        List<Object> objectList = stringRedisTemplate.opsForHash().values(this.getRedisHashKey());

        // 将获取到 List<Object> 转成 List<RegistrationRecord>
        List<RegistrationRecord> list = new ArrayList<>();
        for (Object o : objectList) {
            // 将 JSON 解析成对应的对象
            String recordJson = o.toString();
            RegistrationRecord record = JSONUtils.parseJsonToObject(recordJson, new TypeReference<RegistrationRecord>() {});
            list.add(record);
        }
        return this.registrationRecordListSort(list);
    }

    @Override
    public List<RegistrationRecord> getClinicListByDoctorIdFromCache(Integer doctorId) {
        // 从缓存中获取本机构的所有挂号记录
        List<Object> objectList = stringRedisTemplate.opsForHash().values(this.getRedisHashKey());

        // 将获取到 List<Object> 转成 List<RegistrationRecord>
        List<RegistrationRecord> list = new ArrayList<>();
        for (Object o : objectList) {
            // 将 JSON 解析成对应的对象
            String recordJson = o.toString();
            RegistrationRecord record = JSONUtils.parseJsonToObject(recordJson, new TypeReference<RegistrationRecord>() {});
            if (record.getSysDoctorId().intValue() == doctorId.intValue()) {
                list.add(record);
            }
        }
        return this.registrationRecordListSort(list);
    }

    @Override
    public void save(RegistrationRecord registrationRecord) {
        registrationRecordMapper.insert(registrationRecord);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(Integer sysClinicId,
                                                             String[] creationDate,
                                                             String mrmMemberName,
                                                             String cimItemName,
                                                             String sysDoctorName) {
        return registrationRecordMapper.selectClinicListByCriteria(sysClinicId, creationDate, mrmMemberName, cimItemName, sysDoctorName);
    }


}
