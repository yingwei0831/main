package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.PlanTicketCityFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlanTicketInfoForHomeRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlanTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;

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
     * 飞机出发城市、到达城市
     */
    public void getPlaneTicketCityInfo(PlanTicketCityFetch request, BizGenericCallback<ArrayList<PlanTicketCityInfo>> callback){
        request.code = "Plane_flyarea";
        FetchGenericResponse<ArrayList<PlanTicketCityInfo>> fetchGenericResponse = new FetchGenericResponse<ArrayList<PlanTicketCityInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<PlanTicketCityInfo> array = parseJsonToObjectArray(response,PlanTicketCityInfo.class);
                GenericResponseModel<ArrayList<PlanTicketCityInfo>> returnModel = new GenericResponseModel<ArrayList<PlanTicketCityInfo>>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(request,new FetchGenericCallback<>(fetchGenericResponse));
    }

    /**
     *  国内机票
     */

    public void planeTicketInfoInChina(PlanTicketInfoForHomeRequest request, BizGenericCallback<PlaneTicketInfoOfChina> callback) {
        request.code = "Fly_index";
        FetchGenericResponse<PlaneTicketInfoOfChina> fetchResponse = new FetchGenericResponse<PlaneTicketInfoOfChina>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                PlaneTicketInfoOfChina info = parseJsonToObject(response, PlaneTicketInfoOfChina.class);
                GenericResponseModel<PlaneTicketInfoOfChina> returnModel = new GenericResponseModel<PlaneTicketInfoOfChina>(response.head, info);
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
