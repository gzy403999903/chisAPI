package com.chisapp.modules.inventory.service;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.inventory.bean.InventorySubtract;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/9 17:04
 * @Version 1.0
 */
public interface InventorySubtractService {

    @Transactional
    void save(List<InventorySubtract> subtractList, Byte actionType);

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
     * 查询机构退货明细
     * @param creationDate
     * @param sysClinicId
     * @param approveState
     * @param pemSupplierName
     * @return
     */
    List<Map<String, Object>> getClinicListByCriteria(String[] creationDate,
                                                      Integer sysClinicId,
                                                      Byte approveState,
                                                      String pemSupplierName);

    /**
     * 查询机构退货汇总记录
     * @param creationDate
     * @param sysClinicId
     * @param approveState
     * @param sysClinicName
     * @param pemSupplierName
     * @return
     */
    List<Map<String, Object>> getClinicLshGroupListByCriteria(String[] creationDate,
                                                              Integer sysClinicId,
                                                              Byte approveState,
                                                              String sysClinicName,
                                                              String pemSupplierName);

    /**
     * 根据 LSH 获取退货明细
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
    default List<InventorySubtract> parseToObjectList(List<Map<String, Object>> listMap) {
        String json = JSONUtils.parseObjectToJson(listMap);
        return JSONUtils.parseJsonToObject(json, new TypeReference<List<InventorySubtract>>() {});
    }

    /***
     * 检查明细是否全部为给定状态
     * @param list
     * @param approveState
     * @return
     */
    default Boolean examineApproveState(List<InventorySubtract> list, Byte approveState) {
        if (list.isEmpty()) {
            throw new RuntimeException("退货明细不能为空");
        }

        for (InventorySubtract subtract : list) {
            if (subtract.getApproveState().byteValue() != approveState.byteValue()) {
                return false;
            }
        }
        return true;
    }

}
