package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/6.
 */
public class Tab1RecommendBiz {
    private Context context;
    private Handler handler;
    private String CODE_TAB1_RECOMMEND = "Publics_allline";
    private int typeMessage;

    public Tab1RecommendBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }
//{"head":{"code":"Publics_allline"},"field":{"type":"","area":"北京"}}  //type:1国内游、2出境游,area:地区
    public void getRecommendForYou(final String type, final String area, int typeMessage){
        this.typeMessage = typeMessage;
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_TAB1_RECOMMEND);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_TYPE, type);
                fieldMap.put(Consts.TAB1_RECOMMEND_AREA, area);
                HttpUtils.executeXutils(headMap, fieldMap, tab1RecommendCallback);
            }
        }.start();
    }

    private ResponseResult tab1RecommendCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = typeMessage;
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
                    String body = resultObj.getString(Consts.KEY_BODY);
                    JSONArray bodyArray = new JSONArray(body);
                    List<Travel> lists = new ArrayList<>();
                    if(bodyArray != null) {
                        for (int i = 0; i < bodyArray.length(); i++) {
                            JSONObject travelObj = (JSONObject) bodyArray.get(i);
                            Travel travel = new Travel();
                            travel.setId(travelObj.getString(Consts.ORDER_ID));
                            travel.setTravelTitle(travelObj.getString(Consts.KEY_TITLE));
                            travel.setTravelIconPath(travelObj.getString(Consts.PIC_PATH_LITPIC));
                            travel.setTravelPrice(travelObj.getString(Consts.KEY_PRICE));
                            travel.setGroup(travelObj.getString(Consts.KEY_GROUP));
                            lists.add(travel);
                        }
                    }
                    msg.obj = lists;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
