package com.chisapp.modules.inventory.service.impl;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.goods.bean.Goods;
import com.chisapp.modules.goods.service.GoodsService;
import com.chisapp.modules.inventory.bean.Inventory;
import com.chisapp.modules.inventory.dao.InventoryMapper;
import com.chisapp.modules.inventory.service.InventoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Tandy
 * @Date: 2019/9/29 16:24
 * @Version 1.0
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    private InventoryMapper inventoryMapper;
    @Autowired
    public void setInventoryMapper(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    private GoodsService goodsService;
    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void save(List<Inventory> inventoryList) {
        // 获取对应商品最后一次平均进价记录
        Map<Integer, BigDecimal> lacpMap = this.getLastAverageCostPrice(inventoryList);
        // 计算本次库存的平均成本价
        for (Inventory inventory : inventoryList) {
            BigDecimal averageCostPrice = this.computeAverageCostPrice(lacpMap, inventory.getGsmGoodsId(), inventory.getCostPrice());
            inventory.setAverageCostPrice(averageCostPrice); // 批次平均成本价
        }
        // 提交数据
        this.doSave(inventoryList);
    }

    private Map<Integer, BigDecimal> getLastAverageCostPrice(List<Inventory> inventoryList) {
        // 获取商品ID
        List<Integer> goodsIdList = new ArrayList<>();
        for (Inventory inventory : inventoryList) {
            goodsIdList.add(inventory.getGsmGoodsId());
        }
        // 查询并返回最后一次记录
        List<Inventory> lastInventoryList = this.getLastByGoodsIdList(goodsIdList);

        // 将记录以 Map 的方式返回
        Map<Integer, BigDecimal> inventoryMap = new HashMap<>();
        for (Inventory inventory : lastInventoryList) {
            inventoryMap.put(inventory.getGsmGoodsId(), inventory.getAverageCostPrice());
        }

        return inventoryMap;
    }

    private BigDecimal computeAverageCostPrice(Map<Integer, BigDecimal> lastAverageCostPriceMap, Integer goodsId, BigDecimal costPrice) {
        if (lastAverageCostPriceMap == null || goodsId == null || costPrice == null) {
            throw new RuntimeException("计算平均成本价时不能有空参数");
        }

        BigDecimal lacp = lastAverageCostPriceMap.get(goodsId);
        if (lacp == null) {
            return costPrice;
        } else {
            return lacp.add(costPrice).divide(new BigDecimal("2"), 4, BigDecimal.ROUND_HALF_UP);
        }
    }

    private void doSave (List<Inventory> inventoryList) {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        InventoryMapper mapper = batchSqlSession.getMapper(InventoryMapper.class);
        try {
            for (Inventory inventory : inventoryList) {
                mapper.insert(inventory);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void updateQuantityByList(List<Inventory> inventoryList) {
        // 获取要更新的库存ID 并将其封装成Map key=id, value=quantity(相同ID 数量要累加)
        List<Integer> inventoryIdList = new ArrayList<>();
        Map<Integer, Integer> inventoryMap = new HashMap<>();
        for (Inventory inventory : inventoryList) {
            if (inventory.getId() == null) {
                throw new RuntimeException("要更新的库存ID不能为空");
            }
            if (inventory.getQuantity() == null || inventory.getQuantity() == 0) {
                throw new RuntimeException("要更新的库存数量必须大于 0");
            }
            // 试图获取当前库存ID的Map记录
            Integer quantity = inventoryMap.get(inventory.getId());
            if (quantity != null) {
                // 如果可以获取的到, 则将记录的 quantity 进行累加
                quantity += inventory.getQuantity();
            } else {
                // 如果获取不到, 则使用当前库存 quantity, 并将ID加入到 inventoryIdList
                quantity = inventory.getQuantity();
                inventoryIdList.add(inventory.getId());
            }
            inventoryMap.put(inventory.getId(), quantity);
        }

        // 获取对应的库存集合
        List<Inventory> inventoryRecordList = this.getByIdList(inventoryIdList);

        // 判断库存是否足够
        for (Inventory recordInventory : inventoryRecordList) {
            int recordQuantity = recordInventory.getQuantity();
            int quantity = inventoryMap.get(recordInventory.getId());
            if (recordQuantity < quantity) {
                throw new RuntimeException("商品编码:【" + recordInventory.getGsmGoodsOid() + "】" +
                        " 商品名称:【" + recordInventory.getGsmGoodsName() + "】" +
                        " 批号:【" + recordInventory.getPh() + "】" +
                        " 批次号:【" + recordInventory.getPch() + "】" +
                        " 库存数量不足");
            }
        }

        // 获取批量执行的 sqlSessionFactory
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        // 获取操作的 Mapper 对象
        InventoryMapper mapper = batchSqlSession.getMapper(InventoryMapper.class);
        try {
            for (Inventory inventory : inventoryList) {
                mapper.updateQuantityById(inventory.getId(), inventory.getQuantity());
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void updateIymInventoryTypeIdByList(List<Inventory> inventoryList) {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        InventoryMapper mapper = batchSqlSession.getMapper(InventoryMapper.class);
        try {
            for (Inventory inventory : inventoryList) {
                mapper.updateIymInventoryTypeIdById(inventory.getIymInventoryTypeId(), inventory.getId());
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void splitQuantity(Integer id) {
        // 根据 ID 获取对应的库存信息
        Inventory inventory = inventoryMapper.selectByPrimaryKey(id);
        // 获取该库存的商品信息
        Goods goods = goodsService.getById(inventory.getGsmGoodsId());
        // 设置库存的部分商品信息
        inventory.setGsmGoodsOid(goods.getOid());
        inventory.setGsmGoodsName(goods.getName());

        // 判断该商品是否可拆零
        if (!goods.getSplitable()) {
            throw new RuntimeException("该商品禁止拆零");
        }
        // 判断拆零数量是否大于 1
        if (goods.getSplitQuantity() <= 1) {
            throw new RuntimeException("该商品拆零数量必须大于1");
        }
        // 判断该库存是否为拆零库存
        if (inventory.getSplitQuantity() > 1) {
            throw new RuntimeException("该记录已被拆零, 不能重复操作");
        }
        // 判断库存是否足够
        if (inventory.getSplitQuantity() <= 0) {
            throw new RuntimeException("库存可用拆零数量不足1");
        }

        // 拆零操作
        // 计算拆零成本单价
        BigDecimal costPrice = inventory.getCostPrice();
        // 获取拆分数量
        int splitQuantity = goods.getSplitQuantity();
        // 平均成本单价 = 成本单价 / 拆分数量
        BigDecimal averageSplitCostPrice = costPrice.divide(new BigDecimal(splitQuantity), 4, BigDecimal.ROUND_HALF_UP);
        // 获取最后一次平均成本单价的取值(通过该值判断是否有可以除尽)
        BigDecimal lastOneSplitCostPrice = costPrice.subtract(averageSplitCostPrice.multiply(new BigDecimal(splitQuantity -1)));

        // 创建一条拆零记录
        List<Inventory> inventoryList = new ArrayList<>();

        // 判断是否拆零平均成本单价无法除尽, 则单独创建一条拆零库存记录
        if (averageSplitCostPrice.compareTo(lastOneSplitCostPrice) != 0) {
            Inventory splitInventory = new Inventory();
            BeanUtils.copyProperties(inventory, splitInventory);

            splitInventory.setId(null); // 清空ID
            splitInventory.setSplitQuantity(splitQuantity); // 设置拆分数量
            splitInventory.setQuantity(1); // 设置拆分后的库存数量
            splitInventory.setCostPrice(lastOneSplitCostPrice); // 设置成本价

            inventoryList.add(splitInventory);
        }

        // 创建平均成本价的拆零库存记录
        {
            Inventory splitInventory = new Inventory();
            BeanUtils.copyProperties(inventory, splitInventory);

            splitInventory.setId(null); // 清空ID
            splitInventory.setSplitQuantity(splitQuantity); // 设置拆分数量
            splitInventory.setQuantity(splitQuantity - inventoryList.size()); // 设置拆分后的库存数量(如果已经创建了一条, 则减去对应的库存数量)
            splitInventory.setCostPrice(averageSplitCostPrice); // 设置成本价

            inventoryList.add(splitInventory);
        }

        // 提交拆分库存
        this.doSave(inventoryList);

        // 将原库存(进行拆分的库存)数量减 1
        inventory.setQuantity(1);
        List<Inventory> subtractInventoryList = new ArrayList<>();
        subtractInventoryList.add(inventory);
        this.updateQuantityByList(subtractInventoryList);

    }

    @Override
    public List<Inventory> getLastByGoodsIdList(List<Integer> goodsIdList) {
        return inventoryMapper.selectLastByGoodsIdList(goodsIdList);
    }

    @Override
    public List<Map<String, Object>> getClinicPchEnabledLikeByCriteria(Integer sysClinicId, Integer iymInventoryTypeId, String gsmGoodsName) {
        return inventoryMapper.selectClinicPchEnabledLikeByCriteria(sysClinicId, iymInventoryTypeId, gsmGoodsName);
    }

    @Override
    public List<Map<String, Object>> getClinicPchListByGoodsIdList(Integer sysClinicId, List<Integer> gsmGoodsIdList) {
        return inventoryMapper.selectClinicPchListByGoodsIdList(sysClinicId, gsmGoodsIdList);
    }

    @Override
    public List<Map<String, Object>> getClinicSubtractPchLikeByCriteria(Integer sysClinicId, Integer iymInventoryTypeId, Integer pemSupplierId, String gsmGoodsName) {
        return inventoryMapper.selectClinicSubtractPchLikeByCriteria(sysClinicId, iymInventoryTypeId, pemSupplierId, gsmGoodsName);
    }

    @Override
    public List<Inventory> getByIdList(List<Integer> idList) {
        List<Map<String, Object>> list = inventoryMapper.selectByIdList(idList);
        String inventoryJson = JSONUtils.parseObjectToJson(list);
        return JSONUtils.parseJsonToObject(inventoryJson, new TypeReference<List<Inventory>>() {});
    }

    @Override
    public List<Map<String, Object>> getPhListByCriteria(Integer sysClinicId,
                                                         Integer iymInventoryTypeId,
                                                         Boolean showZero,
                                                         String sysClinicName,
                                                         String gsmGoodsOid,
                                                         String gsmGoodsName,
                                                         String ph) {
        return inventoryMapper.selectPhListByCriteria(sysClinicId, iymInventoryTypeId, showZero, sysClinicName, gsmGoodsOid, gsmGoodsName, ph);
    }

    @Override
    public List<Map<String, Object>> getPchListByCriteria(Integer sysClinicId,
                                                          Integer iymInventoryTypeId,
                                                          Boolean showZero,
                                                          String sysClinicName,
                                                          String gsmGoodsOid,
                                                          String gsmGoodsName,
                                                          String ph) {
        return inventoryMapper.selectPchListByCriteria(sysClinicId, iymInventoryTypeId, showZero, sysClinicName, gsmGoodsOid, gsmGoodsName, ph);
    }


}
