package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.model.User;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/2.
 */
public class RegisterBiz {
    private Context context;
    private Handler handler;
    private String CODE_REGISTER = "User_register";

    public RegisterBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }
//    {"head":{"code":"User_register"},"field":{"mobile":"15210656911","password":"admin123","verify":"cwly","codes":"CWuhvle"}}

    /**
     *
     * @param mobile 手机号
     * @param password 密码
     * @param verify 验证码
     * @param codes 注册码
     */
    public void register(final String mobile, final String password, final String verify, final String codes){
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_REGISTER);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_USER_MOBILE, mobile);
                    fieldMap.put(Consts.KEY_PASSWORD, password);
                    fieldMap.put(Consts.KEY_CHECK_CODE, verify);
                    fieldMap.put(Consts.KEY_REGISTER_CODE, codes);
                    HttpUtils.executeXutils(headMap, fieldMap, registerCallback);
                }
            }.start();
        }else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult registerCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_REGISTER;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
//{"head":{"res_code":"0001","res_msg":"error","res_arg":"手机号已被注册"},"body":"[]"}
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                    msg.obj = resArg;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
// {"head":{"res_code":"0000","res_msg":"success","res_arg":"[]"},
// "body":{"mid":39,"mobile":"13260398606","nickname":"13260***","sex":"保密","avatar":"http://www.cwly1118.com/res/images/member_nopic.png","hxname":"cw14732302154"}}
                    JSONObject bodyObj = resultObj.getJSONObject(Consts.KEY_BODY);
                    User user = null;
                    if (bodyObj != null) {
                        user = new User();
                        user.setUserId(bodyObj.getString(Consts.KEY_USER_USER_MID));
                        user.setUserNickName(bodyObj.getString(Consts.KEY_USER_NICK_NAME));
                        user.setUserGender(bodyObj.getString(Consts.KEY_USER_GENDER));
                        user.setUserIconPath(bodyObj.getString(Consts.KEY_USER_ICON_PATH));
                        user.setUserPhoneNumber(bodyObj.getString(Consts.KEY_USER_MOBILE));
                        user.setHxname(bodyObj.getString("hxname"));
                    }
                    msg.obj = user;
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
