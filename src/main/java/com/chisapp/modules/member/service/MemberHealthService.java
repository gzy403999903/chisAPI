package com.chisapp.modules.member.service;

import com.chisapp.modules.member.bean.MemberHealth;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/10/24 15:45
 * @Version 1.0
 */

public interface MemberHealthService {

    /**
     * 保存操作
     * @param mrmMemberId
     * @param memberHealthList
     */
    @Transactional
    void save(Integer mrmMemberId, List<MemberHealth> memberHealthList);

    /**
     * 删除操作
     * @param mrmMemberId
     */
    @Transactional
    void delete(Integer mrmMemberId);

    /**
     * 根据会员ID 获取对应的档案信息
     * @param mrmMemberId
     * @return
     */
    List<MemberHealth> getByMrmMemberId(Integer mrmMemberId);
}
