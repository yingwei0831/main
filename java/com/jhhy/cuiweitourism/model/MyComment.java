package com.jhhy.cuiweitourism.model;

/**
 * Created by birney1 on 16/9/29.
 * 我的评价
 */
public class MyComment {
//    {
//        "typeid": "1",
//        "articleid": "14",
//        "content": "滴答滴答滴答滴答滴答滴答滴答滴答的",
//        "addtime": null,
//        "memberid": "1",
//        "litpic": "http://cwly.taskbees.cn:8083/uploads/2016/0812/51a99f8bb88767b1373e0dc078970b95.jpg",
//        "title": "海岛游",
//        "suppliername": "易搜学",
//        "logo": "http://cwly.taskbees.cn:8083/uploads/member/57d60b0c2f809.jpg"
//    }

    private String typeId;
    private String articleId;
    private String content;
    private String addTime;
    private String mid;
    private String litPic; //列表展示图片
    private String title; //发布的内容
    private String supplierName;
    private String logo; //

    public MyComment() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getLitPic() {
        return litPic;
    }

    public void setLitPic(String litPic) {
        this.litPic = litPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "MyComment{" +
                "id='" + typeId + '\'' +
                ", articleId='" + articleId + '\'' +
                ", addTime='" + addTime + '\'' +
                ", mid='" + mid + '\'' +
                ", litPic='" + litPic + '\'' +
                ", title='" + title + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
