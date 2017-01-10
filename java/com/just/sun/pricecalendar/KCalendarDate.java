package com.just.sun.pricecalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jiahe008 on 2016/10/19.
 */
public class KCalendarDate extends KCalendar {
    public KCalendarDate(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KCalendarDate(Context context) {
        super(context);
    }



    /**
     * 下一月日历
     */
    @Override
    public synchronized void nextMonth() {
//        super.nextMonth();

        if (groups != null) {
            int thisYear = getCalendarYear();
            int thisMonth = getCalendarMonth();
            //int thisDay = getCalendarDay()
            List<GroupDate> Months = new ArrayList<GroupDate>();
            if(groups.size() == 0){
                for(int i = 1; i <= 8; i++){ //8个月的
                    GroupDeadline currentLine = new GroupDeadline();
                    String dataString = String.format("%d-%02d-%02d",thisYear,i,1);
                    currentLine.setDate(dataString);
                    currentLine.setStock("-1");
                    groups.add(currentLine);
                }

            }
            //首先找出这一年及以后的月份
            for (int i = 0; i < groups.size(); i++) {
                GroupDeadline g = groups.get(i);
                if (thisYear <= getYear(g.getDate())) {
                    if (/*g.getState() == 1 && */Integer.parseInt(g.getStock()) >= -1) {
                        int year = getYear(g.getDate());
                        int month = getMonth(g.getDate());
                        Months.add(new GroupDate(year, month));
                    }
                }
            }
            //如果存在
            if (Months.size() > 0) {
                if (Months.get(Months.size() - 1).getYear() >= thisYear ||
                        Months.get(Months.size() - 1).getMonth() >= thisMonth) { //大于本月或者是大于本年

                    // 改变日历上下顺序
                    if (currentCalendar == firstCalendar) {
                        currentCalendar = secondCalendar;
                    } else {
                        currentCalendar = firstCalendar;
                    }
                    // 设置动画
                    setInAnimation(push_left_in);
                    setOutAnimation(push_left_out);
                    // 改变日历日期
                    if (calendarMonth == Calendar.DECEMBER) {
                        calendarYear++;
                        calendarMonth = Calendar.JANUARY;
                        groups.clear();
                    } else {
                        calendarMonth++;
                    }
                    calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
                    // 填充日历
                    setCalendarDate();
                    // 下翻到下一月
                    showNext();
                    // 回调
                    if (onCalendarDateChangedListener != null) {
                        onCalendarDateChangedListener.onCalendarDateChanged(calendarYear,
                                calendarMonth + 1);
                    }

                }else{
                    ToastCommon.toastShortShow(getContext(), null, groups.get(groups.size() - 1).getDate() + "之后无团期...");
                }
            } else {
                ToastCommon.toastShortShow(getContext(), null, groups.get(groups.size() - 1).getDate() + "之后无团期...");
            }
        }
    }
    /**
     * 上一月日历
     */
    @Override
    public synchronized void lastMonth() {
//        super.lastMonth();

        if (groups != null) {
            int thisYear = getCalendarYear();
            int thisMonth = getCalendarMonth();
            List<GroupDate> Months = new ArrayList<GroupDate>();
            //首先找出这一年的月份
            if(groups.size() == 0){
                for(int i = 1; i <= 6; i++){
                    GroupDeadline currentLine = new GroupDeadline();
                    String dataString = String.format("%d-%02d-%02d", thisYear, i, 1);
                    currentLine.setDate(dataString);
                    currentLine.setStock("-1");
                    groups.add(currentLine);
                }
            }
            for (int i = 0; i < groups.size(); i++) {
                GroupDeadline g = groups.get(i);
                if (thisYear >= getYear(g.getDate())) {
                    if (/*g.getState() == 1 && */Integer.parseInt(g.getStock()) >= -1) {
                        int year = getYear(g.getDate());
                        int month = getMonth(g.getDate());
                        Months.add(new GroupDate(year, month));
                    }
                }
            }
            //            for (int i = 0; i < Months.size(); i++) {
            if (Months.size() > 0) {
                if (Months.get(0).getYear() <= thisYear || Months.get(0).getMonth() <= thisMonth) {
                    if (currentCalendar == firstCalendar) {
                        currentCalendar = secondCalendar;
                    } else {
                        currentCalendar = firstCalendar;
                    }
                    setInAnimation(push_right_in);
                    setOutAnimation(push_right_out);
                    if (calendarMonth == Calendar.JANUARY) {
                        calendarYear--;
                        calendarMonth = Calendar.DECEMBER;
                        groups.clear();
                    } else {
                        calendarMonth--;
                    }
                    calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
                    setCalendarDate();
                    showPrevious();
                    if (onCalendarDateChangedListener != null) {
                        onCalendarDateChangedListener.onCalendarDateChanged(calendarYear,
                                calendarMonth + 1);
                    }
                }
            } else {
                ToastCommon.toastShortShow(getContext(), null, groups.get(0).getDate() + "之前无团期...");
            }
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        return super.onFling(e1, e2, velocityX, velocityY);
        return true;
    }
}
