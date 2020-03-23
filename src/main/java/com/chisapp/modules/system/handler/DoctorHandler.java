package com.chisapp.modules.system.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.system.bean.Doctor;
import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.service.DoctorService;
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
 * @Date: 2019/10/22 14:02
 * @Version 1.0
 */
@RequestMapping("/doctor")
@RestController
public class DoctorHandler {

    private DoctorService doctorService;
    @Autowired
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * 请求参数中带有 ID 的方法在被调用前都会先调用此方法
     * 如果 ID 部位空则会进行查询并填充 model
     * map 中的 key 必须为 model 类名首字母小写
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getById (@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            Doctor doctor = doctorService.getById(id);
            if (doctor != null) {
                map.put("doctor", doctor);
            }
        }
    }

    /**
     * 保存操作
     * @param doctor
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid Doctor doctor, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        doctorService.save(doctor);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param doctor
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid Doctor doctor, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        doctorService.update(doctor);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param doctor
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (Doctor doctor) {
        doctorService.delete(doctor);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String name){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = doctorService.getClinicListByCriteria(sysClinicName, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 查询对应机构启用状态的医生信息
     * @return
     */
    @GetMapping("/getClinicEnabled")
    public List<Map<String, Object>> getClinicEnabled() {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return doctorService.getClinicEnabled(user.getSysClinicId());
    }


}
