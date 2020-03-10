package com.chisapp.modules.purchase.service;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.purchase.bean.PurchaseOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/9/2 17:06
 * @Version 1.0
 */
public interface PurchaseOrderService {

    /**
     * 保存操作
     * @param purchaseOrderList
     * @param planIdList
     */
    @Transactional
    void save(List<PurchaseOrder> purchaseOrderList, List<Integer> planIdList);

    /**
     * 驳回操作
     * (审核载入的明细是将 商品 进行分组后的结果集,
     * 在执行审核前需要将每一条明细都进行状态核验,
     * 所以需要调用 List<PurchaseOrder> getByLsh(String lsh) 方法获取明细)
     * @param lsh
     */
    @Transactional
    void unapproved(String lsh);

    /**
     * 通过操作
     * (审核载入的明细是将 商品 进行分组后的结果集,
     *  在执行审核前需要将每一条明细都进行状态核验,
     *  所以需要调用 List<PurchaseOrder> getByLsh(String lsh) 方法获取明细)
     * @param lsh
     */
    @Transactional
    void approved(String lsh);

    /**
     * 入库操作
     * (修改对应明细为入库状态)
     * @param inventoryState
     * @param purchaseOrderList
     */
    @Transactional
    void updateInventoryStateByCriteria(Boolean inventoryState, List<PurchaseOrder> purchaseOrderList);

    /**
     * 按流水号进行汇总查询
     * @param creationDate
     * @param approveState
     * @param pemSupplierName
     * @return
     */
    List<Map<String, Object>> getLshGroupListByCriteria(String[] creationDate, Byte approveState, String lsh, String pemSupplierName);

    /**
     * 按商品进行汇总查询
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getGoodsGroupListByLsh(String lsh);

    /**
     * 根据 lsh 获取对应明细
     * (该方法一般是用于获取最新的结果集, 没必要进行缓存)
     * @param lsh
     * @return
     */
    List<PurchaseOrder> getByLsh(String lsh);

    /**
     * 获取机构未入库的订单汇总记录
     * @param sysClinicId
     * @param inventoryState
     * @return
     */
    List<Map<String, Object>> getClinicLshGroupListByInventoryState(Integer sysClinicId, Boolean inventoryState);

    /**
     * 根据流水号获取机构对应订单集合
     * @param lsh
     * @param sysClinicId
     * @return
     */
    List<Map<String, Object>> getClinicListByLsh(String lsh, Integer sysClinicId);

    /**
     * 获取追踪订单明细
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getTrackListByLsh(String lsh);

    /* ------------------------------------------------------------------------------------------------------------- */
    /**
     * 将 mapList 转成 ObjectList
     * @param listMap
     * @return
     */
    default List<PurchaseOrder> convertToObjectList(List<Map<String, Object>> listMap) {
        String json = JSONUtils.parseObjectToJson(listMap);
        return JSONUtils.parseJsonToObject(json, new TypeReference<List<PurchaseOrder>>() {});
    }

    /***
     * 检查明细是否全部为给定状态
     * @param list
     * @param approveState
     * @return
     */
    default Boolean examineApproveState(List<PurchaseOrder> list, Byte approveState) {
        if (list.isEmpty()) {
            throw new RuntimeException("采购订单明细不能为空");
        }

        for (PurchaseOrder order : list) {
            if (order.getApproveState().byteValue() != approveState.byteValue()) {
                return false;
            }
        }
        return true;
    }

}
