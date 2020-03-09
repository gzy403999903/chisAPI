package com.chisapp.modules.item.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.item.bean.Item;
import com.chisapp.modules.item.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 此类被子类集成 并通过构造方法赋值 CIM_ITEM_TYPE_ID
 *
 * @Author: Tandy
 * @Date: 2019/8/7 9:00
 * @Version 1.0
 */
@RequestMapping("/item")
@RestController
public class ItemHandler {

    protected Integer CIM_ITEM_TYPE_ID = null;   // 收费项目分类ID
    @Value("${billing-type.registration-fee-id}")
    private Integer REGISTRATION_FEE_ID = null;  // 挂号费ID

    private ItemService itemService;
    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
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
            map.put("item", itemService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param item
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid Item item, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        // 设置对应的项目分类
        if (CIM_ITEM_TYPE_ID != null) {
            item.setCimItemTypeId(CIM_ITEM_TYPE_ID); // 设置对应的分类 ID
        }

        itemService.save(item);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param item
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid Item item, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemService.update(item);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param item
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (Item item) {
        itemService.delete(item);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param state
     * @param itemClassifyId
     * @param ybItem
     * @param name
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) Boolean state, // 启用状态
            @RequestParam(required = false) Integer itemClassifyId, // 项目分类ID
            @RequestParam(required = false) Boolean ybItem, // 是否医保项目
            @RequestParam(required = false) String name){ // 项目名称或助记码

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = itemService.getByCriteria(CIM_ITEM_TYPE_ID, state, itemClassifyId, ybItem, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据 name 或 code 进行检索
     * @param name
     * @return
     */
    @GetMapping("/getEnabledLikeByName")
    public List<Map<String, Object>> getEnabledLikeByName (@RequestParam("name") String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }
        return itemService.getEnabledLikeByName(name);
    }

    /**
     * 根据查询条件获取对应的收费项目集合 (处方调用)
     * @param name
     * @return
     */
    @GetMapping("/getEnabledLikeByNameForPrescription")
    public List<Map<String, Object>> getEnabledLikeByNameForPrescription (@RequestParam("name") String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }
        return itemService.getEnabledLikeByNameForPrescription(CIM_ITEM_TYPE_ID, name);
    }

    /**
     * 获取启用状态的挂号费用
     * @return
     */
    @GetMapping("/getEnabledRegistrationFee")
    public List<Item> getEnabledRegistrationFee() {
        return itemService.getEnabledByBillingTypeId(this.REGISTRATION_FEE_ID);
    }

}
