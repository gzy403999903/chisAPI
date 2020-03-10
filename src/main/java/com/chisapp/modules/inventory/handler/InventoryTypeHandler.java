package com.chisapp.modules.inventory.handler;

import com.chisapp.modules.inventory.bean.InventoryType;
import com.chisapp.modules.inventory.service.InventoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/9/29 18:08
 * @Version 1.0
 */
@RequestMapping("/inventoryType")
@RestController
public class InventoryTypeHandler {

    private InventoryTypeService inventoryTypeService;
    @Autowired
    public void setInventoryTypeService(InventoryTypeService inventoryTypeService) {
        this.inventoryTypeService = inventoryTypeService;
    }

    @GetMapping("/getAll")
    public List<InventoryType> getAll() {
        return inventoryTypeService.getAll();
    }
}
