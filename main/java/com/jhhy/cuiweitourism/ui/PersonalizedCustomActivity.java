package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PersonalizedCustomGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.Tab1GridViewAdapter;
import com.jhhy.cuiweitourism.moudle.CustomTravel;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

public class PersonalizedCustomActivity extends BaseActivity implements XScrollView.IXScrollViewListener , GestureDetector.OnGestureListener, View.OnClickListener {

    private XScrollView mScrollView;
    private GestureDetector mGestureDetector; // MyScrollView的手势?

    private View content;
    private Button btnStartCustom; //开始定制按钮
    private MyGridView gvCustom;
    private PersonalizedCustomGridViewAdapter adapter;
    private List<CustomTravel> lists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalized_custom);

        setupView();
        addListener();
    }

    private void setupView(){
        for (int i = 0; i < 11; i++) {
            CustomTravel travel = new CustomTravel();
            travel.setPrice(Integer.toString(9995 + i));
            travel.setDays(Integer.toString(i+5));
            lists.add(travel);
        }
        mScrollView = (XScrollView)findViewById(R.id.scroll_view);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
        mScrollView.setAutoLoadEnable(true);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(Utils.getCurrentTime());

        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_personalized_custom_content, null);
        if(content != null) {
            btnStartCustom = (Button) content.findViewById(R.id.btn_custom_start);
            gvCustom = (MyGridView) content.findViewById(R.id.gv_personalized_custom);
            gvCustom.setFocusable(false);
            gvCustom.setFocusableInTouchMode(false);
            adapter = new PersonalizedCustomGridViewAdapter(getApplicationContext(), lists);
            gvCustom.setAdapter(adapter);

            mGestureDetector = new GestureDetector(getApplicationContext(), this);
        }
        mScrollView.setView(content);
    }

    private void addListener(){
        btnStartCustom.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_custom_start:
                PersonalizedCustomStartActivity.actionStart(getApplicationContext(), null);
                break;
        }
    }

    @Override
    public void onRefresh() {
        mScrollView.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        mScrollView.stopLoadMore();
    }

    public static void actionStart(Context context, Bundle data) {
        Intent intent = new Intent(context, PersonalizedCustomActivity.class);
        if(data != null){
            intent.putExtra("data", data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

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

}
