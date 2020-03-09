package com.chisapp.modules.member.service.impl;

import com.chisapp.common.enums.DictTypeEnum;
import com.chisapp.modules.member.bean.Member;
import com.chisapp.modules.member.bean.MemberHealth;
import com.chisapp.modules.member.dao.MemberMapper;
import com.chisapp.modules.member.service.CommitteeService;
import com.chisapp.modules.member.service.MemberHealthService;
import com.chisapp.modules.member.service.MemberService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/23 15:09
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Member")
@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;
    @Autowired
    public void setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    private CommitteeService committeeService;
    @Autowired
    public void setCommitteeService(CommitteeService committeeService) {
        this.committeeService = committeeService;
    }

    private MemberHealthService memberHealthService;
    @Autowired
    public void setMemberHealthService(MemberHealthService memberHealthService) {
        this.memberHealthService = memberHealthService;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void save(Member member) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 设置创建机构、创建人、创建时间
        member.setSysClinicId(user.getSysClinicId());
        member.setCreatorId(user.getId());
        member.setCreationDate(new Date());

        memberMapper.insert(member);
    }

    @Caching(
            put = {
                    @CachePut(key = "#member.id")
            },
            evict = {
                    @CacheEvict(key = "'treatment' + #member.id")
            }
    )
    @Override
    public Member update(Member member) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 设置最后一次更新人、更新时间
        member.setLastUpdaterId(user.getId());
        member.setLastUpdateDate(new Date());

        memberMapper.updateByPrimaryKey(member);
        return member;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#id"),
                    @CacheEvict(key = "'treatment' + #id")
            }
    )
    @Override
    public void updateBalanceAndPoints(Integer id, BigDecimal balance, Integer points) {
        try {
            memberMapper.updateBalanceAndPoints(id, balance, points);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("会员余额或积分不足");
        }
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#id"),
                    @CacheEvict(key = "'treatment' + #id")
            }
    )
    @Override
    public void updateBalanceForDeposit(Integer id, BigDecimal balance) {
        try {
            memberMapper.updateBalanceForDeposit(id, balance);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("会员余额不足");
        }
    }

    @Caching(
            put = {
                    @CachePut(key = "#member.id")
            },
            evict = {
                    @CacheEvict(key = "'treatment' + #member.id")
            }
    )
    @Override
    public Member updateHealthArchive(Member member, Member oldMember, List<MemberHealth> memberHealthList) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 设置最后一次更新人、更新时间
        member.setLastUpdaterId(user.getId());
        member.setLastUpdateDate(new Date());

        // 当会员健康档案编码为空 或者 所在居委会发生改变时设置档案编码
        if (member.getArchiveNo() == null || member.getArchiveNo().trim().equals("")) {
            this.setMemberArchiveNo(member);
        } else if (member.getMrmCommitteeId().intValue() != oldMember.getMrmCommitteeId().intValue()) {
            this.setMemberArchiveNo(member);
        }

        // 设置健康档案状态
        member.setArchiveState(true);

        // 保存会员基本信息
        memberMapper.updateByPrimaryKey(member);
        // 保存会员健康信息
        memberHealthService.save(member.getId(), memberHealthList);

        return member;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#member.id"),
                    @CacheEvict(key = "'treatment' + #member.id")
            }
    )
    @Override
    public void delete(Member member) {
        memberMapper.deleteByPrimaryKey(member.getId());
    }

    @Cacheable(key = "#id")
    @Override
    public Member getById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String name, String phone, String idCardNo, Boolean state) {
       return this.getByCriteria(null, name, phone, idCardNo, state, null);
    }

    @Override
    public List<Map<String, Object>> getByCriteriaForHealth(String name, String phone, String idCardNo, String healthState) {
        return this.getByCriteria(null, name, phone, idCardNo, null, healthState);
    }

    @Cacheable(key = "'treatment' + #id")
    @Override
    public Map<String, Object> getByIdForTreatment(Integer id) {
        return this.getByCriteria(id, null, null, null, null, null).get(0);
    }

    private List<Map<String, Object>> getByCriteria(Integer mrmMemberId, String name, String phone, String idCardNo, Boolean state, String healthState) {
        // 获取暴露史ID
        Integer exposureId = DictTypeEnum.EXPOSURE.getIndex();
        // 获取过敏史ID
        Integer allergyId = DictTypeEnum.ALLERGY.getIndex();
        // 获取残疾情况ID
        Integer disabilityId = DictTypeEnum.DISABILITY.getIndex();
        // 获取既往史ID 集合
        List<Integer> previousIdList = new ArrayList<>();
        previousIdList.add(DictTypeEnum.PREVIOUS_ILLNESS.getIndex());
        previousIdList.add(DictTypeEnum.PREVIOUS_SURGERY.getIndex());
        previousIdList.add(DictTypeEnum.PREVIOUS_TRAUMA.getIndex());
        previousIdList.add(DictTypeEnum.PREVIOUS_TRANSFUSION.getIndex());
        // 获取家族史ID 集合
        List<Integer> familyIdList = new ArrayList<>();
        familyIdList.add(DictTypeEnum.FAMILY_FATHER.getIndex());
        familyIdList.add(DictTypeEnum.FAMILY_MOTHER.getIndex());
        familyIdList.add(DictTypeEnum.FAMILY_SIBLING.getIndex());
        familyIdList.add(DictTypeEnum.FAMILY_CHILDREN.getIndex());

        return memberMapper.selectByCriteria(mrmMemberId, name, phone, idCardNo, state, healthState, exposureId, allergyId, disabilityId, previousIdList, familyIdList);
    }

    @Override
    public List<Map<String, Object>> getEnabledLikeByKeyword(String keyword) {
        return memberMapper.selectEnabledLikeByKeyword(keyword);
    }

    @Override
    public Map<String, Object> getLastMemberMapByCommitteeId (Integer mrmCommitteeId) {
        return memberMapper.selectLastMemberMapByCommitteeId(mrmCommitteeId);
    }

    /**
     * 设置会员档案编码
     * 【6位区域编码 + 3位乡镇(街道)编码 + 3位村(居委会)编码 + 5位人员所在村(居委会)的档案顺序号】
     * @param member
     * @return
     */
    private void setMemberArchiveNo (Member member) {
        if (member.getMrmCommitteeId() == null) {
            throw new RuntimeException("构建档案编号失败, 未获取正确的 乡镇(街道) 和 村(居委会)");
        }

        // 获取用于档案编码的记录(先从会员记录获取, 如果获取不到再从 村(居委会)进行获取)
        // 这两个地方所返回的 Map key 要一致
        Map<String, Object> archiveNoMap = this.getLastMemberMapByCommitteeId(member.getMrmCommitteeId());
        if (archiveNoMap == null) {
            archiveNoMap = committeeService.getCommitteeMapById(member.getMrmCommitteeId());
        }

        // 提取编码结构元素
        if (archiveNoMap.get("maxIndex") == null) {
            throw new RuntimeException("未能获取档案最大编码");
        }
        int maxIndex = Integer.parseInt(archiveNoMap.get("maxIndex").toString()) + 1;

        if (archiveNoMap.get("areaNo") == null) {
            throw new RuntimeException("未能获取档案6位地区编码");
        }
        String areaNo = archiveNoMap.get("areaNo").toString();

        if (archiveNoMap.get("townshipNo") == null) {
            throw new RuntimeException("未能获取档案3位乡镇(街道)编码");
        }
        String townshipNo = archiveNoMap.get("townshipNo").toString();

        switch (townshipNo.length()) {
            case 1: townshipNo = "00" + townshipNo; break;
            case 2: townshipNo = "0" + townshipNo; break;
        }

        if (archiveNoMap.get("committeeNo") == null) {
            throw new RuntimeException("未能获取档案3位村(居委会)编码");
        }
        String committeeNo = archiveNoMap.get("committeeNo").toString();

        switch (committeeNo.length()) {
            case 1: committeeNo = "00" + committeeNo; break;
            case 2: committeeNo = "0" + committeeNo; break;
        }

        // 生成档案编码
        String archiveNo12 = areaNo + townshipNo + committeeNo;
        String archiveNo5 = String.valueOf(maxIndex);
        switch (archiveNo5.length()) {
            case 1: archiveNo5 = "0000" + archiveNo5; break;
            case 2: archiveNo5 = "000" + archiveNo5; break;
            case 3: archiveNo5 = "00" + archiveNo5; break;
            case 4: archiveNo5 = "0" + archiveNo5; break;
        }

        // 设置会员属性
        member.setCommitteeArchiveIndex(maxIndex);
        member.setArchiveNo(archiveNo12 + archiveNo5);
    }


}
