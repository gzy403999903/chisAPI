package com.chisapp.modules.purchase.service;

import com.chisapp.modules.purchase.bean.PurchasePlan;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/28 13:58
 * @Version 1.0
 */
public interface PurchasePlanService {

    /**
     * 保存操作
     * @param purchasePlanList
     */
    @Transactional
    void save(List<PurchasePlan> purchasePlanList);

    /**
     * 撤销操作
     * @param planList
     */
    @Transactional
    void cancel(List<PurchasePlan> planList);

    /**
     * 驳回操作
     * @param planList
     */
    @Transactional
    void unapproved(List<PurchasePlan> planList);

    /**
     * 待采购操作
     * @param planList
     */
    @Transactional
    void purchasing(List<PurchasePlan> planList);

    /**
     * 通过操作
     * @param planList
     */
    @Transactional
    void approved(List<PurchasePlan> planList);

    /**
     * 查询机构计划明细
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
     * 根据条件查询
     * @param creationDate
     * @param approveState
     * @param sysClinicName
     * @param gsmGoodsName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] creationDate,
                                            Byte approveState,
                                            String sysClinicName,
                                            String gsmGoodsName);

    /**
     * 审核采购计划查询
     * @param creationDate
     * @param gsmGoodsName
     * @return
     */
    List<Map<String, Object>> getPendingGroupListByCriteria(String[] creationDate, String gsmGoodsName);

    /**
     * 订单采购计划汇总查询
     * @param approveDate
     * @return
     */
    List<Map<String, Object>> getPurchasingGroupListByCriteria(String[] approveDate);

    /**
     * 订单机构采购计划汇总查询
     * @param planIdList
     * @return
     */
    List<Map<String, Object>> getClinicGroupListByPlanIdList(List<Integer> planIdList);

    /**
     * 获取对应 ID 的采购计划
     * @param planIdList
     * @return
     */
    List<PurchasePlan> getByPlanIdList(List<Integer> planIdList);

    /* ------------------------------------------------------------------------------------------------------------- */

    /**
     * 检查明细是否全部符合给定的审批状态
     * @param list
     * @return
     */
    default Boolean examineApproveState(List<PurchasePlan> list, Byte approveState) {
        if (list.isEmpty()) {
            throw new RuntimeException("采购计划明细不能为空");
        }

        for (PurchasePlan plan : list) {
            if (plan.getApproveState().byteValue() != approveState.byteValue()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 将集合按商品ID分类 并对 计划数量、建议数量进行累加
     * 该方法会在 map 中加入一个 idArray 进行返回 其中记录了分组商品的所有计划ID
     *
     * @param mapList 需要进行分组的源数据
     * @param groupField 进行分组的字段
     * @return
     */
    default List<Map<String, Object>> getGroupList(List<Map<String, Object>> mapList, String[] groupField) {
        // 创建分组 map
        Map<String, Map<String, Object>> groupMap = new LinkedHashMap<>();

        // 开始分组
        for (int i = 0; i < mapList.size(); i++) {
            // 获取当前 map
            Map<String, Object> map = mapList.get(i);
            // 拼装 key
            String key = "";
            for (int j = 0; j < groupField.length; j++) {
                if (map.get(groupField[j]) == null) {
                    throw new RuntimeException("未能获取对应的分组字段");
                }
                key += map.get(groupField[j]).toString();
            }
            // 尝试通过 key 从分组map 中获取
            Map<String, Object> group = groupMap.get(key);

            if (group == null) {
                map.put("idArray", map.get("id"));
            } else {
                // 汇总计划数量
                map.put("quantity", Integer.parseInt(map.get("quantity").toString()) + Integer.parseInt(group.get("quantity").toString()));
                // 汇总建议数量
                map.put("recommendQuantity", Integer.parseInt(map.get("recommendQuantity").toString()) + Integer.parseInt(group.get("recommendQuantity").toString()));
                // 汇总计划ID
                map.put("idArray", map.get("id") + "," + group.get("idArray"));
            }
            groupMap.put(key, map);
        }

        // 将分组后的数据转成 List 返回
        mapList.clear();
        for (String key : groupMap.keySet()) {
            mapList.add(groupMap.get(key));
        }
        return mapList;
    }

















}
