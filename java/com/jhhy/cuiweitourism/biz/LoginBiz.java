package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.easemob.DemoHelper;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/2.
 */
public class LoginBiz {

    private static final String TAG = LoginBiz.class.getSimpleName();

    private Context context;
    private Handler handler;
    private String CODE_LOGIN = "User_login";

    public LoginBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

//{"head":{"code":"User_login"},"field":{"mobile":"15210656911","password":"admin123"}}

    public void login(final String userName, final String password) {
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    //登录翠微服务器
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_LOGIN);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_USER_MOBILE, userName);
                    fieldMap.put(Consts.KEY_PASSWORD, password);
                    HttpUtils.executeXutils(headMap, fieldMap, loginCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
//        if (NetworkUtil.checkNetwork(context)) {
//        } else {
//            handler.sendEmptyMessage(Consts.NET_ERROR);
//        }
    }

//    {"head":{"code":"User_forget"},"field":{"mobile":"15210656911","verify":"cwly","newpwd":"admin123456"}}
    private String CODE_FIND_PWD = "User_forget";
    /**
     * 忘记密码，找回密码
     * @param mobile 手机号
     * @param checkCode 验证码
     * @param newPwd 新密码
     */
    public void findPassword(String mobile, String checkCode, String newPwd){
        if (NetworkUtil.checkNetwork(context)) {
            Map<String, Object> headMap = new HashMap<>();
            headMap.put(Consts.KEY_CODE, CODE_FIND_PWD);
            Map<String, Object> fieldMap = new HashMap<>();
            fieldMap.put(Consts.KEY_USER_MOBILE, mobile);
            fieldMap.put(Consts.KEY_CHECK_CODE, checkCode);
            fieldMap.put("newpwd", newPwd);
            HttpUtils.executeXutils(headMap, fieldMap, findPwdCallback);
//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                }
//            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult findPwdCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_FIND_PWD;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
//                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            super.onError(ex, isOnCallback);
            if (!isOnCallback) {
                if (ex instanceof SocketTimeoutException) {
                    handler.sendEmptyMessage(Consts.NET_ERROR_SOCKET_TIMEOUT);
                } else {
                    handler.sendEmptyMessage(-2);
                }
            }
        }
    };

    //    {"head":{"res_code":"0001","res_msg":"error","res_arg":"用户名不存在"},"body":"[]"}
    private ResponseResult loginCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            final Message msg = new Message();
            msg.what = Consts.MESSAGE_LOGIN;
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
                    handler.sendMessage(msg);
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
// "mid":"1","nickname":"15210***","sex":"保密","avatar":"http:\/\/cwly.taskbees.cn:8083\/res\/images\/member_nopic.png","mobile":"15210656912",
// "truename":"孙婷芳","cardid":"211383198508292654","jifen":"25010"}}

//{"res_code":"0000","res_msg":"success","res_arg":"登录成功"},"body":{
// "mid":"11","nickname":"15210***","sex":"保密","avatar":"","mobile":"15210656919",
// "truename":"","cardid":"","jifen":"0","hxname":""}}
                    String body = resultObj.getString(Consts.KEY_BODY);
                    JSONObject bodyObj = new JSONObject(body);
                    User user = new User();
                    user.setUserId(bodyObj.getString(Consts.KEY_USER_USER_MID));
                    user.setUserNickName(bodyObj.getString(Consts.KEY_USER_NICK_NAME));
                    user.setUserGender(bodyObj.getString(Consts.KEY_USER_GENDER));
                    user.setUserIconPath(bodyObj.getString(Consts.KEY_USER_ICON_PATH));
                    user.setUserPhoneNumber(bodyObj.getString(Consts.KEY_USER_MOBILE));
                    user.setUserTrueName(bodyObj.getString(Consts.KEY_USER_TRUE_NAME));
                    user.setUserCardId(bodyObj.getString(Consts.KEY_USER_CARD_ID));
                    user.setUserScore(bodyObj.getString(Consts.KEY_USER_SCORE));
                    user.setHxname(bodyObj.getString("hxname"));
                    msg.obj = user;

                    //登录环信聊天服务器
                    EMChatManager.getInstance().login(user.getHxname(), "admin123", new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                        }

                        @Override
                        public void onError(final int code, final String message) {
                            LogUtil.e(TAG, "code = " + code + ", 环信失败原因 message = " + message);
                            Message msg = new Message();
                            msg.what = Consts.MESSAGE_LOGIN;
                            msg.arg1 = 0;
                            msg.obj = "登录聊天服务器失败";
                            handler.sendMessage(msg);
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            finally {
//                handler.sendMessage(msg);
//            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            super.onError(ex, isOnCallback);
            if (!isOnCallback) {
                if (ex instanceof SocketTimeoutException) {
                    handler.sendEmptyMessage(Consts.NET_ERROR_SOCKET_TIMEOUT); //连接超时
                } else {
                    handler.sendEmptyMessage(-2); //其它异常
                }
            }else{
                handler.sendEmptyMessage(-2); //解析异常
            }
        }
    };

    public void logout(String username) {
        DemoHelper.getInstance().logout(true, null);
    }
}
