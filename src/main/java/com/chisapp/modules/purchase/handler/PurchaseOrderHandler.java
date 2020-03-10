package com.chisapp.modules.purchase.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.purchase.bean.PurchaseOrder;
import com.chisapp.modules.purchase.service.PurchaseOrderService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/9/2 17:35
 * @Version 1.0
 */
@RequestMapping("/purchaseOrder")
@RestController
public class PurchaseOrderHandler {

    private PurchaseOrderService purchaseOrderService;
    @Autowired
    public void setPurchaseOrderService(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    /**
     * 保存采购订单
     * @param mapJson
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("orderJson") == null || map.get("planIdArrayJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }

        String orderJson = map.get("orderJson");
        String planIdArrayJson = map.get("planIdArrayJson");

        List<Integer> planIdList =
                JSONUtils.parseJsonToObject(planIdArrayJson, new TypeReference<List<Integer>>() {});
        for (int i = 0; i < planIdList.size(); i++) {
            if (planIdList.get(i) == null) {
                planIdList.remove(i);
            }
        }
        List<PurchaseOrder> purchaseOrderList =
                JSONUtils.parseJsonToObject(orderJson, new TypeReference<List<PurchaseOrder>>() {});

        purchaseOrderService.save(purchaseOrderList, planIdList);
        return PageResult.success();
    }

    /**
     * 驳回操作
     * @param lsh
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved(String lsh) {
        purchaseOrderService.unapproved(lsh);
        return PageResult.success();
    }

    /**
     * 通过操作
     * @param lsh
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved(String lsh) {
        purchaseOrderService.approved(lsh);
        return PageResult.success();
    }


    /**
     * 获取以流水号进行分组的集合
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param approveState
     * @param pemSupplierName
     * @return
     */
    @GetMapping("/getLshGroupListByCriteria")
    public PageResult getLshGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) Byte approveState,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String pemSupplierName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = purchaseOrderService.getLshGroupListByCriteria(creationDate, approveState, lsh, pemSupplierName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取以商品进行分组的集合
     * @param lsh
     * @return
     */
    @GetMapping("/getGoodsGroupListByLsh")
    public PageResult getGoodsGroupListByLsh(@RequestParam String lsh){
        List<Map<String, Object>> list = purchaseOrderService.getGoodsGroupListByLsh(lsh);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 获取机构未入库的订单汇总记录
     * @return
     */
    @GetMapping("/getClinicLshGroupListByInventoryState")
    public List<Map<String, Object>> getClinicLshGroupListByInventoryState(){
        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return purchaseOrderService.getClinicLshGroupListByInventoryState(user.getSysClinicId(), false);
    }

    /**
     * 根据流水号获取机构对应订单集合
     * @param lsh
     * @return
     */
    @GetMapping("/getClinicListByLsh")
    public PageResult getClinicListByLsh (@RequestParam String lsh) {
        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return PageResult.success().resultSet("list", purchaseOrderService.getClinicListByLsh(lsh, user.getSysClinicId()));
    }

    @GetMapping("/getTrackListByLsh")
    public  List<Map<String, Object>> getTrackListByLsh(@RequestParam String lsh) {
        return purchaseOrderService.getTrackListByLsh(lsh);
    }
}
