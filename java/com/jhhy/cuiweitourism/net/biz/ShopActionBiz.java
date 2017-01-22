package com.jhhy.cuiweitourism.net.biz;

import com.jhhy.cuiweitourism.model.ShopRecommend;
import com.jhhy.cuiweitourism.net.models.FetchModel.ShopSearchRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;

/**
 * Created by jiahe008 on 2017/1/22.
 */
public class ShopActionBiz extends BasicActionBiz{

    private String TAG = "ShopActionBiz";

    public void getShopSearch(ShopSearchRequest fetch, BizGenericCallback<ArrayList<ShopRecommend>> callback){
        fetch.code = "Publics_storesearch";
        FetchGenericResponse<ArrayList<ShopRecommend>> fetchResponse = new FetchGenericResponse<ArrayList<ShopRecommend>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<ShopRecommend> array = parseJsonToObjectArray(response, ShopRecommend.class);
                GenericResponseModel<ArrayList<ShopRecommend>> model = new GenericResponseModel<>(response.head, array);
                this.bizCallback.onCompletion(model);
            }

            @Override
            public void onError(FetchError error) {
                this.onError(error);
            }
        };
        HttpUtils.executeXutils(fetch, new FetchGenericCallback<>(fetchResponse));
    }

}
