package com.chisapp.modules.item.handler;

import com.chisapp.modules.item.bean.ItemType;
import com.chisapp.modules.item.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/7/31 16:12
 * @Version 1.0
 */
@RequestMapping("/itemType")
@RestController
public class ItemTypeHandler {

    private ItemTypeService itemTypeService;
    @Autowired
    public void setItemTypeService(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

    /**
     * 获取所有对象
     * @return
     */
    @GetMapping("/getAll")
    public List<ItemType> getAll () {
        return itemTypeService.getAll();
    }

}
