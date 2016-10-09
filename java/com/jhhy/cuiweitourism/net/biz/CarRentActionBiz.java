package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarModel;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarOrderQuery;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarPriceEstimate;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentCity;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentFetchModel;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentNextModel;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentOrder;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentPickLocation1;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentPickLocation2;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarSmallOrder;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarSmallOrderCancel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.BasicResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CarNextResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CarRentDetail;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CarRentOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CarRentOrderResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.SmallCarOrderResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchCallBack;
import com.jhhy.cuiweitourism.net.netcallback.FetchResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by zhangguang on 16/9/29.
 */
public class CarRentActionBiz extends BasicActionBiz {
    public CarRentActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    private static final String BIZTAG = "CarRentActionBiz";

    public JsonElement parseJsonBody(FetchResponseModel model){
        JsonParser parser = new JsonParser();
        if(model.body != null && !("null".equals(model.body)) && model.body.length() > 0){
            JsonElement element = parser.parse(model.body);
            return element;
        }
        else {
            return null;
        }


    }

    public  ArrayList<ArrayList<String>> parseJsonTotwoLevelArray(FetchResponseModel model){
        JsonElement element = parseJsonBody(model);
        if (element != null && element.isJsonArray()) {
            ArrayList<ArrayList<String>> array = new  ArrayList<ArrayList<String>>();
             array = new Gson().fromJson(element, array.getClass());
            return array;
        }
        else {
            return null;
        }
    }

    /**
     *  租车
     */
    public void fetchCarRentalServiceDetail(BizCallback callBack){
        CarRentFetchModel fetchRequest = new CarRentFetchModel();
        fetchRequest.setBizCode("Car_index");
        LogUtil.e(BIZTAG,fetchRequest.toBizJsonString());

        final FetchResponse fetchResponse = new FetchResponse(callBack) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                returnModel.headModel = response.head;
                JsonElement element = parseJsonBody(response);
                if(null != element){
                    if (element.isJsonObject()) {

                    } else if (element.isJsonArray()) {
                        ArrayList<CarRentDetail> array = new ArrayList<CarRentDetail>();
                        JsonArray jsonArray = element.getAsJsonArray();
                        Iterator it = jsonArray.iterator();
                        while (it.hasNext()) {
                            JsonElement e = (JsonElement) it.next();
                            CarRentDetail detail = new Gson().fromJson(e, CarRentDetail.class);
                            array.add(detail);
                        }
                        returnModel.body = array;

                    }

                }

                this.bizCallback.onCompletion(returnModel);

            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(fetchRequest, new FetchCallBack(fetchResponse));
    }


    /**
     *  租车下一步
     *  @param model   参数，参照文档
     */
    public void carRentNextApi(CarRentNextModel model, BizCallback callBack){
        model.code = "Car_nexts";
        FetchResponse fetchResponse = new FetchResponse(callBack) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                JsonElement element = parseJsonBody(response);
                if (element.isJsonObject()){
                    CarNextResponse carNext = new Gson().fromJson(element,CarNextResponse.class);
                    returnModel.headModel = response.head;
                    returnModel.body = carNext;

                }
                else{
                    // exception
                    assert (false);
                }
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(model,new FetchCallBack(fetchResponse));
    }

