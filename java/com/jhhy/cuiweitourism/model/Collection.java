package com.jhhy.cuiweitourism.model;

/**
 * Created by birney1 on 16/9/25.
 */
public class Collection {
//{
//    "id": "7",  //收藏id
//        "memberid": "6", //会员id
//        "typeid": "1",   //类型id
//        "productaid": "13", //产品id
//        "addtime": "1473318267",
//        "title": "这是测试的线路这是测试的线路这是测试的线路",
//        "litpic": "http://cwly.taskbees.cn:8083/uploads/2016/0607/f1a7e360b659d4dbf6f40e133ffe5d20.jpg",
//        "price": "3500"
//}

    private String colId;
    private String colMemberId;
    private String colTypeId;
    private String colProductId;
    private String colAddTime;
    private String colTitle;
    private String colLitpic;
    private String colPrice;

    private boolean selection; //控制radioButton的选择

    public Collection() {
    }

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public String getColId() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId = colId;
    }

    public String getColMemberId() {
        return colMemberId;
    }

    public void setColMemberId(String colMemberId) {
        this.colMemberId = colMemberId;
    }

    public String getColTypeId() {
        return colTypeId;
    }

    public void setColTypeId(String colTypeId) {
        this.colTypeId = colTypeId;
    }

    public String getColProductId() {
        return colProductId;
    }

    public void setColProductId(String colProductId) {
        this.colProductId = colProductId;
    }

    public String getColAddTime() {
        return colAddTime;
    }

    public void setColAddTime(String colAddTime) {
        this.colAddTime = colAddTime;
    }

    public String getColTitle() {
        return colTitle;
    }

    public void setColTitle(String colTitle) {
        this.colTitle = colTitle;
    }

    public String getColLitpic() {
        return colLitpic;
    }

    public void setColLitpic(String colLitpic) {
        this.colLitpic = colLitpic;
    }

    public String getColPrice() {
        return colPrice;
    }

    public void setColPrice(String colPrice) {
        this.colPrice = colPrice;
    }

    @Override
    public String toString() {
        return "Collection { " +
                "colId='" + colId + '\'' +
                ", colMemberId='" + colMemberId + '\'' +
                ", colTypeId='" + colTypeId + '\'' +
                ", colProductId='" + colProductId + '\'' +
                ", colAddTime='" + colAddTime + '\'' +
                ", colTitle='" + colTitle + '\'' +
                ", colLitpic='" + colLitpic + '\'' +
                ", colPrice='" + colPrice + '\'' +
                '}';
    }
}
