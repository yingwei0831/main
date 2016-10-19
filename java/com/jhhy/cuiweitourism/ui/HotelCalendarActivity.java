package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.CalendarLineBiz;
import com.jhhy.cuiweitourism.moudle.TravelDetail;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotDetailInfo;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.GroupDeadline;
import com.just.sun.pricecalendar.KCalendar;
import com.just.sun.pricecalendar.KCalendarDate;
import com.just.sun.pricecalendar.ToastCommon;

import java.sql.RowIdLifetime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HotelCalendarActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = HotelCalendarActivity.class.getSimpleName();
    private TextView tvTitleTop;
    private ImageView ivTitleLeft;

    private String date = null;// 设置默认选中的日期  格式为 “2015-09-18” 标准DATE格式

    private KCalendarDate calendar;

    private TextView tvCurrentDate; //当前的年月
    private ImageView ivLastMonth; //上个月
    private ImageView ivNextMonth; //下个月

    private Button btnReserve; //确定，返回入住时间和离店时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_calendar);
        getData();
        setupView();
        addListener();
        addCalendarListener();
    }

    private void getData() {
        //获取传过来的入住日期和离店日期，显示在日历上

    }

    private void setupView() {
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        calendar = (KCalendarDate) findViewById(R.id.calendar_date);
        tvCurrentDate = (TextView) findViewById(R.id.tv_calendar_month);
        ivLastMonth = (ImageView) findViewById(R.id.imgv_calendar_last_month);
        ivNextMonth = (ImageView) findViewById(R.id.imgv_calendar_next_month);

        btnReserve = (Button) findViewById(R.id.btn_hotel_calendar_confirm);

        tvTitleTop.setText(getString(R.string.hotel_select_date));
        //获取当前日期
        Calendar calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = (calendar.get(Calendar.MONTH) + 1);
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        //初始化，与当前日期一样
        yearCalendar = yearCurrent;
        monthCalendar = monthCurrent;

        tvCurrentDate.setText(String.format("%d年%02d月", yearCalendar, monthCalendar));
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        ivLastMonth.setOnClickListener(this);
        ivNextMonth.setOnClickListener(this);
        btnReserve.setOnClickListener(this);
    }

