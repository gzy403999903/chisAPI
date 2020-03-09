package com.chisapp.modules.member.service;

import com.chisapp.modules.member.bean.MemberType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/10/19 18:19
 * @Version 1.0
 */

public interface MemberTypeService {

    /**
     * 保存操作
     * @param memberType
     */
    @Transactional
    void save(MemberType memberType);

    /**
     * 编辑操作
     * @param memberType
     */
    @Transactional
    MemberType update(MemberType memberType);

    /**
     * 删除操作
     * @param id
     */
    @Transactional
    void delete(Integer id);

    /**
     * 根据 ID 获取对象
     * @param id
     * @return
     */
    MemberType getById(Integer id);

    /**
     * 按查询条件获取对应的集合
     * @param name
     * @param state
     * @return
     */
    List<MemberType> getByCriteria(String name, Boolean state);

    /**
     * 获取所有启用的会员类型
     * @return
     */
    List<MemberType> getEnabled();

}
