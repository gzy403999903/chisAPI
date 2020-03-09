package com.chisapp.modules.member.service;

import com.chisapp.modules.member.bean.Member;
import com.chisapp.modules.member.bean.MemberHealth;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/23 14:55
 * @Version 1.0
 */

public interface MemberService {

    /**
     * 保存操作
     * @param member
     */
    @Transactional
    void save(Member member);

    /**
     * 修改操作
     * @param member
     * @return
     */
    @Transactional
    Member update(Member member);

    /**
     * 更新余额与积分
     * 不能直接更新 要在原值的基础上加减
     * (balance = balance - #{balance}, points = points + #{points})
     * @param id
     * @param balance
     * @param points
     */
    @Transactional
    void updateBalanceAndPoints(Integer id, BigDecimal balance, Integer points);

    /**
     * 储值更新余额
     * 不能直接更新 要在原值的基础上加减
     * balance = balance - #{balance}
     * @param id
     * @param balance
     */
    @Transactional
    void updateBalanceForDeposit(Integer id, BigDecimal balance);

    /**
     * 更新健康档案操作
     * @param member
     * @param oldMember
     * @param memberHealthList
     * @return
     */
    @Transactional
    Member updateHealthArchive(Member member, Member oldMember, List<MemberHealth> memberHealthList);

    /**
     * 删除操作
     * @param member
     */
    @Transactional
    void delete(Member member);

    /**
     * 根据主键 ID 获取对象
     * @param id
     * @return
     */
    Member getById(Integer id);

    /**
     * 按查询条件获取对象集合
     * @param name
     * @param phone
     * @param idCardNo
     * @param state
     * @return
     */
    List<Map<String, Object>> getByCriteria(String name, String phone, String idCardNo, Boolean state);

    /**
     * 按查询条件获取会员及相关健康信息
     * @param name
     * @param phone
     * @param idCardNo
     * @param healthState
     * @return
     */
    List<Map<String, Object>> getByCriteriaForHealth(String name, String phone, String idCardNo, String healthState);

    /**
     * 根据会员ID 获取会员及相关健康信息
     * @param id
     * @return
     */
    Map<String, Object> getByIdForTreatment(Integer id);

    /**
     * 根据 关键字 进行启用状态的检索查询
     * @param keyword
     * @return
     */
    List<Map<String, Object>> getEnabledLikeByKeyword(String keyword);

    /**
     * 获取指定 村(居委会) 最后一个会员记录
     * 次方法主要在档案号进行编码时进行调用
     * Map至少包含以下内容: 6位区域编码、3位乡镇(街道)编码、3位村(居委会)编码
     * @param mrmCommitteeId
     * @return
     */
    Map<String, Object> getLastMemberMapByCommitteeId(Integer mrmCommitteeId);


}
