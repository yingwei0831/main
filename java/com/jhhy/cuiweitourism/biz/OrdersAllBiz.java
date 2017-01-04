package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.model.Invoice;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.model.TravelDetail;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.just.sun.pricecalendar.GroupDeadline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/5.
 */
public class OrdersAllBiz {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private Handler handler;
    private String CODE_ALL_ORDERS = "User_allorder";

    public OrdersAllBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

//    {"head":{"code":"User_allorder"},"field":{"mid":"1","type":"0"}}
//    参数说明：type 0.全部订单、1.线路、2.酒店、3租车、8签证、14私人定制、202活动；
// 返回订单状态：0等待处理、1等待付款、2付款成功、3订单取消、4已退款、5交易完成

    public void getAllOrders(final String mid, final String type){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_ALL_ORDERS);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                    fieldMap.put(Consts.ORDER_TYPE, type);
                    HttpUtils.executeXutils(headMap, fieldMap, allOrdersCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }
//{"head":{"code":"Order_index"},"field":{"memberid":"6",
// "supplierlist":"1","productaid":"9","productname":"北京山五日游", "usedate":"2016-08-30",
// "price":"5000","childprice":"0.00", "dingnum":"1","childnum":"0",  "oldnum":"0", "oldprice":"0.00",
// "linkman":"张三","linktel":"15210656884","linkemail":"826782665@qq.com",
// "isneedpiao":"1","fapiao":{"title":"北京发票","receiver":"王二麻子","mobile":"13895784597","address":"北京市昌平区"},
// "jifentprice":"10","usejifen":"1","needjifen":"10",
// "lxr":[{"linkman":"王二麻子","idcard":"233695898745896597","mobile":"13895878954","passport":"hz12346789"}],"remark":"备注可不填"}}
    private String CODE_COMMIT_ORDER = "Order_index";
    /**
     * 提交订单
     */
    public void commitOrder(final TravelDetail detail, final GroupDeadline groupDeadline, final int countAdult, final int countChild,
                            final String linkName, final String linkMobile, final String linkMail,
                            final String needInvoice, final Invoice invoice,
                            final String useScore, final String priceScore,
                            final List<UserContacts> contactses, final String remark){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_COMMIT_ORDER);
                    JSONObject fieldObj = new JSONObject();
                    try {
                        if ("1".equals(needInvoice)){
                            HashMap<String, String> invoiceMap = new HashMap<String, String>();
                            invoiceMap.put("title", invoice.getTitle());
                            invoiceMap.put("receiver", invoice.getReceiver());
                            invoiceMap.put("mobile", invoice.getMobile());
                            invoiceMap.put("address", invoice.getAddress());
                            JSONObject invoiceObj = new JSONObject(invoiceMap);
                            fieldObj.put("fapiao", invoiceObj);
                        }else{
                            fieldObj.put("fapiao", null);
                        }
                        JSONArray contactArray = new JSONArray();
                        for (int i = 0; i < contactses.size(); i++){
                            UserContacts con = contactses.get(i);
                            HashMap<String, String> conMap = new HashMap<String, String>();
                            conMap.put("linkman", con.getContactsName());
                            conMap.put("idcard", con.getContactsIdCard());
                            conMap.put("mobile", con.getContactsMobile());
                            conMap.put("passport", con.getContactsPassport());
                            contactArray.put(new JSONObject(conMap));
                        }
                        fieldObj.put("memberid", MainActivity.user.getUserId());
                        fieldObj.put("supplierlist", detail.getBusinessId());
                        fieldObj.put("productaid", detail.getId());
                        fieldObj.put("productname", detail.getTitle());
                        fieldObj.put("price", groupDeadline.getSell_price_adult());
                        fieldObj.put("childprice", groupDeadline.getSell_price_children());
                        fieldObj.put("usedate", groupDeadline.getDate());
                        fieldObj.put("dingnum", countAdult+"");
                        fieldObj.put("childnum", countChild+"");
                        fieldObj.put("oldprice", "0");
                        fieldObj.put("oldnum", "0");
                        fieldObj.put("linkman", linkName);
                        fieldObj.put("linktel", linkMobile);
                        fieldObj.put("linkemail", linkMail);
                        fieldObj.put("isneedpiao", needInvoice);
                        fieldObj.put("usejifen", useScore); //0,1是否使用积分
                        fieldObj.put("jifentprice", priceScore); //用了多少积分
                        fieldObj.put("needjifen", detail.getNeedScore());

                        fieldObj.put("lxr", contactArray);
                        fieldObj.put("remark", remark);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    org.xutils.http.RequestParams param = new org.xutils.http.RequestParams(Consts.SERVER_URL);
                    param.addHeader("Content-Type", "application/json;charset=utf-8");
                    JSONObject json = new JSONObject();

                    try {
                        json.put(Consts.KEY_HEAD, setFieldParams(headMap));
                        json.put(Consts.KEY_FIELD, fieldObj);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String entityString = json.toString();
                    param.setBodyContent(entityString);
                    LogUtil.e(TAG, "发送数据：" + param.getBodyContent());
                    x.http().post(param, commitOrderCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult commitOrderCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDER_COMMIT;
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
// "head":{"res_code":"0000","res_msg":"success","res_arg":"订单添加成功"},"body":{"ordersn":"01637090631960","productname":"海岛游","price":200}}
                    JSONObject bodyObj = resultObj.getJSONObject(Consts.KEY_BODY);
                    Order order = new Order();
                    order.setOrderSN(bodyObj.getString("ordersn"));
                    order.setProductName(bodyObj.getString("productname"));
                    order.setPrice(bodyObj.getString(Consts.KEY_PRICE));
                    msg.obj = order;
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

    private ResponseResult allOrdersCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDERS_ALL;
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
//                    {"id": "1",
//                    "productname": "北京5日游",
//                    "price": "4800.00",
//                    "addtime": "1472097452",
//                    "status": "3",
//                    "ispinlun": "0",
//                    "typeid": "1",
//                    "productautoid": "12",
//                    "ordersn": "01974528995005",
//                    "litpic": "http://cwly.taskbees.cn:8083/uploads/2016/0607/9329dff285092f3874aed5e814d437c3.jpg"}
                    String body = resultObj.getString(Consts.KEY_BODY);
                    JSONArray bodyArray = new JSONArray(body);
                    List<Order> lists = new ArrayList<>();
                    for (int i = 0; i < bodyArray.length(); i++) {
                        JSONObject orderObj = (JSONObject) bodyArray.get(i);
                        Order order = new Order();
                        order.setId(orderObj.getString(Consts.KEY_ID));
                        order.setProductName(orderObj.getString(Consts.ORDER_PRODUCT_NAME));
                        order.setPrice(orderObj.getString(Consts.KEY_PRICE));
                        order.setAddTime(orderObj.getString(Consts.ORDER_ADD_TIME));
                        order.setStatus(orderObj.getString(Consts.ORDER_STATUS));
                        order.setStatusComment(orderObj.getString(Consts.ORDER_IS_COMMENT));
                        order.setTypeId(orderObj.getString("typeid"));
                        order.setProductautoid(orderObj.getString("productautoid"));
                        order.setOrderSN(orderObj.getString("ordersn"));
                        order.setPicPath(orderObj.getString(Consts.PIC_PATH_LITPIC));
                        order.setStartaddress(orderObj.getString("startaddress"));
                        order.setEndaddress(orderObj.getString("endaddress"));
                        order.setSanfangorderno(orderObj.getString("sanfangorderno"));
                        lists.add(order);
                    }
                    msg.obj = lists;
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

//{"head":{"code":"User_dfkorder"},"field":{"mid":"6","type":"0"}}
    private String CODE_ALL_WAIT_PAY = "User_dfkorder";

    /**
     * 待付款
     */
    public void getWaitPayment(final String mid, final String type){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_ALL_WAIT_PAY);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                    fieldMap.put(Consts.ORDER_TYPE, type);
                    HttpUtils.executeXutils(headMap, fieldMap, waitPayOrdersCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult waitPayOrdersCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDERS_WAIT_PAY;
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
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"}, "body":[
// {"id":"3","productname":"北京山五日游","price":"5000.00","addtime":"1473058598","status":"1",
// "ispinlun":"0","typeid":"1","productautoid":"9","ordersn":"01585988836818", "litpic":"http:\/\/cwly.taskbees.cn:8083"}]}
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<Order> listWaitPay = new ArrayList<>();
                    if(bodyAry != null) {
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject orderObj = (JSONObject) bodyAry.get(i);
                            Order order = new Order();
                            order.setId(orderObj.getString(Consts.KEY_ID));
                            order.setProductName(orderObj.getString(Consts.ORDER_PRODUCT_NAME));
                            order.setPrice(orderObj.getString(Consts.KEY_PRICE));
                            order.setAddTime(orderObj.getString(Consts.ORDER_ADD_TIME));
                            order.setStatus(orderObj.getString(Consts.ORDER_STATUS));
                            order.setStatusComment(orderObj.getString(Consts.ORDER_IS_COMMENT));
                            order.setOrderSN(orderObj.getString("ordersn"));
                            order.setPicPath(orderObj.getString(Consts.PIC_PATH_LITPIC));
                            order.setTypeId(orderObj.getString("typeid"));
                            listWaitPay.add(order);
                        }
                    }
                    msg.obj = listWaitPay;
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
//    {"head":{"code":"User_ordercomment"},"field":{"mid":"6","type":"0"}}
    private String CODE_WAIT_COMMENT = "User_ordercomment";
    public void getWaitComment(final String mid, final String type){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_WAIT_COMMENT);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                    fieldMap.put(Consts.ORDER_TYPE, type);
                    HttpUtils.executeXutils(headMap, fieldMap, waitCommentOrdersCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult waitCommentOrdersCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDERS_WAIT_COMMENT;
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
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[]}
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[
//{"id":"12","productname":"北京5日游","price":"4800.00","addtime":"1473212174","status":"5",
// "ispinlun":"0","typeid":"1","productautoid":"12","ordersn":"01121746957465",
// "litpic":"http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0607\/9329dff285092f3874aed5e814d437c3.jpg"}]}
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<Order> listWaitComment = new ArrayList<>();
                    if(bodyAry != null) {
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject orderObj = (JSONObject) bodyAry.get(i);
                            Order order = new Order();
                            order.setId(orderObj.getString(Consts.KEY_ID));
                            order.setProductName(orderObj.getString(Consts.ORDER_PRODUCT_NAME));
                            order.setPrice(orderObj.getString(Consts.KEY_PRICE));
                            order.setAddTime(orderObj.getString(Consts.ORDER_ADD_TIME));
                            order.setStatus(orderObj.getString(Consts.ORDER_STATUS));
                            order.setStatusComment(orderObj.getString(Consts.ORDER_IS_COMMENT));

                            order.setTypeId(orderObj.getString("typeid"));
                            order.setProductautoid(orderObj.getString("productautoid"));

                            order.setOrderSN(orderObj.getString("ordersn"));
                            order.setPicPath(orderObj.getString(Consts.PIC_PATH_LITPIC));
                            listWaitComment.add(order);
                        }
                    }
                    msg.obj = listWaitComment;
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

    private String CODE_WAIT_REFUND = "User_forarefund";
//{"head":{"code":"User_forarefund"},"field":{"mid":"1","type":"0"}}
    public void getWaitRefund(final String mid, final String type){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_WAIT_REFUND);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                    fieldMap.put(Consts.ORDER_TYPE, type);
                    HttpUtils.executeXutils(headMap, fieldMap, waitRefundOrdersCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult waitRefundOrdersCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDERS_WAIT_REFUND;
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
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[]}
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[
// {"id":"10","productname":"测试的跟团游","price":"1000.00","addtime":"1473156292","status":"0",
// "ispinlun":"0","typeid":"1","productautoid":"16","ordersn":"01562923949226",
// "litpic":"http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0607\/f1a7e360b659d4dbf6f40e133ffe5d20.jpg"}]}
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<Order> listWaitRefund = new ArrayList<>();
                    if(bodyAry != null) {
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject orderObj = (JSONObject) bodyAry.get(i);
                            Order order = new Order();
                            order.setId(orderObj.getString(Consts.KEY_ID));
                            order.setProductName(orderObj.getString(Consts.ORDER_PRODUCT_NAME));
                            order.setPrice(orderObj.getString(Consts.KEY_PRICE));
                            order.setAddTime(orderObj.getString(Consts.ORDER_ADD_TIME));
                            order.setStatus(orderObj.getString(Consts.ORDER_STATUS));
                            order.setStatusComment(orderObj.getString(Consts.ORDER_IS_COMMENT));
                            order.setOrderSN(orderObj.getString("ordersn"));
                            order.setPicPath(orderObj.getString(Consts.PIC_PATH_LITPIC));
                            order.setTypeId(orderObj.getString("typeid"));
                            listWaitRefund.add(order);
                        }
                    }
                    msg.obj = listWaitRefund;
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
    private JSONObject setFieldParams(Map<String, Object> map) {
        JSONObject fieldObj = new JSONObject();
        if(null == map) return fieldObj;
        try {
            for(String key : map.keySet()){
                fieldObj.put(key, map.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fieldObj;
    }
}
