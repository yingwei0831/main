package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.Line;
import com.jhhy.cuiweitourism.moudle.ShopRecommend;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by birney1 on 16/9/29.
 */
public class FindShopBiz {

    private Context context;
    private Handler handler;

    public FindShopBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    //找商铺    {"head":{"code":"Publics_store"},"field":{"page":"1","offset":"10"}}
    private String CODE_FIND_SHOP = "Publics_store";

    public void getShop(final String page) {
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_FIND_SHOP);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put("page", page);
                    fieldMap.put("offset", "10");
                    HttpUtils.executeXutils(headMap, fieldMap, findShopCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    //该商铺所有线路{"head":{"code":"Publics_storeline"},"field":{"sjid":"1","page":"1","offset":"10"}}
    private String CODE_SHOP_LINE_LIST = "Publics_storeline";

    public void getLineList(final String shopId, final String page) {
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_SHOP_LINE_LIST);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put("sjid", shopId);
                    fieldMap.put("page", page);
                    fieldMap.put("offset", "10");
                    HttpUtils.executeXutils(headMap, fieldMap, shopLineListCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult shopLineListCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_FIND_SHOP_LINE_LIST;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
//                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                    msg.obj = resArg;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//                    {
//                        "id": "16",
//                        "title": "测试的跟团游",
//                        "price": "1000",
//                        "litpic": "http://cwly.taskbees.cn:8083/uploads/2016/0607/f1a7e360b659d4dbf6f40e133ffe5d20.jpg",
//                        "group": "东"
//                    }
                    List<Line> lines = new ArrayList<>();
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    for (int i = 0; i < bodyAry.length(); i++) {
                        JSONObject lineObj = bodyAry.getJSONObject(i);
                        Line line = new Line(lineObj.getString(Consts.KEY_ID), lineObj.getString(Consts.KEY_TITLE),
                                lineObj.getString(Consts.PIC_PATH_LITPIC), lineObj.getString(Consts.KEY_PRICE),
                                lineObj.getString("group"));
                        lines.add(line);
                    }
                    msg.obj = lines;
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

    private ResponseResult findShopCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_FIND_SHOP;
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
//                    "body": [
//                    {
//                        "sid": "1",
//                        "suppliername": "易搜学",
//                        "litpic": "http://cwly.taskbees.cn:8083/uploads/member/57d60b0c2f809.jpg"
//                    }
//                    ]
                    List<ShopRecommend> listShop = new ArrayList<>();
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    for (int i = 0; i < bodyAry.length(); i++) {
                        JSONObject shopObj = bodyAry.getJSONObject(i);
                        ShopRecommend shop = new ShopRecommend();
                        shop.setId(shopObj.getString("sid"));
                        shop.setName(shopObj.getString("suppliername"));
                        shop.setLitpic(shopObj.getString(Consts.PIC_PATH_LITPIC));
                        listShop.add(shop);
                    }

                    msg.obj = listShop;
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