//    private GroupDeadline selectGroupDeadLine; //选择某天的价格日历

    private boolean selectFromDay = true; //选择入住时间
    private boolean selectEndDay = true; //选择离开时间
    private boolean selectDay;

    private String hotelInDate; //入住酒店时间
    private String hotelOutDate; //离开酒店时间

    private int fromYear; //开始的年
    private int fromMonth; //开始的月
    private int fromDay; //开始的日期

    private int toYear; //开始的年
    private int toMonth; //开始的月
    private int toDay; //开始的日期


    private void addCalendarListener() {
        //监听所选中的日期
        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {
                LogUtil.e(TAG, "---------------onCalendarClick-------------- row = " + row + ", col = " + col +", dateFormat = " + dateFormat);
                int month = Integer.parseInt(dateFormat.substring(dateFormat.indexOf("-") + 1, dateFormat.lastIndexOf("-")));
                if (calendar.getCalendarMonth() - month == 1 //跨年跳转
                        || calendar.getCalendarMonth() - month == -11) {
                    calendar.lastMonth();
                } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
                        || month - calendar.getCalendarMonth() == -11) {
                    calendar.nextMonth();
                } else {
                    //不可以选择今天之前的日期
                    int daySelect = Integer.parseInt(dateFormat.substring(dateFormat.lastIndexOf("-") + 1, dateFormat.length()));
                    if (yearCalendar == yearCalendar){
                        if (monthCurrent == monthCalendar){ //本月，切记不能选择当前日期之前的日期
                            if (dayCurrent > daySelect){
                                return;
                            }
                        }
                    }
                    if (selectFromDay && selectEndDay){ //选择入住时间
                        hotelInDate = dateFormat;
                        calendar.setCalendarDayBgColor(dateFormat, ContextCompat.getColor(getApplicationContext(), R.color.colorActionBar)); //Color.parseColor("#45BDEF")
                        selectFromDay = false;

                        fromYear = yearCalendar;
                        fromMonth = monthCalendar;
                        fromDay = daySelect;
                    }else if (!selectFromDay && selectEndDay){ //选择离开时间,不能选择比入住时间小的时间,28天以内
                        long diff = Utils.getDiff(hotelInDate, dateFormat);
                        if (diff <= 28 && diff > 0){
                            List<String> dates = new ArrayList<String>();

                        } else {
                            return;
                        }
//                        if (yearCalendar < fromYear){
//                            return;
//                        }else if (yearCalendar == fromYear){
//                            if (monthCalendar < fromMonth){
//                                return;
//                            }else{
//                                if (monthCalendar == fromMonth){ //本月
//                                    if (daySelect - fromDay > 28){
//                                        return;
//                                    }else{
//                                        int addDay = fromDay;
//                                        for (int i = 0; i <= (toDay - fromDay); i++){
//                                            String date = String.format("%d-%02d-%02d", toYear, toMonth, addDay++);
//                                            LogUtil.e(TAG, "date = " + date);
//                                            dates.add(date);
//                                        }
//                                    }
//                                }else if (monthCalendar - fromMonth == 1 && Utils.getDiff(hotelInDate, dateFormat) <= 28){ //下个月 当前日期加28天的日期，比较
//                                    long diff = Utils.getDiff(hotelInDate, dateFormat);
//                                    LogUtil.e(TAG, "相差天数 diff = " + diff);
//                                    Date date = new Date();
////                                    if (diff <= 28){
//                                        //本月，加下个月 将字符串转为毫秒？
//                                        dates.add(Utils.getTimeStrYMD(date.getTime()));
//                                        for (int i = 1; i < diff; i++){
//                                            String laterDate = Utils.getTimeStrYMD(date.getTime() + (long)(i * 24 * 60 * 60 * 1000));
//                                            dates.add(laterDate);
//                                        }
////                                    }else{
////                                        return;
////                                    }
//                                } else {
//                                    return;
//                                }
//                            }
//                        }else if (yearCalendar - fromYear == 1 && fromMonth == 12 && monthCalendar == 1 && Utils.getDiff(hotelInDate, dateFormat) <= 28){ //下一年 //只有12月份可以下一年 //只有1月份可以选择
//                            long diff = Utils.getDiff(hotelInDate, dateFormat);
//                            LogUtil.e(TAG, "相差天数 diff = " + diff);
//                            Date date = new Date();
//                            dates.add(Utils.getTimeStrYMD(date.getTime()));
//                            for (int i = 1; i < diff; i++){
//                                String laterDate = Utils.getTimeStrYMD(date.getTime() + (long)(i * 24 * 60 * 60 * 1000));
//                                dates.add(laterDate);
//                            }
//                        } else {
//                            return;
//                        }
                        toYear  = yearCalendar;
                        toMonth = monthCalendar;
                        toDay   = daySelect;
                        List<GroupDeadline> groups = calendar.getGroups();


//                        if (fromYear < toYear){ //翻过年了
//
//                        }else{ //本年
//                            if (fromMonth < toMonth){ //跨月了
//                                int addMonth = fromMonth;
//                                int addDay = fromDay;
//                                if (fromMonth == 1 || fromMonth == 3 || fromMonth == 5 || fromMonth == 7 || fromMonth == 8 || fromMonth == 10 || fromMonth == 12){ //31天
//
//                                }else if (fromMonth == 4 || fromMonth == 6 || fromMonth == 9 || fromMonth == 11){ //30天
//
//                                }else if(fromMonth == 2){ //平年28天，闰年29天
//
//                                }
//                                for (int i = 0; i <= (toMonth - fromMonth); i++){ //外层为月遍历
//                                    for (int j = 0; j <= (toDay - fromDay); j++){ //内层为日期遍历
//
//                                    }
//                                }
//                            }else{ //本月
//                                int addDay = fromDay;
//                                for (int i = 0; i <= (toDay - fromDay); i++){
//                                    String date = String.format("%d-%02d-%02d", toYear, toMonth, addDay++);
//                                    LogUtil.e(TAG, "date = " + date);
//                                    dates.add(date);
//                                }
//                            }
//                        }

                        hotelOutDate = dateFormat;
                        calendar.setCalendarDayBgColor(dateFormat, ContextCompat.getColor(getApplicationContext(), R.color.colorActionBar));
                        selectEndDay = false;
                    }else if (!selectFromDay && !selectEndDay){ //重新选择,选择入住时间
                        calendar.removeAllBgColor();
                        selectFromDay = true;
                        selectEndDay = true;
                        hotelInDate = dateFormat;
                        calendar.setCalendarDayBgColor(dateFormat, ContextCompat.getColor(getApplicationContext(), R.color.colorActionBar)); //Color.parseColor("#45BDEF")
                        selectFromDay = false;
                    }

//                    for (int i = 0; i < calendarPricesNew.size(); i++) {
//                        String positionDate = calendarPricesNew.get(i).getDate();
//                        LogUtil.e("SHF", "dateFormat--->" + dateFormat + ", date--->" + calendarPrices.get(i).getDate()); // + "peopleNumCur--->" + "stock--->" + stock);
//                        LogUtil.e("SHF", "dateFormat--->" + dateFormat + ", positionDate--->" + positionDate + ", date = " + date); // + "peopleNumCur--->" + "stock--->" + stock);
//                        if (dateFormat.equals(positionDate)) { //当前点击日期的背景色
////                                && (stock) > 0) {
//                            //设置背景色
//                            calendar.removeAllBgColor();
//                            calendar.setCalendarDayBgColor(dateFormat, ContextCompat.getColor(getApplicationContext(), R.color.colorActionBar)); //Color.parseColor("#45BDEF")
//                            selectGroupDeadLine = calendarPricesNew.get(i);
//                        } else if (date.equals(positionDate)) { //如果是选择今天的日期
////                                && (stock) > 0) {
////                            ToastCommon.toastShortShow(getApplicationContext(), null, "此团期剩余空位不足，请选择其他团期或减少参团人数");
//                        }
//                    }
                }
            }
        });
        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                //日历改变
                yearCalendar = year;
                monthCalendar = month;
                tvCurrentDate.setText(year + "年" + month + "月");
            }
        });
    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HotelCalendarActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.imgv_calendar_last_month: //上个月
                lastMonth();
                break;
            case R.id.imgv_calendar_next_month: //下个月
                nextMonth();
                break;
            case R.id.btn_price_calendar_reserve:
                commit();
                break;
        }
    }

    private void commit() {
//        if ()
    }

    private int yearCurrent; //本年
    private int monthCurrent; //本月
    private int dayCurrent; //当日

    private int yearCalendar; //日历年
    private int monthCalendar; //日历月
    private int dateCalendar; //日历日

    private void lastMonth() {
        LogUtil.e(TAG, "yearCurrent = " + yearCurrent +", monthCurrent = " + monthCurrent);
        LogUtil.e(TAG, "yearCalendar = " + yearCalendar +", monthCalendar = " + monthCalendar);
        if (yearCalendar == yearCurrent){
            if (monthCalendar > monthCurrent) {
                calendar.lastMonth();
            }
        }else if(yearCalendar > yearCurrent){
            calendar.lastMonth();
        }
    }

    private void nextMonth(){
        calendar.nextMonth();
    }

}
