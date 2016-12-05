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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
     * 正则表达式:验证身份证
     */
//    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
//    isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;

    /**
     * 正则表达式:验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 获得屏幕高度
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
     * 校验身份证
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
//    public static boolean isIdCard(String idCard){
//        return Pattern.matches(REGEX_ID_CARD, idCard);
//    }
    /**
     * 校验邮箱
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
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


    public static String getDayOfStrE(int day){
        String week = null;
        switch (day){
            case 1:
                week = "周日";
                break;
            case 2:
                week ="周一";
                break;
            case 3:
                week ="周二";
                break;
            case 4:
                week ="周三";
                break;
            case 5:
                week ="周四";
                break;
            case 6:
                week ="周五";
                break;
            case 7:
                week ="周六";
                break;
        }
        return week;
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
     * 格式：yyyy-MM-dd 周六
     * @return
     */
     public static String getCurrentTimeYMDE(){
        Date date = new Date();
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd E");
        return formate.format(date);
    }

    //将分钟数转化为XX时XX分
    public static String getDuration(long minute){
        long mi = minute;
        long ho = mi / 60;
        mi = mi % 60;
        return String.format("%d时%02d分", ho, mi);
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
    public static String getTimeStrYMDE(long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINA);
        return format.format(date);
    }

    /**
     * 转为毫秒
     * @param date 2016-10-19
     * @return
     */
    public static long getTime(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date time =  format.parse(date);
            return time.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return 0;
    }

    public static long getTimeYMDH(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        try {
            Date time =  format.parse(date);
            return time.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取日期 的星期 2016-10-26 周三
     * @param day 2016-10-26
     * @return
     */
    public static String getDateStrYMDE(String day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd E");
        String dateOk = simpleDateFormat.format(nowDate);
        return dateOk;
    }

    /**
     * 获取两个日期相隔的天数
     * @param beginDateStr yyyy-MM-dd
     * @param endDateStr yyyy-MM-dd
     * @return 相差几天
     */
    public static long getDiff(String beginDateStr, String endDateStr){
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Date beginDate= null;
        Date endDate= null;
        long minute = 0;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            minute = (endDate.getTime() - beginDate.getTime()) / (24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println("相隔的天数="+day);
        return minute;
    }

    //起飞时间，到达时间
    public static String getDiffMinute(String beginDateStr, String endDateStr){
        SimpleDateFormat format = new java.text.SimpleDateFormat("HHmm");
        Date beginDate= null;
        Date endDate= null;
        long day = 0; //分钟数
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        String date1 = getTimeStr(beginDate.getTime());
//        String date2 = getTimeStr(endDate.getTime());
//        LogUtil.e(TAG, "date1 = " + date1 +", date2 = " + date2);
//        LogUtil.e(TAG, "minute = " + day);
        if (day < 0){
            day = 24 * 60 + day;
        }
        return getDuration(day);
    }

    /**
     *
     * @param beginDateStr yyyy-MM-dd HH:mm
     * @param endDateStr yyyy-MM-dd HH:mm
     * @return xx时xx分
     */
    public static String getDiffMinuteStr(String beginDateStr, String endDateStr){
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date beginDate= null;
        Date endDate= null;
        long day = 0; //分钟数
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDuration(day);
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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    /**
     * 18位身份证校验,粗略的校验
     * @author lyl
     * @param idCard
     * @return
     */
    public static boolean is18ByteIdCard(String idCard){
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$"); //粗略的校验
        Matcher matcher = pattern1.matcher(idCard);
        if(matcher.matches()){
            return true;
        }
        return false;
    }
    /**
     * 18位身份证校验,比较严格校验
     * @author lyl
     * @param idCard
     * @return
     */
    public static boolean is18ByteIdCardComplex(String idCard){
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");
        Matcher matcher = pattern1.matcher(idCard);
        int[] prefix = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        int[] suffix = new int[]{ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        if(matcher.matches()){
            Map<String, String> cityMap = initCityMap();
            if(cityMap.get(idCard.substring(0,2)) == null ){
                return false;
            }
            int idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
            for(int i=0;i<17;i++){
                idCardWiSum+=Integer.valueOf(idCard.substring(i,i+1))*prefix[i];
            }

            int idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
            String idCardLast=idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if(idCardMod==2){
                if(idCardLast.equalsIgnoreCase("x")){
                    return true;
                }else{
                    return false;
                }
            }else{
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if(idCardLast.equals(suffix[idCardMod]+"")){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    private static Map<String, String> initCityMap(){
        Map<String, String> cityMap = new HashMap<String, String>();
        cityMap.put("11", "北京");
        cityMap.put("12", "天津");
        cityMap.put("13", "河北");
        cityMap.put("14", "山西");
        cityMap.put("15", "内蒙古");

        cityMap.put("21", "辽宁");
        cityMap.put("22", "吉林");
        cityMap.put("23", "黑龙江");

        cityMap.put("31", "上海");
        cityMap.put("32", "江苏");
        cityMap.put("33", "浙江");
        cityMap.put("34", "安徽");
        cityMap.put("35", "福建");
        cityMap.put("36", "江西");
        cityMap.put("37", "山东");

        cityMap.put("41", "河南");
        cityMap.put("42", "湖北");
        cityMap.put("43", "湖南");
        cityMap.put("44", "广东");
        cityMap.put("45", "广西");
        cityMap.put("46", "海南");

        cityMap.put("50", "重庆");
        cityMap.put("51", "四川");
        cityMap.put("52", "贵州");
        cityMap.put("53", "云南");
        cityMap.put("54", "西藏");

        cityMap.put("61", "陕西");
        cityMap.put("62", "甘肃");
        cityMap.put("63", "青海");
        cityMap.put("64", "宁夏");
        cityMap.put("65", "新疆");

//          cityMap.put("71", "台湾");
//          cityMap.put("81", "香港");
//          cityMap.put("82", "澳门");
//          cityMap.put("91", "国外");
//          System.out.println(cityMap.keySet().size());
        return cityMap;
    }


}
