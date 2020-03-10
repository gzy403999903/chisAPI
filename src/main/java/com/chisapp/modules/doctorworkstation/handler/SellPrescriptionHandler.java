package com.chisapp.modules.doctorworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.enums.SellTypeEnum;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.doctorworkstation.bean.SellPrescription;
import com.chisapp.modules.doctorworkstation.service.SellPrescriptionService;
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
 * @Date: 2019/11/20 14:26
 * @Version 1.0
 */
@RequestMapping("/sellPrescription")
@RestController
public class SellPrescriptionHandler {

    private SellPrescriptionService sellPrescriptionService;
    @Autowired
    public void setSellPrescriptionService(SellPrescriptionService sellPrescriptionService) {
        this.sellPrescriptionService = sellPrescriptionService;
    }

    /**
     * 将处暂存到缓存 --- 西药
     * @param mapJson
     * @return
     */
    @PostMapping("/saveOrUpdateToCacheForWesternDrugs")
    public PageResult saveOrUpdateToCacheForWesternDrugs(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("prescriptionJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }
        sellPrescriptionService.saveOrUpdateToCache(map.get("prescriptionJson"));
        return PageResult.success();
    }

    /**
     * 将处暂存到缓存 --- 中药
     * @param mapJson
     * @return
     */
    @PostMapping("/saveOrUpdateToCacheForChineseDrugs")
    public PageResult saveOrUpdateToCacheForChineseDrugs(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("prescriptionJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }
        sellPrescriptionService.saveOrUpdateToCache(map.get("prescriptionJson"));
        return PageResult.success();
    }

    /**
     * 将处暂存到缓存 --- 卫生材料
     * @param mapJson
     * @return
     */
    @PostMapping("/saveOrUpdateToCacheForHygienicMaterial")
    public PageResult saveOrUpdateToCacheForHygienicMaterial(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("prescriptionJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }
        sellPrescriptionService.saveOrUpdateToCache(map.get("prescriptionJson"));
        return PageResult.success();
    }

    /**
     * 将处暂存到缓存 --- 医技处方
     * @param mapJson
     * @return
     */
    @PostMapping("/saveOrUpdateToCacheForMedicalItem")
    public PageResult saveOrUpdateToCacheForMedicalItem(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("prescriptionJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }
        sellPrescriptionService.saveOrUpdateToCache(map.get("prescriptionJson"));
        return PageResult.success();
    }

    /**
     * 将处暂存到缓存 --- 理疗处方
     * @param mapJson
     * @return
     */
    @PostMapping("/saveOrUpdateToCacheForAdjuvantItem")
    public PageResult saveOrUpdateToCacheForAdjuvantItem(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("prescriptionJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }
        sellPrescriptionService.saveOrUpdateToCache(map.get("prescriptionJson"));
        return PageResult.success();
    }

    /**
     * 将处暂存到缓存 --- 其他项目
     * @param mapJson
     * @return
     */
    @PostMapping("/saveOrUpdateToCacheForOtherItem")
    public PageResult saveOrUpdateToCacheForOtherItem(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("prescriptionJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }
        sellPrescriptionService.saveOrUpdateToCache(map.get("prescriptionJson"));
        return PageResult.success();
    }

    /**
     * 根据 lsh 将处方从缓存中删除
     * @param lsh
     * @return
     */
    @DeleteMapping("/deleteByLshFromCache")
    public PageResult deleteByLshFromCache(@RequestParam("lsh") String lsh) {
        sellPrescriptionService.deleteByLshFromCache(lsh);
        return PageResult.success();
    }

    /**
     * 获取对应机构对应医生对应会员对应类型的处方
     * @param mrmMemberId
     * @return
     */
    @GetMapping("/getClinicDoctorGroupListByCriteriaFromCache")
    public PageResult getClinicDoctorGroupListByCriteriaFromCache(@RequestParam("mrmMemberId") Integer mrmMemberId,
                                                                  @RequestParam("sysSellTypeId") Integer sysSellTypeId,
                                                                  @RequestParam("entityTypeId") Integer entityTypeId) {

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        List<SellPrescription> list = sellPrescriptionService.getClinicDoctorGroupListByCriteriaFromCache(
                        user.getId(), mrmMemberId, sysSellTypeId, entityTypeId);
        return PageResult.success().resultSet("list", list);
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 根据流水号获取对应的处方明细
     * @param lsh
     * @return
     */
    @GetMapping("/getByLshFromCache")
    public PageResult getByLshFromCache(@RequestParam("lsh") String lsh) {
        List<SellPrescription> list = sellPrescriptionService.getByLshFromCache(lsh);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 根据条件获取处方汇总
     * [用于医生调用历史处方 参数有必填项]
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysSellTypeId
     * @param entityTypeId
     * @param mrmMemberId
     * @param sysDoctorName
     * @return
     */
    @GetMapping("/getGroupListByCriteriaForTemplate")
    public PageResult getGroupListByCriteriaForTemplate (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,  // 处方日期
            @RequestParam Integer sysSellTypeId, // 销售类型 [商品、项目]
            @RequestParam Integer entityTypeId, // 销售实体类型 [西药、中药、卫生材料]
            @RequestParam Integer mrmMemberId,  // 会员ID
            @RequestParam(required = false) String sysDoctorName){ // 创建处方医生的姓名

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellPrescriptionService.getGroupListByCriteria(creationDate, null, null, sysSellTypeId, entityTypeId, mrmMemberId, sysDoctorName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据条件获取处方汇总
     * [处方配药调用]
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param entityTypeId
     * @param sysDoctorName
     * @return
     */
    @GetMapping("/getClinicGroupListByCriteriaForPerform")
    public PageResult getClinicGroupListByCriteriaForPerform (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,  // 处方日期
            @RequestParam(required = false) String lsh,  // 流水号
            @RequestParam(required = false) Integer entityTypeId,  // 销售实体类型 [西药、中药、卫生材料]
            @RequestParam(required = false) String sysDoctorName){ // 创建处方医生的姓名

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellPrescriptionService.getGroupListByCriteria(creationDate, lsh, user.getSysClinicId(), SellTypeEnum.GOODS.getIndex(), entityTypeId, null, sysDoctorName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 打印处方
     * [仅作为权限判断]
     * @return
     */
    @GetMapping("/printPrescription")
    public PageResult printPrescription () {
        return PageResult.success();
    }

    /**
     * 根据流水号获取机构处方明细
     * @param lsh
     * @return
     */
    @GetMapping("/getClinicListByLsh")
    public PageResult getClinicListByLsh(@RequestParam String lsh) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        List<Map<String, Object>> list = this.sellPrescriptionService.getClinicListByLsh(lsh, user.getSysClinicId());
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 根据流水号获取处方明细
     * [用于医生调用历史处方]
     * @param lsh
     * @return
     */
    @GetMapping("/getByLshForTemplate")
    public PageResult getByLshForTemplate (@RequestParam String lsh){
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        List<Map<String, Object>> list = sellPrescriptionService.getByLshForTemplate(lsh, user.getSysClinicId());
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 根据病例ID 获取对应的处方集合
     * @param dwtClinicalHistoryId
     * @return
     */
    @GetMapping("/getByClinicalHistoryId")
    public PageResult getByClinicalHistoryId(@RequestParam("dwtClinicalHistoryId") Integer dwtClinicalHistoryId) {
        List<SellPrescription> list = this.sellPrescriptionService.getByClinicalHistoryId(dwtClinicalHistoryId);
        return PageResult.success().resultSet("list", list);
    }

}
