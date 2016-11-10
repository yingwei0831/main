package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.Collection;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by birney1 on 16/9/25.
 */
public class UserCollectionBiz {

    private String TAG = UserCollectionBiz.class.getSimpleName();

    private Context context;
    private Handler handler;

    public UserCollectionBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    //    {"head":{"code":"User_collect"},"field":{"mid":"6"}}
    private String CODE_USER_COLLECTION = "User_collect";

    public void getMyCollection(final String mid){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_USER_COLLECTION);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                HttpUtils.executeXutils(headMap, fieldMap, myCollectionCallback);
            }
        }.start();
    }

    //收藏
//    {"head":{"code":"Publics_shoucang"},"field":{"memberid":"6","typeid":"1","productaid":"9"}}
//    typeid 1.线路、2.酒店、3租车、8签证、14私人定制；
    private String CODE_COLLECTION = "Publics_shoucang";
    public void doCollection(final String mid, final String typeId, final String productId){
        if (NetworkUtil.checkNetwork(context)) {
            Map<String, Object> headMap = new HashMap<>();
            headMap.put(Consts.KEY_CODE, CODE_COLLECTION);
            Map<String, Object> fieldMap = new HashMap<>();
            fieldMap.put("memberid", mid);
            fieldMap.put("typeid", typeId);
            fieldMap.put("productaid", productId);
            HttpUtils.executeXutils(headMap, fieldMap, doCollectionCallback);
        }else{
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult doCollectionCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_DO_COLLECTION;
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
// {"head":{"res_code":"0000","res_msg":"success","res_arg":"收藏成功"},"body":"[]"}
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult myCollectionCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_GET_MY_COLLECTION;
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
//    {"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":
// [{"id":"7","memberid":"6","typeid":"1","productaid":"13","addtime":"1473318267","title":"这是测试的线路这是测试的线路这是测试的线路",
// "litpic":"http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0607\/f1a7e360b659d4dbf6f40e133ffe5d20.jpg","price":"3500"}]}
                    List<Collection> listCollection = new ArrayList<>();
                    JSONArray collecAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    if (collecAry != null){
                        for (int i = 0; i < collecAry.length(); i ++){
                            JSONObject colObj = collecAry.getJSONObject(i);
                            Collection collection = new Collection();
                            collection.setColId(colObj.getString(Consts.KEY_ID));
                            collection.setColMemberId(colObj.getString("memberid"));
                            collection.setColTypeId(colObj.getString("typeid"));
                            collection.setColProductId(colObj.getString("productaid"));
                            collection.setColAddTime(Utils.getTimeStrYMD(Long.parseLong(colObj.getString("addtime")) * 1000));
                            collection.setColTitle(colObj.getString("title"));
                            collection.setColLitpic(colObj.getString("litpic"));
                            collection.setColPrice(colObj.getString("price"));
                            listCollection.add(collection);
                        }
                    }
                    LogUtil.e(TAG, "listCollection.size = " + listCollection.size());
                    msg.obj = listCollection;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
