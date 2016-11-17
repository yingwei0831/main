package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.TrainListAdapter;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowScreenTrain;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TrainListActivity extends BaseActionBarActivity implements  AdapterView.OnItemClickListener //,RadioGroup.OnCheckedChangeListener
{

    private String TAG = TrainListActivity.class.getSimpleName();

    private TrainTicketFetch ticket;
    private TrainTicketActionBiz trainBiz;

    private String trainCode; //列车代码
    private String trainLowPrice; //最低价格
    private String trainTime; //出发时间
    private String tempTime; //出发时间
    private String trainType; //列车类型
    private String seatType; //座位类型

    private TextView tvPreDay; //前一天
    private TextView tvNextDay; //后一天
    private TextView tvCurrentDay; //今天 2016-10-24 星期一

    private View parent;
    private PullToRefreshListView pullListView;
    private ListView listView;
    private List<TrainTicketDetailInfo> list = new ArrayList<>();
    private TrainListAdapter adapter;

    private RadioGroup bottomRg; //底部筛选组合
    private RadioButton rbScreen;       //筛选
    private RadioButton rbStartTime;    //出发时间
    private RadioButton rbConsumingTime; //耗时
    private RadioButton rbArrivalTime;  //到达时间

    private Drawable drawableStartTimeIncrease; //从早到晚，升序，默认
    private Drawable drawableStartTimeDecrease; //从晚到早，降序
    private Drawable drawableConsumingIncrease; //耗时：从早到晚，升序
    private Drawable drawableConsumingDecrease; //耗时：从晚到早，降序
    private Drawable drawableArrivalTimeIncrease; //到达时间：从早到晚，升序，默认
    private Drawable drawableArrivalTimeDecrease; //到达时间：从晚到早，降序

    private Drawable startTimeDrawable; //初始图片
    private Drawable consumingTimeDrawable; //初始图片
    private Drawable arrivalTimeDrawable; //初始图片

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    break;
                case -2:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求火车票信息出错，请返回重试");
                    break;
                case 1:
                    tvCurrentDay.setText(trainTime);
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_train_list);
        getData();
        super.onCreate(savedInstanceState);
        getInternetData();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        ticket = (TrainTicketFetch) bundle.getSerializable("ticket");
        if (ticket.getTraincode() == null){
            trainCode = "";
        }
        if (ticket.getOnlylowprice() == null){
            trainLowPrice = "";
        }
        if (ticket.getTraintype() == null){
            trainType = "";
        }
        if (ticket.getTrainseattype() == null){
            seatType = "";
        }
        trainTime = ticket.getTraveltime();
        LogUtil.e(TAG, "ticket = " + ticket);
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(String.format("%s—%s", ticket.getFromstation(), ticket.getArrivestation()));
        trainBiz = new TrainTicketActionBiz();

        tvPreDay = (TextView) findViewById( R.id.tv_train_preference);
        tvNextDay = (TextView) findViewById(R.id.tv_train_next_day);
        tvCurrentDay = (TextView) findViewById(R.id.tv_train_ticket_day);
        tempTime = trainTime;
        tvCurrentDay.setText(trainTime);

        parent = findViewById(R.id.train_list_parent);
        pullListView = (PullToRefreshListView) findViewById(R.id.list_train_detail);
        //这几个刷新Label的设置
        pullListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullListView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        pullListView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        pullListView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        pullListView.setMode(PullToRefreshBase.Mode.DISABLED);
        listView = pullListView.getRefreshableView();

        //上拉、下拉监听函数
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        adapter = new TrainListAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);

        rbScreen = (RadioButton) findViewById(R.id.rb_train_screen);

        bottomRg = (RadioGroup) findViewById(R.id.rg_train_list);
        rbStartTime = (RadioButton) findViewById(R.id.rb_train_start_time);
        rbConsumingTime = (RadioButton) findViewById(R.id.rb_train_time_consuming);
        rbArrivalTime = (RadioButton) findViewById(R.id.rb_train_arrival_time);

        drawableStartTimeIncrease = ContextCompat.getDrawable(TrainListActivity.this, R.mipmap.icon_train_start_time_increase);
        drawableStartTimeDecrease = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_train_start_time_decrease);
        drawableConsumingIncrease = ContextCompat.getDrawable(TrainListActivity.this, R.mipmap.icon_train_consuming_increase);
        drawableConsumingDecrease = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_train_consuming_decrease);
        drawableArrivalTimeIncrease = ContextCompat.getDrawable(TrainListActivity.this, R.mipmap.icon_train_arrival_time_increase);
        drawableArrivalTimeDecrease = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_train_arrival_time_decrease);

        startTimeDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_train_start_time);
        consumingTimeDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_train_consuming_time);
        arrivalTimeDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_train_arrival_time);

        drawableStartTimeIncrease.setBounds(0, 0, drawableStartTimeIncrease.getMinimumWidth(), drawableStartTimeIncrease.getMinimumHeight());
        drawableStartTimeDecrease.setBounds(0, 0, drawableStartTimeDecrease.getMinimumWidth(), drawableStartTimeDecrease.getMinimumHeight());
        drawableConsumingIncrease.setBounds(0, 0, drawableConsumingIncrease.getMinimumWidth(), drawableConsumingIncrease.getMinimumHeight());
        drawableConsumingDecrease.setBounds(0, 0, drawableConsumingDecrease.getMinimumWidth(), drawableConsumingDecrease.getMinimumHeight());
        drawableArrivalTimeIncrease.setBounds(0, 0, drawableArrivalTimeIncrease.getMinimumWidth(), drawableArrivalTimeIncrease.getMinimumHeight());
        drawableArrivalTimeDecrease.setBounds(0, 0, drawableArrivalTimeDecrease.getMinimumWidth(), drawableArrivalTimeDecrease.getMinimumHeight());

        startTimeDrawable.setBounds(0, 0, startTimeDrawable.getMinimumWidth(), startTimeDrawable.getMinimumHeight());
        consumingTimeDrawable.setBounds(0, 0, consumingTimeDrawable.getMinimumWidth(), consumingTimeDrawable.getMinimumHeight());
        arrivalTimeDrawable.setBounds(0, 0, arrivalTimeDrawable.getMinimumWidth(), arrivalTimeDrawable.getMinimumHeight());
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_train_preference: //前一天
                tempTime = getDateStr(trainTime.substring(0, trainTime.indexOf(" ")), -1);
                getInternetData();
                break;
            case R.id.tv_train_next_day: //后一天
                tempTime = getDateStr(trainTime.substring(0, trainTime.indexOf(" ")), 1);
                getInternetData();
                break;
            case R.id.rb_train_screen: //筛选
                screenTrain();
                break;
            case R.id.rb_train_start_time: //出发时间
                setRbBg();
                sortByStartTime();
                adapter.setData(list);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rb_train_time_consuming: //耗时
                setRbBg();
                sortByConsuming();
                adapter.setData(list);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rb_train_arrival_time: //到达时间
                setRbBg();
                sortByArrivalTime();
                adapter.setData(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void setRbBg() {
        rbStartTime.setCompoundDrawables(null, startTimeDrawable, null, null);
        rbConsumingTime.setCompoundDrawables(null, consumingTimeDrawable, null, null);
        rbArrivalTime.setCompoundDrawables(null,   arrivalTimeDrawable, null, null);
    }

    @Override
    protected void addListener() {
        super.addListener();
        tvPreDay .setOnClickListener(this);
        tvNextDay.setOnClickListener(this);

        rbScreen.setOnClickListener(this);
        rbStartTime.setOnClickListener(this);
        rbConsumingTime.setOnClickListener(this);
        rbArrivalTime.setOnClickListener(this);

//        bottomRg.setOnCheckedChangeListener(this);
        listView.setOnItemClickListener(this);
    }

    private void getInternetData() {
        LoadingIndicator.show(TrainListActivity.this, getString(R.string.http_notice));
        //火车票
        new Thread(){
            @Override
            public void run() {
                super.run();
                TrainTicketFetch fetch = new TrainTicketFetch(ticket.getFromstation(), ticket.getArrivestation(), tempTime.substring(0, tempTime.indexOf(" ")), trainCode, trainType, seatType, trainLowPrice);
                trainBiz.trainTicketInfo(fetch, new BizGenericCallback<ArrayList<TrainTicketDetailInfo>>() {
                    @Override
                    public void onCompletion(GenericResponseModel<ArrayList<TrainTicketDetailInfo>> model) {
                        if ("0001".equals(model.headModel.res_code)){
//                    ToastUtil.show(getApplicationContext(), model.headModel.res_arg);
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
                            trainTime = tempTime;
                            list = model.body;
                            handler.sendEmptyMessage(1);
                            LogUtil.e(TAG,"trainTicketInfo =" + list.toString());
                        }
                        LoadingIndicator.cancel();
                    }

                    @Override
                    public void onError(FetchError error) {
                        if (error.localReason != null){
//                    ToastUtil.show(getApplicationContext(), error.localReason);
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
//                    ToastUtil.show(getApplicationContext(), "请求火车票信息出错，请返回重试");
                            handler.sendEmptyMessage(-2);
                        }
                        LogUtil.e(TAG, "trainTicketInfo: " + error.toString());
                        LoadingIndicator.cancel();
                    }
                });
            }
        }.start();

    }


//    @Override
//    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        switch (i){
//            case R.id.rb_train_screen: //筛选
//                screenTrain();
//                break;
//            case R.id.rb_train_start_time: //出发时间
//                sortByStartTime();
//                adapter.setData(list);
//                adapter.notifyDataSetChanged();
//                break;
//            case R.id.rb_train_time_consuming: //耗时
//                sortByConsuming();
//                adapter.setData(list);
//                adapter.notifyDataSetChanged();
//                break;
//            case R.id.rb_train_arrival_time: //到达时间
//                sortByArrivalTime();
//                adapter.setData(list);
//                adapter.notifyDataSetChanged();
//                break;
//        }
//    }

    //筛选列车
    private void screenTrain() {
        if (popupWindow == null) {
            popupWindow = new PopupWindowScreenTrain(TrainListActivity.this, parent);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    boolean commit = popupWindow.getCommit();
                    if (commit){
                        startTimePosition = popupWindow.getStartTime();
                        arrivalTimePosition = popupWindow.getSelectionArrivalTime();
                        typeTrainPosition = popupWindow.getSelectionTypeTrain();
                        typeSeatPosition = popupWindow.getSelectionTypeSeat();
                        //TODO 重新请求数据

//                        getInternetData();
                    }
                }
            });
        }else{
            if(popupWindow.isShowing()){
                popupWindow.dismiss();
            }else{
                popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                popupWindow.refreshView(startTimePosition, arrivalTimePosition, typeTrainPosition, typeSeatPosition);
            }
        }
    }

    private PopupWindowScreenTrain popupWindow;
    private int startTimePosition = 0, arrivalTimePosition = -1, typeTrainPosition = -1, typeSeatPosition = -1;
    private String startTime, arrivalTime, typeTrain, typeSeat;

    private void sortByArrivalTime() {
        Collections.sort(list, new Comparator<TrainTicketDetailInfo>(){
            public int compare(TrainTicketDetailInfo arg0, TrainTicketDetailInfo arg1) {
                //14:00
                int hour1 = Integer.parseInt(arg0.arrivalTime.substring(0, arg0.arrivalTime.indexOf(":")));
                int hour2 = Integer.parseInt(arg1.arrivalTime.substring(0, arg1.arrivalTime.indexOf(":")));
                if (hour1 > hour2){
                    return 1;
                }else if (hour1 == hour2){
                    int minute1 = Integer.parseInt(arg0.arrivalTime.substring(arg0.arrivalTime.indexOf(":") + 1));
                    int minute2 = Integer.parseInt(arg1.arrivalTime.substring(arg1.arrivalTime.indexOf(":") + 1 ));
                    if (minute1 > minute2){
                        return 1;
                    }else if (minute1 == minute2){
                        return 0;
                    }else{
                        return -1;
                    }
                }else{
                    return -1;
                }
            }
        });
        if (sortArrivalTimeIncrease){ //如果当前到大时间是升序排列，则倒序
            Collections.reverse(list);
            sortArrivalTimeIncrease = false;
            rbArrivalTime.setCompoundDrawables(null, drawableArrivalTimeDecrease, null, null);
        }else{
            sortArrivalTimeIncrease = true;
            rbArrivalTime.setCompoundDrawables(null, drawableArrivalTimeIncrease, null, null);
        }
    }

    private void sortByConsuming() {
        Collections.sort(list, new Comparator<TrainTicketDetailInfo>(){
            public int compare(TrainTicketDetailInfo arg0, TrainTicketDetailInfo arg1) {
                //666分钟
                int hour1 = Integer.parseInt(arg0.duration);
                int hour2 = Integer.parseInt(arg1.duration);
                if (hour1 > hour2){
                    return 1;
                }else if (hour1 == hour2){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
        if(sortConsumingIncrease){ //如果当前耗时是从短到长，则改变倒序
            Collections.reverse(list);
            sortConsumingIncrease = false;
            rbConsumingTime.setCompoundDrawables(null, drawableConsumingDecrease, null, null);
        }else{
            sortConsumingIncrease = true;
            rbConsumingTime.setCompoundDrawables(null, drawableConsumingIncrease, null, null);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), TrainListItemInfoActivity.class);
        Bundle bundle = new Bundle();
        TrainTicketDetailInfo item = list.get((int) l);
        bundle.putSerializable("detail", item);
        bundle.putString("startDate", trainTime);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_TRAIN_ITEM); //查看某趟列车
    }

    private int VIEW_TRAIN_ITEM = 7546; //查看某趟列车

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIEW_TRAIN_ITEM){ //有可能订票了
            if (resultCode == RESULT_OK){

            }
        }
    }

    private boolean sortStartTimeIncrease = true; //从早到晚,出发时间（默认从早到晚）
    private boolean sortConsumingIncrease = false; //耗时，从短到长
    private boolean sortArrivalTimeIncrease = false; //到达时间，从早到晚

    //按出发时间排序
    private void sortByStartTime() {
        //默认从早到晚排序
        Collections.sort(list, new Comparator<TrainTicketDetailInfo>() {
            public int compare(TrainTicketDetailInfo arg0, TrainTicketDetailInfo arg1) {
                //14:00
                int hour1 = Integer.parseInt(arg0.departureTime.substring(0, arg0.departureTime.indexOf(":")));
                int hour2 = Integer.parseInt(arg1.departureTime.substring(0, arg1.departureTime.indexOf(":")));
                if (hour1 > hour2) {
                    return 1;
                } else if (hour1 == hour2) {
                    int minute1 = Integer.parseInt(arg0.departureTime.substring(arg0.departureTime.indexOf(":") + 1));
                    int minute2 = Integer.parseInt(arg1.departureTime.substring(arg1.departureTime.indexOf(":") + 1));
                    if (minute1 > minute2) {
                        return 1;
                    } else if (minute1 == minute2) {
                        return 0;
                    } else {
                        return -1;
                    }
                } else {
                    return -1;
                }
            }
        });
        if (sortStartTimeIncrease){ //如果当前是从早到晚，则改变为从晚到早
            Collections.reverse(list);
            //更换图标
            sortStartTimeIncrease = false; //任何一个顺序改变，都变为false
            rbStartTime.setCompoundDrawables(null, drawableStartTimeDecrease, null, null);
        }else{
            sortStartTimeIncrease = true;
            rbStartTime.setCompoundDrawables(null, drawableStartTimeIncrease, null, null);
        }
    }

    /**
    * 获取指定日后 后 dayAddNum 天的 日期
    * @param day  日期，格式为String："2013-9-3";
    * @param dayAddNum 增加天数 格式为int;
    * @return
            */
    private String getDateStr(String day, int dayAddNum) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd E");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }



}
