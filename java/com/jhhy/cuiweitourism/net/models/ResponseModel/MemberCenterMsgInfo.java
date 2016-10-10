package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/10.
 */
public class MemberCenterMsgInfo {
//
//    {
//        "title": "撒哈拉沙漠",
//            "id": "1",
//            "memberid": "1",
//            "litpic": "/uploads/2015/0909/6576cb3bfd961a0e13e6cc203b4ce012.jpg",
//            "pl": [
//        {
//            "content": "这个活动很好",
//                "addtime": null
//        }
//        ]
//    }

    public class comment{
        public String content;
        public String addtime;

        public comment(String content, String addtime) {
            this.content = content;
            this.addtime = addtime;
        }

        public comment() {
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

        @Override
        public String toString() {
            return "comment{" +
                    "content='" + content + '\'' +
                    ", addtime='" + addtime + '\'' +
                    '}';
        }
    }

    public String title;
    public String id;
    public String memberid;
    public String litpic;
    public ArrayList<comment> pl;


    public MemberCenterMsgInfo(String title, String id, String memberid, String litpic, ArrayList<comment> pl) {
        this.title = title;
        this.id = id;
        this.memberid = memberid;
        this.litpic = litpic;
        this.pl = pl;
    }

    public MemberCenterMsgInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public ArrayList<comment> getPl() {
        return pl;
    }

    public void setPl(ArrayList<comment> pl) {
        this.pl = pl;
    }

    @Override
    public String toString() {
        return "MemberCenterMsgInfo{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", memberid='" + memberid + '\'' +
                ", litpic='" + litpic + '\'' +
                ", pl=" + pl +
                '}';
    }
}
