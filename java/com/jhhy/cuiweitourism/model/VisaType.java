package com.jhhy.cuiweitourism.model;

/**
 * Created by jiahe008 on 2016/9/29.
 * 签证类型
 */
public class VisaType {
//    {"id":"6","kindname":"旅游签证"}
    private String id;
    private String typeName;

    public VisaType() {
    }

    public VisaType(String id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
