package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrainListActivity extends BaseActionBarActivity {

    private String TAG = TrainListActivity.class.getSimpleName();

    private TrainTicketFetch ticket;
    private TrainTicketActionBiz trainBiz;

    private String trainCode; //列车代码
    private String trainLowPrice; //最低价格
    private String trainTime; //出发时间
    private String trainType; //列车类型
    private String seatType; //座位类型


    private TextView tvPreDay; //前一天
    private TextView tvNextDay; //后一天
    private TextView tvCurrentDay; //今天 2016-10-24 星期一

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
//        tvCurrentDay.setText(String.format("%s %s", trainTime, ));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_train_preference: //前一天
                trainTime = getDateStr(trainTime, -1);
                getInternetData();
                break;
            case R.id.tv_train_next_day: //后一天
                trainTime = getDateStr(trainTime, 1);
                getInternetData();
                break;
        }
    }

    @Override
    protected void addListener() {
        super.addListener();
        tvPreDay .setOnClickListener(this);
        tvNextDay.setOnClickListener(this);

    }

    private void getInternetData() {
        //火车票
        TrainTicketFetch fetch = new TrainTicketFetch(ticket.getFromstation(), ticket.getArrivestation(), trainTime, trainCode, trainType, seatType, trainLowPrice);
        trainBiz.trainTicketInfo(fetch, new BizGenericCallback<ArrayList<TrainTicketDetailInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<TrainTicketDetailInfo>> model) {
                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    ArrayList<TrainTicketDetailInfo> array = model.body;
                    LogUtil.e(TAG,"trainTicketInfo =" + array.toString());
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求火车票信息出错，请返回重试");
                }
                LogUtil.e(TAG, "trainTicketInfo: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
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
