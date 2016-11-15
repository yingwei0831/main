package com.jhhy.cuiweitourism.net.models.FetchModel;


/**
 * Created by zhangguang on 16/10/9.
 */
public class VisaDetail extends  BasicFetchModel {

    //  {"head":{"code":"Visa_getvisadetial"},"field":{"ProductID":"946","PltType":"P"}}
    public String ProductID; //ProductID:签证产品id ;
    public String PltType; //PltType:平台代码P; A: 安卓;I: 苹果;不填默认为P;

    public VisaDetail() {
    }

    public VisaDetail(String productID, String pltType) {
        ProductID = productID;
        PltType = pltType;
    }

    public String getPltType() {
        return PltType;
    }

    public void setPltType(String pltType) {
        PltType = pltType;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }
}
