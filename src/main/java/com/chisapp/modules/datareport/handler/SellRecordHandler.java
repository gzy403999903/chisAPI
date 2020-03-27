package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.datareport.bean.SellRecord;
import com.chisapp.modules.datareport.service.SellRecordService;
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
 * @Date: 2019/12/6 14:29
 * @Version 1.0
 */
@RequestMapping("/sellRecord")
@RestController
public class SellRecordHandler {

    private SellRecordService sellRecordService;
    @Autowired
    public void setSellRecordService(SellRecordService sellRecordService) {
        this.sellRecordService = sellRecordService;
    }

    /**
     * 将销售记录暂存到缓存
     * @param mapJson
     * @return
     */
    @PostMapping("/saveOrUpdateToCache")
    public PageResult saveOrUpdateToCache(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("sellRecordJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }
        sellRecordService.saveOrUpdateToCache(map.get("sellRecordJson"));
        return PageResult.success();
    }

    /**
     * 获取对应机构 所有会员的汇总记录
     * @return
     */
    @GetMapping("/getClinicMemberGroupListFromCache")
    public PageResult getClinicMemberGroupListFromCache () {
        List<SellRecord> list = sellRecordService.getClinicMemberGroupListFromCache();
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 获取对应机构对应会员销售明细记录
     * @param mrmMemberId
     * @return
     */
    @GetMapping("/getClinicListByMrmMemberIdFromCache")
    public PageResult getClinicListByMrmMemberIdFromCache (@RequestParam("mrmMemberId") Integer mrmMemberId) {
        List<SellRecord> list = sellRecordService.getClinicListByMrmMemberIdFromCache(mrmMemberId);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 获取对应机构 所有药品处方的汇总记录
     * @return
     */
    @GetMapping("/getClinicDrugsPrescriptionGroupListFromCache")
    public PageResult getClinicDrugsPrescriptionGroupListFromCache() {
        List<SellRecord> list = sellRecordService.getClinicDrugsPrescriptionGroupListFromCache();
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 根据处方流水号获取对应的销售记录明细
     * @return
     */
    @GetMapping("/getClinicListByPrescriptionLshFromCache")
    public PageResult getClinicListByPrescriptionLshFromCache(@RequestParam("prescriptionLsh") String prescriptionLsh) {
        List<SellRecord> list = sellRecordService.getClinicListByPrescriptionLshFromCache(prescriptionLsh);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 获取应机构 对应医生 对应会员的销售明细, 并计算出一些结果进行返回
     * [医生问诊调用: 显示处方数量, 合计金额, 中药处方数量, 中药克数]
     * @param mrmMemberId
     * @return
     */
    @GetMapping("/countPrescriptionByCriteriaFromCache")
    public PageResult countPrescriptionByCriteriaFromCache(@RequestParam Integer mrmMemberId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> countMap = sellRecordService.countPrescriptionByCriteriaFromCache(user.getId(), mrmMemberId);
        return PageResult.success().resultSet("countMap", countMap);
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 发票开票
     * @param idListJson
     * @param invoiceTypeId
     * @param invoiceNo
     * @return
     */
    @PutMapping("/updateInvoiceByIdList")
    public PageResult updateInvoiceByIdList (String idListJson, Integer invoiceTypeId, String invoiceNo) {
        List<Integer> idList = JSONUtils.parseJsonToObject(idListJson, new TypeReference<List<Integer>>() {});
        this.sellRecordService.updateInvoiceByIdList(idList, invoiceTypeId, invoiceNo);
        return PageResult.success();
    }

    /**
     * 获取对应机构的收费明细汇总
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param mrmMemberName
     * @return
     */
    @GetMapping("/getClinicGroupListByCriteria")
    public PageResult getClinicGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String mrmMemberName){

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordService.getClinicGroupListByCriteria(user.getSysClinicId(), creationDate, lsh, mrmMemberName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据流水号获取未退费的销售明细
     * [退费调用]
     * @param lsh
     * @return
     */
    @GetMapping("/getClinicUnreturnedListByLshForReturned")
    public PageResult getClinicUnreturnedListByLshForReturned (@RequestParam String lsh){
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Map<String, Object>> list = sellRecordService.getClinicUnreturnedListByLsh(user.getSysClinicId(), lsh);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 根据流水号获取未退费的销售明细
     * [开发票调用]
     * @param lsh
     * @return
     */
    @GetMapping("/getClinicUnreturnedListByLshForInvoice")
    public PageResult getClinicUnreturnedListByLshForInvoice (@RequestParam String lsh){
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Map<String, Object>> list = sellRecordService.getClinicUnreturnedListByLsh(user.getSysClinicId(), lsh);
        return PageResult.success().resultSet("list", list);
    }

}
