package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.VisaHotCountry;
import com.jhhy.cuiweitourism.net.models.ResponseModel.BasicResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaHotCountryInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizCallback;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchCallBack;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.FetchResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;


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
     *  热门签证国家
     */

    public  void VisaGetHotCountry(BizGenericCallback<ArrayList<VisaHotCountryInfo>> callBack){
        VisaHotCountry hotCountry = new VisaHotCountry();
        hotCountry.setBizCode("Visa_index");
        final FetchGenericResponse<ArrayList<VisaHotCountryInfo>> fetchResponse = new FetchGenericResponse<ArrayList<VisaHotCountryInfo>>(callBack) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<VisaHotCountryInfo> array = parseJsonToObjectArray(response,VisaHotCountryInfo.class);
                GenericResponseModel<ArrayList<VisaHotCountryInfo>> returnModel  =
                        new GenericResponseModel<ArrayList<VisaHotCountryInfo>>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(hotCountry, new FetchGenericCallback(fetchResponse));
    }
}

