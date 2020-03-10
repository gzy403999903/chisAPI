package com.chisapp.modules.nurseworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.nurseworkstation.service.ChargeFeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 处方结算
 * @Author: Tandy
 * @Date: 2019/12/23 15:12
 * @Version 1.0
 */
@RequestMapping("/chargeFee")
@RestController
public class ChargeFeeHandler {

    private ChargeFeeService chargeFeeService;
    @Autowired
    public void setChargeFeeService(ChargeFeeService chargeFeeService) {
        this.chargeFeeService = chargeFeeService;
    }

    /**
     * 处方结账操作
     * @RequestBody 只能接收一个参数
     * @param mapJson
     * @return
     */
    @PostMapping("/saveForPrescription")
    public PageResult saveForPrescription(@RequestBody String mapJson) {
        Map<String, Object> map = JSONUtils.parseJsonToObject(mapJson,new TypeReference<Map<String, Object>>() {});
        Integer mrmMemberId;
        String paymentRecordJson;
        String sellRecordJson;
        try {
           mrmMemberId = Integer.parseInt(map.get("mrmMemberId").toString());
           paymentRecordJson = map.get("paymentRecordJson").toString();
           sellRecordJson = map.get("sellRecordJson").toString();
        } catch (NullPointerException e) {
            return PageResult.fail().msg("缺少请求参数");
        }

        String lsh = chargeFeeService.saveForPrescription(mrmMemberId, paymentRecordJson, sellRecordJson);
        return PageResult.success().resultSet("lsh", lsh);
    }

    /**
     * POS结账操作
     * @RequestBody 只能接收一个参数
     * @param mapJson
     * @return
     */
    @PostMapping("/saveForPos")
    public PageResult saveForPos(@RequestBody String mapJson) {
        Map<String, Object> map = JSONUtils.parseJsonToObject(mapJson,new TypeReference<Map<String, Object>>() {});
        Integer mrmMemberId;
        String paymentRecordJson;
        String sellRecordJson;
        try {
            mrmMemberId = Integer.parseInt(map.get("mrmMemberId").toString());
        } catch (NullPointerException e) {
            mrmMemberId = null;
        }

        try {
            paymentRecordJson = map.get("paymentRecordJson").toString();
            sellRecordJson = map.get("sellRecordJson").toString();
        } catch (NullPointerException e) {
            return PageResult.fail().msg("缺少请求参数");
        }

        String lsh = chargeFeeService.saveForPos(mrmMemberId, paymentRecordJson, sellRecordJson);
        return PageResult.success().resultSet("lsh", lsh);
    }

    /**
     * 退费操作
     * @RequestBody
     * @param mapJson
     * @return
     */
    @PostMapping("/saveForReturned")
    public PageResult saveForReturned(@RequestBody String mapJson) {
        Map<String, Object> map = JSONUtils.parseJsonToObject(mapJson,new TypeReference<Map<String, Object>>() {});
        Integer mrmMemberId;
        Boolean neglectQuantity;
        String paymentRecordJson;
        String sellRecordJson;

        try {
            mrmMemberId = Integer.parseInt(map.get("mrmMemberId").toString());
        } catch (NullPointerException e) {
            mrmMemberId = null;
        }

        try {
            neglectQuantity = Boolean.parseBoolean(map.get("neglectQuantity").toString());
            paymentRecordJson = map.get("paymentRecordJson").toString();
            sellRecordJson = map.get("sellRecordJson").toString();
        } catch (NullPointerException e) {
            return PageResult.fail().msg("缺少请求参数");
        }

        chargeFeeService.saveForReturned(mrmMemberId, neglectQuantity, paymentRecordJson, sellRecordJson);
        return PageResult.success();
    }

    /**
     * 根据流水号获取收费明细
     * @param lsh
     * @return
     */
    @GetMapping("/getChargeFeeRecordByLsh")
    public PageResult getChargeFeeRecordByLsh(@RequestParam String lsh) {
        return PageResult.success().resultSet("chargeFeeRecord", this.chargeFeeService.getChargeFeeRecordByLsh(lsh));
    }

    /**
     * 打印收费单 (仅做权限判断使用)
     * @return
     */
    @GetMapping("/printChargeFeeBill")
    public PageResult printChargeFeeBill () {
        return PageResult.success();
    }


}
