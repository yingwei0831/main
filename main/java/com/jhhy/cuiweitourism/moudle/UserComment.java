package com.jhhy.cuiweitourism.moudle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiahe008 on 2016/9/18.
 * 国内游——>游客点评内容类
 */
public class UserComment implements Serializable{


    private String content; //评论内容
    private String nickName; //昵称
    private String userIcon; //用户头像
    private String commentTime; //评论时间
    private List<String> picList; //评论图片

    public UserComment(String nickName, String userIcon, String commentTime, String content, List<String> picList) {
        this.nickName = nickName;
        this.userIcon = userIcon;
        this.commentTime = commentTime;
        this.content = content;
        this.picList = picList;
    }

    public UserComment() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}
