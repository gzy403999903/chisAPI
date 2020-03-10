package com.chisapp.modules.doctorworkstation.service;

import com.chisapp.modules.doctorworkstation.bean.CommonDiagnose;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/11/6 16:58
 * @Version 1.0
 */
public interface CommonDiagnoseService {

    /**
     * 保存操作 返回 ID
     * @param commonDiagnose
     */
    @Transactional
    Integer save(CommonDiagnose commonDiagnose);

    /**
     * 修改操作
     * @param commonDiagnose
     * @return
     */
    @Transactional
    CommonDiagnose update(CommonDiagnose commonDiagnose);

    /**
     * 删除操作
     * @param id
     */
    @Transactional
    void delete(Integer id);

    /**
     * 根据 ID 获取对象
     * @param id
     * @return
     */
    CommonDiagnose getById(Integer id);

    /**
     * 根据查询条件获取 指定医生自定义和共享状态的诊断集合
     * @param creatorId
     * @param dwtDiagnoseTypeId
     * @param shareState
     * @param name
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer creatorId,
                                            Integer dwtDiagnoseTypeId,
                                            Boolean shareState,
                                            String name);

    /**
     * 根据诊断名称或助记码进行检索
     * @param dwtDiagnoseTypeId
     * @param name
     * @return
     */
    List<CommonDiagnose> getLikeByName(Integer dwtDiagnoseTypeId, String name);
}
