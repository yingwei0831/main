package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/9.
 */
public class FindLinesBiz {

    private String TAG = FindLinesBiz.class.getSimpleName();
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
    //order:排序，值怎么传？
    //order: ""—>时间降序，"1"—>价格降序，其它—>价格升序

    public void getLines(final int page, final String fromCityId, final String sort, final String day, final String price, final String earlyTime, final String laterTime){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_FIND_LINES);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put("startareaid", fromCityId);
                    fieldMap.put("order", sort);
                    fieldMap.put("day", day);
                    fieldMap.put("price", price);
                    fieldMap.put("zcfdate", earlyTime);
                    fieldMap.put("wcfdate", laterTime);
                    fieldMap.put(Consts.KEY_PAGE, Integer.toString(page));
                    fieldMap.put(Consts.KEY_OFFSET, "10");
                    HttpUtils.executeXutils(headMap, fieldMap, findLinesCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
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
                LogUtil.e(TAG, "headObj = " + headObj.toString());
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                    msg.obj = resArg;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
                    List<Travel> listLines = new ArrayList<>();
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    if(bodyAry != null){
                        for (int i = 0; i < bodyAry.length(); i++){
                            JSONObject lineObj = (JSONObject) bodyAry.get(i);
                            Travel travel = new Travel();
                            travel.setId(lineObj.getString(Consts.KEY_ID));
                            travel.setTravelTitle(lineObj.getString(Consts.KEY_TITLE));
                            travel.setTravelPrice(lineObj.getString(Consts.KEY_PRICE));
                            travel.setTravelIconPath(lineObj.getString(Consts.PIC_PATH_LITPIC));
                            travel.setType(lineObj.getString("txt"));
                            travel.setGroup(lineObj.getString(Consts.KEY_GROUP));
                            travel.setIm(lineObj.getString("im"));
                            listLines.add(travel);
                        }
                    }
                    msg.obj = listLines;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                msg.arg1 = 0;
                msg.obj = "数据解析错误";
            } finally {
                handler.sendMessage(msg);
            }
        }
        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            super.onError(ex, isOnCallback);
            if (ex instanceof SocketTimeoutException) {
                handler.sendEmptyMessage(Consts.NET_ERROR_SOCKET_TIMEOUT);
            }
        }
    };
}
