package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by jiahe008 on 2016/11/15.
 * 签证人员类别
 */
public class VisaClassification {

//    "客户类别": [
//                    [
//                        "资料名称",
//                        "是否必要材料",
//                        "模版",
//                        "备注"
//                    ]
//    ]

    public String classificatinName; //客户类别
    public ArrayList<VisaMaterial> materialList; //材料列表

    public String getClassificatinName() {
        return classificatinName;
    }

    public void setClassificatinName(String classificatinName) {
        this.classificatinName = classificatinName;
    }

    public ArrayList<VisaMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(ArrayList<VisaMaterial> materialList) {
        this.materialList = materialList;
    }

    public VisaClassification() {
    }

    public VisaClassification(String classificatinName, ArrayList<VisaMaterial> materialList) {
        this.classificatinName = classificatinName;
        this.materialList = materialList;
    }
}
