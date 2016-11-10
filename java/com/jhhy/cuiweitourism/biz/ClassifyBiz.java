package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.ClassifyArea;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/8.
 */
public class ClassifyBiz {

    private String TAG = "ClassifyBiz";

    private Context context;
    private Handler handler;
    private String CODE_CLASSIFY = "Publics_classify";
    private int requestType;

    public ClassifyBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    //{"head":{"code":"Publics_classify"},"field":{"type":"1"}} //type:1国内、2出境、102周边
    public void getAreaList(final String type, int requestType) {
        this.requestType = requestType;
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_CLASSIFY);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_TYPE, type);
                    HttpUtils.executeXutils(headMap, fieldMap, classifyCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    public void getAreaListNearBy(final String type, int requestType) {
        this.requestType = requestType;
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_CLASSIFY);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_TYPE, type);
                    HttpUtils.executeXutils(headMap, fieldMap, nearByCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult nearByCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {

            Message msg = new Message();
            msg.what = requestType;
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
                    List<ClassifyArea> listArea = null;
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<String> areaNameList = new ArrayList<>(); //左侧地区名字列表
                    if (bodyAry != null) {
                        listArea = new ArrayList<>(); //所有地区数据集合
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject areaObj = (JSONObject) bodyAry.get(i);
                            ClassifyArea area = new ClassifyArea();
                            area.setAreaId(areaObj.getString(Consts.KEY_ID));
                            area.setAreaName(areaObj.getString(Consts.KEY_KIND_NAME)); //左侧地区名字
                            areaNameList.add(areaObj.getString(Consts.KEY_KIND_NAME));
                            area.setAreaPic(areaObj.getString(Consts.PIC_PATH_LITPIC));
//                            JSONArray provinceAry = areaObj.getJSONArray(Consts.KEY_SON);
//                            List<ClassifyProvince> listProvince = null; //右侧省市列表
//                            if(provinceAry != null){
//                                listProvince = new ArrayList<>();
//                                for (int j = 0; j < provinceAry.length(); j++){
//                                    JSONObject provinceObj = (JSONObject) provinceAry.get(j);
//                                    ClassifyProvince province = new ClassifyProvince();
//                                    province.setProvinceId(provinceObj.getString(Consts.KEY_ID));
//                                    province.setProvinceName(provinceObj.getString(Consts.KEY_KIND_NAME)); //右侧城市名字
//                                    province.setProvincePicPath(provinceObj.getString(Consts.PIC_PATH_LITPIC)); //暂时注释，数据不全
//                                    listProvince.add(province);
//                                }
//                                area.setListProvince(listProvince);
//                            }
                            listArea.add(area);
                        }
                    }
                    List[] array = new List[2];
                    array[0] = listArea;
                    array[1] = areaNameList;
                    msg.obj = array;
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


    private ResponseResult classifyCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {

            Message msg = new Message();
            msg.what = requestType;
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
                    List<ClassifyArea> listArea = null;
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<String> areaNameList = new ArrayList<>(); //左侧地区名字列表
                    if (bodyAry != null) {
                        listArea = new ArrayList<>(); //所有地区数据集合
                        for (int i = 0; i < bodyAry.length(); i++) {
                            LogUtil.e(TAG, bodyAry.length()+"——>大区数据个数 i = " + i);
                            JSONObject areaObj = (JSONObject) bodyAry.get(i);
                            ClassifyArea area = new ClassifyArea();
                            area.setAreaId(areaObj.getString(Consts.KEY_ID));
                            area.setAreaName(areaObj.getString(Consts.KEY_KIND_NAME)); //左侧地区名字
                            areaNameList.add(areaObj.getString(Consts.KEY_KIND_NAME));
                            LogUtil.e(TAG, "left name = " + areaObj.getString(Consts.KEY_KIND_NAME));
                            JSONArray provinceAry = areaObj.getJSONArray(Consts.KEY_SON);
                            List<ClassifyArea> listProvince = null; //右侧省市列表
                            if (provinceAry != null) {
                                listProvince = new ArrayList<>();
                                for (int j = 0; j < provinceAry.length(); j++) {
                                    LogUtil.e(TAG, provinceAry.length()+"——>省市数据个数 j = " + j);
                                    JSONObject provinceObj = provinceAry.getJSONObject(j);
//                                    LogUtil.e(TAG, provinceObj.toString());
                                    ClassifyArea province = new ClassifyArea();
                                    province.setAreaId(provinceObj.getString(Consts.KEY_ID));
                                    province.setAreaName(provinceObj.getString(Consts.KEY_KIND_NAME)); //右侧城市名字
//                                    province.setAreaPic(provinceObj.getString(Consts.PIC_PATH_LITPIC)); //暂时注释，数据不全
                                    listProvince.add(province);
                                }
                                area.setListProvince(listProvince);
                            }
                            listArea.add(area);
                            LogUtil.e(TAG, "right listAreaItem = " + area);
                        }
                    }
                    List[] array = new List[2];
                    array[0] = listArea;
                    array[1] = areaNameList;
                    msg.obj = array;
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
