package com.chisapp.modules.system.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.system.bean.ClinicRoom;
import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.service.ClinicRoomService;
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
@RequestMapping("/clinicRoom")
@RestController
public class ClinicRoomHandler {

    private ClinicRoomService clinicRoomService;
    @Autowired
    public void setClinicRoomService(ClinicRoomService clinicRoomService) {
        this.clinicRoomService = clinicRoomService;
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
            map.put("clinicRoom", clinicRoomService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param clinicRoom
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid ClinicRoom clinicRoom, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        clinicRoomService.save(clinicRoom);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param clinicRoom
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid ClinicRoom clinicRoom, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        clinicRoomService.update(clinicRoom);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param clinicRoom
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (ClinicRoom clinicRoom) {
        clinicRoomService.delete(clinicRoom);
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
            @RequestParam(required = false) String name){

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(pageNum, pageSize);
        List<ClinicRoom> pageList = clinicRoomService.getClinicListByCriteria(user.getSysClinicId(), name);
        PageInfo<ClinicRoom> pageInfo = new PageInfo<ClinicRoom>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取对应机构的所有诊室
     * @return
     */
    @GetMapping("/getClinicListAll")
    public List<ClinicRoom> getClinicListAll() {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return clinicRoomService.getClinicListAll(user.getSysClinicId());
    }

}
