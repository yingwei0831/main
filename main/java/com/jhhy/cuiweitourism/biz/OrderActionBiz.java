package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.Invoice;
import com.jhhy.cuiweitourism.moudle.Order;
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.utils.BitmapUtil;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/23.
 */
public class OrderActionBiz {

    private String TAG = OrderActionBiz.class.getSimpleName();
    private Context context;
    private Handler handler;

    public OrderActionBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    //    {"head":{"code":"User_orderinfo"},"field":{"ordersn":"01585988836818"}}
    private String CODE_ORDER_DETAIL = "User_orderinfo";
    public void getOrderDetail(final String orderSN){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_ORDER_DETAIL);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("ordersn", orderSN);
                HttpUtils.executeXutils(headMap, fieldMap, detailCallback);
            }
        }.start();
    }
//    {"head":{"code":"User_refund"},"field":{"ordersn":"01586793683431","remark":"时间周转不开"}}
    private String CODE_REQUEST_REFUND = "User_refund";
    public void requestRefund(final String orderSN, final String remark){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_REQUEST_REFUND);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("ordersn", orderSN);
                fieldMap.put("remark", remark);
                HttpUtils.executeXutils(headMap, fieldMap, refundCallback);
            }
        }.start();
    }

//    {"head":{"code":"User_cancelrefund"},"field":{"ordersn":"01585988836818"}}
    private String CODE_REQUEST_CANCEL_REFUND = "User_cancelrefund";
    public void requestCancelRefund(final String orderSN){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_REQUEST_CANCEL_REFUND);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("ordersn", orderSN);
                HttpUtils.executeXutils(headMap, fieldMap, cancelRefundCallback);
            }
        }.start();
    }
//{"head":{"code":"User_orderpj"},"field":{"mid":"6","articleid":"1","typeid":"1","orderid":"1",
// "content":"旅游线路很不错。","img":"img"}}
    private String CODE_COMMIT_COMMENT = "User_orderpj";
    public void requestComment(final String mid, final Order order,
                               final String content, final List<Bitmap> upLoadBimp){
        new Thread(){
            @Override
            public void run() {
                super.run();

                JSONArray picArray = new JSONArray();
                for (int i = 0; i < upLoadBimp.size(); i++) {
                    picArray.put(BitmapUtil.bitmaptoString(upLoadBimp.get(i)));
                }

                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_COMMIT_COMMENT);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                fieldMap.put("typeid", order.getTypeId());
                fieldMap.put("orderid", order.getId());
                fieldMap.put("articleid", order.getProductautoid());
                fieldMap.put("content", content);
                fieldMap.put("img", picArray);

                HttpUtils.executeXutils(headMap, fieldMap, commentCallback);
            }
        }.start();
    }
//    {"head":{"code":"User_cancelorder"},"field":{"ordersn":"01974528995005"}}
    private String CODE_CANCEL_ORDER = "User_cancelorder";
    public void requestCancelOrder(final String orderSN){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_CANCEL_ORDER);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("ordersn", orderSN);
                HttpUtils.executeXutils(headMap, fieldMap, cancelOrderCallback);
            }
        }.start();
    }
//    {"head":{"code":"User_refund"},"field":{"ordersn":"01586793683431","remark":"时间周转不开"}}

    private ResponseResult cancelOrderCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDER_CANCEL;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//  {"head":{"res_code":"0000","res_msg":"success","res_arg":"取消订单成功"},"body":"[]"}
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult commentCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDER_COMMENT;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//{"head":{"res_code":"0001","res_msg":"error","res_arg":"创建图像失败"},"body":"[]"}
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult cancelRefundCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDER_CANCEL_REFUND;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"取消成功,请支付订单"},"body":"[]"}
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult refundCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDER_REFUND;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//   {"head":{"res_code":"0000","res_msg":"success","res_arg":"退款申请提交成功，等待处理"},"body":"[]"}
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult detailCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ORDER_DETAIL;
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
// "body":{"id":"3","productname":"北京山五日游","ordersn":"01585988836818","addtime":"2016-09-05 14:56:38","dingnum":"1","childnum":"0","oldnum":"0",
// "linkman":"张三","linktel":"15210656884",          "needjifen":"10",   "status":"1",   "ispay":"0",    "productautoid": "9",  "jifentprice": "10",
// "youke":[{"tourername":"王二麻子","mobile":"13895878954","cardnumber":"233695898745896597"}],
// "fapiao":[{"title":"北京发票","receiver":"王二麻子","mobile":"13895784597","address":"北京市昌平区"}],"lvyoubi":"10","total":4990}
                    Order travelOrder = null;
                    JSONObject bodyObj = resultObj.getJSONObject(Consts.KEY_BODY);
                    if (bodyObj != null){
                        travelOrder = new Order();
                        travelOrder.setId(bodyObj.getString(Consts.KEY_ID));
                        travelOrder.setProductName(bodyObj.getString("productname"));
                        travelOrder.setOrderSN(bodyObj.getString("ordersn"));
                        travelOrder.setAddTime(bodyObj.getString("addtime"));
                        travelOrder.setAdultNum(bodyObj.getString("dingnum"));
                        travelOrder.setChildNum(bodyObj.getString("childnum"));

                        travelOrder.setIsPay(bodyObj.getString("ispay"));
                        travelOrder.setLinkMan(bodyObj.getString("linkman"));
                        travelOrder.setLinkMobile(bodyObj.getString("linktel"));
                        travelOrder.setStatus(bodyObj.getString("status"));
                        travelOrder.setProductId(bodyObj.getString("productautoid"));

                        String travelersStr = bodyObj.getString("youke");
                        List<UserContacts> travelers = null;
                        if (travelersStr != null && travelersStr.length() != 0 && !"null".equals(travelersStr)) {
                            JSONArray travelersAry = bodyObj.getJSONArray("youke");
                            if (travelersAry != null && travelersAry.length() != 0) {
                                travelers = new ArrayList<>();
                                for (int i = 0; i < travelersAry.length(); i++) {
                                    UserContacts traveler = new UserContacts();
                                    JSONObject travelerObj = travelersAry.getJSONObject(i);
                                    traveler.setContactsName(travelerObj.getString("tourername"));
                                    traveler.setContactsMobile(travelerObj.getString("mobile"));
                                    traveler.setContactsIdCard(travelerObj.getString("cardnumber"));
                                    travelers.add(traveler);
                                }
                            }
                        }
                        travelOrder.setTravelers(travelers);

                        String invoiceStr = bodyObj.getString("fapiao");
                        Invoice invoice = null;
                        if (invoiceStr != null && invoiceStr.length() != 0 && !"null".equals(invoiceStr)) {
                            JSONArray invoiceAry = bodyObj.getJSONArray("fapiao");
                            if (invoiceAry != null && invoiceAry.length() != 0) {
                                invoice = new Invoice();
                                JSONObject invoiceObj = invoiceAry.getJSONObject(0);
                                invoice.setTitle(invoiceObj.getString("title"));
                                invoice.setReceiver(invoiceObj.getString("receiver"));
                                invoice.setMobile(invoiceObj.getString("mobile"));
                                invoice.setAddress(invoiceObj.getString("address"));
                            }
                        }
                        travelOrder.setInvoice(invoice);
                        travelOrder.setUseTravelIcon(bodyObj.getString("lvyoubi"));
                        travelOrder.setPrice(bodyObj.getString("total"));
                    }
                    msg.obj = travelOrder;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };


}
