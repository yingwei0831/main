package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.CustomActivity;
import com.jhhy.cuiweitourism.picture.Bimp;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.utils.BitmapUtil;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class StartActivityBiz {

    private String TAG = StartActivityBiz.class.getSimpleName();

    private Context context;
    private Handler handler;
    private List<Bitmap> upLoadBimp;
    private String CODE_PUBLISH_ACTIVITY = "Activity_publish";

    public StartActivityBiz(Context context, Handler handler, List<Bitmap> upLoadBimp){
        this.context = context;
        this.handler = handler;
        this.upLoadBimp = upLoadBimp;
    }
//{"head":{"code":"Activity_publish"},"field":{
// "mid":"1","litpic":"img","title":"撒哈拉沙漠自由行","price":"5000","cfcity":"北京","cftime":"2016-10-20","days":"5","num":"5",
// "lineinfo":"线路概述","xqinfo":"详情描述","fyinfo":"费用描述","xcinfo":"行程描述","ydxz":"预定需知"}}

    public void publishActivity(final CustomActivity activity){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_PUBLISH_ACTIVITY);

                JSONObject fieldObj = new JSONObject();
                JSONArray picArray = new JSONArray();
                for (int i = 0; i < activity.getLipPicAry().length; i++) {
                    picArray.put(BitmapUtil.bitmaptoString(upLoadBimp.get(i)));
                }

                try {
                    fieldObj.put(Consts.KEY_USER_USER_MID, MainActivity.user.getUserId());
                    fieldObj.put(Consts.PIC_PATH_LITPIC, picArray);
//                LogUtil.e(TAG, "网传图片，litpic = " + fieldObj.getJSONArray(Consts.PIC_PATH_LITPIC));
                    fieldObj.put(Consts.KEY_TITLE, activity.getActivityTitle());
                    fieldObj.put(Consts.KEY_PRICE, activity.getActivityPrice());
                    fieldObj.put("cfcity", activity.getActivityFromCity());
                    fieldObj.put("cftime", activity.getActivityFromTime());
                    fieldObj.put("days", activity.getActivityTripDays());
                    fieldObj.put("num", activity.getActivityGroupAccount());
                    fieldObj.put("lineinfo", activity.getActivityLineDetails());
                    fieldObj.put("xqinfo", activity.getActivityDetailDescribe());
                    fieldObj.put("fyinfo", activity.getActivityPriceDescribe());
                    fieldObj.put("xcinfo", activity.getActivityTripDescribe());
                    fieldObj.put("ydxz", activity.getActivityReserveNotice());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                org.xutils.http.RequestParams param = new org.xutils.http.RequestParams(Consts.SERVER_URL);
                param.addHeader("Content-Type", "application/json;charset=utf-8");
                JSONObject json = new JSONObject();

                try {
                    json.put(Consts.KEY_HEAD, setFieldParams(headMap));
//                    json.put(Consts.KEY_FIELD, setFieldParams(fieldMap));
                    json.put(Consts.KEY_FIELD, fieldObj);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String entityString = json.toString();
                param.setBodyContent(entityString);
                LogUtil.e(TAG, "发送数据：" + param.getBodyContent());
                x.http().post(param, publishActivityCallback);
            }
        }.start();
    }

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

    private ResponseResult publishActivityCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_START_ACTIVITY;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
//                {"head":{"res_code":"0001","res_msg":"error","res_arg":"创建图像失败"},"body":"[]"}
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

}

