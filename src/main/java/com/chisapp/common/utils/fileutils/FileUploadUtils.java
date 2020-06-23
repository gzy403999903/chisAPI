package com.chisapp.common.utils.fileutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author: Tandy
 * @Date: 2020-06-18 19:28
 * @Version 1.0
 */
@Component
public class FileUploadUtils {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 上传文件虚拟路径
    @Value("${upload.virtual-dir}")
    private String virtualDir;

    /**
     * 创建上传文件
     * @param file
     * @param realDir
     * @return
     */
    public FileCreator create (MultipartFile file, String realDir) {
        return new FileCreator(file, realDir, this.virtualDir);
    }

    /**
     * 根据虚拟路径获取文件名
     * @param virtualDir
     * @return
     */
    public String getFileName (String virtualDir) {
        if (virtualDir == null || virtualDir.trim().equals("")) {
            throw new RuntimeException("virtualUrl is null");
        }
        // 返回文件名
        return virtualDir.substring(virtualDir.lastIndexOf("/") + 1);
    }

    /**
     * 通过路径定位文件并进行删除
     * @param realFilePath
     * @return
     */
    public Boolean delete (String realFilePath) {
        if (realFilePath == null || realFilePath.trim().equals("")) {
            throw new RuntimeException("realFilePath is null");
        }
        // 返回删除结果
        return new File(realFilePath).delete();
    }

    /**
     * 通过文件和对应的文件名删除
     * @param file
     * @return
     */
    public int delete (File file, String fileName) {
        int dCount =  0;

        // 获取文件集合, 并检查是否有可删除的文件
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return dCount;
        }

        // 遍历文件
        // 如果当前文件是文件夹类型在进行递归查找
        // 如果当前文件是文件类型且名称与传入的文件名称相同, 则对其进行删除
        // 删除成功后计数器 +1
        for (File f : files) {
            if (f.isDirectory()) {
                delete(f, fileName);
            } else if (f.isFile() && f.getName().equals(fileName)) {
                if (f.delete()) {
                    ++dCount;
                }
            }
        }
        return dCount;
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private static final String DISSOCIATIVE_KEY = "dissociativeFile";

    /**
     * 标记一个文件为游离状态
     * @param realFilePath
     */
    public void markDissociative (String realFilePath) {
        stringRedisTemplate.opsForSet().add(DISSOCIATIVE_KEY, realFilePath);
    }

    /**
     * 取消一个文件为游离状态
     * @param realFilePath
     */
    public void cancelDissociative(String realFilePath) {
        stringRedisTemplate.opsForSet().remove(DISSOCIATIVE_KEY, realFilePath);
    }

    /**
     * 自动清理游离状态的上传文件
     */
    @Scheduled(cron = "20 0 3 * * 1") // 秒 分 时 日 月 周
    private void dissociativeSweeper () {
        logger.info("开始清理游离状态的上传文件, 当前时间为" + LocalDateTime.now());

        Set<String> set = stringRedisTemplate.opsForSet().members(DISSOCIATIVE_KEY);
        assert set != null;
        for (String realFilePath : set) {
            this.delete(realFilePath);
            this.cancelDissociative(realFilePath);
        }
    }

}
