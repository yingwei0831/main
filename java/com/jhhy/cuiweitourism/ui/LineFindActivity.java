package com.jhhy.cuiweitourism.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab1InnerTravelListViewAdapter;
import com.jhhy.cuiweitourism.biz.FindLinesBiz;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.markmao.pulltorefresh.widght.XListView;

import java.util.ArrayList;
import java.util.List;

public class LineFindActivity extends AppCompatActivity implements View.OnClickListener, XListView.IXListViewListener, AdapterView.OnItemClickListener, ArgumentOnClick {

    private View layout;
    private XListView   xListView;
    private Tab1InnerTravelListViewAdapter adapter;
    private List<Travel> mLists = new ArrayList<>();

    private TextView    tvSortDefault;
    private TextView    tvTripDays;
    private TextView    tvStartTime;
    private TextView    tvPriceScreen;
    private int tag;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_FIND_LINES:
                    if(msg.arg1 == 0){
                        ToastUtil.show(getApplicationContext(), "加载数据出错");
                    }else {
                        List<Travel> listNew = (List<Travel>) msg.obj;
                        if(listNew != null && listNew.size() != 0){
//                            ToastUtil.show(getApplicationContext(), "加载数据成功");
                            adapter.setData(listNew);
                        }else{
                            ToastUtil.show(getApplicationContext(), "加载数据失败");
                        }
                    }
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_find);

        setupView();
        addListener();
        getData();
    }

    private void getData() {
        FindLinesBiz biz = new FindLinesBiz(getApplicationContext(), handler);
//        biz.getLines(1, 10);
    }

    private void setupView() {
        layout = findViewById(R.id.layout_line_find);
        xListView = (XListView) findViewById(R.id.xlistview_line);

        tvSortDefault   = (TextView) findViewById(R.id.tv_activity_line_sort_default);
        tvTripDays      = (TextView) findViewById(R.id.tv_activity_line_trip_days);
        tvStartTime     = (TextView) findViewById(R.id.tv_activity_line_start_time);
        tvPriceScreen   = (TextView) findViewById(R.id.tv_activity_line_screen_price);

        adapter = new Tab1InnerTravelListViewAdapter(getApplicationContext(), mLists, this);
        xListView.setAdapter(adapter);
    }

    private void addListener() {
        xListView.setXListViewListener(this);
        xListView.setOnItemClickListener(this);

        tvSortDefault.setOnClickListener(this);
        tvTripDays.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvPriceScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_activity_line_sort_default:
                tag = 1;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
            case R.id.tv_activity_line_trip_days:
                tag = 2;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
            case R.id.tv_activity_line_start_time:
                tag = 3;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
            case R.id.tv_activity_line_screen_price:
                tag = 4;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
        }
    }

    @Override
    public void onRefresh() {
        xListView.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        xListView.stopLoadMore();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    /**
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
        ToastUtil.show(getApplicationContext(), "讨价还价");
    }
}
