package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.PriceArea;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/9.
 */
public class ScreenBiz {

    private Context context;
    private Handler handler;

    private String CODE_TRIP_DAYS = "Publics_days"; //行程天数

    private String CODE_TRIP_PRICE = "Publics_prices"; //价格筛选

    public ScreenBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

//    {"head":{"code":"Publics_days"},"field":[]}
//    {"head":{"code":"Publics_prices"},"field":[]}

    public void getScreenDays(){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_TRIP_DAYS);
                HttpUtils.executeXutils(headMap, null, tripDaysCallback);
            }
        }.start();
    }

    public void getScreenPrice(){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_TRIP_PRICE);
                HttpUtils.executeXutils(headMap, null, tripPriceCallback);
            }
        }.start();
    }
    private ResponseResult tripPriceCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_TRIP_PRICE;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                    msg.obj = resArg;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[{"lowerprice":"0","highprice":"200"},{"lowerprice":"201","highprice":"500"},
// {"lowerprice":"501","highprice":"2000"},{"lowerprice":"2001","highprice":"5000"},{"lowerprice":"5001","highprice":"10000"},{"lowerprice":"10001","highprice":""}]}
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<PriceArea> listPriceArea = null;
                    if (bodyAry != null && bodyAry.length() != 0){
                        listPriceArea = new ArrayList<>();
                        for (int i = 0; i < bodyAry.length(); i++){
                            JSONObject priceObj = bodyAry.getJSONObject(i);
                            PriceArea priceArea = new PriceArea(priceObj.getString("lowerprice"), priceObj.getString("highprice"));
                            listPriceArea.add(priceArea);
                        }
                    }
                    msg.obj = listPriceArea;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult tripDaysCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_TRIP_DAYS;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                    msg.obj = resArg;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[
// {"word":"1"},{"word":"2"},{"word":"3"},{"word":"4"},{"word":"5"},{"word":"6"},{"word":"7"},{"word":"8"},{"word":"9"},{"word":"10"}]}

                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<String> listDays = null;
                    if (bodyAry != null && bodyAry.length() != 0){
                        listDays = new ArrayList<>();
                        for (int i = 0; i < bodyAry.length(); i++){
                            JSONObject dayObj = bodyAry.getJSONObject(i);
                            listDays.add(dayObj.getString("word"));
                        }
                    }
                    msg.obj = listDays;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
