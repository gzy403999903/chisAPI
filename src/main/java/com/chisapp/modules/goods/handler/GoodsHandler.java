package com.chisapp.modules.goods.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.goods.bean.Goods;
import com.chisapp.modules.goods.service.GoodsService;
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
 * @Date: 2019/8/19 16:11
 * @Version 1.0
 */
@RequestMapping("/goods")
@RestController
public class GoodsHandler {

    protected Integer GSM_GOODS_TYPE_ID = null; // 商品类型ID

    private GoodsService goodsService;
    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
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
            map.put("goods", goodsService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param goods
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid Goods goods, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        if (GSM_GOODS_TYPE_ID != null) {
            goods.setGsmGoodsTypeId(GSM_GOODS_TYPE_ID);
        }

        goodsService.save(goods);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param goods
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid Goods goods, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        goodsService.update(goods);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param goods
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (Goods goods) {
        goodsService.delete(goods);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param oid
     * @param goodsClassifyId
     * @param name
     * @param state
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String oid,
            @RequestParam(required = false) Integer goodsClassifyId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean state){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = goodsService.getByCriteria(oid, GSM_GOODS_TYPE_ID, goodsClassifyId, state, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据 通用名称 或 助记码 进行模糊查询
     * @param name
     * @return
     */
    @GetMapping("/getEnabledLikeByName")
    public List<Map<String, Object>> getEnabledLikeByName (@RequestParam("name") String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }
        return goodsService.getEnabledLikeByName(name);
    }

    /**
     * 采购计划调用 检索对应机构对应商品
     * @param name
     * @return
     */
    @GetMapping("/getEnabledLikeByNameForPlan")
    public List<Map<String, Object>> getEnabledLikeByNameForPlan (@RequestParam("name") String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }
        // 获取操作人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return goodsService.getEnabledLikeByNameForPlan(user.getSysClinicId(), name);
    }

    /**
     * 开具处方调用 检索对应机构对应商品
     * @param name
     * @return
     */
    @GetMapping("/getEnabledLikeByNameForPrescription")
    public List<Map<String, Object>> getEnabledLikeByNameForPrescription (@RequestParam("name") String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }
        // 获取操作人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return goodsService.getEnabledLikeByNameForPrescription(user.getSysClinicId(), GSM_GOODS_TYPE_ID, name);
    }

}
