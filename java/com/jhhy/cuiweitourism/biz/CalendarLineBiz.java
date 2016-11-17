package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.GroupDeadline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/19.
 */
public class CalendarLineBiz {

    private Context context;
    private Handler handler;
    private String CODE_CALENDAR_PRICE = "Publics_rili";

    public CalendarLineBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }
//    {"head":{"code":"Publics_rili"},"field":{"id":"14"}}
    public void getCalendarLine(final String id){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_CALENDAR_PRICE);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_ID, id);
                    HttpUtils.executeXutils(headMap, fieldMap, priceCallback);
                }
            }.start();
        }else{
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult priceCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_CALENDAR_PRICE;
            int position = -1;
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
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List[] ary = new List[2];
                    List<GroupDeadline> listCalendar = null;
                    List<GroupDeadline> listCalendarNew = null;
                    if (bodyAry != null && bodyAry.length() != 0){
                        listCalendar = new ArrayList<>();
                        listCalendarNew = new ArrayList<>();
                        for (int i = 0; i < bodyAry.length(); i ++){
                            JSONObject cpObj = bodyAry.getJSONObject(i);
                            GroupDeadline groupDeadline = new GroupDeadline();
                            String dateStr = Utils.getTimeStrYMD(Long.parseLong(cpObj.getString("day")) * 1000);
                            groupDeadline.setDate(dateStr);//将时间戳改成2016-09-20
                            groupDeadline.setSell_price_children(cpObj.getString("childprice"));
                            groupDeadline.setSell_price_elder(cpObj.getString("oldprice"));
                            groupDeadline.setSell_price_adult(cpObj.getString("adultprice"));
                            listCalendar.add(groupDeadline);
//                            if (Utils.getNowDateShort().equals(dateStr)){
//                                position = i;
//                            }
//                            if (i >= position && position != -1) {
                                listCalendarNew.add(groupDeadline);
//                            }
                        }
                    }
                    ary[0] = listCalendar;
                    ary[1] = listCalendarNew;
                    msg.obj = ary;

                }
            } catch (JSONException e) {
                e.printStackTrace();
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
