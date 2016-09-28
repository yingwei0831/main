package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jhhy.cuiweitourism.ILauncherView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.LauncherPagerAdapter;
import com.jhhy.cuiweitourism.biz.LoginBiz;

public class FlashActivity extends AppCompatActivity implements ILauncherView, ViewPager.OnPageChangeListener {

    private String TAG = FlashActivity.class.getSimpleName();

    private ViewPager mViewPager;
    private LauncherPagerAdapter mAdapter;

//    private ILauncherView launcherView;

    private ViewGroup viewGroup;
    private ImageView[] indicators;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flash);
//        if(!isFirst()){ //非第一次登录
//            LoginBiz biz = new LoginBiz(getApplicationContext(), handler);
//
//            gotoMain();
//            return;
//        }
        //第一次登录
        mViewPager = (ViewPager)findViewById(R.id.viewpager_launcher);
        mAdapter = new LauncherPagerAdapter(getApplicationContext(), this);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mAdapter);
        //初始化底部显示控件

        viewGroup = (ViewGroup) findViewById(R.id.viewGroup);
        int countIndicator = mAdapter.getCount();
        indicators = new ImageView[countIndicator];
        for(int i = 0; i < countIndicator; i ++){
            ImageView imageView = new ImageView(getApplicationContext());
            if(i == 0){
                imageView.setImageResource(R.mipmap.icon_point_pre);
            }else {
                imageView.setImageResource(R.mipmap.icon_point);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 6;
            params.rightMargin = 6;
            indicators[i] = imageView;
            viewGroup.addView(imageView, params);
        }
    }

    /**
     * 去往登录页面
     */
    @Override
    public void gotoMain() {
        LoginActivity.actionStart(getApplicationContext(), null);
        finish();
    }

    /**
     * 已经登录，去往主页面
     */
//    public void gotoMainLogined(){
//        MainActivity.actionStart(getApplicationContext(), null);
//        finish();
//    }
    /**
     * 判断是否第一次登录
     * @return
     */
    private boolean isFirst() {
        SharedPreferences sp = getSharedPreferences("cuiwei_tourism", MODE_PRIVATE);
        Boolean user_first = sp.getBoolean("FIRST", true);
        if (user_first) {// 第一次
            sp.edit().putBoolean("FIRST", false).commit();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { //不断回调

    }

    @Override
    public void onPageSelected(int position) { //停止后回调一次
        if(mAdapter.getCount() - 1 == position ){
            viewGroup.setVisibility(View.GONE);
            View view = mAdapter.getViews().get(position);
            view.findViewById(R.id.tv_start_headlines).setVisibility(View.VISIBLE);
            return;
        }
        //此处改变点点效果
        if(viewGroup.getVisibility() == View.GONE){
            viewGroup.setVisibility(View.VISIBLE);
        }
        for(int i = 0; i < mAdapter.getCount(); i++){
            if(position == i){
                indicators[i].setImageResource(R.mipmap.icon_point_pre);
            }else {
                indicators[i].setImageResource(R.mipmap.icon_point);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, FlashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

}
