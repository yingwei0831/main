package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

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
     * 发起活动
     */

    public void lanchActivity(ActivitiesLaunch lanch, BizGenericCallback<ArrayList<Object>> callback)
    {
        lanch.code = "Activity_publish";
        final FetchGenericResponse<ArrayList<Object>> fetchResponse =  new FetchGenericResponse<ArrayList<Object>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<Object> objects = parseJsonToObjectArray(response,Object.class);
                GenericResponseModel<ArrayList<Object>> returnModel = new GenericResponseModel<ArrayList<Object>>(response.head,objects);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(lanch,new FetchGenericCallback(fetchResponse));
    }
}
