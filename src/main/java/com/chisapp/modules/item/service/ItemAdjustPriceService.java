package com.chisapp.modules.item.service;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.item.bean.ItemAdjustPrice;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/9 8:33
 * @Version 1.0
 */
public interface ItemAdjustPriceService {

    /**
     * 保存调价明细
     * @param itemAdjustPriceList
     */
    @Transactional
    void save(List<ItemAdjustPrice> itemAdjustPriceList);

    /**
     * 撤销操作
     * @param lsh
     */
    @Transactional
    void cancel(String lsh);

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
     * 根据 lsh 获取对象集合
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getByLsh(String lsh);

    /**
     * 根据条件获取项目调价明细
     * @param creationDate
     * @param name
     * @param approveState
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] creationDate, String name, Byte approveState);

    /**
     * 根据条件获取项目调价汇总
     * @param creationDate
     * @param lsh
     * @param approveState
     * @return
     */
    List<Map<String, Object>> getGroupListByCriteria(String[] creationDate, String lsh, Byte approveState);

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 将 List<Map<String, Object>> 转成 List<ItemAdjustPrice>
     * @param listMap
     * @return
     */
    default List<ItemAdjustPrice> parseMapToItemAdjustPrice(List<Map<String, Object>> listMap) {
        String json = JSONUtils.parseObjectToJson(listMap);
        return JSONUtils.parseJsonToObject(json, new TypeReference<List<ItemAdjustPrice>>() {});
    }

    /**
     * 检查调价单明细是否全部符合给定的审批状态
     * @param itemAdjustPriceList
     * @return
     */
    default Boolean examineApproveState(List<ItemAdjustPrice> itemAdjustPriceList, Byte approveState) {
        if (itemAdjustPriceList.isEmpty()) {
            throw new RuntimeException("调价单明细不能为空");
        }

        for (ItemAdjustPrice iap : itemAdjustPriceList) {
            if (iap.getApproveState().byteValue() != approveState.byteValue()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查单据明细的创建日期是否过期
     * @param itemAdjustPriceList
     * @return
     */
    default Boolean examineCreationDate(List<ItemAdjustPrice> itemAdjustPriceList) {
        if (itemAdjustPriceList.isEmpty()) {
            throw new RuntimeException("调价单明细不能为空");
        }

        // 获取创建日期, 所有明细的创建日期都必须一致
        Date creationDate = itemAdjustPriceList.get(0).getCreationDate();

        // 获取失效日期, 所有明细的过期日期都必须一致
        Date expiryDate = itemAdjustPriceList.get(0).getExpiryDate();

        for (ItemAdjustPrice iap : itemAdjustPriceList) {
            if (creationDate.compareTo(iap.getCreationDate()) != 0) {
                throw new RuntimeException("调价单明细创建日期必须一致");
            }

            if (expiryDate.compareTo(iap.getExpiryDate()) != 0) {
                throw new RuntimeException("调价单明细失效日期必须一致");
            }
        }

        // 当前日期是否在失效日期之前
        return new Date().before(expiryDate);
    }
}
