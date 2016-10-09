package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.HomePageCustomAdd;
import com.jhhy.cuiweitourism.net.models.FetchModel.HomePageCustomList;
import com.jhhy.cuiweitourism.net.models.FetchModel.HomePageCustonDetail;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HomePageCustomDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HomePageCustomListInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by birney1 on 16/10/9.
 */
public class HomePageActionBiz extends BasicActionBiz {
    public HomePageActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    public HomePageActionBiz() {
    }


    /**
     *  个性定制列表
     */

    public void houmePageCustomList(HomePageCustomList list, BizGenericCallback<ArrayList<HomePageCustomListInfo>> callback){
        list.code = "Publics_gxorder";
        final FetchGenericResponse<ArrayList<HomePageCustomListInfo>> fetchResponse = new FetchGenericResponse<ArrayList<HomePageCustomListInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<HomePageCustomListInfo> array = parseJsonToObjectArray(response,HomePageCustomListInfo.class);
                GenericResponseModel<ArrayList<HomePageCustomListInfo>> returnModel  =
                        new GenericResponseModel<ArrayList<HomePageCustomListInfo>>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(list, new FetchGenericCallback(fetchResponse));
    }


    /**
     *  个性定制详情
     */

    public  void homePageCustomDetail(HomePageCustonDetail detail, BizGenericCallback<HomePageCustomDetailInfo> callback){
        detail.code = "Publics_gxordershow";
        final  FetchGenericResponse<HomePageCustomDetailInfo> fetchResponse = new FetchGenericResponse<HomePageCustomDetailInfo>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HomePageCustomDetailInfo info = parseJsonToObject(response,HomePageCustomDetailInfo.class);
                GenericResponseModel<HomePageCustomDetailInfo> returnModel = new GenericResponseModel<HomePageCustomDetailInfo>(response.head,info);
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
     *  个性定制添加
     */

    public void homePageCustomAdd(HomePageCustomAdd add, BizGenericCallback<ArrayList<Object>> callback)
    {
        add.code = "Publics_gxorderadd";
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
        HttpUtils.executeXutils(add,new FetchGenericCallback(fetchResponse));
    }
}
