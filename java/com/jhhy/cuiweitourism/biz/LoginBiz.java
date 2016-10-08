package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.ui.easemob.DemoHelper;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void login(final String userName, final String password){
        //登录环信聊天服务器
        EMChatManager.getInstance().login(userName, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                new Thread() {
                    @Override
                    public void run() {
                        Map<String, Object> headMap = new HashMap<>();
                        headMap.put(Consts.KEY_CODE, CODE_LOGIN);
                        Map<String, Object> fieldMap = new HashMap<>();
                        fieldMap.put(Consts.KEY_USER_MOBILE, userName);
                        fieldMap.put(Consts.KEY_PASSWORD, password);
                        HttpUtils.executeXutils(headMap, fieldMap, loginCallback);
                    }
                }.start();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                Message msg = new Message();
                msg.what = Consts.MESSAGE_LOGIN;
                msg.arg1 = 0;
                msg.obj = "登录聊天服务器失败";
                handler.sendMessage(msg);
            }
        });

    }

//    {"head":{"res_code":"0001","res_msg":"error","res_arg":"用户名不存在"},"body":"[]"}
    private ResponseResult loginCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_LOGIN;
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
// "mid":"1","nickname":"15210***","sex":"保密","avatar":"http:\/\/cwly.taskbees.cn:8083\/res\/images\/member_nopic.png","mobile":"15210656912",
// "truename":"孙婷芳","cardid":"211383198508292654","jifen":"25010"}}

//{"res_code":"0000","res_msg":"success","res_arg":"登录成功"},"body":{
// "mid":"11","nickname":"15210***","sex":"保密","avatar":"","mobile":"15210656919",
// "truename":"","cardid":"","jifen":"0","hxname":""}}
                    String body = resultObj.getString(Consts.KEY_BODY);
                    JSONObject bodyObj = new JSONObject(body);
                    User user = null;
                    if (bodyObj != null) {
                        user = new User();
                        user.setUserId(bodyObj.getString(Consts.KEY_USER_USER_MID));
                        user.setUserNickName(bodyObj.getString(Consts.KEY_USER_NICK_NAME));
                        user.setUserGender(bodyObj.getString(Consts.KEY_USER_GENDER));
                        user.setUserIconPath(bodyObj.getString(Consts.KEY_USER_ICON_PATH));
                        user.setUserPhoneNumber(bodyObj.getString(Consts.KEY_USER_MOBILE));
                        user.setUserTrueName(bodyObj.getString(Consts.KEY_USER_TRUE_NAME));
                        user.setUserCardId(bodyObj.getString(Consts.KEY_USER_CARD_ID));
                        user.setUserScore(bodyObj.getString(Consts.KEY_USER_SCORE));
                    }
                    msg.obj = user;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    public void logout(String username){
        DemoHelper.getInstance().logout(true, null);
    }
}
