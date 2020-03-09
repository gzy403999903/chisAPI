package com.chisapp.modules.member.dao;

import com.chisapp.modules.member.bean.Member;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);


    /* -------------------------------------------------------------------------------------------------------------- */

    void updateBalanceAndPoints(@Param("id") Integer id,
                                @Param("balance") BigDecimal balance,
                                @Param("points") Integer points);

    void updateBalanceForDeposit(@Param("id") Integer id,
                                 @Param("balance") BigDecimal balance);

    List<Map<String, Object>> selectByCriteria(@Param("mrmMemberId") Integer mrmMemberId,
                                               @Param("name") String name,
                                               @Param("phone") String phone,
                                               @Param("idCardNo") String idCardNo,
                                               @Param("state") Boolean state,
                                               @Param("healthState") String healthState,
                                               @Param("exposureId") Integer exposureId,
                                               @Param("allergyId") Integer allergyId,
                                               @Param("disabilityId") Integer disabilityId,
                                               @Param("previousIdList") List<Integer> previousIdList,
                                               @Param("familyIdList") List<Integer> familyIdList);

    List<Map<String, Object>> selectEnabledLikeByKeyword(@Param("keyword") String keyword);

    Map<String, Object> selectLastMemberMapByCommitteeId(@Param("mrmCommitteeId") Integer mrmCommitteeId);
}
