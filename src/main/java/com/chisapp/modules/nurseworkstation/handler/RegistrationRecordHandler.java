package com.chisapp.modules.nurseworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.nurseworkstation.bean.RegistrationRecord;
import com.chisapp.modules.nurseworkstation.service.RegistrationRecordService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/16 13:49
 * @Version 1.0
 */
@RequestMapping("/registrationRecord")
@RestController
public class RegistrationRecordHandler {

    private RegistrationRecordService registrationRecordService;
    @Autowired
    public void setRegistrationRecordService(RegistrationRecordService registrationRecordService) {
        this.registrationRecordService = registrationRecordService;
    }

    /**
     * 保存挂号记录到缓存
     * @param registrationRecord
     * @param result
     * @return
     */
    @PostMapping("/saveToCache")
    public PageResult saveToCache(@Valid RegistrationRecord registrationRecord, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        registrationRecordService.saveOrUpdateToCache(registrationRecord);
        return PageResult.success();
    }

    /**
     * 修改挂号记录到缓存
     * @param registrationRecord
     * @param result
     * @return
     */
    @PutMapping("/updateToCache")
    public PageResult updateToCache(@Valid RegistrationRecord registrationRecord, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        registrationRecordService.saveOrUpdateToCache(registrationRecord);
        return PageResult.success();
    }

    /**
     * 从缓存中删除一个挂号记录
     * @param lsh
     * @return
     */
    @DeleteMapping("/deleteByLshFromCache")
    public PageResult deleteByLshFromCache(@RequestParam("lsh") String lsh) {
        registrationRecordService.deleteByLshFromCache(lsh);
        return PageResult.success();
    }

    /**
     * 从缓存中获取对应机构的所有挂号记录
     * @return
     */
    @GetMapping("/getClinicListFromCache")
    public PageResult getClinicListFromCache() {
        List<RegistrationRecord> list = registrationRecordService.getClinicListFromCache();
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 从缓存中获取对应医生的挂号记录
     * @return
     */
    @GetMapping("/getClinicListByDoctorIdFromCache")
    public PageResult getClinicListByDoctorIdFromCache() {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        List<RegistrationRecord> list = registrationRecordService.getClinicListByDoctorIdFromCache(user.getId());
        return PageResult.success().resultSet("list", list);
    }

    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String mrmMemberName,
            @RequestParam(required = false) String cimItemName,
            @RequestParam(required = false) String sysDoctorName){

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                registrationRecordService.getClinicListByCriteria(user.getSysClinicId(), creationDate, mrmMemberName, cimItemName, sysDoctorName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

}
