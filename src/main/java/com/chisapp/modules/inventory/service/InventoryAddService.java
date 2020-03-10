package com.chisapp.modules.inventory.service;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.inventory.bean.InventoryAdd;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/9/20 17:47
 * @Version 1.0
 */
public interface InventoryAddService {

    /**
     * 添加操作
     * @param inventoryAddList
     * @param orderLsh
     * @param actionType
     */
    @Transactional
    void save(List<InventoryAdd> inventoryAddList, String orderLsh, Byte actionType);

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
     * 根据条件查询机构入库明细
     * @param creationDate
     * @param sysClinicId
     * @param approveState
     * @param actionType
     * @param pemSupplierName
     * @return
     */
    List<Map<String, Object>> getClinicListByCriteria(String[] creationDate,
                                                      Integer sysClinicId,
                                                      Byte approveState,
                                                      Byte actionType,
                                                      String pemSupplierName);

    /**
     * 根据条件查询机构入库汇总记录
     * @param creationDate
     * @param sysClinicId
     * @param approveState
     * @param pemSupplierName
     * @return
     */
    List<Map<String, Object>> getClinicLshGroupListByCriteria(String[] creationDate,
                                                              Integer sysClinicId,
                                                              Byte approveState,
                                                              String pemSupplierName);

    /**
     * 根据流水号获取对应的明细
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getByLsh(String lsh);

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 将 mapList 转成 ObjectList
     * @param listMap
     * @return
     */
    default List<InventoryAdd> parseToObjectList(List<Map<String, Object>> listMap) {
        String json = JSONUtils.parseObjectToJson(listMap);
        return JSONUtils.parseJsonToObject(json, new TypeReference<List<InventoryAdd>>() {});
    }

    /***
     * 检查明细是否全部为给定状态
     * @param list
     * @param approveState
     * @return
     */
    default Boolean examineApproveState(List<InventoryAdd> list, Byte approveState) {
        if (list.isEmpty()) {
            throw new RuntimeException("入库明细不能为空");
        }

        for (InventoryAdd add : list) {
            if (add.getApproveState().byteValue() != approveState.byteValue()) {
                return false;
            }
        }
        return true;
    }

}
