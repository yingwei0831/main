package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.PlanTicketInfoForHomeRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by birney on 2016-10-10.
 */
public class PlaneTicketActionBiz extends BasicActionBiz {

    public PlaneTicketActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    public PlaneTicketActionBiz(){
    }

    /**
     *  国内机票
     */

    public void planeTicketInfoOfHome(PlanTicketInfoForHomeRequest request, BizGenericCallback<ArrayList<Object>> callback) {
        request.code = "Fly_index";
        FetchGenericResponse fetchResponse = new FetchGenericResponse(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<Object> array = parseJsonToObjectArray(response, Object.class);
                GenericResponseModel returnModel = new GenericResponseModel(response.head, array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(request, new FetchGenericCallback<>(fetchResponse));
    }

    /**
     *  国外机票
     */

    public void planeTicketInfoOfAbroad(){

    }

    /**
     *  国际飞机票政策
     */

    public void planeTicketInternationalPolicyInfo(){

    }

    /**
     *  出发城市，到达城市
     */
    public void planeTicketDepartureAndReachCity(){

    }
}
