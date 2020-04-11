package com.chisapp.modules.inventory.service;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.inventory.bean.InventoryAllot;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/15 17:53
 * @Version 1.0
 */
public interface InventoryAllotService {

    /**
     * 保存操作
     * @param allotList
     */
    @Transactional
    void save(List<InventoryAllot> allotList);

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
    @Deprecated
    @Transactional
    void approved(String lsh);

    /**
     * 查询机构仓库调拨单明细
     * @param creationDate
     * @param sysClinicId
     * @param approveState
     * @param gsmGoodsName
     * @return
     */
    List<Map<String, Object>> getClinicListByCriteria(String[] creationDate,
                                                      Integer sysClinicId,
                                                      Byte approveState,
                                                      String gsmGoodsName);

    /**
     * 查询机构仓库调拨单汇总
     * @param creationDate
     * @param sysClinicId
     * @param approveState
     * @return
     */
    List<Map<String, Object>> getClinicLshGroupListByCriteria(String[] creationDate,
                                                              Integer sysClinicId,
                                                              Byte approveState);

    /**
     * 获取流水号对应明细
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getByLsh(String lsh);

    /* ------------------------------------------------------------------------------------------------------------- */
    /**
     * 将 mapList 转成 ObjectList
     * @param listMap
     * @return
     */
    default List<InventoryAllot> parseToObjectList(List<Map<String, Object>> listMap) {
        String json = JSONUtils.parseObjectToJson(listMap);
        return JSONUtils.parseJsonToObject(json, new TypeReference<List<InventoryAllot>>() {});
    }

    /***
     * 检查明细是否全部为给定状态
     * @param list
     * @param approveState
     * @return
     */
    default Boolean examineApproveState(List<InventoryAllot> list, Byte approveState) {
        if (list.isEmpty()) {
            throw new RuntimeException("明细不能为空");
        }

        for (InventoryAllot temp : list) {
            if (temp.getApproveState().byteValue() != approveState.byteValue()) {
                return false;
            }
        }
        return true;
    }






}
