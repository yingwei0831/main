package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.KCalendar;
import com.just.sun.pricecalendar.KCalendarDate;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Calendar;
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
//    private boolean selectDay;

    private String hotelInDate; //入住酒店时间
    private String hotelOutDate; //离开酒店时间
    private long stayDays; //住宿天数

//    private int fromYear; //开始的年
//    private int fromMonth; //开始的月
//    private int fromDay; //开始的日期
//
//    private int toYear; //开始的年
//    private int toMonth; //开始的月
//    private int toDay; //开始的日期


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
                    if (yearCurrent == yearCalendar){
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

//                        fromYear = yearCalendar;
//                        fromMonth = monthCalendar;
//                        fromDay = daySelect;
                    }else if (!selectFromDay && selectEndDay){ //选择离开时间,不能选择比入住时间小的时间,28天以内
                        long diff = Utils.getDiff(hotelInDate, dateFormat);
                        LogUtil.e(TAG, "相差天数：diff = " + diff);
                        List<String> dates = new ArrayList<>();
                        if (diff <= 20 && diff > 0){
                            //本月，加下个月 将字符串转为毫秒？
                            long time = Utils.getTime(hotelInDate);
                            dates.add(hotelInDate);
                            for (int i = 1; i <= diff; i++) {
                                String laterDate = Utils.getTimeStrYMD(time + (long) i * 24 * 60 * 60 * 1000);
                                dates.add(laterDate);
                            }
                            LogUtil.e(TAG, "相差天数：dates = " + dates);
                        } else {
                            ToastCommon.toastShortShow(getApplicationContext(), null, "最长可选20天");
                            return;
                        }
//                        toYear  = yearCalendar;
//                        toMonth = monthCalendar;
//                        toDay   = daySelect;

                        stayDays = diff;
                        hotelOutDate = dateFormat;
                        calendar.setCalendarDaysBgColor(dates, ContextCompat.getColor(getApplicationContext(), R.color.colorActionBar));
                        selectEndDay = false;
                    } else if (!selectFromDay && !selectEndDay) { //重新选择,选择入住时间
                        calendar.removeAllBgColor();
                        stayDays = 0;
                        selectFromDay = true;
                        selectEndDay = true;
                        hotelInDate = dateFormat;
                        hotelOutDate = null;
                        calendar.setCalendarDayBgColor(dateFormat, ContextCompat.getColor(getApplicationContext(), R.color.colorActionBar)); //Color.parseColor("#45BDEF")
                        selectFromDay = false;
                    }
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
            case R.id.btn_hotel_calendar_confirm:
                commit();
                break;
        }
    }

    private void commit() {
        if (!selectFromDay && !selectEndDay){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("hotelInDate", hotelInDate);
            bundle.putString("hotelOutDate", hotelOutDate);
            bundle.putInt("stayDays", (int) stayDays);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择离店日期");
        }
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
