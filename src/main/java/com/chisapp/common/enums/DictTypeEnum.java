package com.chisapp.common.enums;

/**
 * @Author: Tandy
 * @Date: 2019/8/6 16:58
 * @Version 1.0
 */
public enum DictTypeEnum {
    /**
     * 100 项目字典
     * 200 财务字典
     * 300 会员字典
     * 400 商品字典
     */
    ITEM_UNITS("项目单位", 101),
    ITEM_CLASSIFY("项目分类", 102),

    BILLING_TYPE("计费类型", 201),
    INVOICE_TYPE("发票类型", 202),
    PAYMENT_TYPE("结算类型", 203),
    PAYMENT_WAY("结算方式", 204),

    GENDER("性别", 301),
    NATIONALITY("民族", 302),
    MARITAL("婚姻状况", 303),
    EDUCATION("文化程度", 304),
    BLOOD_TYPE("血型", 305),
    RH("RH", 306),
    PROFESSION("职业", 307),
    FFS_PAYMENT_WAY("医疗费用支付方式", 308), // FFS = fee-for-service
    EXPOSURE("暴露史", 309),
    ALLERGY("药物过敏史", 310),
    ILLNESS("疾病", 311),
    DISABILITY("残疾情况", 312),
    KITCHEN_EXHAUST("厨房通风设施", 313),
    FUEL_TYPE("燃料类型", 314),
    WATER_SOURCE("饮水", 315),
    TOILET_TYPE("厕所", 316),
    LIVESTOCK_FENCE("禽畜栏", 317),
    PREVIOUS_ILLNESS("既往史-疾病", 318),
    PREVIOUS_SURGERY("既往史-手术", 319),
    PREVIOUS_TRAUMA("既往史-外伤", 320),
    PREVIOUS_TRANSFUSION("既往史-输血", 321),
    FAMILY_FATHER("家族史-父亲", 322),
    FAMILY_MOTHER("家族史-母亲", 323),
    FAMILY_SIBLING("家族史-兄弟姐妹", 324),
    FAMILY_CHILDREN("家族史-子女", 325),
    FAMILY_ILLNESS("家族史疾病", 326),

    GOODS_UNITS("商品单位", 401),
    DOSE_UNITS("剂量单位", 402),
    DOSE_TYPE("药品剂型", 403),
    DRUG_FREQUENCY("用药频率", 404),
    DRUG_USAGE("给药途径", 405),
    MANUFACTURER("生产厂家", 406),
    ORIGIN("产地", 407),
    GOODS_CLASSIFY("商品分类", 408),
    SELL_CLASSIFY("销售分类", 409),
    SPECIAL_DRUG("特殊药品", 410),
    STORAGE_TEMPERATURE("存储条件", 411),
    PRESCRIPTION_TYPE("处方类型", 412),
    DRUGS_PREPARE_METHOD("炮制方法", 413),

    OTHER("其他", 999);

    private String name;
    private Integer index;

    private DictTypeEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
