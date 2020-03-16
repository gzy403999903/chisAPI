package com.chisapp.modules.messager.handler;

import com.chisapp.modules.messager.ben.Messager;
import com.chisapp.modules.messager.service.MessagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2020-03-16 21:55
 * @Version 1.0
 */
@RequestMapping("/messager")
@RestController
public class MessagerHandler {

    private MessagerService messagerService;
    @Autowired
    public void setMessagerService(MessagerService messagerService) {
        this.messagerService = messagerService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 获取消息集合
     * @return
     */
    @GetMapping("/getMessageList")
    public List<Messager> getMessageList() {
        return messagerService.getMessageList();
    }













}
