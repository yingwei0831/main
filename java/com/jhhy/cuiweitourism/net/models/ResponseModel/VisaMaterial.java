package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2016/11/15.
 * 所需材料
 */
public class VisaMaterial {

//                                [
//                                    "资料名称",
//                                    "是否必要材料",
//                                    "模版",
//                                    "备注"
//                                ]
        public String materialName;
        public String materialMust;
        public String materialModel;
        public String materialRemark;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialMust() {
        return materialMust;
    }

    public void setMaterialMust(String materialMust) {
        this.materialMust = materialMust;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
    }

    public String getMaterialRemark() {
        return materialRemark;
    }

    public void setMaterialRemark(String materialRemark) {
        this.materialRemark = materialRemark;
    }

    public VisaMaterial() {
    }

    public VisaMaterial(String materialName, String materialMust, String materialModel, String materialRemark) {
        this.materialName = materialName;
        this.materialMust = materialMust;
        this.materialModel = materialModel;
        this.materialRemark = materialRemark;
    }

    @Override
    public String toString() {
        return "VisaMaterial{" +
                "materialName='" + materialName + '\'' +
                ", materialMust='" + materialMust + '\'' +
                ", materialModel='" + materialModel + '\'' +
                ", materialRemark='" + materialRemark + '\'' +
                '}';
    }
}
