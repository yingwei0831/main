package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/22.
 */
public class CityBiz {

    private Context context;
    private Handler handler;

    public CityBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    private String CODE_GET_CITY = "Publics_bourn";
//    {"head":{"code":"Publics_bourn"},"field":[]}
    public void getCitySelection(){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_GET_CITY);
                Map<String, Object> fieldMap = new HashMap<>();
                HttpUtils.executeXutils(headMap, fieldMap, getCityCallback);
            }
        }.start();
    }
    private ResponseResult getCityCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_GET_CITY;
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
// {"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[{"id":"2","cityname":"鞍山"},{"id":"39","cityname":"百色"}]}
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<PhoneBean> cityList = null;
                    if (bodyAry != null && bodyAry.length() != 0){
                        cityList = new ArrayList<>();
                        for (int i = 0; i < bodyAry.length(); i++){
                            JSONObject cityObj = bodyAry.getJSONObject(i);
                            PhoneBean city = new PhoneBean();
                            city.setName(cityObj.getString("cityname"));
                            city.setCity_id(cityObj.getString(Consts.KEY_ID));
                            cityList.add(city);
                        }
                    }
                    msg.obj = cityList;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

}
