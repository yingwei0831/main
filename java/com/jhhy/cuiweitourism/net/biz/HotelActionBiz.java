package com.jhhy.cuiweitourism.net.biz;

import com.jhhy.cuiweitourism.net.models.FetchModel.HotelCityRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelDetailRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListFetchRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelPriceCheckRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelScreenBrandRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelPositionLocationResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelPriceCheckResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.models.FetchModel.NullArrayFetchModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelScreenBrandResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelScreenFacilities;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HoutelPropertiesInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.net.utils.HanziToPinyin;

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
                List<HotelProvinceResponse.ProvinceBean> listHotelProvince = returnModel.body.getItem();
                HanziToPinyin hzToPyConvertor = HanziToPinyin.getInstance();
                for (HotelProvinceResponse.ProvinceBean itemInfo : listHotelProvince){
                    HanziToPinyin.PinYinCollection collection = hzToPyConvertor.getPinYin(itemInfo.getName());
                    itemInfo.setQuanPin(collection.fullPY);
                    itemInfo.setJianPin(collection.shortPY);
                    itemInfo.setHeadChar(String.valueOf(collection.headChar));
                }
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
                List<HotelProvinceResponse.ProvinceBean> listCityBean = returnModel.body.getItem();
                HanziToPinyin hzToPyConvertor = HanziToPinyin.getInstance();
                for (HotelProvinceResponse.ProvinceBean itemInfo : listCityBean){
                    HanziToPinyin.PinYinCollection collection = hzToPyConvertor.getPinYin(itemInfo.getName().replace("(", "").replace(")", ""));
                    itemInfo.setQuanPin(collection.fullPY);
                    itemInfo.setJianPin(collection.shortPY);
                    itemInfo.setHeadChar(String.valueOf(collection.headChar));
                }
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(request, new FetchGenericCallback<>(response));
    }

    /**
     * 酒店列表
     */
    public void getHotelList(HotelListRequest request, BizGenericCallback<HotelListResponse> callback){
        request.code = "Hotel_lists";
        FetchGenericResponse<HotelListResponse> fetchResponse = new FetchGenericResponse<HotelListResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                GenericResponseModel<HotelListResponse> returnModel = null;
                if ("0001".equals(response.head.res_code)){
                    returnModel = new GenericResponseModel<>(response.head, null);
                }else if ("0000".equals(response.head.res_code)){
                    HotelListResponse model = parseJsonToObject(response, HotelListResponse.class);
                    returnModel = new GenericResponseModel<>(response.head, model);
                }
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
     * 当前城市酒店品牌
     */
    public void hotelScreenBrand(HotelScreenBrandRequest request, BizGenericCallback<HotelScreenBrandResponse> callback){
        request.code = "Hotel_dqcityjd";
        FetchGenericResponse<HotelScreenBrandResponse> fetchResponse = new FetchGenericResponse<HotelScreenBrandResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelScreenBrandResponse model = parseJsonToObject(response, HotelScreenBrandResponse.class);
                GenericResponseModel<HotelScreenBrandResponse> returnModel = new GenericResponseModel<>(response.head, model);
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
     * 酒店设施
     */
    public void hotelScreenFacilities(BizGenericCallback<HotelScreenFacilities> callback){
        NullArrayFetchModel fetchModel = new NullArrayFetchModel();
        fetchModel.code = "Hotel_facility";
        FetchGenericResponse<HotelScreenFacilities> fetchResponse = new FetchGenericResponse<HotelScreenFacilities>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelScreenFacilities model = parseJsonToObject(response, HotelScreenFacilities.class);
                GenericResponseModel<HotelScreenFacilities> returnModel = new GenericResponseModel<>(response.head, model);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(fetchModel, new FetchGenericCallback<>(fetchResponse));
    }

    /**
     * 行政区
     */
    public void getHotelLocationDistrict(HotelScreenBrandRequest request, BizGenericCallback<HotelPositionLocationResponse> callback){
        request.code = "Hotel_xzq";
        FetchGenericResponse<HotelPositionLocationResponse> fetchResponse = new FetchGenericResponse<HotelPositionLocationResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelPositionLocationResponse model = parseJsonToObject(response, HotelPositionLocationResponse.class);
                GenericResponseModel<HotelPositionLocationResponse> returnModel = new GenericResponseModel<>(response.head, model);
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
     * 商区
     */
    public void getHotelLocationBusinessDistrict(HotelScreenBrandRequest request, BizGenericCallback<HotelPositionLocationResponse> callback){
        request.code = "Hotel_syq";
        FetchGenericResponse<HotelPositionLocationResponse> fetchResponse = new FetchGenericResponse<HotelPositionLocationResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelPositionLocationResponse model = parseJsonToObject(response, HotelPositionLocationResponse.class);
                GenericResponseModel<HotelPositionLocationResponse> returnModel = new GenericResponseModel<>(response.head, model);
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
     * 景点
     */
    public void getHotelLocationViewSpot(HotelScreenBrandRequest request, BizGenericCallback<HotelPositionLocationResponse> callback){
        request.code = "Hotel_fjq";
        FetchGenericResponse<HotelPositionLocationResponse> fetchResponse = new FetchGenericResponse<HotelPositionLocationResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelPositionLocationResponse model = parseJsonToObject(response, HotelPositionLocationResponse.class);
                GenericResponseModel<HotelPositionLocationResponse> returnModel = new GenericResponseModel<>(response.head, model);
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
     *数据校验
     */
    public void getHotelPriceCheck(HotelPriceCheckRequest request, BizGenericCallback<HotelPriceCheckResponse> callback){
        request.code = "Hotel_datas";
        FetchGenericResponse<HotelPriceCheckResponse> fetchResponse = new FetchGenericResponse<HotelPriceCheckResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelPriceCheckResponse model = parseJsonToObject(response, HotelPriceCheckResponse.class);
                GenericResponseModel<HotelPriceCheckResponse> returnModel = new GenericResponseModel<>(response.head, model);
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
     *  获取酒店列表 废弃
     */
    public void hotelGetInfoList(HotelListFetchRequest request, BizGenericCallback<ArrayList<HotelListInfo>> callback)
    {
        request.code = "Hotel_index";

        final FetchGenericResponse<ArrayList<HotelListInfo>> fetchResponse = new FetchGenericResponse<ArrayList<HotelListInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<HotelListInfo> array = parseJsonToObjectArray(response,HotelListInfo.class);
                GenericResponseModel returnModel = new GenericResponseModel<ArrayList<HotelListInfo>>(response.head,array);
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
     *  获取酒店详情
     */
    public void getHotelDetailInfo(HotelDetailRequest request, BizGenericCallback<HotelDetailResponse> callback){
        request.code = "Hotel_roominfo";

        final FetchGenericResponse<HotelDetailResponse> fetchResponse = new FetchGenericResponse<HotelDetailResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                HotelDetailResponse info = parseJsonToObject(response, HotelDetailResponse.class);
                GenericResponseModel<HotelDetailResponse> returnModel = new GenericResponseModel<>(response.head,info);
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
