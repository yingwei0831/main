package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaHotAnyCountryGridAdapter;
import com.jhhy.cuiweitourism.adapter.VisaHotCountryGridAdapter;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.moudle.VisaHotCountry;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.just.sun.pricecalendar.ToastCommon;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

public class VisaMainActivity extends BaseActivity implements XScrollView.IXScrollViewListener, GestureDetector.OnGestureListener, View.OnClickListener {

    private String TAG = VisaMainActivity.class.getSimpleName();

    private XScrollView mScrollView;
    private GestureDetector mGestureDetector; // MyScrollView的手势?

    private View content;
//    private Button btnStartCustom; //开始定制按钮


    private MyGridView gvHotVisaCountry; //热门签证国家(top)
    private VisaHotCountryGridAdapter adapterHotCountry;
    private List<CityRecommend> listHotCountry = new ArrayList<>();

    private Button btnVisaViewMore; //查看全部国家和地区

    private MyGridView gvHotVisaAny; //热门签证（bottom）
    private VisaHotAnyCountryGridAdapter adapterHotAny;
    private List<VisaHotCountry> listHotAny = new ArrayList<>();


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingIndicator.cancel();
            switch (msg.what){
                case Consts.MESSAGE_VISA_HOT: //热门签证（bottom）
                    if (msg.arg1 == 1){
                        listHotAny = (List<VisaHotCountry>) msg.obj;
                        if (listHotAny != null || listHotAny.size() > 0){
                            adapterHotAny.setData(listHotAny);
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取服务器数据失败");
                        }
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    }
                    break;
                case Consts.MESSAGE_VISA_HOT_COUNTRY: //热门签证国家（top）
                    if (msg.arg1 == 1){
                        listHotCountry = (List<CityRecommend>) msg.obj;
                        if (listHotCountry != null || listHotCountry.size() > 0){
                            adapterHotCountry.setData(listHotCountry);
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取服务器数据失败");
                        }
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_main);
        setupView();
        addListener();
    }

    private void setupView(){


        mScrollView = (XScrollView) findViewById(R.id.scroll_view_visa);
        mScrollView.setPullRefreshEnable(false);
        mScrollView.setPullLoadEnable(false);
        mScrollView.setAutoLoadEnable(false);
//        mScrollView.setIXScrollViewListener(this);
//        mScrollView.setRefreshTime(Utils.getCurrentTime());

        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_visa_main_content, null);
        if (content != null){
            gvHotVisaCountry = (MyGridView) content.findViewById(R.id.gv_visa_hot_country);
            gvHotVisaCountry.setFocusable(false);
            gvHotVisaCountry.setFocusableInTouchMode(false);

            adapterHotCountry = new VisaHotCountryGridAdapter(getApplicationContext(), listHotCountry);
            gvHotVisaCountry.setAdapter(adapterHotCountry);

            btnVisaViewMore = (Button) content.findViewById(R.id.btn_visa_view_all);

            gvHotVisaAny = (MyGridView) content.findViewById(R.id.gv_hot_country_any);
            adapterHotAny = new VisaHotAnyCountryGridAdapter(getApplicationContext(), listHotAny);
            gvHotVisaAny.setAdapter(adapterHotAny);

        }
        mScrollView.setView(content);
        LoadingIndicator.show(VisaMainActivity.this, getString(R.string.http_notice));
        getHotVisaCountry();
        getHotVisaAny();
    }

    private void getHotVisaAny() { //热门签证
        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
        biz.getHotVisa();
    }

    private void getHotVisaCountry() { //热门签证国家
        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
        biz.getHotCountry();
    }

    private void addListener(){
        btnVisaViewMore.setOnClickListener(this);

        //热门签证国家
        gvHotVisaCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), VisaListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nationId", listHotCountry.get(i).getCityId());
                bundle.putString("nationName", listHotCountry.get(i).getCityName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //热门签证
        gvHotVisaAny.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.e(TAG, "position = " + i +", l = " + l);
                String id = listHotAny.get(i).getId();
                Intent intent = new Intent(getApplicationContext(), VisaItemDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_VISA_DETAIL);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_visa_view_all: //查看全部国家和地区
                startActivity(new Intent(getApplicationContext(), SelectAllCountryAreaActivity.class));
                break;
        }
    }

    private final int VIEW_VISA_DETAIL = 1801; //热门签证详情

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIEW_VISA_DETAIL){ //底部，热门签证，详情
            if (RESULT_OK == resultCode){

            }
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

//    public static void actionStart(Context context, Bundle bundle){
//        Intent intent = new Intent(context, VisaMainActivity.class);
//        if(bundle != null){
//            intent.putExtra("bundle", bundle);
//        }
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, VisaMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

}
