package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.UserMessage;
import com.jhhy.cuiweitourism.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by birney on 2016-09-08.
 * 我的消息
 */
public class UserMessageBiz {
    private Context context;
    private Handler handler;
    private String CODE_USER_MESSAGE = "User_mynews";

    public UserMessageBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

//{"head":{"code":"User_mynews"},"field":{"mid":"1"}}
    public void getUserMessage(final String mid){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_USER_MESSAGE);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                HttpUtils.executeXutils(headMap, fieldMap, userMessageCallback);
            }
        }.start();
    }

    private ResponseResult userMessageCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_USER_MESSAGE;
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
//{"title":"撒哈拉沙漠","id":"1","memberid":"1",
// "litpic":"\/uploads\/2015\/0909\/6576cb3bfd961a0e13e6cc203b4ce012.jpg","pl":null}]}
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<UserMessage> listMessage = new ArrayList<>();
                    if(bodyAry != null) {
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject msgObj = (JSONObject) bodyAry.get(i);
                            UserMessage userMessage = new UserMessage();
                            userMessage.setMsgId(msgObj.getString(Consts.KEY_ID));
                            userMessage.setMsgTitle(msgObj.getString(Consts.KEY_TITLE));
                            userMessage.setMsgMemberId(msgObj.getString(Consts.MSG_MEMBER_ID));
                            userMessage.setMsgPicPath(msgObj.getString(Consts.PIC_PATH_LITPIC));
                            //TODO 此处是评论，是数组还是一串字符串
                            userMessage.setMsgPl(msgObj.getString(Consts.MSG_COMMENT));

                            listMessage.add(userMessage);
                        }
                    }
                    msg.obj = listMessage;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
