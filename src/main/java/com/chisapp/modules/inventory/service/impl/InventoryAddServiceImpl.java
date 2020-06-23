package com.chisapp.modules.inventory.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.financial.bean.PayableAccount;
import com.chisapp.modules.financial.service.PayableAccountService;
import com.chisapp.modules.inventory.bean.Inventory;
import com.chisapp.modules.inventory.bean.InventoryAdd;
import com.chisapp.modules.inventory.dao.InventoryAddMapper;
import com.chisapp.modules.inventory.service.InventoryAddService;
import com.chisapp.modules.inventory.service.InventoryService;
import com.chisapp.modules.purchase.bean.AssessCost;
import com.chisapp.modules.purchase.service.AssessCostService;
import com.chisapp.modules.purchase.service.PurchaseOrderService;
import com.chisapp.modules.purchase.service.SupplierService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Tandy
 * @Date: 2019/9/21 21:52
 * @Version 1.0
 */
@Service
public class InventoryAddServiceImpl implements InventoryAddService {

    private InventoryAddMapper inventoryAddMapper;
    @Autowired
    public void setInventoryAddMapper(InventoryAddMapper inventoryAddMapper) {
        this.inventoryAddMapper = inventoryAddMapper;
    }

    private PurchaseOrderService purchaseOrderService;
    @Autowired
    public void setPurchaseOrderService(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    private InventoryService inventoryService;
    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    private PayableAccountService payableAccountService;
    @Autowired
    public void setPayableAccountService(PayableAccountService payableAccountService) {
        this.payableAccountService = payableAccountService;
    }

    private SupplierService supplierService;
    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Autowired
    private AssessCostService assessCostService;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void save(List<InventoryAdd> inventoryAddList, String orderLsh, Byte actionType) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        // 如果订单号不为空, 则为采购计划入库, 将订单设置为已入库状态(此操作不通过则不继续执行)
        if (orderLsh != null) {
            List<Map<String, Object>> clinicOrderList = purchaseOrderService.getClinicListByLsh(orderLsh, user.getSysClinicId());
            purchaseOrderService.updateInventoryStateByCriteria(true, purchaseOrderService.convertToObjectList(clinicOrderList));
        }

        // 对同一商品、相同批号进行合并
        Map<String, InventoryAdd> distinctMap = new HashMap<>();
        for (InventoryAdd inventoryAdd : inventoryAddList) {
            String key = inventoryAdd.getGsmGoodsId() + inventoryAdd.getPh(); // 创建 distinctMap key 为 商品ID+批号
            InventoryAdd distinctIa = distinctMap.get(key); // 试图从distinctMap中获取记录

            if (distinctIa != null) {  // 如果可以获取到入库记录则将当前记录和获取到的记录进行数量合并
                int quantity = distinctIa.getQuantity() + inventoryAdd.getQuantity(); // 合并数量 = 记录数量 + 当前数量
                inventoryAdd.setQuantity(quantity); // 进行赋值
            }

            distinctMap.put(key, inventoryAdd);
        }

        // 获取流水号、 明细号
        String lsh = KeyUtils.getLSH(user.getId());
        int mxh = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        InventoryAddMapper mapper = batchSqlSession.getMapper(InventoryAddMapper.class);
        try {
            for (String key : distinctMap.keySet()) {
                InventoryAdd inventoryAdd = distinctMap.get(key);

                inventoryAdd.setLsh(lsh); // 设置流水号
                inventoryAdd.setMxh(String.valueOf(mxh++)); // 设置明细号
                inventoryAdd.setPch(KeyUtils.getPch()); // 设置批次号
                inventoryAdd.setActionType(actionType); // 操作类型
                inventoryAdd.setSysClinicId(user.getSysClinicId()); // 机构ID
                inventoryAdd.setCreatorId(user.getId()); // 创建人ID
                inventoryAdd.setCreationDate(new Date()); // 创建时间
                inventoryAdd.setApproveState(ApproveStateEnum.PENDING.getIndex()); // 设置审批状态

               mapper.insert(inventoryAdd);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void unapproved(String lsh) {
        List<InventoryAdd> inventoryAddList = this.parseToObjectList(this.getByLsh(lsh));
        if (!this.examineApproveState(inventoryAddList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }

        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        inventoryAddMapper.updateApproveStateByLsh(
                user.getId(), new Date(), ApproveStateEnum.UNAPPROVED.getIndex(), lsh, ApproveStateEnum.PENDING.getIndex());
    }

    @Override
    public void approved(String lsh) {
        List<InventoryAdd> inventoryAddList = this.parseToObjectList(this.getByLsh(lsh));
        if (!this.examineApproveState(inventoryAddList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }
        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        inventoryAddMapper.updateApproveStateByLsh(user.getId(), new Date(), ApproveStateEnum.APPROVED.getIndex(),
                lsh, ApproveStateEnum.PENDING.getIndex());

        // 添加库存
        List<Inventory> inventoryList = new ArrayList<>();
        for (InventoryAdd inventoryAdd : inventoryAddList) {
            // 库存数据
            Inventory inventory = new Inventory();

            inventory.setSysClinicId(user.getSysClinicId()); // 机构ID
            inventory.setIymInventoryTypeId(inventoryAdd.getIymInventoryTypeId()); // 仓库ID
            inventory.setGsmGoodsId(inventoryAdd.getGsmGoodsId()); // 商品ID
            inventory.setPh(inventoryAdd.getPh()); // 批号
            inventory.setPch(inventoryAdd.getPch()); // 批次号
            inventory.setQuantity(inventoryAdd.getQuantity()); // 入库数量
            inventory.setCostPrice(inventoryAdd.getCostPrice()); // 成本价
            inventory.setFirstCostPrice(inventoryAdd.getCostPrice()); // 一成本价
            inventory.setSecondCostPrice(inventoryAdd.getCostPrice()); // 二成本价
            inventory.setPurchaseTaxRate(inventoryAdd.getPurchaseTaxRate()); // 进货税率
            inventory.setSellTaxRate(inventoryAdd.getSellTaxRate()); // 销售税率
            inventory.setProducedDate(inventoryAdd.getProducedDate()); // 生产日期
            inventory.setExpiryDate(inventoryAdd.getExpiryDate()); // 有效期至
            inventory.setPemSupplierId(inventoryAdd.getPemSupplierId()); // 供应商ID
            inventory.setIymInventoryAddId(inventoryAdd.getId());

            inventoryList.add(inventory);
        }
        // 设置考核成本
        this.setAssessCost(inventoryList);
        // 保存库存记录
        inventoryService.save(inventoryList);
        // 供应商应付账款记账
        this.savePayableAccount(inventoryAddList);
    }

    private void setAssessCost(List<Inventory> inventoryList) {
        if (inventoryList.isEmpty()) {
            return;
        }

        // 获取供应商ID 与 商品ID集合
        Integer pemSupplierId = inventoryList.get(0).getPemSupplierId();
        List<Integer> gsmGoodsIdList = inventoryList.stream().map(Inventory::getGsmGoodsId).distinct().collect(Collectors.toList());

        // 获取对应的考核成本集合
        List<AssessCost> assessCostList = this.assessCostService.getBySupplierIdAndGoodsIdList(pemSupplierId, gsmGoodsIdList);
        // 将 assessCostList 转成 map<gsmGoodsId, AssessCost>
        Map<Integer, AssessCost> assessCostMap = new HashMap<>();
        for (AssessCost assessCost : assessCostList) {
            assessCostMap.put(assessCost.getGsmGoodsId(), assessCost);
        }

        // 设置考核成本
        AssessCost assessCost = null;
        for (Inventory inventory : inventoryList) {
            assessCost = assessCostMap.get(inventory.getGsmGoodsId());
            if (assessCost != null) {
                inventory.setFirstCostPrice(assessCost.getFirstCostPrice());
                inventory.setSecondCostPrice(assessCost.getSecondCostPrice());
            }
        }

    }

    private void savePayableAccount (List<InventoryAdd> inventoryAddList) {
        Integer pemSupplierId = null;
        BigDecimal payableAmount = new BigDecimal("0");
        PayableAccount payableAccount;
        List<PayableAccount> payableAccountList = new ArrayList<>();

        for (InventoryAdd inventoryAdd : inventoryAddList) {
            // 获取本次供应商应付总金额
            if (pemSupplierId == null) {
               pemSupplierId = inventoryAdd.getPemSupplierId();
            }

            // 计算合计应付金额
            BigDecimal quantity = new BigDecimal(inventoryAdd.getQuantity());
            payableAmount = payableAmount.add(inventoryAdd.getCostPrice().multiply(quantity));

            // 获取供应商应付账款明细
            payableAccount = new PayableAccount();
            payableAccount.setLsh(inventoryAdd.getLsh());
            payableAccount.setMxh(inventoryAdd.getMxh());
            payableAccount.setGsmGoodsId(inventoryAdd.getGsmGoodsId());
            payableAccount.setPh(inventoryAdd.getPh());
            payableAccount.setPch(inventoryAdd.getPch());
            payableAccount.setCostPrice(inventoryAdd.getCostPrice());
            payableAccount.setQuantity(inventoryAdd.getQuantity());
            payableAccount.setPurchaseTaxRate(inventoryAdd.getPurchaseTaxRate());
            payableAccount.setSellTaxRate(inventoryAdd.getSellTaxRate());
            payableAccount.setPemSupplierId(inventoryAdd.getPemSupplierId());
            payableAccount.setIymInventoryAddId(inventoryAdd.getId());
            payableAccount.setSysClinicId(inventoryAdd.getSysClinicId());
            payableAccount.setCreatorId(inventoryAdd.getCreatorId());
            payableAccount.setCreationDate(inventoryAdd.getCreationDate());

            payableAccountList.add(payableAccount);
        }

        // 增加供应商应付账款金额
        supplierService.addArrearagesAmount(pemSupplierId, payableAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        // 保存应付账款记录
        payableAccountService.saveList(payableAccountList);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(String[] creationDate,
                                                             Integer sysClinicId,
                                                             Byte approveState,
                                                             Byte actionType,
                                                             String pemSupplierName,
                                                             String gsmGoodsOid,
                                                             String gsmGoodsName) {
        return inventoryAddMapper.selectClinicListByCriteria(
                creationDate, sysClinicId, approveState, actionType, pemSupplierName, gsmGoodsOid, gsmGoodsName);
    }

    @Override
    public List<Map<String, Object>> getClinicLshGroupListByCriteria(String[] creationDate,
                                                                     Integer sysClinicId,
                                                                     Byte approveState,
                                                                     String pemSupplierName) {
        return inventoryAddMapper.selectClinicLshGroupListByCriteria(creationDate, sysClinicId, approveState, pemSupplierName);
    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return inventoryAddMapper.selectByLsh(lsh);
    }


}
