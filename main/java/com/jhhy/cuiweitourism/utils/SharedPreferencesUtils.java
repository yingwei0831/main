package com.jhhy.cuiweitourism.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by jiahe008 on 2016/4/7.
 */
public class SharedPreferencesUtils {

    private static SharedPreferencesUtils instance;
    private SharedPreferences sp;
    private Context context;

    /**
     * 默认无参构造方法
     */
    private SharedPreferencesUtils(){}

    /**
     * 构造方法
     * @param context
     */
    private SharedPreferencesUtils(Context context){
        sp = context.getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    /**
     * 单例模式，保证唯一性
     * @param context
     * @return
     */
    public static SharedPreferencesUtils getInstance(Context context){
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtils(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取用户是否第一次登录APP
     * @return
     */
    public boolean getFirstIn(){
        return sp.getBoolean(context.getPackageName(), true);
    }

    /**
     * 用户第一次登录APP，保存状态
     * @param firstIn 第一次登录状态
     * @return 是否保存成功
     */
    public boolean setFirstIn(boolean firstIn){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(context.getPackageName(), firstIn);
        return editor.commit();
    }

    /**
     * 获取用户是否有登录APP时保存的userId
     * @return 用户的userid
     */
//    public String getUserId(){return sp.getString(Consts.KEY_USER_USER_ID, null);}

    /**
     * 用户登录APP成功，保存userId
     * @param userId 用户在服务器注册返回的userid
     * @return 是否保存成功
     */
//    public boolean saveUserIdIn(String userId){
//        LogUtil.i("SharedPreferencesUtils", "===saveUserIdIn===");
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(Consts.KEY_USER_USER_ID, userId);
//        return editor.commit();
//    }

    /**
     * 保存登录方式
     * @param type：0表示本平台登录，1表示第三方登录,-1表示已退出登录
     * @return
     */
//    public boolean saveUserTypeIn(int type){
//        LogUtil.i("SharedPreferencesUtils", "===saveUserTypeIn===");
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt(Consts.KEY_USER_USER_TYPE, type);
//        return editor.commit();
//    }
//    public int getUserTypeIn(){
//        return sp.getInt(Consts.KEY_USER_USER_TYPE, -1);
//    }

    /**
     * 用户登出APP，置userId值为空
     * @return 是否保存成功
     */
//    public boolean setUserIdOut(){
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(Consts.KEY_USER_USER_ID, null);
//        return editor.commit();
//    }

    /**
     * 获取用户最后一次更新的位置信息
     * @return
     */
//    public String[] getLocation(){
//        String latitude = sp.getString(Consts.KEY_LATITUDE, Double.toString(Constants.BEIJING.latitude));
//        String longitude = sp.getString(Consts.KEY_LONGITUDE, Double.toString(Constants.BEIJING.longitude));
//        return new String[]{latitude, longitude};
//    }

    /**
     * 保存用户最后一次定位地址
     * @param latitude 经度
     * @param longitude 纬度
     * @return 保存是否成功
     */
//    public boolean saveLocation(String latitude, String longitude){
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(Consts.KEY_LATITUDE, latitude);
//        editor.putString(Consts.KEY_LONGITUDE, longitude);
//        return editor.commit();
//    }
    /**
     * 保存登录手机号和密码
     * @param telephoneNumber 手机号
     * @param password 密码
     * @return 保存是否成功
     */
    public boolean saveNamePassword(String telephoneNumber, String password){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Consts.SP_LOGIN_NAME, telephoneNumber);
        editor.putString(Consts.SP_LOGIN_PASSWORD, password);
        return editor.commit();
    }

    /**
     * 修改密码后，保存密码
     * @param password 密码
     * @return 保存是否成功
     */
    public boolean savePassword(String password){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Consts.SP_LOGIN_PASSWORD, password);
        return editor.commit();
    }
    /**
     * 获取登录密码
     * @return 用户密码
     */
    public String getPassword(){
        return sp.getString(Consts.SP_LOGIN_PASSWORD, null);
    }

    /**
     * 获取用户手机号
     * @return 用户手机号
     */
    public String getTelephoneNumber(){
        return sp.getString(Consts.SP_LOGIN_NAME, null);
    }
    public boolean saveTelephoneNumber(String mobile){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Consts.SP_LOGIN_NAME, mobile);
        return editor.commit();
    }

    /**
     * 是否自动登录
     * @param autoLogin 是否自动登录
     * @return 保存是否成功
     */
//    public boolean setAutoLogin(boolean autoLogin){
//        SharedPreferences.Editor editor = sp.edit();
//        return editor.commit();
//    }

    /**
     * 保存用户最后一次触摸此APP的时间
     * @param currentTime 当前时间毫秒值
     * @return 是否保存成功
     */
//    public boolean saveCurrentTime(long currentTime){
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putLong(Consts.SP_LOGIN_TIME, currentTime);
//        return editor.commit();
//    }


    /**
     * 保存用户是否接收通知
     * @param status 是否自动接收通知
     * @return 自动接收通知状态
     */
//    public boolean saveJPushStatus(boolean status){
//        SharedPreferences.Editor et = sp.edit();
//        et.putBoolean(Consts.SP_JPUSH_STATUS + MainActivity.currentUser.getUserPhoneNumber(), status);
//        return et.commit();
//    }
//
//    public boolean getJPushStatus(){
//        return sp.getBoolean(Consts.SP_JPUSH_STATUS + MainActivity.currentUser.getUserPhoneNumber(), true);
//    }


}
