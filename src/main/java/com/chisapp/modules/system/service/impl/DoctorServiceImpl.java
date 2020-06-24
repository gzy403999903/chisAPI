package com.chisapp.modules.system.service.impl;

import com.chisapp.common.utils.fileutils.FileUploadUtils;
import com.chisapp.modules.system.bean.Doctor;
import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.dao.DoctorMapper;
import com.chisapp.modules.system.service.DoctorService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.util.*;
import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/10/22 16:10
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Doctor")
@Service
public class DoctorServiceImpl implements DoctorService {
    private DoctorMapper doctorMapper;
    @Autowired
    public void setDoctorMapper(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @CacheEvict(allEntries = true)
    @Override
    public void save(Doctor doctor) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        doctor.setCreatorId(user.getId());
        doctor.setCreationDate(new Date());
        doctorMapper.insert(doctor);

        // 取消上传文件的游离状态
        this.cancelDissociative(doctor);
    }

    @CacheEvict(allEntries = true)
    @Override
    public Doctor update(Doctor doctor) {
        // 删除历史上传文件
        this.fileDeleteFromRecord(doctor);
        // 取消上传文件的游离状态
        this.cancelDissociative(doctor);

        doctorMapper.updateByPrimaryKey(doctor);
        return doctor;
    }

    private void cancelDissociative (Doctor doctor) {
        // 取消上传签名的游离状态
        if (doctor.getSignatureUrl() != null && !doctor.getSignatureUrl().trim().equals("")) {
            fileUploadUtils.cancelDissociative(this.imageDir + doctor.getSignatureUrl());
        }
        // 取消上传头像的游离状态
        if (doctor.getAvatarUrl() != null && !doctor.getAvatarUrl().trim().equals("")) {
            fileUploadUtils.cancelDissociative(this.imageDir + doctor.getAvatarUrl());
        }
    }

    private void fileDeleteFromRecord (Doctor doctor) {
        Doctor record = this.getById(doctor.getId());
        // 删除历史签名文件
        if (record.getSignatureUrl() != null && !doctor.getSignatureUrl().equals(record.getSignatureUrl())) {
            this.fileDelete(record.getSignatureUrl());
        }
        // 删除历史头像文件
        if (record.getAvatarUrl() != null && !doctor.getAvatarUrl().equals(record.getAvatarUrl())) {
            this.fileDelete(record.getAvatarUrl());
        }
    }

    @CacheEvict(allEntries = true)
    @Override
    public void delete(Doctor doctor) {
        doctorMapper.deleteByPrimaryKey(doctor.getId());

        // 删除上传的签名
        if (doctor.getSignatureUrl() != null && !doctor.getSignatureUrl().trim().equals("")) {
            this.fileDelete(doctor.getSignatureUrl());
        }
        // 删除上传的头像
        if (doctor.getAvatarUrl() != null && !doctor.getAvatarUrl().trim().equals("")) {
            this.fileDelete(doctor.getAvatarUrl());
        }
    }

    @Override
    public Doctor getById(Integer id) {
        return doctorMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(String sysClinicName, String name) {
        return doctorMapper.selectClinicListByCriteria(sysClinicName, name);
    }

    // 如果 Doctor.id 已经做为缓存 key, 则此处需加字符串防止 sysClinicId 与 Doctor.id 相同
    @Cacheable(key = "#sysClinicId")
    @Override
    public List<Map<String, Object>> getClinicEnabled(Integer sysClinicId) {
        return doctorMapper.selectClinicEnabled(sysClinicId);
    }

    @CacheEvict(key = "#sysClinicId")
    @Override
    public void getClinicEnabledCacheEvict(Integer sysClinicId) {
    }

    @Autowired
    private FileUploadUtils fileUploadUtils;

    // 上传图片的真实路径
    @Value("${upload.image-dir}")
    private String imageDir;

    @Override
    public String fileUploadSignature(MultipartFile file) {
        String virtualUrl = fileUploadUtils.create(file, this.imageDir).validateToSave(
                Arrays.asList("jpg", "jpeg"),
                500,
                500,
                1024L);
        String fileName = fileUploadUtils.getFileName(virtualUrl);
        fileUploadUtils.markDissociative(this.imageDir + fileName);

        return virtualUrl;
    }

    @Override
    public String fileUploadAvatar(MultipartFile file) {
        String virtualUrl = fileUploadUtils.create(file, this.imageDir).validateToSave(
                Arrays.asList("jpg", "jpeg"),
                500,
                500,
                2048L);
        String fileName = fileUploadUtils.getFileName(virtualUrl);
        fileUploadUtils.markDissociative(this.imageDir + fileName);

        return virtualUrl;
    }

    @Override
    public Boolean fileDelete(String virtualDir) {
        String fileName = fileUploadUtils.getFileName(virtualDir);
        fileUploadUtils.cancelDissociative(this.imageDir + fileName);
        return fileUploadUtils.delete(imageDir + fileName);
    }


}
