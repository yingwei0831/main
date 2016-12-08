package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.model.CustomActivity;
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
 * Created by birney1 on 16/9/26.
 */
public class UserReleaseBiz {

    private String TAG = UserReleaseBiz.class.getSimpleName();

    private Context context;
    private Handler handler;

    public UserReleaseBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }
//    {"head":{"code":"User_activitylist"},"field":{"mid":"1"}}
    private String CODE_GET_RELEASE = "User_activitylist";
    public void getUserRelease(final String mid){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_GET_RELEASE);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                HttpUtils.executeXutils(headMap, fieldMap, myReleaseCallback);
            }
        }.start();
    }

    private String CODE_USER_RELEASE = "";


    private ResponseResult myReleaseCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_GET_MY_RELEASE;
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
//                   {"id": "5",
//                    "memberid": "1",
//                    "title": "Yyyyy",
//                    "price": "2222.00",
//                    "addtime": "1473642620",
//                    "cfcity": "20",
//                    "cftime": "1476921600",
//                    "days": "12311",
//                    "num": "123",
//                    "lineinfo": "123123123",
//                    "xqinfo": "123123123",
//                    "fyinfo": "123123123",
//                    "xcinfo": "2131231212312",
//                    "ydxz": "123123123",
//                    "litpic": "http://cwly.taskbees.cn:8083/uploads/activity/861473642620.jpg,/uploads/activity/351473642620.jpg",
//                    "status": "0",
//                    "ishot": "0"},
                    List<CustomActivity> listRelase = null;
                    JSONArray collecAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    if (collecAry != null){
                        listRelase = new ArrayList<>();
                        for (int i = 0; i < collecAry.length(); i ++){
                            JSONObject actyObj = collecAry.getJSONObject(i);
                            CustomActivity actyRelase = new CustomActivity();
                            actyRelase.setId(actyObj.getString(Consts.KEY_ID));
                            actyRelase.setActyMemberId(actyObj.getString("memberid"));
                            actyRelase.setActivityTitle(actyObj.getString("title"));
                            actyRelase.setActivityPrice(actyObj.getString("price"));
                            actyRelase.setActyAddTime(Utils.getTimeStrYMD(Long.parseLong(actyObj.getString("addtime")) * 1000));
                            actyRelase.setActivityFromCity(actyObj.getString("cfcity"));
                            actyRelase.setActivityFromTime(Utils.getTimeStrYMD(Long.parseLong(actyObj.getString("cftime")) * 1000));
                            actyRelase.setActivityTripDays(actyObj.getString("days"));
                            actyRelase.setActivityGroupAccount(actyObj.getString("num"));
                            actyRelase.setActivityLineDetails(actyObj.getString("lineinfo"));
                            actyRelase.setActivityDetailDescribe(actyObj.getString("xqinfo"));
                            actyRelase.setActivityPriceDescribe(actyObj.getString("fyinfo"));
                            actyRelase.setActivityReserveNotice(actyObj.getString("ydxz"));
                            actyRelase.setActivityLitpic(actyObj.getString("litpic"));
                            actyRelase.setActyStatus(actyObj.getString("status"));
                            actyRelase.setActyIsHot(actyObj.getString("ishot"));
                            listRelase.add(actyRelase);
                        }
                    }
                    msg.obj = listRelase;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
