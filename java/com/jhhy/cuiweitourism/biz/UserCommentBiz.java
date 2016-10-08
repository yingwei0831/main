package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.MyComment;
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
 * Created by birney on 2016-09-09.
 * 我的评论
 */
public class UserCommentBiz {
    
    private Context context;
    private Handler handler;

    //    {"head":{"code":"User_orderpjlist"},"field":{"mid":"6"}}
    private String CODE_MY_COMMENT = "User_orderpjlist";
    public void getUserComment(final String mid){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_MY_COMMENT);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                HttpUtils.executeXutils(headMap, fieldMap, userCommentCallback);
            }
        }.start();
    }

    private ResponseResult userCommentCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_USER_COMMENT;
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

//                    [{
//                        "typeid": "1",
//                        "articleid": "14",
//                        "content": "66666666666666",
//                        "addtime": "1471828891",
//                        "memberid": "1",
//                        "litpic": "http://cwly.taskbees.cn:8083/uploads/2016/0812/51a99f8bb88767b1373e0dc078970b95.jpg",
//                        "title": "海岛游",
//                        "suppliername": "易搜学",
//                        "logo": "http://cwly.taskbees.cn:8083/uploads/member/57d60b0c2f809.jpg"
//                    },{
//                        "typeid": "1",
//                        "articleid": "14",
//                        "content": "滴答滴答滴答滴答滴答滴答滴答滴答的",
//                        "addtime": null,
//                        "memberid": "1",
//                        "litpic": "http://cwly.taskbees.cn:8083/uploads/2016/0812/51a99f8bb88767b1373e0dc078970b95.jpg",
//                        "title": "海岛游",
//                        "suppliername": "易搜学",
//                        "logo": "http://cwly.taskbees.cn:8083/uploads/member/57d60b0c2f809.jpg"
//                    }]
                    List<MyComment> listComment = new ArrayList<>();
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    for (int i = 0; i < bodyAry.length(); i++){
                        MyComment myComment = new MyComment();
                        JSONObject comObj = bodyAry.getJSONObject(i);
                        myComment.setTypeId(comObj.getString("typeid"));
                        myComment.setArticleId(comObj.getString("articleid"));
                        myComment.setContent(comObj.getString("content"));
                        myComment.setAddTime(Utils.getTimeStrYMD(Long.parseLong(comObj.getString("addtime")) * 1000));
                        myComment.setMid(comObj.getString("memberid"));
                        myComment.setLitPic(comObj.getString("litpic"));
                        myComment.setTitle(comObj.getString("title"));
                        myComment.setSupplierName(comObj.getString("suppliername"));
                        myComment.setLogo(comObj.getString("logo"));
                        listComment.add(myComment);
                    }
                    msg.obj = listComment;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
