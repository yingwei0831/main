package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.biz.UserCollectionBiz;
import com.jhhy.cuiweitourism.fragment.VisaMaterialFragment;
import com.jhhy.cuiweitourism.moudle.VisaDetail;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class VisaNeedMaterialActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> mContent = new ArrayList<>();
    private String[] mTitles = new String[]{"在职人员", "自由职业", "退休人员", "学生", "学龄前儿童"};
    private OrdersPagerAdapter pagerAdapter;

    private VisaDetail mVisaDetail;

    private TextView tvCollection; //收藏
    private TextView tvShare; //分享
    private Button btnReserve; //立即预定

    private View layoutParent; //所有Fragment的父布局

    private VisaMaterialFragment fragment1;
    private VisaMaterialFragment fragment2;
    private VisaMaterialFragment fragment3;
    private VisaMaterialFragment fragment4;
    private VisaMaterialFragment fragment5;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Consts.NET_ERROR:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "网络无连接，请检查网络");
                    break;
                case Consts.MESSAGE_DO_COLLECTION: //收藏
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1) {
                        //TODO 改变收藏按钮颜色

                    }
                    break;
            }
            LoadingIndicator.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        ActionBar bar =  getSupportActionBar();
        //自定义一个布局，并居中
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        setContentView(R.layout.activity_visa_need_material);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        mVisaDetail = (VisaDetail) bundle.getSerializable("visaDetail");
    }

    private void addListener() {
        tvCollection.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        btnReserve.setOnClickListener(this);
    }

    private void setupView() {
        layoutParent = findViewById(R.id.layout_parent);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewPager = (ViewPager) findViewById(R.id.visa_viewpager);
        viewPager.setOffscreenPageLimit(5);
        fragment1 = VisaMaterialFragment.newInstance(mTitles[0], mVisaDetail.getMaterial1());
        fragment2 = VisaMaterialFragment.newInstance(mTitles[1], mVisaDetail.getMaterial2());
        fragment3 = VisaMaterialFragment.newInstance(mTitles[2], mVisaDetail.getMaterial3());
        fragment4 = VisaMaterialFragment.newInstance(mTitles[3], mVisaDetail.getMaterial4());
        fragment5 = VisaMaterialFragment.newInstance(mTitles[4], mVisaDetail.getMaterial5());
        fragment1.setParent(layoutParent);
        fragment2.setParent(layoutParent);
        fragment3.setParent(layoutParent);
        fragment4.setParent(layoutParent);
        fragment5.setParent(layoutParent);
        mContent.add(fragment1);
        mContent.add(fragment2);
        mContent.add(fragment3);
        mContent.add(fragment4);
        mContent.add(fragment5);
        pagerAdapter = new OrdersPagerAdapter(getSupportFragmentManager(), mTitles, mContent);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        tvCollection = (TextView) findViewById(R.id.tv_visa_detail_collection);
        tvShare = (TextView) findViewById(R.id.tv_visa_detail_share);
        btnReserve = (Button) findViewById(R.id.btn_visa_detail_reserve);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_visa_detail_collection: //收藏
                doCollection();
                break;
            case R.id.tv_visa_detail_share: //分享

                break;
            case R.id.btn_visa_detail_reserve: //立即预定
                toReserve();
                break;
        }
    }

    //立即预定,去日历中添加人数
    private void toReserve() {
        Intent intent = new Intent(getApplicationContext(), VisaPriceCalendarReserveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("visaDetail", mVisaDetail);
        intent.putExtras(bundle);
        startActivityForResult(intent, VISA_RESERVE);
    }

    private final int VISA_RESERVE = 1821; //立即预定

    private void doCollection() {
        UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
        biz.doCollection(MainActivity.user.getUserId(), "8", mVisaDetail.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VISA_RESERVE){
            if (resultCode == RESULT_OK){

            }
        }
    }
}