    /**
     *  租车提交订单
     *  @param model   参数，参照文档
     */
    public void carRentSubmitOrder(CarRentOrder model, BizCallback callBack){
        model.code = "Order_carorder";
        FetchResponse fetchResponse = new FetchResponse(callBack){
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                JsonElement element = parseJsonBody(response);
                if (element.isJsonObject()){
                    CarRentOrderResponse orderResponse = new Gson().fromJson(element,CarRentOrderResponse.class);
                    returnModel.headModel = response.head;
                    returnModel.body = orderResponse;

                }
                else{
                    // exception
                    assert (false);
                }
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(model,new FetchCallBack(fetchResponse));
    }


    /**
     *  获取租车城市
     *  @param model   参数，参照文档
     */
     public void  carRentGetCitys(CarRentCity model, BizCallback callBack){
        model.code = "Car_zccity";

         FetchResponse fetchResponse = new FetchResponse(callBack){
             @Override
             public void onCompletion(FetchResponseModel response) {
                 BasicResponseModel returnModel = new BasicResponseModel();
                 JsonElement element = parseJsonBody(response);
                 if (element.isJsonArray()){
                     ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
                     List<List<String>> result =  new Gson().fromJson(element,array.getClass());
                     returnModel.body = result;
                     returnModel.headModel = response.head;

                 }
                 else{
                     assert (false);
                 }
                 this.bizCallback.onCompletion(returnModel);
             }

             @Override
             public void onError(FetchError error) {
                 this.bizCallback.onError(error);
             }
         };

         HttpUtils.executeXutils(model,new FetchCallBack(fetchResponse));
     }


    /**
     *  获取城市车型
     *  @param carModel   参数，参照文档
     */
    public void carRentGetCityCarModel(CarModel carModel, BizCallback callBack){
        carModel.code = "Car_cartype";
        FetchResponse fetchResponse = new FetchResponse(callBack){
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                ArrayList<ArrayList<String>> result =  parseJsonTotwoLevelArray(response);
                returnModel.body = result;
                returnModel.headModel = response.head;
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(carModel,new FetchCallBack(fetchResponse));
    }


    /**
     *  获取城市接送地点  车站，机场
     *  @param model   参数，参照文档
     */

    public  void carRentGetPickupLocationForAirport(CarRentPickLocation1 model, BizCallback callBack){
        model.code = "Car_jsaddress";
        FetchResponse fetchResponse = new FetchResponse(callBack){
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                ArrayList<ArrayList<String>> result =  parseJsonTotwoLevelArray(response);
                returnModel.body = result;
                returnModel.headModel = response.head;
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(model,new FetchCallBack(fetchResponse));
    }

    /**
     *  获取城市接送地点  小区办公楼
     *  @param model   参数，参照文档
     */

    public  void carRentGetPickupLocationForOfficeBuilding(CarRentPickLocation2 model, BizCallback callBack){
        model.code = "Car_jsdizhi";
        FetchResponse fetchResponse = new FetchResponse(callBack){
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                ArrayList<ArrayList<String>> result =  parseJsonTotwoLevelArray(response);
                returnModel.body = result;
                returnModel.headModel = response.head;
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(model,new FetchCallBack(fetchResponse));
    }

    /**
     *  价格预估
     *  @param priceEstimate   参数，参照文档
     */
    public  void carRentPriceEstimate(CarPriceEstimate priceEstimate, BizCallback callBack){
        priceEstimate.code = "Car_price";
        FetchResponse fetchResponse = new FetchResponse(callBack){
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                ArrayList<ArrayList<String>> result =  parseJsonTotwoLevelArray(response);
                returnModel.body = result;
                returnModel.headModel = response.head;
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(priceEstimate,new FetchCallBack(fetchResponse));
    }


    /**
     *  小车订单
     *  @param order   参数，参照文档
     */
    public  void carRentSmallCarOrder(CarSmallOrder order, BizCallback callBack){
        order.code = "Order_xcarorder";
        FetchResponse fetchResponse = new FetchResponse(callBack){
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                JsonElement element = parseJsonBody(response);
                if(null != element){
                    if (element.isJsonObject()){
                        SmallCarOrderResponse orderResponse = new Gson().fromJson(element,SmallCarOrderResponse.class);
                        returnModel.headModel = response.head;
                        returnModel.body = orderResponse;
                    }
                    else{
                        assert (false);
                    }
                }


                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(order,new FetchCallBack(fetchResponse));
    }

    /**
     *  取消小车订单
     *  @param cancel   参数，参照文档
     */

    public void carRentSmallCarOrderCancel(CarSmallOrderCancel cancel,BizCallback callBack){
        cancel.code = "Order_cancelorder";
        FetchResponse fetchResponse = new FetchResponse(callBack){
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();
                returnModel.headModel = response.head;
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(cancel,new FetchCallBack(fetchResponse));
    }

    /**
     *  查询订单
     *  @param query   参数，参照文档
     */

    public void carRentOrderQuery(CarOrderQuery query, BizCallback callback){
        query.code = "Order_selectorder";
        FetchResponse fetchResponse = new FetchResponse(callback){
            @Override
            public void onCompletion(FetchResponseModel response) {
                BasicResponseModel returnModel = new BasicResponseModel();

                JsonElement element = parseJsonBody(response);
                if(null != element){
                    if (element.isJsonObject()){
                        CarRentOrderInfo orderInfo = new Gson().fromJson(element,CarRentOrderInfo.class);
                        returnModel.body = orderInfo;
                    }
                    else{
                        assert (false);
                    }
                }

                returnModel.headModel = response.head;
                this.bizCallback.onCompletion(returnModel);
            }
            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(query,new FetchCallBack(fetchResponse));
    }
}
