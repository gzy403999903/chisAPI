package com.chisapp.modules.member.handler;

import com.chisapp.modules.member.bean.MemberHealth;
import com.chisapp.modules.member.service.MemberHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/10/24 15:44
 * @Version 1.0
 */
@RequestMapping("/memberHealth")
@RestController
public class MemberHealthHandler {

    private MemberHealthService memberHealthService;
    @Autowired
    public void setMemberHealthService(MemberHealthService memberHealthService) {
        this.memberHealthService = memberHealthService;
    }

    /**
     * 根据会员ID 获取对应的健康档案信息
     * @param mrmMemberId
     * @return
     */
    @GetMapping("/getByMrmMemberId")
    public List<MemberHealth> getByMrmMemberId(Integer mrmMemberId) {
        return memberHealthService.getByMrmMemberId(mrmMemberId);
    }


}
