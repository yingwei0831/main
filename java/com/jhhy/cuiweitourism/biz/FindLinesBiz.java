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
 * Created by jiahe008 on 2016/9/9.
 */
public class FindLinesBiz {

    private Context context;
    private Handler handler;

    private String CODE_FIND_LINES = "Publics_line";

    public FindLinesBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

// "head":{"code":"Publics_line"},"field":{"page":"1","offset":"10"}}

//{"head":{"code":"Publics_line"},"field":{
// "startareaid":"2","order":"storeprice desc","day":"5","price":"0,10000",
// "zcfdate":"2016-8-18","wcfdate":"2016-8-18","page":"1","offset":"10"}}

    public void getLines(final int page, final String fromCityId, final String sort, String day, String price, String earlyTime, String laterTime){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_FIND_LINES);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("startareaid", fromCityId);
                fieldMap.put("order", sort);

                fieldMap.put(Consts.KEY_PAGE, Integer.toString(page));
                fieldMap.put(Consts.KEY_OFFSET, "10");
                HttpUtils.executeXutils(headMap, fieldMap, findLinesCallback);
            }
        }.start();
    }

    private ResponseResult findLinesCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_FIND_LINES;
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
                    List<Travel> listLines = null;
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    if(bodyAry != null){
                        listLines = new ArrayList<>();
                        for (int i = 0; i < bodyAry.length(); i++){
                            JSONObject lineObj = (JSONObject) bodyAry.get(i);
                            Travel travel = new Travel();
                            travel.setId(lineObj.getString(Consts.KEY_ID));
                            travel.setTravelTitle(lineObj.getString(Consts.KEY_TITLE));
                            travel.setTravelPrice(lineObj.getString(Consts.KEY_PRICE));
                            travel.setTravelIconPath(lineObj.getString(Consts.PIC_PATH_LITPIC));
                            travel.setGroup(lineObj.getString(Consts.KEY_GROUP));
                            listLines.add(travel);
                        }
                    }
                    msg.obj = listLines;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
