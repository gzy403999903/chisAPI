package com.chisapp.modules.item.dao;

import com.chisapp.modules.item.bean.ItemType;
import java.util.List;

public interface ItemTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemType record);

    ItemType selectByPrimaryKey(Integer id);

    List<ItemType> selectAll();

    int updateByPrimaryKey(ItemType record);
}