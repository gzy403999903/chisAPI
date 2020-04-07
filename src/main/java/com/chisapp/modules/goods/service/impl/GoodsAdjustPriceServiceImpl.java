package com.chisapp.modules.goods.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.DateUtils;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.goods.bean.Goods;
import com.chisapp.modules.goods.bean.GoodsAdjustPrice;
import com.chisapp.modules.goods.dao.GoodsAdjustPriceMapper;
import com.chisapp.modules.goods.service.GoodsAdjustPriceService;
import com.chisapp.modules.goods.service.GoodsService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/22 10:32
 * @Version 1.0
 */
@Service
public class GoodsAdjustPriceServiceImpl implements GoodsAdjustPriceService {

    private GoodsAdjustPriceMapper goodsAdjustPriceMapper;
    @Autowired
    public void setGoodsAdjustPriceMapper(GoodsAdjustPriceMapper goodsAdjustPriceMapper) {
        this.goodsAdjustPriceMapper = goodsAdjustPriceMapper;
    }

    private GoodsService goodsService;
    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void save(List<GoodsAdjustPrice> goodsAdjustPriceList) {
        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 获取流水号
        String lsh = KeyUtils.getLSH(user.getId());
        // 初始化明细号
        int mxh = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        GoodsAdjustPriceMapper mapper = batchSqlSession.getMapper(GoodsAdjustPriceMapper.class);
        try {
            for (GoodsAdjustPrice goodsAdjustPrice : goodsAdjustPriceList) {
                goodsAdjustPrice.setLsh(lsh);
                goodsAdjustPrice.setMxh(String.valueOf(mxh++));
                goodsAdjustPrice.setApproveState(ApproveStateEnum.PENDING.getIndex());
                goodsAdjustPrice.setCreatorId(user.getId());
                goodsAdjustPrice.setCreationDate(new Date());
                goodsAdjustPrice.setExpiryDate(DateUtils.getFutureDate(1));

                mapper.insert(goodsAdjustPrice);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void unapproved(String lsh) {
        List<GoodsAdjustPrice> goodsAdjustPriceList = this.parseMapToList(this.getByLsh(lsh));
        if (!this.examineApproveState(goodsAdjustPriceList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        // 检查单据是否过期
        if (!this.examineCreationDate(goodsAdjustPriceList)) {
            throw new RuntimeException("调价单已过期");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        goodsAdjustPriceMapper.updateByLsh(lsh, user.getId(), new Date(), ApproveStateEnum.UNAPPROVED.getIndex());
    }

    @Override
    public void approved(String lsh) {
        List<GoodsAdjustPrice> goodsAdjustPriceList = this.parseMapToList(this.getByLsh(lsh));
        if (!this.examineApproveState(goodsAdjustPriceList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        // 检查单据是否过期
        if (!this.examineCreationDate(goodsAdjustPriceList)) {
            throw new RuntimeException("调价单已过期");
        }

        // 修改调价单状态
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        goodsAdjustPriceMapper.updateByLsh(lsh, user.getId(), new Date(), ApproveStateEnum.APPROVED.getIndex());

        // 进行调价
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsAdjustPrice gap : goodsAdjustPriceList) {
            Goods goods = new Goods();
            goods.setId(gap.getGsmGoodsId());
            goods.setRetailPrice(gap.getNewRetailPrice());
            goods.setSplitPrice(gap.getNewSplitPrice());
            goodsList.add(goods);
        }
        goodsService.updateRetailPriceByList(goodsList);

        // 因为是批量更新 所以要清除缓存 使其价格立即生效
        for (Goods goods : goodsList) {
            goodsService.cacheEvictById(goods.getId());
        }
    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return goodsAdjustPriceMapper.selectByLsh(lsh);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate, String name, Byte approveState) {
        return goodsAdjustPriceMapper.selectByCriteria(creationDate, name, approveState);
    }

    @Override
    public List<Map<String, Object>> getGroupListByCriteria(String[] creationDate, String lsh, Byte approveState) {
        return goodsAdjustPriceMapper.selectGroupListByCriteria(creationDate, lsh, approveState);
    }
}
