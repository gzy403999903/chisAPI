package com.chisapp.modules.item.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.modules.item.bean.Item;
import com.chisapp.modules.item.bean.ItemApply;
import com.chisapp.modules.item.dao.ItemApplyMapper;
import com.chisapp.modules.item.service.ItemApplyService;
import com.chisapp.modules.item.service.ItemService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/7 15:45
 * @Version 1.0
 */
@Service
public class ItemApplyServiceImpl implements ItemApplyService {

    private ItemApplyMapper itemApplyMapper;
    @Autowired
    public void setItemApplyMapper(ItemApplyMapper itemApplyMapper) {
        this.itemApplyMapper = itemApplyMapper;
    }

    private ItemService itemService;
    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void save(ItemApply itemApply) {
        // 获取操作人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        itemApply.setCreatorId(user.getId());
        itemApply.setCreationDate(new Date());
        itemApply.setApproveState(ApproveStateEnum.PRICING.getIndex());

        itemApplyMapper.insert(itemApply);
    }

    @Override
    public void update(ItemApply itemApply) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user.getId().intValue() != itemApply.getCreatorId().intValue()) {
            throw new RuntimeException("操作未被允许, 操作人和创建人不一致");
        }
        if (itemApply.getApproveState() != ApproveStateEnum.UNAPPROVED.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为驳回状态");
        }

        // 重置部分属性
        itemApply.setPricerId(null);
        itemApply.setPricingDate(null);
        itemApply.setApproverId(null);
        itemApply.setApproveDate(null);
        itemApply.setLastApproverId(null);
        itemApply.setLastApproveDate(null);
        itemApply.setApproveState(ApproveStateEnum.PRICING.getIndex());
        itemApplyMapper.updateByPrimaryKey(itemApply);
    }

    @Override
    public void pricing(ItemApply itemApply) {
        if (itemApply.getApproveState() != ApproveStateEnum.PRICING.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待定价状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemApply.setPricerId(user.getId());
        itemApply.setPricingDate(new Date());
        itemApply.setApproveState(ApproveStateEnum.PENDING.getIndex());
        itemApplyMapper.updateByPrimaryKey(itemApply);
    }

    @Override
    public void cancelPricing(ItemApply itemApply) {
        if (itemApply.getApproveState() != ApproveStateEnum.PENDING.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待审核状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemApply.setApproverId(user.getId());
        itemApply.setApproveDate(new Date());
        itemApply.setApproveState(ApproveStateEnum.PRICING.getIndex());
        itemApplyMapper.updateByPrimaryKey(itemApply);
    }

    @Override
    public void unapproved(ItemApply itemApply) {
        if (itemApply.getApproveState() == ApproveStateEnum.APPROVED.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据不能为通过状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemApply.setApproverId(user.getId());
        itemApply.setApproveDate(new Date());
        itemApply.setApproveState(ApproveStateEnum.UNAPPROVED.getIndex());
        itemApplyMapper.updateByPrimaryKey(itemApply);
    }

    @Override
    public void approved(ItemApply itemApply) {
        if (itemApply.getApproveState() != ApproveStateEnum.PENDING.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待审核状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemApply.setApproverId(user.getId());
        itemApply.setApproveDate(new Date());
        itemApply.setApproveState(ApproveStateEnum.APPROVED.getIndex());
        itemApplyMapper.updateByPrimaryKey(itemApply);
    }

    @Override
    public void lastCancelPricing(ItemApply itemApply) {
        if (itemApply.getApproveState() != ApproveStateEnum.APPROVED.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemApply.setApproverId(user.getId());
        itemApply.setApproveDate(new Date());
        itemApply.setApproveState(ApproveStateEnum.PRICING.getIndex());
        itemApplyMapper.updateByPrimaryKey(itemApply);
    }

    @Override
    public void lastUnapproved(ItemApply itemApply) {
        if (itemApply.getApproveState() != ApproveStateEnum.APPROVED.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemApply.setLastApproverId(user.getId());
        itemApply.setLastApproveDate(new Date());
        itemApply.setApproveState(ApproveStateEnum.UNAPPROVED.getIndex());
        itemApplyMapper.updateByPrimaryKey(itemApply);
    }

    @Override
    public void lastApproved(ItemApply itemApply) {
        if (itemApply.getApproveState() != ApproveStateEnum.APPROVED.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemApply.setLastApproverId(user.getId());
        itemApply.setLastApproveDate(new Date());
        itemApply.setApproveState(ApproveStateEnum.LAST_APPROVED.getIndex());
        itemApplyMapper.updateByPrimaryKey(itemApply);

        // 复制到正式项目信息表
        Item item = new Item();
        BeanUtils.copyProperties(itemApply, item);
        item.setId(null); // 设置 ID 为 NULL
        itemService.save(item);
    }

    @Override
    public ItemApply getById(Integer id) {
        return itemApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate, Byte approveState, String name) {
        return itemApplyMapper.selectByCriteria(creationDate, approveState, name);
    }
}
