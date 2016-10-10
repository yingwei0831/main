package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class MemberCenterRemarkInfo {


//    "typeid": "1",
//    "articleid": "14",
//    "content": "66666666666666",
//    "addtime": "1471828891",
//    "memberid": "1",
//    "litpic": "http://www.cwly1118.com/uploads/2016/0812/51a99f8bb88767b1373e0dc078970b95.jpg",
//    "title": "海岛游",
//    "suppliername": "易搜学",
//    "logo": "http://www.cwly1118.com/uploads/member/57d60b0c2f809.jpg"

    public String typeid;
    public String articleid;
    public String content;
    public String addtime;
    public String memberid;
    public String litpic;
    public String title;
    public String suppliername;
    public String logo;

    public MemberCenterRemarkInfo(String typeid, String articleid, String content, String addtime, String memberid, String litpic, String title, String suppliername, String logo) {
        this.typeid = typeid;
        this.articleid = articleid;
        this.content = content;
        this.addtime = addtime;
        this.memberid = memberid;
        this.litpic = litpic;
        this.title = title;
        this.suppliername = suppliername;
        this.logo = logo;
    }

    public MemberCenterRemarkInfo() {
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "MemberCenterRemarkInfo{" +
                "typeid='" + typeid + '\'' +
                ", articleid='" + articleid + '\'' +
                ", content='" + content + '\'' +
                ", addtime='" + addtime + '\'' +
                ", memberid='" + memberid + '\'' +
                ", litpic='" + litpic + '\'' +
                ", title='" + title + '\'' +
                ", suppliername='" + suppliername + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
