package com.chisapp.common.utils.fileutils;

import com.chisapp.common.utils.KeyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2020-06-18 19:32
 * @Version 1.0
 */
public class FileCreator {

    private MultipartFile file;
    private String realDir;
    private String virtualDir;

    FileCreator(MultipartFile file, String realDir, String virtualDir){
        this.requiredParamsValidate(file, realDir, virtualDir);
        this.file = file;
        this.realDir = realDir;
        this.virtualDir = virtualDir;
    }

    /**
     * 对上传文件进行属性验证
     * @param fileSuffix 允许上传的图片格式
     * @param maxWidth 允许图片宽度
     * @param maxHeight 允许图片高度
     * @param maxFileSize 允许文件的大小(单位是 KB)
     * @return
     */
    public String validateToSave(List<String> fileSuffix, Integer maxWidth, Integer maxHeight, Long maxFileSize) {
        this.fileSuffixValidate(fileSuffix);
        this.ImageSizeValidate(maxWidth, maxHeight);
        this.fileSizeValidate(maxFileSize);
        return this.save();
    }

    /**
     * 保存文件
     * @return
     */
    public String save() {
        // 设置文件名称
        String fileName = KeyUtils.getUUID() + "." + this.getFileSuffix();
        // 设置存放路径
        String filePath = this.realDir + fileName;
        // 创建文件
        File dest = new File(filePath);
        // 检查存放路径是否存在, 如果不存在则自动创建
        if (!dest.exists() && !dest.mkdirs()) {
            throw new RuntimeException("创建" + this.realDir + "路径失败");
        }
        // 保存文件
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }

        return this.virtualDir + fileName;
    }

    /**
     * 验证必须传入的参数
     * @param file
     * @param realDir
     * @param virtualDir
     */
    private void requiredParamsValidate(MultipartFile file, String realDir, String virtualDir) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }
        if (realDir == null || realDir.trim().equals("")) {
            throw new RuntimeException("上传真实路径不能为空");
        }
        if (virtualDir == null || virtualDir.trim().equals("")) {
            throw new RuntimeException("上传虚拟路径不能为空");
        }
    }

    /**
     * 获取文件的后缀
     * @return String
     */
    private String getFileSuffix () {
        // 获取文件名
        String originalFileName = this.file.getOriginalFilename();
        if (originalFileName == null) {
            throw new RuntimeException("无法获取文件名");
        }

        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }

    /**
     * 验证上传文件是否符合指定的格式
     * @param fileSuffix
     */
    private void fileSuffixValidate (List<String> fileSuffix) {
        if (fileSuffix == null || fileSuffix.size() == 0) {
            throw new RuntimeException("fileSuffix is null");
        }

        // 判断是否为指定的格式
        String uploadSuffix= this.getFileSuffix();
        if (!uploadSuffix.contains(uploadSuffix)) {
            throw new RuntimeException("上传文件格式只能是 " + fileSuffix + " 格式");
        }
    }

    /**
     * 验证上传图片的尺寸
     * @param maxWidth
     * @param maxHeight
     */
    private void ImageSizeValidate (Integer maxWidth, Integer maxHeight) {
        if (maxWidth == null || maxWidth < 0) {
            throw new RuntimeException("maxWidth is null or zero");
        }
        if (maxHeight == null || maxHeight < 0) {
            throw new RuntimeException("maxHeight is null or zero");
        }

        // 判断是否符合指定尺寸
        int uploadWidth;
        int uploadHeight;
        try {
            uploadWidth = ImageIO.read(file.getInputStream()).getWidth();
            uploadHeight = ImageIO.read(file.getInputStream()).getHeight();
        } catch (IOException e) {
            throw new RuntimeException("无法获取图片尺寸");
        }
        if (uploadWidth > maxWidth || uploadHeight > maxHeight) {
            throw new RuntimeException("上传图片尺寸不能超过以下尺寸 [宽:" + maxWidth + ", 高:" + maxHeight + "]");
        }
    }

    /**
     * 验证上传文件的大小(参数单位是 KB)
     * @param maxFileSize
     */
    private void fileSizeValidate (Long maxFileSize) {
        if (maxFileSize == null || maxFileSize < 0) {
            throw new RuntimeException("maxFileSize not fit the rule");
        }

        // 获取上传文件大小(转成KB), 并判断是否符合指定大小
        long uploadSize = this.file.getSize() / 1024;
        if (uploadSize > maxFileSize) {
            throw new RuntimeException("上传图片不能大于 " + maxFileSize + "KB");
        }
    }












}
