package com.chisapp.modules.inventory.service;

import com.chisapp.modules.inventory.bean.ShelfPosition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2020/1/5 13:24
 * @Version 1.0
 */
public interface ShelfPositionService {

    /**
     * 保存操作
     * @param shelfPosition
     */
    @Transactional
    void save(ShelfPosition shelfPosition);

    /**
     * 编辑操作
     * @param shelfPosition
     * @return
     */
    @Transactional
    ShelfPosition update(ShelfPosition shelfPosition);

    /**
     * 删除操作
     * @param shelfPosition
     */
    @Transactional
    void delete(ShelfPosition shelfPosition);

    /**
     * 根据 ID 获取对象
     * @param id
     * @return
     */
    ShelfPosition getById(Integer id);

    /**
     * 根据查询条件获取对应的集合
     * @param sysClinicId
     * @param name
     * @return
     */
    List<ShelfPosition> getClinicListByCriteria(Integer sysClinicId, String name);

    /**
     * 获取对应机构所有的诊室
     * @return
     */
    List<ShelfPosition> getClinicListAll(Integer sysClinicId);


}
