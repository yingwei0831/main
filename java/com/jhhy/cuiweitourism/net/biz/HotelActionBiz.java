package com.jhhy.cuiweitourism.net.biz;

import com.jhhy.cuiweitourism.net.models.FetchModel.HotelCityRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelDetailRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListFetchRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.models.FetchModel.NullArrayFetchModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HoutelPropertiesInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by birneysky on 16/10/10.
 */
public class HotelActionBiz extends  BasicActionBiz {

    public HotelActionBiz() {
    }

    /**
     * 获取省份信息
     */
    public void getHotelProvinceList(BizGenericCallback<HotelProvinceResponse> callback){
        NullArrayFetchModel request = new NullArrayFetchModel();
        request.code = "Hotel_index";
        FetchGenericResponse<HotelProvinceResponse> response = new FetchGenericResponse<HotelProvinceResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelProvinceResponse model = parseJsonToObject(response, HotelProvinceResponse.class);
                GenericResponseModel<HotelProvinceResponse> returnModel = new GenericResponseModel<>(response.head, model);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.onError(error);
            }
        };
        HttpUtils.executeXutils(request, new FetchGenericCallback<>(response));
    }

    /**
     * 获取城市信息
     */
    public void getHotelCityList(HotelCityRequest request, BizGenericCallback<HotelProvinceResponse> callback){
        request.code = "Hotel_city";
        FetchGenericResponse<HotelProvinceResponse> response = new FetchGenericResponse<HotelProvinceResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelProvinceResponse model = parseJsonToObject(response, HotelProvinceResponse.class);
                GenericResponseModel<HotelProvinceResponse> returnModel = new GenericResponseModel<>(response.head, model);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.onError(error);
            }
        };
        HttpUtils.executeXutils(request, new FetchGenericCallback<>(response));
    }

    /**
     *  获取酒店属性列表
     */
    public void hotelGetPropertiesList(BizGenericCallback<ArrayList<HoutelPropertiesInfo>> callback){
        NullArrayFetchModel fetchModel = new NullArrayFetchModel();
        fetchModel.code = "Hotel_hotelattr";

        FetchGenericResponse<ArrayList<HoutelPropertiesInfo>> fetchResponse = new FetchGenericResponse<ArrayList<HoutelPropertiesInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<HoutelPropertiesInfo> array = parseJsonToObjectArray(response,HoutelPropertiesInfo.class);
                GenericResponseModel returnModel = new GenericResponseModel<ArrayList<HoutelPropertiesInfo>>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        } ;

        HttpUtils.executeXutils(fetchModel,new FetchGenericCallback<>(fetchResponse));
    }


    /**
     *  获取酒店列表
     */
    public void hotelGetInfoList(HotelListFetchRequest request, BizGenericCallback<ArrayList<HotelListInfo>> callback)
    {
        request.code = "Hotel_index";

        final FetchGenericResponse fetchResponse = new FetchGenericResponse<ArrayList<HotelListInfo>>(callback) {
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

        final FetchGenericResponse fetchResponse = new FetchGenericResponse<HotelDetailInfo>(callback) {
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
        final FetchGenericResponse fetchResponse = new FetchGenericResponse<HotelOrderInfo>(callback) {
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
