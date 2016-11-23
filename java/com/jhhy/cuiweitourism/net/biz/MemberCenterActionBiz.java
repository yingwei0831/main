package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.MemberCenterMsg;
import com.jhhy.cuiweitourism.net.models.FetchModel.MemberIcon;
import com.jhhy.cuiweitourism.net.models.FetchModel.MemberReleaseDeleteFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.BasicResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.MemberCenterMsgInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.MemberCenterRemarkInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.MemberIconNum;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchCallBack;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/10.
 */
public class MemberCenterActionBiz extends BasicActionBiz {

//    public MemberCenterActionBiz(Context context, Handler handler) {
//        super(context, handler);
//    }

    public MemberCenterActionBiz() {
    }

    /**
     * 我的消息
     */
    public void memberCenterGetMessageInfo(MemberCenterMsg msg, BizGenericCallback<ArrayList<MemberCenterMsgInfo>> callback){
        msg.code = "User_mynews";

        final FetchGenericResponse<ArrayList<MemberCenterMsgInfo>> fetchResponse = new FetchGenericResponse<ArrayList<MemberCenterMsgInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<MemberCenterMsgInfo> array = parseJsonToObjectArray(response,MemberCenterMsgInfo.class);
                GenericResponseModel<ArrayList<MemberCenterMsgInfo>> returnModel = new GenericResponseModel<>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(msg, new FetchGenericCallback(fetchResponse));
    }


    /**
     * 我的点评
     */
    public void memberCenterGetRemarkInfo(MemberCenterMsg msg, BizGenericCallback<ArrayList<MemberCenterRemarkInfo>> callback){
        msg.code = "User_orderpjlist";
        final FetchGenericResponse<ArrayList<MemberCenterRemarkInfo>> fetchResponse = new FetchGenericResponse<ArrayList<MemberCenterRemarkInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<MemberCenterRemarkInfo> array = parseJsonToObjectArray(response,MemberCenterRemarkInfo.class);
                GenericResponseModel<ArrayList<MemberCenterRemarkInfo>> returnModel = new GenericResponseModel<>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(msg, new FetchGenericCallback(fetchResponse));
    }
    /**
     * 我的旅游币
     */
    public void memberTravelIcon(MemberIcon msg, BizGenericCallback<MemberIconNum> callback){
        msg.code = "User_lybnum";
        FetchGenericResponse<MemberIconNum> fetchResponse = new FetchGenericResponse<MemberIconNum>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                MemberIconNum info = parseJsonToObject(response, MemberIconNum.class);
                GenericResponseModel<MemberIconNum> returnModel = new GenericResponseModel<MemberIconNum>(response.head, info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(msg, new FetchGenericCallback(fetchResponse));
    }

    /**
     * 我的发布删除 {"head":{"code":"User_delactivity"},"field":{"id":"1"}}
     */
    public void memberReleaseDelete(MemberReleaseDeleteFetch msg, BizGenericCallback<Object> callback){
        msg.code = "User_delactivity";
        FetchGenericResponse<Object> fetchResponse = new FetchGenericResponse<Object>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                Object info = parseJsonToObject(response, Object.class);
                GenericResponseModel<Object> returnModel = new GenericResponseModel<Object>(response.head, info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(msg, new FetchGenericCallback(fetchResponse));
    }
}
