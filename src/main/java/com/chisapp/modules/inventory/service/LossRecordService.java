package com.chisapp.modules.inventory.service;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.inventory.bean.LossRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/2 23:39
 * @Version 1.0
 */
public interface LossRecordService {

    /**
     * 保存操作
     * @param lossRecordList
     */
    @Transactional
    void saveList(List<LossRecord> lossRecordList);

    /**
     * 更新单据状态
     * @param approverId
     * @param approveDate
     * @param approveState
     * @param lsh
     */
    @Transactional
    void updateApproveState(Integer approverId, Date approveDate, Byte approveState, String lsh);

    /**
     * 驳回操作
     * @param lsh
     */
    @Transactional
    void unapproved(String lsh);

    /**
     * 通过操作
     * @param lsh
     */
    @Transactional
    void approved(String lsh);

    /**
     * 根据条件获取领用明细
     * @param sysClinicId
     * @param creationDate
     * @param lsh
     * @param sysClinicName
     * @param approveState
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer sysClinicId,
                                            String[] creationDate,
                                            String lsh,
                                            String sysClinicName,
                                            Byte approveState);

    /**
     * 根据条件获取机构领用单汇总
     * @param sysClinicId
     * @param creationDate
     * @param lsh
     * @param approveState
     * @return
     */
    List<Map<String, Object>> getClinicGroupListByCriteria(Integer sysClinicId,
                                                           String[] creationDate,
                                                           String lsh,
                                                           Byte approveState);

    /**
     * 根据流水号获取领用明细
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getByLsh(String lsh);

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * 将 mapList 转成 ObjectList
     * @param listMap
     * @return
     */
    default List<LossRecord> parseToObjectList(List<Map<String, Object>> listMap) {
        String json = JSONUtils.parseObjectToJson(listMap);
        return JSONUtils.parseJsonToObject(json, new TypeReference<List<LossRecord>>() {});
    }

    /***
     * 检查明细是否全部为给定状态
     * @param list
     * @param approveState
     * @return
     */
    default Boolean examineApproveState(List<LossRecord> list, Byte approveState) {
        if (list.isEmpty()) {
            throw new RuntimeException("明细不能为空");
        }

        for (LossRecord lossRecord : list) {
            if (lossRecord.getApproveState().byteValue() != approveState.byteValue()) {
                return false;
            }
        }
        return true;
    }


}
