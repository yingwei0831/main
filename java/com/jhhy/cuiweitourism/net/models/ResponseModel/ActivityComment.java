package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jiahe008 on 2016/10/10.
 */
public class ActivityComment implements Serializable{
    public String content;
    public ArrayList<String> pllist;
    public String nickname;
    public String face;
    public String addtime;


    public ActivityComment(String content, ArrayList<String> pllist, String nickname, String face, String addtime) {
        this.content = content;
        this.pllist = pllist;
        this.nickname = nickname;
        this.face = face;
        this.addtime = addtime;
    }

    public ActivityComment() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getPllist() {
        return pllist;
    }

    public void setPllist(ArrayList<String> pllist) {
        this.pllist = pllist;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "ActivityComment{" +
                "content='" + content + '\'' +
                ", pllist=" + pllist +
                ", nickname='" + nickname + '\'' +
                ", face='" + face + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }

}
