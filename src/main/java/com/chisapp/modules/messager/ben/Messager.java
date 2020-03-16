package com.chisapp.modules.messager.ben;

import java.io.Serializable;

/**
 * @Author: Tandy
 * @Date: 2020-03-16 21:36
 * @Version 1.0
 */
public class Messager implements Serializable {
    private String type; // 消息类型
    private String content; // 消息内容
    private String sender; // 发送人

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
