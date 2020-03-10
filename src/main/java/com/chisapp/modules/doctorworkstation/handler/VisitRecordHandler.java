package com.chisapp.modules.doctorworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.doctorworkstation.bean.VisitRecord;
import com.chisapp.modules.doctorworkstation.service.VisitRecordService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/16 23:14
 * @Version 1.0
 */
@RequestMapping("/visitRecord")
@RestController
public class VisitRecordHandler {

    private VisitRecordService visitRecordService;
    @Autowired
    public void setVisitRecordService(VisitRecordService visitRecordService) {
        this.visitRecordService = visitRecordService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /**
     * 请求参数中带有 ID 的方法在被调用前都会先调用此方法
     * 如果 ID 部位空则会进行查询并填充 model
     * map 中的 key 必须为 model 类名首字母小写
     *
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getById(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("visitRecord", visitRecordService.getById(id));
        }
    }

    /**
     * 保存操作
      * @param visitRecord
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@Valid VisitRecord visitRecord, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        visitRecordService.save(visitRecord);
        return PageResult.success();
    }

    /**
     * 编辑操作 更新回访内容
     * @param visitRecord
     * @param result
     * @param nextAppointmentDate
     * @return
     */
    @PutMapping("/update")
    public PageResult update(@Valid VisitRecord visitRecord, BindingResult result, String nextAppointmentDate) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        Date appointmentDate = null;
        if (nextAppointmentDate != null && !nextAppointmentDate.trim().equals("")) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                appointmentDate = df.parse(nextAppointmentDate);
            } catch (ParseException e) {
                return PageResult.fail().msg("下一次预约时间: 不正确的时间格式");
            }
        }

        visitRecordService.update(visitRecord, appointmentDate);
        return PageResult.success();
    }

    /**
     * 根据条件获取所有机构的回访记录
     * @param pageNum
     * @param pageSize
     * @param appointmentDate
     * @param mrmMemberName
     * @param finished
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] appointmentDate,
            @RequestParam(required = false) String mrmMemberName,
            @RequestParam(required = false) Boolean finished) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                visitRecordService.getByCriteria(null, null, appointmentDate, mrmMemberName, finished);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据条件获取对应机构的回访记录
     * @param pageNum
     * @param pageSize
     * @param appointmentDate
     * @param mrmMemberName
     * @param finished
     * @return
     */
    @GetMapping("/getClinicDoctorListByCriteria")
    public PageResult getClinicDoctorListByCriteria(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] appointmentDate,
            @RequestParam(required = false) String mrmMemberName,
            @RequestParam(required = false) Boolean finished) {

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                visitRecordService.getByCriteria(user.getSysClinicId(), user.getId(), appointmentDate, mrmMemberName, finished);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }





}
