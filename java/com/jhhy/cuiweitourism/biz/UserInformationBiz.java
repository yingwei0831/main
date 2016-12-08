package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.model.UserIcon;
import com.jhhy.cuiweitourism.utils.BitmapUtil;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/21.
 * 我的旅游币
 */
public class UserInformationBiz {

    private Context context;
    private Handler handler;
    private String CODE_GET_COIN = "User_lymoney";

    public UserInformationBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    //    {"head":{"code":"User_lymoney"},"field":{"mid":"1"}}

    /**
     * 获取旅游币
     */
    public void getUserCoin(final String mid){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_GET_COIN);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                HttpUtils.executeXutils(headMap, fieldMap, getCoinCallback);
            }
        }.start();
    }
//    {"head":{"code":"User_face"},"field":{"avatar":"img","mid":"1"}}
    private String CODE_MODIFY_ICON = "User_face";
    public void modifyUserIcon(final String mid, final Bitmap avatar){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_MODIFY_ICON);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                fieldMap.put(Consts.KEY_USER_ICON_PATH, BitmapUtil.bitmaptoString(avatar));
                HttpUtils.executeXutils(headMap, fieldMap, modifyUserIconCallback);
            }
        }.start();
    }

//    {"head":{"code":"User_edit"},"field":{"mid":"1","nickname":"小蜗","sex":"男"}}
    private String CODE_MODIFY_INFO = "User_edit";
    public void modifyUserInfo(final String mid, final String nickName, final String gender){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_MODIFY_INFO);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                fieldMap.put(Consts.KEY_USER_NICK_NAME, nickName);
                fieldMap.put(Consts.KEY_USER_GENDER, gender);
                HttpUtils.executeXutils(headMap, fieldMap, modifyInfoCallback);
            }
        }.start();
    }

//    {"head":{"code":"User_edit"},"field":{"mid":"1","mobile":"15210656911","verify":"cwly"}}
    private String CODE_MODIFY_MOBILE = "User_edit";
    public void modifyUserMobile(final String mid, final String mobile, final String verify){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_MODIFY_MOBILE);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                fieldMap.put(Consts.KEY_USER_MOBILE, mobile);
                fieldMap.put(Consts.KEY_CHECK_CODE, verify);
                HttpUtils.executeXutils(headMap, fieldMap, modifyMobileCallback);
            }
        }.start();
    }

    //    {"head":{"code":"User_repwd"},"field":{"mid":"1","newpwd":"admin789","oldpwd":"admin123"}}
    private String CODE_MODIFY_PASSWORD = "User_repwd";
    public void modifyUserPassword(final String mid, final String newPwd, final String oldPwd){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_MODIFY_PASSWORD);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                fieldMap.put("newpwd", newPwd);
                fieldMap.put("oldpwd", oldPwd);
                HttpUtils.executeXutils(headMap, fieldMap, modifyPwdCallback);
            }
        }.start();
    }

//{"head":{"code":"User_approve"},"field":{
// "mid":"1","truename":"翠微","cardid":"211382198608262687","trueavatar":"img","cardimg1":"img",
// "cardimg2":"img","remarks":"I'm a programmer","other":"img"}}
//    trueavatar（真实头像）、cardimg1（身份证正面）、cardimg2 （身份证反面）、remarks（备注）、other（其他
    private String CODE_CERTIFACITION = "User_approve";

    public void userAuthentication(final String mid, final String trueName, final String cardID, final Bitmap trueAvatar,
                                    final Bitmap cardimgPositive, final Bitmap cardimgOpposite, final String remarks, final Bitmap other){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_CERTIFACITION);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                fieldMap.put("truename", trueName);
                fieldMap.put("cardid", cardID);
                fieldMap.put("trueavatar", BitmapUtil.bitmaptoString(trueAvatar));
                fieldMap.put("cardimg1", BitmapUtil.bitmaptoString(cardimgPositive));
                fieldMap.put("cardimg2", BitmapUtil.bitmaptoString(cardimgOpposite));
                fieldMap.put("remarks", remarks);
                if (other != null){
                    fieldMap.put("other", BitmapUtil.bitmaptoString(other));
                }
                HttpUtils.executeXutils(headMap, fieldMap, authenticationCallback);
            }
        }.start();
    }

    private ResponseResult authenticationCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_MODIFY_USER_AUTHENTICATION;
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
//                    {"head":{"res_code":"0000","res_msg":"success","res_arg":"成功"},"body":"[]"}
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult modifyPwdCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_MODIFY_USER_PASSWORD;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"修改成功"},"body":"[]"}
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
    };

    private ResponseResult modifyMobileCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_MODIFY_USER_MOBILE;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
//                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
// {"head":{"res_code":"0000","res_msg":"环信修改失败BUT本地修改成功","res_arg":"[]"},"body":"[]"}
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
                }
                msg.obj = resMsg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult modifyInfoCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_MODIFY_USER_INFO;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
//                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
// {"head":{"res_code":"0000","res_msg":"环信修改失败BUT本地修改成功","res_arg":"[]"},"body":"[]"}
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
                }
                msg.obj = resMsg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult modifyUserIconCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_MODIFY_USER_ICON;
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
// {"head":{"res_code":"0000","res_msg":"success","res_arg":"上传成功"},
// "body":{"avatar":"http:\/\/cwly.taskbees.cn:8083\/uploads\/member\/111474787153.jpg"}}
                    msg.arg1 = 1;
                    JSONObject body = resultObj.getJSONObject(Consts.KEY_BODY);
                    String avatarPath = body.getString(Consts.KEY_USER_ICON_PATH);
                    msg.obj = avatarPath;
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
            Message msg = new Message();

            handler.sendMessage(msg);
        }
    };

    private ResponseResult getCoinCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.REQUEST_CODE_GET_ICONS;
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
//                    "0": [
//                    {
//                        "id": "6",
//                        "memberid": "1",
//                        "content": "注册赠送旅游币",
//                        "addtime": "1469178070",
//                        "type": "2",
//                        "jifen": "2"
//                    }
//                    ],
//                    "lvb": "24010"
                    JSONObject bodyObj = resultObj.getJSONObject(Consts.KEY_BODY);
                    JSONArray recordAry = bodyObj.getJSONArray("0");
                    List<UserIcon> listIcon = new ArrayList<>();
                    if (recordAry.length() > 0){
                        for(int i = 0; i < recordAry.length(); i++){
                            JSONObject iconObj = recordAry.getJSONObject(i);
                            UserIcon icon = new UserIcon();
                            icon.setId(iconObj.getString(Consts.KEY_ID));
                            icon.setMemberId(iconObj.getString("memberid"));
                            icon.setContent(iconObj.getString("content"));
                            icon.setAddtime(Utils.getTimeStrYMD(Long.parseLong(iconObj.getString("addtime")) * 1000));
                            icon.setType(iconObj.getString("type"));
                            icon.setScore(iconObj.getString("jifen"));
                            listIcon.add(icon);
                        }
                    }
                    String iconNum = bodyObj.getString("lvb");
                    msg.obj = listIcon;
                    msg.arg2 = Integer.parseInt(iconNum);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

}
