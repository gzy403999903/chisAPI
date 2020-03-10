package com.chisapp.modules.inventory.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.inventory.bean.ShelfPosition;
import com.chisapp.modules.inventory.service.ShelfPositionService;
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
 * @Date: 2020/1/5 14:03
 * @Version 1.0
 */
@RequestMapping("/shelfPosition")
@RestController
public class ShelfPositionHandler {

    private ShelfPositionService shelfPositionService;
    @Autowired
    public void setShelfPositionService(ShelfPositionService shelfPositionService) {
        this.shelfPositionService = shelfPositionService;
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
            map.put("shelfPosition", shelfPositionService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param shelfPosition
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid ShelfPosition shelfPosition, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        shelfPositionService.save(shelfPosition);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param shelfPosition
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid ShelfPosition shelfPosition, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        shelfPositionService.update(shelfPosition);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param shelfPosition
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (ShelfPosition shelfPosition) {
        shelfPositionService.delete(shelfPosition);
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

        User user = (User) SecurityUtils.getSubject().getPrincipal();  // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<ShelfPosition> pageList = shelfPositionService.getClinicListByCriteria(user.getSysClinicId(), name);
        PageInfo<ShelfPosition> pageInfo = new PageInfo<ShelfPosition>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取对应机构的所有诊室
     * @return
     */
    @GetMapping("/getClinicListAll")
    public List<ShelfPosition> getClinicListAll() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();  // 获取用户信息
        return shelfPositionService.getClinicListAll(user.getSysClinicId());
    }
}
