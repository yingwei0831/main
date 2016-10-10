package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.ActivityHot;
import com.jhhy.cuiweitourism.net.models.FetchModel.ActivityOrder;
import com.jhhy.cuiweitourism.net.models.FetchModel.HomePageCustomList;
import com.jhhy.cuiweitourism.net.models.FetchModel.HomePageCustonDetail;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;

/**
 * Created by birney1 on 16/10/9.
 */
public class ActivityActionBiz extends BasicActionBiz {


    public ActivityActionBiz() {
    }

    public ActivityActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    /**
     * 热门活动
     */

    public void activitiesHotGetInfo(ActivityHot hot, BizGenericCallback<ArrayList<ActivityHotInfo>> callback){
        hot.code = "Activity_hotpublish";
        final FetchGenericResponse<ArrayList<ActivityHotInfo>> fetchResponse = new FetchGenericResponse<ArrayList<ActivityHotInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<ActivityHotInfo> array = parseJsonToObjectArray(response,ActivityHotInfo.class);
                GenericResponseModel<ArrayList<ActivityHotInfo>> returnModel = new GenericResponseModel<>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(hot, new FetchGenericCallback(fetchResponse));
    }

    /**
     * 热门活动详情
     */

    public void activitiesHotGetDetailInfo(HomePageCustonDetail detail,BizGenericCallback<ActivityHotDetailInfo> callback){
        detail.code = "Activity_hotpublishshow";
        final FetchGenericResponse<ActivityHotDetailInfo> fetchResponse = new FetchGenericResponse<ActivityHotDetailInfo>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ActivityHotDetailInfo info = parseJsonToObject(response,ActivityHotDetailInfo.class);
                GenericResponseModel<ActivityHotDetailInfo> returnModel = new GenericResponseModel<ActivityHotDetailInfo>(response.head,info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(detail, new FetchGenericCallback(fetchResponse));
    }


    /**
     * 活动订单提交
     */

    public void activitiesOrderSubmit(ActivityOrder order, BizGenericCallback<ActivityOrderInfo> callback){
        order.code = "Order_activityorer";
        final FetchGenericResponse<ActivityOrderInfo> fetchResponse = new FetchGenericResponse<ActivityOrderInfo>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ActivityOrderInfo info = parseJsonToObject(response,ActivityOrderInfo.class);
                GenericResponseModel<ActivityOrderInfo> returnModel = new GenericResponseModel<>(response.head,info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(order, new FetchGenericCallback(fetchResponse));
    }
}
