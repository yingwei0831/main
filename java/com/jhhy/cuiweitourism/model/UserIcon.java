package com.jhhy.cuiweitourism.model;

/**
 * Created by birney1 on 16/9/24.
 * 用户积分
 */
public class UserIcon {
//    {
//        "id": "1",
//            "memberid": "1",
//            "content": "注册赠送旅游币",
//            "addtime": "1469176131",
//            "type": "2",
//            "jifen": "2"
//    }
    private String id;
    private String memberId;
    private String content;
    private String addtime;
    private String type;
    private String score;

    public UserIcon() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public UserIcon(String id, String memberId, String content, String addtime, String type, String score) {
        this.id = id;
        this.memberId = memberId;
        this.content = content;
        this.addtime = addtime;
        this.type = type;
        this.score = score;
    }
}
