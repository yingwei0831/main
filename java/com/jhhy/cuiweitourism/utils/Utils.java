/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jhhy.cuiweitourism.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
@SuppressLint("SimpleDateFormat")
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }



    /**
     * 让键盘隐藏
     */
    public static void setKeyboardInvisible(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获得状态栏/通知栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
    // 去除导航栏：在onCraete（）方法中的setContentView（）；的之前调用下面这句代码
    // requestWindowFeature(Window.FEATURE_NO_TITLE);
    // 去除状态栏/通知栏：在onCraete（）方法中的setContentView（）；的之前调用下面这句代码
    // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    /**
     * 获得导航栏高度
     * @param activity
     * @return
     */
    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    public static String getDayOfStr(int day){
        String week = null;
        switch (day){
            case 1:
                week = "星期日";
                break;
            case 2:
                week ="星期一";
                break;
            case 3:
                week ="星期二";
                break;
            case 4:
                week ="星期三";
                break;
            case 5:
                week ="星期四";
                break;
            case 6:
                week ="星期五";
                break;
            case 7:
                week ="星期六";
                break;

        }
        return week;
    }

    /**
     * 检查密码串的合法性
     * @param password
     * @return
     */
    public static boolean checkPassword(String password){
        String arg = "^[0-9A-Za-z]{6,18}$";//密码:字母数字混合表达式
        Pattern pattern = Pattern.compile(arg);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 验证Email
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 获取double值的字符串格式
     * 米、千米
     * @param doubleData
     * @return
     */
    public static String getStr(double doubleData){
        StringBuffer str = new StringBuffer();
//        LogUtil.i(TAG, "值："+(doubleData/1000)+"");
        String dateStr = Double.toString(doubleData);
        int index = dateStr.indexOf(".");
        if(doubleData / 1000 < 1){
            if(doubleData / 1000 == 0){
                return str.append(0).append("m").toString();
            }
            str.append(dateStr.substring(0, index + 3)).append("m");
        }else{
            double result = doubleData / 1000;
            String resultStr = Double.toString(result);
            index = resultStr.indexOf(".");
            str.append(resultStr.substring(0, index + 3)).append("km");
        }
        return str.toString();
    }

    /**
     * 获取当前时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formate.format(date);
    }
    public static String getCurrentTimeYMD(){
        Date date = new Date();
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return formate.format(date);
    }
    /**
     * 时间的格式化：yyyy-MM-dd HH:mm:ss
     * @param time 时间毫秒值
     * @return 格式化后的时间字符串
     */
    public static String getTimeStr(long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(date);
    }

    /**
     * @param time 毫秒
     * @return
     */
    public static String getTimeStrYMD(long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }

    /**
     * 获取现在时间的短时间格式
     * @return 返回短时间格式 yyyy-MM-dd
     */
    public static String getNowDateShort() { //给定的毫秒值
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        LogUtil.e(TAG, "dateStringCurrent = " + dateString);
//        ParsePosition pos = new ParsePosition(8);

//        Date currentTime_2 = formatter.parse(dateString, pos); //当天日期

        //给定的日期 >= 当前日期
//        Date dateOther2 = new Date(dateOther);
//        String dateString2 = formatter.format(dateOther2);
//        LogUtil.e(TAG, "dateString = " + dateString2);
//        Date date2 = formatter.parse(dateString2, pos); //给定的日期
//        if (currentTime_2.getTime() <= date2.getTime()){
//            LogUtil.e(TAG, "true!");
//            return true;
//        }
//        return false;
        return dateString;
    }


    public static String getTimeFormatHMS(long time){
        StringBuffer sb = new StringBuffer();
        //当前时间-发布时间=耗时
//        long currentTime = System.currentTimeMillis() / 1000;
//        long timeConsuming = currentTime - time;

        long second = time % 60;
        long minute = time / 60 % 60;
        long hour = time / 60 / 60;
        if(hour < 10){
            if(hour != 0){
                sb.append(hour).append(":");
            }
        }else{
            sb.append(hour).append(":");
        }
        if(minute < 10){
            sb.append("0").append(minute).append(":");
        }else{
            sb.append(minute).append(":");
        }
        if(second < 10){
            sb.append("0").append(second);
        }else{
            sb.append(second);
        }
//        LogUtil.i(TAG, "时间："+sb.toString());
        return sb.toString();
    }

    /**
     * 打电话
     * @param mobile
     */
    public static void contact(Context context, String mobile) {
        if(mobile != null && mobile.length() != 0){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:"+mobile));
            context.startActivity(intent);
        }
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * @param t  时间戳
     * @return
     */
    public static String getStandardDate(long t) {
        StringBuffer sb = new StringBuffer();

        long time = (System.currentTimeMillis())/1000 - t;
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

        long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

        long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前
//        LogUtil.i(TAG, "time = "+time+", mill = " + mill +", minute = "+minute +", hour = "+hour+", day = "+day);
        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
//        LogUtil.i(TAG, sb.toString());
        return sb.toString();
    }


    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    /*public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date = format.parse("2013-11-11 18:35:35");
        System.out.println(format(date));
    }*/

    public static String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }


    private void showShare(Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }
}
