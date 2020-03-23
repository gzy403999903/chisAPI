package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.ClinicRoom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/22 13:50
 * @Version 1.0
 */
public interface ClinicRoomService {

    /**
     * 保存操作
     * @param clinicRoom
     */
    @Transactional
    void save(ClinicRoom clinicRoom);

    /**
     * 修改操作
     * @param clinicRoom
     * @return
     */
    @Transactional
    ClinicRoom update(ClinicRoom clinicRoom);

    /**
     * 删除操作
     * @param clinicRoom
     */
    @Transactional
    void delete(ClinicRoom clinicRoom);

    /**
     * 根据 ID 获取对象
     * @param id
     * @return
     */
    ClinicRoom getById(Integer id);

    /**
     * 根据查询条件获取对应的诊室集合
     * @param sysClinicName
     * @param name
     * @return
     */
    List<Map<String, Object>> getClinicListByCriteria(String sysClinicName, String name);

    /**
     * 获取对应机构所有的诊室
     * @return
     */
    List<ClinicRoom> getClinicListAll(Integer sysClinicId);
}
