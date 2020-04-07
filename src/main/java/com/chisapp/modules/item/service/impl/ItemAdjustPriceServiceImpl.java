package com.chisapp.modules.item.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.DateUtils;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.item.bean.Item;
import com.chisapp.modules.item.bean.ItemAdjustPrice;
import com.chisapp.modules.item.dao.ItemAdjustPriceMapper;
import com.chisapp.modules.item.service.ItemAdjustPriceService;
import com.chisapp.modules.item.service.ItemService;
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
 * @Date: 2019/8/9 8:38
 * @Version 1.0
 */
@Service
public class ItemAdjustPriceServiceImpl implements ItemAdjustPriceService {

    private ItemAdjustPriceMapper itemAdjustPriceMapper;
    @Autowired
    public void setItemAdjustPriceMapper(ItemAdjustPriceMapper itemAdjustPriceMapper) {
        this.itemAdjustPriceMapper = itemAdjustPriceMapper;
    }

    private ItemService itemService;
    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void save(List<ItemAdjustPrice> itemAdjustPriceList) {
        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 获取流水号
        String lsh = KeyUtils.getLSH(user.getId());
        // 初始化明细号
        int mxh = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        ItemAdjustPriceMapper mapper = batchSqlSession.getMapper(ItemAdjustPriceMapper.class);
        try {
            for (ItemAdjustPrice iap : itemAdjustPriceList) {
                iap.setLsh(lsh);
                iap.setMxh(String.valueOf(mxh++));
                iap.setApproveState(ApproveStateEnum.PENDING.getIndex());
                iap.setCreatorId(user.getId());
                iap.setCreationDate(new Date());
                iap.setExpiryDate(DateUtils.getFutureDate(1));

                mapper.insert(iap);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void unapproved(String lsh) {
        List<ItemAdjustPrice> itemAdjustPriceList = this.parseMapToItemAdjustPrice(this.getByLsh(lsh));
        if (!this.examineApproveState(itemAdjustPriceList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        // 检查单据是否过期
        if (!this.examineCreationDate(itemAdjustPriceList)) {
            throw new RuntimeException("调价单已过期");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemAdjustPriceMapper.updateByLsh(lsh, user.getId(), new Date(), ApproveStateEnum.UNAPPROVED.getIndex());
    }

    @Override
    public void approved(String lsh) {
        List<ItemAdjustPrice> itemAdjustPriceList = this.parseMapToItemAdjustPrice(this.getByLsh(lsh));
        if (!this.examineApproveState(itemAdjustPriceList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据需为待审批状态");
        }

        // 检查单据是否过期
        if (!this.examineCreationDate(itemAdjustPriceList)) {
            throw new RuntimeException("调价单已过期");
        }

        // 修改调价单状态
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        itemAdjustPriceMapper.updateByLsh(lsh, user.getId(), new Date(), ApproveStateEnum.APPROVED.getIndex());

        // 进行调价
        List<Item> itemList = new ArrayList<>();
        for (ItemAdjustPrice iap : itemAdjustPriceList) {
            Item item = new Item();
            item.setId(iap.getCimItemId());
            item.setRetailPrice(iap.getNewRetailPrice());

            itemList.add(item);
        }
        itemService.updateRetailPriceByList(itemList);

        // 清除缓存使其立即生效
        for (Item item : itemList) {
            itemService.cacheEvictById(item);
        }

    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return itemAdjustPriceMapper.selectByLsh(lsh);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate, String name, Byte approveState) {
        return itemAdjustPriceMapper.selectByCriteria(creationDate, name, approveState);
    }

    @Override
    public List<Map<String, Object>> getGroupListByCriteria(String[] creationDate, String lsh, Byte approveState) {
        return itemAdjustPriceMapper.selectGroupListByCriteria(creationDate, lsh, approveState);
    }
}
