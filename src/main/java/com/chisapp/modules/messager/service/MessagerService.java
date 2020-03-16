package com.chisapp.modules.messager.service;

import com.chisapp.modules.messager.ben.Messager;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2020-03-16 21:57
 * @Version 1.0
 */
public interface MessagerService {

    /**
     * 获取消息集合
     * @return
     */
    List<Messager> getMessageList();


}
