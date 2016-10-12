package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndAdvertise;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndMoreCommentFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndSearch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForceEndSearchInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndMoreCommentInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/10.
 */
public class ForeEndActionBiz extends  BasicActionBiz {

    public ForeEndActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    public ForeEndActionBiz() {
    }


    /**
     *  搜索  前台接口
     */


    public void forceEndSearch(ForeEndSearch search, BizGenericCallback<ArrayList<ForceEndSearchInfo>> callback){
        search.code = "Publics_search";

        final FetchGenericResponse fetchGenericResponse = new FetchGenericResponse(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<ForceEndSearchInfo> array = parseJsonToObjectArray(response,ForceEndSearchInfo.class);
                GenericResponseModel returnModel = new GenericResponseModel(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(search,new FetchGenericCallback(fetchGenericResponse));

    }



    /**
     *  广告位
     */

    public void foreEndGetAdvertisingPosition(ForeEndAdvertise ad, BizGenericCallback<ArrayList<ForeEndAdvertisingPositionInfo>> callback){
        ad.code = "Publics_ad";
        final FetchGenericResponse fetchResponse = new FetchGenericResponse(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<ForeEndAdvertisingPositionInfo> array = parseJsonToObjectArray(response,ForeEndAdvertisingPositionInfo.class);
                GenericResponseModel returnModel = new GenericResponseModel(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(ad, new FetchGenericCallback(fetchResponse));
    }


    /**
     *  更多评论信息
     */


    public void foreEndGetMoreCommentInfo(ForeEndMoreCommentFetch fetch , BizGenericCallback<ArrayList<ForeEndMoreCommentInfo>> callback){
        fetch.code = "Publics_comment";
        FetchGenericResponse<ArrayList<ForeEndMoreCommentInfo>> fetchResponse = new FetchGenericResponse<ArrayList<ForeEndMoreCommentInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<ForeEndMoreCommentInfo> array = parseJsonToObjectArray(response,ForeEndMoreCommentInfo.class);
                GenericResponseModel<ArrayList<ForeEndMoreCommentInfo>> returnModel = new GenericResponseModel<ArrayList<ForeEndMoreCommentInfo>>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(fetch,new FetchGenericCallback<ArrayList<ForeEndMoreCommentInfo>>(fetchResponse));
    }

}
