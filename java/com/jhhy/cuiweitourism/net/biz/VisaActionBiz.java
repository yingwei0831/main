package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.VisaCountry;
import com.jhhy.cuiweitourism.net.models.FetchModel.VisaDetail;
import com.jhhy.cuiweitourism.net.models.FetchModel.VisaHot;
import com.jhhy.cuiweitourism.net.models.FetchModel.VisaHotCountry;
import com.jhhy.cuiweitourism.net.models.ResponseModel.BasicResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaClassification;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaCountryInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaHotCountryInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaHotInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizCallback;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchCallBack;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.FetchResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;


/**
 * Created by zhangguang on 16/10/9.
 */
public class VisaActionBiz extends  BasicActionBiz {

    public VisaActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    public VisaActionBiz(){
        super();
    }
    /**
     *  签证国家
     */
    public  void getVisaCountry(VisaCountry visaCountry, BizGenericCallback<ArrayList<VisaCountryInfo>> callBack){
        visaCountry.setBizCode("Visa_getvisacountry");
        visaCountry.setPltType("A");
        visaCountry.setDeltaCode("");
        final FetchGenericResponse<ArrayList<VisaCountryInfo>> fetchResponse = new FetchGenericResponse<ArrayList<VisaCountryInfo>>(callBack) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<ArrayList<String>> array = parseJsonTotwoLevelArray(response);
                ArrayList<VisaCountryInfo> countries = new ArrayList<>();
                for (ArrayList<String> country : array){
                    VisaCountryInfo countryItem = new VisaCountryInfo();
                    countryItem.setCountryCode(country.get(0));
                    countryItem.setCountryName(country.get(1));
                    countryItem.setCountryNameEn(country.get(2));
                    countryItem.setCountryFlagUrl(country.get(3));
                    countryItem.setContinentCode(country.get(4));
                    countryItem.setContinentName(country.get(5));
                    countryItem.setHot(country.get(6).equals("Y"));
                    countries.add(countryItem);
                }
                GenericResponseModel<ArrayList<VisaCountryInfo>> returnModel  = new GenericResponseModel<>(response.head, countries);
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(visaCountry, new FetchGenericCallback(fetchResponse));
    }

    /**
     * 热门签证 （签证列表）
     * {"head":{"code":"Visa_getvisalist"},
     * "field":{"CountryCode":"","DistributionArea":"","PltType":"A"}}
     */
    public void getVisaHotList(VisaHot visaHot, BizGenericCallback<ArrayList<VisaHotInfo>> callBack){
        visaHot.setBizCode("Visa_getvisalist");
        visaHot.setPltType("A");
        final FetchGenericResponse<ArrayList<VisaHotInfo>> fetchResponse = new FetchGenericResponse<ArrayList<VisaHotInfo>>(callBack) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<ArrayList<String>> array = parseJsonTotwoLevelArray(response);
                ArrayList<VisaHotInfo> countries = new ArrayList<>();
                for (ArrayList<String> country : array){
                    VisaHotInfo countryItem = new VisaHotInfo();
                    countryItem.visaId = country.get(0);
                    countryItem.visaName = country.get(1);
                    countryItem.visaFlagUrl = country.get(2);
                    countryItem.visaAddressCode = country.get(3);
                    countryItem.visaAddress = country.get(4);
                    countryItem.visaTime = country.get(5);
                    countryItem. visaHurry = country.get(6).equals("Y");
                    countryItem.visaPeriodOfValidaty = country.get(7);
                    countryItem.visaStayPeriod = country.get(8);
                    countryItem.innerTimes = country.get(9);
                    countryItem. needInterview = country.get(10).equals("1");
                    countryItem.visaPrice = country.get(11);
                    countryItem.visaPriceLower = country.get(12);
                    countryItem.visaPriceAdditional = country.get(13);
                    countryItem.visaTypeId = country.get(14);
                    countryItem.visaType = country.get(15);
                    countryItem.productResource = country.get(16);
                    countryItem.visaCCode = country.get(17);
                    countryItem.visaCName = country.get(18);
                    countryItem.visaNationCode = country.get(19);
                    countryItem.visaNationName = country.get(20);
                    countries.add(countryItem);
                }
                GenericResponseModel<ArrayList<VisaHotInfo>> returnModel  = new GenericResponseModel<>(response.head, countries);
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(visaHot, new FetchGenericCallback(fetchResponse));
    }

//    {"head":{"code":"Visa_getvisadetial"},"field":{"ProductID":"946","PltType":"P"}}
    /**
     * 签证详情
     */
    public void getVisaDetail(VisaDetail visaDetail, BizGenericCallback<VisaDetailInfo> callBack) {
        visaDetail.setBizCode("Visa_getvisadetial");
        visaDetail.setPltType("A");
        final FetchGenericResponse<VisaDetailInfo> fetchResponse = new FetchGenericResponse<VisaDetailInfo>(callBack){
            @Override
            public void onCompletion(FetchResponseModel response) {
//                ArrayList<String> array = parseJsonTotwoLevelArray(response);
                ArrayList<String> array = parseJsonToObject(response, ArrayList.class);
                VisaDetailInfo visaDetailInfo = new VisaDetailInfo();
                visaDetailInfo.visaId = array.get(0);
                visaDetailInfo.visaName = array.get(1);
                visaDetailInfo.visaType = array.get(2);
                visaDetailInfo.continentCode = array.get(3);
                visaDetailInfo.continentName = array.get(4);
                visaDetailInfo.countryCode = array.get(5);
                visaDetailInfo.countryName = array.get(6);
                visaDetailInfo.countryFlagUrl = array.get(7);
                visaDetailInfo.visaAddressCode = array.get(8);
                visaDetailInfo.visaAddress = array.get(9);
                visaDetailInfo. visaHurry = array.get(10).equals("Y");
                visaDetailInfo.visaPrice = array.get(11);
                visaDetailInfo.visaTime = array.get(12);
                visaDetailInfo. needInterview = array.get(13).equals("1");
                visaDetailInfo.visaPeriodOfValidaty = array.get(14);
                visaDetailInfo.visaStayPeriod = array.get(15);
                visaDetailInfo.innerTimes = array.get(16);
                visaDetailInfo.notice = array.get(17);
                visaDetailInfo.acceptArea = array.get(18);
                visaDetailInfo.remark = array.get(19);
//                visaDetailInfo.classification = array.get(20);
                visaDetailInfo.visaPriceLower = array.get(21);
                visaDetailInfo.visaPriceAdditional = array.get(22);

//                ArrayList<VisaClassification> visaClassification = new ArrayList<>();
//                LinkedHashMap<String, VisaClassification> visaClassification = parseJsonToObject(, LinkedHashMap.class);


//                visaDetailInfo.classification = visaClassification;
                GenericResponseModel<VisaDetailInfo> returnModel  = new GenericResponseModel<>(response.head, visaDetailInfo);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(visaDetail, new FetchGenericCallback(fetchResponse));
    }
}

