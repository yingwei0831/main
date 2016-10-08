package com.jhhy.cuiweitourism.moudle;

/**
 * Created by birney on 2016-09-08.
 * 个人中心，我的消息
 */
public class UserMessage {
    private String msgId;
    private String msgTitle;
    private String msgMemberId;
    private String msgPicPath;
    private String msgPl;

    public UserMessage() {
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgMemberId() {
        return msgMemberId;
    }

    public void setMsgMemberId(String msgMemberId) {
        this.msgMemberId = msgMemberId;
    }

    public String getMsgPicPath() {
        return msgPicPath;
    }

    public void setMsgPicPath(String msgPicPath) {
        this.msgPicPath = msgPicPath;
    }

    public String getMsgPl() {
        return msgPl;
    }

    public void setMsgPl(String msgPl) {
        this.msgPl = msgPl;
    }
}
