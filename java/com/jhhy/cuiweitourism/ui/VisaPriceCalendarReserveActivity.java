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
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaDetailInfo;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.GroupDeadline;
import com.just.sun.pricecalendar.KCalendar;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class VisaPriceCalendarReserveActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = VisaPriceCalendarReserveActivity.class.getSimpleName();

    private VisaDetailInfo detail; //签证详情

    private List<GroupDeadline> calendarPricesNew = new ArrayList<>(); //价格日历集合
    private String date = null;// 设置默认选中的日期  格式为 “2015-09-18” 标准DATE格式

    private KCalendar calendar;

    private TextView tvCurrentDate; //当前的年月
    private ImageView ivLastMonth; //上个月
    private ImageView ivNextMonth; //下个月

    private ImageView ivReduceAdult; //成人减少
    private ImageView ivPlusAdult;   //成人增加

    private TextView tvCountAdult; //成人数量

    private int countAdult = 1;


    private Button btnReserve; //下一步，填写订单

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                switch (msg.what) {
                    case 9685:
                        refreshView();
                        break;
                }
            LoadingIndicator.cancel();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_price_calendar_reserve);
        getData();
        setupView();
        addListener();

    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        detail = (VisaDetailInfo) bundle.getSerializable("visaDetail");

        LoadingIndicator.show(VisaPriceCalendarReserveActivity.this, "正在加载数据，请稍后...");
        final String price = detail.getVisaPrice();
        final long addTime = 24 * 60 * 60 * 1000;
        new Thread(){
            @Override
            public void run() {
                super.run();
                long startTime = System.currentTimeMillis();
                for (long i = 0; i < 150; i++){ //构造了5个月的数据
                    GroupDeadline deadline = new GroupDeadline();
                    deadline.setDate(Utils.getTimeStrYMD(startTime));
                    deadline.setSell_price_adult(price);
                    calendarPricesNew.add(deadline);
                    startTime += addTime;
                }
                Message msg = new Message();
                msg.what = 9685;
                handler.sendMessage(msg);
            }
        }.start();

    }

    private void setupView() {
        ImageView ivLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);
        ivLeft.setOnClickListener(this);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("选择签证需求日期");

        calendar = (KCalendar) findViewById(R.id.calendar);
        tvCurrentDate = (TextView) findViewById(R.id.tv_calendar_month);
        ivLastMonth = (ImageView) findViewById(R.id.imgv_calendar_last_month);
        ivNextMonth = (ImageView) findViewById(R.id.imgv_calendar_next_month);

        ivReduceAdult   = (ImageView) findViewById(R.id.tv_price_calendar_number_reduce);
        ivPlusAdult     = (ImageView) findViewById(R.id.tv_price_calendar_number_add  );

        tvCountAdult = (TextView) findViewById(R.id.tv_price_calendar_number);

        tvCountAdult.setText(String.valueOf(countAdult));

        btnReserve = (Button) findViewById(R.id.btn_price_calendar_reserve);
    }

    private void addListener() {
        ivLastMonth.setOnClickListener(this);
        ivNextMonth.setOnClickListener(this);

        ivReduceAdult.setOnClickListener(this);
        ivPlusAdult  .setOnClickListener(this);
        btnReserve  .setOnClickListener(this);
    }
    private int dateYear; //第一天
    private int dateMonth; //第一天

    private GroupDeadline selectGroupDeadLine; //选择某天的价格日历

    private boolean selectDay;

    private void refreshView() {
        //设置团期
        calendar.setGroups(calendarPricesNew);
        calendar.showCalendar();
        if (TextUtils.isEmpty(date) && calendarPricesNew.size() > 0) {
            date = calendarPricesNew.get(0).getDate();//获取第一天的
            dateYear = Integer.parseInt(date.substring(0, date.indexOf("-")));
            dateMonth = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
            tvCurrentDate.setText(dateYear + "年" + dateMonth + "月");
            calendar.showCalendar(dateYear, dateMonth);//设置初始日子
        }

        //监听所选中的日期
        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {

                int month = Integer.parseInt(dateFormat.substring(
                        dateFormat.indexOf("-") + 1,
                        dateFormat.lastIndexOf("-")));

                if (calendar.getCalendarMonth() - month == 1//跨年跳转
                        || calendar.getCalendarMonth() - month == -11) {
                    calendar.lastMonth();

                } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
                        || month - calendar.getCalendarMonth() == -11) {
                    calendar.nextMonth();

                } else {
                    selectDay = true;
                    //date = dateFormat; //最后返回给全局 date
                    for (int i = 0; i < calendarPricesNew.size(); i++) {
                        String positionDate = calendarPricesNew.get(i).getDate();

//                        int stock = Integer.parseInt(calendarPrices.get(i).getStock()); //因为没有人数限制
//                        LogUtil.e("SHF", "dateFormat--->" + dateFormat + ", date--->" + calendarPrices.get(i).getDate()); // + "peopleNumCur--->" + "stock--->" + stock);
//                        LogUtil.e("SHF", "dateFormat--->" + dateFormat + ", positionDate--->" + positionDate +", date = " + date); // + "peopleNumCur--->" + "stock--->" + stock);
                        if (dateFormat.equals(positionDate) ){ //当前点击日期的背景色
//                                && (stock) > 0) {
                            //设置背景色
                            calendar.removeAllBgColor();
                            calendar.setCalendarDayBgColor(dateFormat, ContextCompat.getColor(getApplicationContext(), R.color.colorCalendarSelection)); //Color.parseColor("#45BDEF")
//                            priceAdult = Integer.parseInt(calendarPricesNew.get(i).getSell_price_adult());
//                            priceChild = Integer.parseInt(calendarPricesNew.get(i).getSell_price_children());
                            selectGroupDeadLine = calendarPricesNew.get(i);
                        } else if (date.equals(positionDate)){ //如果是选择今天的日期
//                                && (stock) > 0) {
//                            ToastCommon.toastShortShow(getApplicationContext(), null, "此团期剩余空位不足，请选择其他团期或减少参团人数");
                        }
                    }

                }
            }
        });
        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                tvCurrentDate.setText(year + "年" + month + "月");
            }
        });
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, VisaPriceCalendarReserveActivity.class);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.imgv_calendar_last_month: //上个月
                calendar.lastMonth();
                break;
            case R.id.imgv_calendar_next_month: //下个月
                calendar.nextMonth();
                break;
            case R.id.tv_price_calendar_number_reduce:
                if (countAdult > 1) {
                    countAdult -= 1;
                    tvCountAdult.setText(Integer.toString(countAdult));
                }
                break;
            case R.id.tv_price_calendar_number_add:
                countAdult += 1;
                tvCountAdult.setText(Integer.toString(countAdult));
                break;

            case R.id.btn_price_calendar_reserve:
                if (selectDay) {
                    Intent intent = new Intent(getApplicationContext(), VisaOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("countAdult", countAdult);
                    bundle.putSerializable("detail", detail);
                    bundle.putSerializable("priceCalendar", selectGroupDeadLine);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_EDIT_ORDER);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请选择出发日期");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){

        }else{
            if (requestCode == Consts.REQUEST_CODE_RESERVE_EDIT_ORDER){ //填写订单

            }
        }
    }
}
