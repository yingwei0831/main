package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.HotelDetailRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListFetchRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/10.
 */
public class HotelActionBiz extends  BasicActionBiz {

    public HotelActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    public HotelActionBiz() {
    }


    /**
     *  获取酒店列表
     */


    public void hotelGetInfoList(HotelListFetchRequest request, BizGenericCallback<ArrayList<HotelListInfo>> callback)
    {
        request.code = "Hotel_index";

        final FetchGenericResponse fetchResponse = new FetchGenericResponse(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<HotelListInfo> array = parseJsonToObjectArray(response,HotelListInfo.class);
                GenericResponseModel returnModel = new GenericResponseModel(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(request,new FetchGenericCallback(fetchResponse));
    }

    /**
     *  获取酒店详情
     */

    public void hotelGetDetailInfo(HotelDetailRequest request, BizGenericCallback<HotelDetailInfo> callback){
       request.code = "Hotel_show";

        final FetchGenericResponse fetchResponse = new FetchGenericResponse(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelDetailInfo info = parseJsonToObject(response,HotelDetailInfo.class);
                GenericResponseModel returnModel = new GenericResponseModel(response.head,info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(request,new FetchGenericCallback<>(fetchResponse));
    }

    /**
     *  提交酒店订单
     */

    public void HotelSubmitOrder(HotelOrderFetch fetch, BizGenericCallback<HotelOrderInfo> callback){
        fetch.code = "Order_hotelorder";
        final FetchGenericResponse fetchResponse = new FetchGenericResponse(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelOrderInfo info = parseJsonToObject(response,HotelOrderInfo.class);
                GenericResponseModel returnModel = new GenericResponseModel(response.head,info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(fetch,new FetchGenericCallback<>(fetchResponse));
    }
}
