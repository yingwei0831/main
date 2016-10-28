package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.Collection;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/10/28.
 */
public class PayActionBiz {

    private Context context;
    private Handler handler;

    private String CODE_PAY = "Alipay_index";

//    {"head":{"code":"Alipay_index"},"field":{"ordersn":"80489619661756"}}


    public PayActionBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public void getPayInfo(final String ordersn){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_PAY);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put("ordersn", ordersn);
                    HttpUtils.executeXutils(headMap, fieldMap, getPayInfoCallback);
                }
            }.start();
        }else{
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

//    {"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},
// "body":["partner='2088521100493980'&seller_id='cwly1118@126.com'&out_trade_no='80489619661756'&subject='购买火车票'&body='翠微旅游商城'&total_fee='0'&notify_url='http://www.cwly1118.com/service.php/Alipay/notifyurl'&service='mobile.securitypay.pay'&payment_type='1'&_input_charset='utf-8'&app_id='2016102102275927'&it_b_pay='30m'&sign='B17zbypmt1eol8iuRIVjZfE5ZDmFQbr6OrfnjfA7Kn83YQX713kM%2B1HmmqiwiyUmyLeQIrmKqzcHVJLD8wKyp2uC2xQvc0ouBKPMf664VsSeU9jPxzDTf6N7Cr4gW7yRGiLvEQyUyln1nt45Y4uLT23myT%2Fa2M64j8dOCRmq%2FrU%3D'&sign_type='RSA'"]}

    private  ResponseResult getPayInfoCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_PAY_ALI;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
//    "head": {
//        "res_code": "0001",
//        "res_msg": "error",
//        "res_arg": "失败"
//    },
// {"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},
// "body":["partner='2088521100493980'&seller_id='cwly1118@126.com'&out_trade_no='80489619661756'&subject='购买火车票'&body='翠微旅游商城'&total_fee='0'&notify_url='http://www.cwly1118.com/service.php/Alipay/notifyurl'&service='mobile.securitypay.pay'&payment_type='1'&_input_charset='utf-8'&app_id='2016102102275927'&it_b_pay='30m'&sign='B17zbypmt1eol8iuRIVjZfE5ZDmFQbr6OrfnjfA7Kn83YQX713kM%2B1HmmqiwiyUmyLeQIrmKqzcHVJLD8wKyp2uC2xQvc0ouBKPMf664VsSeU9jPxzDTf6N7Cr4gW7yRGiLvEQyUyln1nt45Y4uLT23myT%2Fa2M64j8dOCRmq%2FrU%3D'&sign_type='RSA'"]}
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                    msg.obj = resArg;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
                    JSONArray infoAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    String info = null;
                    if (infoAry.length() != 0){
                        for (int i = 0; i < infoAry.length(); i ++){
                            JSONObject colObj = infoAry.getJSONObject(i);
                            info = colObj.getString("partner");
                        }
                    }
                    msg.obj = info;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
