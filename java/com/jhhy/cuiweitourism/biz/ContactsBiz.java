package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/20.
 */
public class ContactsBiz {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private Handler handler;

    private String CODE_GET_CONTACTS = "User_contacts";

    public ContactsBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    //    {"head":{"code":"User_contacts"},"field":{"mid":"1"}}
    public void getContacts(final String mid) {
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_GET_CONTACTS);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_USER_USER_MID, mid);
                    HttpUtils.executeXutils(headMap, fieldMap, getContactsCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult getContactsCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_GET_CONTACTS;
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
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[
// {"id":"19","memberid":"1","linkman":"姓名","mobile":"15933424245","idcard":"身份证号","cardtype":"证件类型","sex":"0","passport":护照号},
// {"id":"20","memberid":"1","linkman":"李小妹","mobile":"15252525425","idcard":"图的","cardtype":"身份证","sex":"0","passport":null}]}
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<UserContacts> listContacts = null;
                    if (bodyAry != null && bodyAry.length() != 0) {
                        listContacts = new ArrayList<>();
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject contactsObj = bodyAry.getJSONObject(i);
                            UserContacts contacts = new UserContacts();
                            contacts.setContactsId(contactsObj.getString(Consts.KEY_ID));
                            contacts.setContactsMemberId(contactsObj.getString("memberid"));
                            contacts.setContactsName(contactsObj.getString("linkman"));
                            contacts.setContactsMobile(contactsObj.getString(Consts.KEY_USER_MOBILE));
                            contacts.setContactsIdCard(contactsObj.getString("idcard"));
                            contacts.setContactsPassport(contactsObj.getString("passport"));
                            listContacts.add(contacts);
                        }
                    }
                    msg.obj = listContacts;
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

    private String CODE_SAVE_CONTACTS = "User_contactsadd";

    //    {"head":{"code":"User_contactsadd"},"field":{"mid":"1","linkman":"张三","mobile":"13865826547","idcard":"211382198908205489","passport":"BJ123456"}}
    public void saveContacts(final UserContacts contacts) {
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_SAVE_CONTACTS);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_USER_USER_MID, contacts.getContactsMemberId());
                    fieldMap.put("linkman", contacts.getContactsName());
                    fieldMap.put(Consts.KEY_USER_MOBILE, contacts.getContactsMobile());
                    fieldMap.put("idcard", contacts.getContactsIdCard());
                    String pass = contacts.getContactsPassport();
                    if (pass != null) {
                        fieldMap.put("passport", pass);
                    }
                    HttpUtils.executeXutils(headMap, fieldMap, addContactsCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult addContactsCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_ADD_CONTACTS;
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
//                    {"head":{"res_code":"0000","res_msg":"success","res_arg":"添加成功"},"body":"[]"}
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
            if (ex instanceof SocketTimeoutException) {
                handler.sendEmptyMessage(Consts.NET_ERROR_SOCKET_TIMEOUT);
            }
        }
    };

//"head":{"code":"User_contactseditdo"},"field":{
// "id":"1","linkman":"张三","mobile":"13865826547","idcard":"211382198908205489","passport":"BJ123456"}}

    private String CODE_MODIFY_CONTACTS = "User_contactseditdo";

    public void modifyContacts(final UserContacts contacts) {
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_MODIFY_CONTACTS);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_ID, contacts.getContactsId());
                    fieldMap.put("linkman", contacts.getContactsName());
                    fieldMap.put(Consts.KEY_USER_MOBILE, contacts.getContactsMobile());
                    fieldMap.put("idcard", contacts.getContactsIdCard());
                    String pass = contacts.getContactsPassport();
                    if (pass != null) {
                        fieldMap.put("passport", pass);
                    }
                    HttpUtils.executeXutils(headMap, fieldMap, modifyContactsCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult modifyContactsCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_MODIFY_CONTACTS;
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
//                    {"head":{"res_code":"0000","res_msg":"success","res_arg":"添加成功"},"body":"[]"}
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
            if (ex instanceof SocketTimeoutException) {
                handler.sendEmptyMessage(Consts.NET_ERROR_SOCKET_TIMEOUT);
            }
        }
    };

    //    {"head":{"code":"User_contactsdel"},"field":{"id":"1"}}
    private String CODE_DELETE_CONTACTS = "User_contactsdel";

    public void deleteContacts(final String id) {
        if (NetworkUtil.checkNetwork(context)) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put(Consts.KEY_CODE, CODE_DELETE_CONTACTS);
                    Map<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put(Consts.KEY_ID, id);
                    HttpUtils.executeXutils(headMap, fieldMap, deleteContactsCallback);
                }
            }.start();
        } else {
            handler.sendEmptyMessage(Consts.NET_ERROR);
        }
    }

    private ResponseResult deleteContactsCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_DELETE_CONTACTS;
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
// {"head":{"res_code":"0001","res_msg":"error","res_arg":"删除失败"},"body":"[]"}
// {"head":{"res_code":"0000","res_msg":"success","res_arg":"删除成功"},"body":"[]"}
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
            if (ex instanceof SocketTimeoutException) {
                handler.sendEmptyMessage(Consts.NET_ERROR_SOCKET_TIMEOUT);
            }
        }
    };
}
