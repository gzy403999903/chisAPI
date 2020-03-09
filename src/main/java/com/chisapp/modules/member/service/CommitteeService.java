package com.chisapp.modules.member.service;

import com.chisapp.modules.member.bean.Committee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/21 14:11
 * @Version 1.0
 */
public interface CommitteeService {

    /**
     * 添加操作
     * @param committee
     */
    @Transactional
    void save(Committee committee);

    /**
     * 修改操作
     * @param committee
     * @return
     */
    @Transactional
    Committee update(Committee committee);

    /**
     * 删除操作
     * @param id
     */
    @Transactional
    void delete(Integer id);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    Committee getById(Integer id);

    /**
     * 根据获取最大的 typeNo
     * @param typeId
     * @param mrmTownshipId
     * @return
     */
    Short getMaxTypeNoByCriteria(Byte typeId, Integer mrmTownshipId);

    /**
     * 根据查询条件获取对象集合
     * @param mrmTownshipName
     * @param name
     * @return
     */
    List<Map<String, Object>> getByCriteria(String mrmTownshipName, String name);

    /**
     * 根据所属 乡镇(街道)ID 获取对应的 村(居委会)集合
     * @param mrmTownshipId
     * @return
     */
    List<Map<String, Object>> getByMrmTownshipId(Integer mrmTownshipId);

    /**
     * 根据 ID 返回一个 map 视图对象
     * 次方法在进行健康档案编码时被调用, 且只有在该村(居委会)没有档案记录时才会被调用
     * Map至少包含以下内容: 6位区域编码、3位乡镇(街道)编码、3位村(居委会)编码
     * @param id
     * @return
     */
    Map<String, Object> getCommitteeMapById(Integer id);

}
