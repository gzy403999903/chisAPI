package com.chisapp.modules.inventory.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.financial.bean.PayableAccount;
import com.chisapp.modules.financial.service.PayableAccountService;
import com.chisapp.modules.inventory.bean.Inventory;
import com.chisapp.modules.inventory.bean.InventorySubtract;
import com.chisapp.modules.inventory.dao.InventorySubtractMapper;
import com.chisapp.modules.inventory.service.InventoryService;
import com.chisapp.modules.inventory.service.InventorySubtractService;
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

/**
 * @Author: Tandy
 * @Date: 2019/10/9 17:27
 * @Version 1.0
 */
@Service
public class InventorySubtractServiceImpl implements InventorySubtractService {

    private InventorySubtractMapper inventorySubtractMapper;
    @Autowired
    public void setInventorySubtractMapper(InventorySubtractMapper inventorySubtractMapper) {
        this.inventorySubtractMapper = inventorySubtractMapper;
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

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void save(List<InventorySubtract> subtractList, Byte actionType) {
        // 获取用户信息、获取流水号、 明细号
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String lsh = KeyUtils.getLSH(user.getId());
        int mxh = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        InventorySubtractMapper mapper = batchSqlSession.getMapper(InventorySubtractMapper.class);
        try {
            for (InventorySubtract inventorySubtract : subtractList) {
                // 将退货记录的部分属性使用库存属性赋值
                inventorySubtract.setLsh(lsh);
                inventorySubtract.setMxh(String.valueOf(mxh++));
                inventorySubtract.setSysClinicId(user.getSysClinicId());
                inventorySubtract.setActionType(actionType);
                inventorySubtract.setCreatorId(user.getId());
                inventorySubtract.setCreationDate(new Date());
                inventorySubtract.setApproveState(ApproveStateEnum.PENDING.getIndex());

                mapper.insert(inventorySubtract);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void unapproved(String lsh) {
        List<InventorySubtract> inventorySubtractList = this.parseToObjectList(this.getByLsh(lsh));
        if (!this.examineApproveState(inventorySubtractList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }

        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        inventorySubtractMapper.updateApproveStateByLsh(user.getId(), new Date(), ApproveStateEnum.UNAPPROVED.getIndex(), lsh);
    }

    @Override
    public void approved(String lsh) {
        List<InventorySubtract> inventorySubtractList = this.parseToObjectList(this.getByLsh(lsh));
        if (!this.examineApproveState(inventorySubtractList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }
        // 更新单据状态
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        inventorySubtractMapper.updateApproveStateByLsh(user.getId(), new Date(), ApproveStateEnum.APPROVED.getIndex(), lsh);

        // 创建要更新的库存集合[inventory.id inventory.quantity 必填]
        List<Inventory> inventoryList = new ArrayList<>();
        Inventory inventory;
        for (InventorySubtract inventorySubtract : inventorySubtractList) {
            inventory = new Inventory();
            inventory.setId(inventorySubtract.getIymInventoryId());
            inventory.setQuantity(inventorySubtract.getQuantity());
        }
        // 更新库存数量
        inventoryService.updateQuantityByList(inventoryList);

        // 供应商应付账款记账
        this.savePayableAccount(inventorySubtractList);
    }

    private void savePayableAccount(List<InventorySubtract> inventorySubtractList) {
        Integer pemSupplierId = null;
        BigDecimal payableAmount = new BigDecimal("0");
        PayableAccount payableAccount;
        List<PayableAccount> payableAccountList = new ArrayList<>();

        for (InventorySubtract inventorySubtract : inventorySubtractList) {
            // 获取本次供应商应付总金额
            if (pemSupplierId == null) {
                pemSupplierId = inventorySubtract.getPemSupplierId();
            }

            BigDecimal quantity = new BigDecimal(inventorySubtract.getQuantity());
            payableAmount = payableAmount.add(inventorySubtract.getCostPrice().multiply(quantity));

            // 获取供应商应付账款明细
            payableAccount = new PayableAccount();
            payableAccount.setLsh(inventorySubtract.getLsh());
            payableAccount.setMxh(inventorySubtract.getMxh());
            payableAccount.setGsmGoodsId(inventorySubtract.getGsmGoodsId());
            payableAccount.setPh(inventorySubtract.getPh());
            payableAccount.setPch(inventorySubtract.getPch());
            payableAccount.setCostPrice(inventorySubtract.getCostPrice());
            payableAccount.setQuantity(inventorySubtract.getQuantity() * -1); // 将数量改为负数
            payableAccount.setPurchaseTaxRate(inventorySubtract.getPurchaseTaxRate());
            payableAccount.setSellTaxRate(inventorySubtract.getSellTaxRate());
            payableAccount.setPemSupplierId(inventorySubtract.getPemSupplierId());
            payableAccount.setIymInventoryAddId(inventorySubtract.getIymInventoryAddId());
            payableAccount.setSysClinicId(inventorySubtract.getSysClinicId());
            payableAccount.setCreatorId(inventorySubtract.getCreatorId());
            payableAccount.setCreationDate(inventorySubtract.getCreationDate());

            payableAccountList.add(payableAccount);
        }

        // 扣减供应商应付账款金额
        supplierService.subtractArrearagesAmount(pemSupplierId, payableAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        // 保存应付账款记录
        payableAccountService.saveList(payableAccountList);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(String[] creationDate, Integer sysClinicId, Byte approveState, String pemSupplierName) {
        return inventorySubtractMapper.selectClinicListByCriteria(creationDate, sysClinicId, approveState, pemSupplierName);
    }

    @Override
    public List<Map<String, Object>> getClinicLshGroupListByCriteria(
            String[] creationDate, Integer sysClinicId, Byte approveState, String pemSupplierName) {
        return inventorySubtractMapper.selectClinicLshGroupListByCriteria(creationDate, sysClinicId, approveState, pemSupplierName);
    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return inventorySubtractMapper.selectByLsh(lsh);
    }


}
