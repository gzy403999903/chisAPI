package com.chisapp.modules.inventory.dao;

import com.chisapp.modules.inventory.bean.InventoryType;
import java.util.List;

public interface InventoryTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InventoryType record);

    InventoryType selectByPrimaryKey(Integer id);

    List<InventoryType> selectAll();

    int updateByPrimaryKey(InventoryType record);
}