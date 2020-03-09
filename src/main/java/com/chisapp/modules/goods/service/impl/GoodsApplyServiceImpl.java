package com.chisapp.modules.goods.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.modules.goods.bean.Goods;
import com.chisapp.modules.goods.bean.GoodsApply;
import com.chisapp.modules.goods.dao.GoodsApplyMapper;
import com.chisapp.modules.goods.service.GoodsApplyService;
import com.chisapp.modules.goods.service.GoodsService;
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
public class GoodsApplyServiceImpl implements GoodsApplyService {

    private GoodsApplyMapper goodsApplyMapper;
    @Autowired
    public void setGoodsApplyMapper(GoodsApplyMapper goodsApplyMapper) {
        this.goodsApplyMapper = goodsApplyMapper;
    }

    private GoodsService goodsService;
    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void save(GoodsApply goodsApply) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        goodsApply.setCreatorId(user.getId());
        goodsApply.setCreationDate(new Date());
        goodsApply.setApproveState(ApproveStateEnum.PRICING.getIndex());
        goodsApplyMapper.insert(goodsApply);
    }

    @Override
    public void cancel(GoodsApply goodsApply) {
        if (goodsApply.getApproveState() != ApproveStateEnum.PRICING.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待定价状态");
        }

        goodsApply.setApproveState(ApproveStateEnum.CANCEL.getIndex());
        goodsApplyMapper.updateByPrimaryKey(goodsApply);
    }

    @Override
    public void pricing(GoodsApply goodsApply) {
        if (goodsApply.getApproveState() != ApproveStateEnum.PRICING.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待定价状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        goodsApply.setPricerId(user.getId());
        goodsApply.setPricingDate(new Date());
        goodsApply.setApproveState(ApproveStateEnum.PENDING.getIndex());
        goodsApplyMapper.updateByPrimaryKey(goodsApply);
    }

    @Override
    public void cancelPricing(GoodsApply goodsApply) {
        if (goodsApply.getApproveState() != ApproveStateEnum.PENDING.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        goodsApply.setApproverId(user.getId());
        goodsApply.setApproveDate(new Date());
        goodsApply.setApproveState(ApproveStateEnum.PRICING.getIndex());
        goodsApplyMapper.updateByPrimaryKey(goodsApply);
    }

    @Override
    public void unapproved(GoodsApply goodsApply) {
        if (goodsApply.getApproveState() == ApproveStateEnum.APPROVED.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据不能为通过状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        goodsApply.setApproverId(user.getId());
        goodsApply.setApproveDate(new Date());
        goodsApply.setApproveState(ApproveStateEnum.UNAPPROVED.getIndex());
        goodsApplyMapper.updateByPrimaryKey(goodsApply);
    }

    @Override
    public void approved(GoodsApply goodsApply) {
        if (goodsApply.getApproveState() != ApproveStateEnum.PENDING.getIndex()) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        goodsApply.setApproverId(user.getId());
        goodsApply.setApproveDate(new Date());
        goodsApply.setApproveState(ApproveStateEnum.APPROVED.getIndex());
        goodsApplyMapper.updateByPrimaryKey(goodsApply);

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsApply, goods);
        goods.setId(null); // 设置 ID 为 NULL
        goodsService.save(goods);
    }

    @Override
    public GoodsApply getById(Integer id) {
        return goodsApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate, Byte approveState, String name) {
        return goodsApplyMapper.selectByCriteria(creationDate, approveState, name);
    }
}
