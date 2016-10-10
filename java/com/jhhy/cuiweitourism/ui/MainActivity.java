package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.LoginBiz;
import com.jhhy.cuiweitourism.fragment.Tab1Fragment;
import com.jhhy.cuiweitourism.fragment.Tab2Fragment_2;
import com.jhhy.cuiweitourism.fragment.Tab3Fragment;
import com.jhhy.cuiweitourism.fragment.Tab4Fragment2;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RadioGroup radioGroup;
    private Fragment mContent;
    private Tab1Fragment tab1Fragment;
//    private Tab1Fragment_3 tab1Fragment_3;
//    private Tab1Fragment_2 tab1Fragment_2;
//    private Tab2Fragment tab2Fragment;
    private Tab2Fragment_2 tab2Fragment_2;
    private Tab3Fragment tab3Fragment;
//    private Tab4Fragment tab4Fragment;
    private Tab4Fragment2 tab4Fragment2;

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "onCreate");
        getData(savedInstanceState);
        setupView();
        addListener();
        setDefaultFragment(savedInstanceState);
    }

    private void getData(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            LogUtil.e(TAG, "========= savedInstanceState == null ==============");
        }else{
            LogUtil.e(TAG, "========= savedInstanceState != null ==============");
        }
        Intent intent = getIntent();
        if (intent != null) {
            LogUtil.e(TAG, "========= intent != null ==============");
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                LogUtil.e(TAG, "========= bundle != null ==============");
                user = (User) bundle.getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    LogUtil.e(TAG, "========= user = " + user.toString() + " ==============");
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }else{
                    LogUtil.e(TAG, "========= user = null ==============");
                }
            }else{
                LogUtil.e(TAG, "========= bundle == null ==============");
            }
        }else{
            LogUtil.e(TAG, "========= intent == null ==============");
        }
    }

    private void setupView() {
        radioGroup = (RadioGroup) findViewById(R.id.tab_menu);
    }

    private void addListener() {
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void setDefaultFragment(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        if(savedInstanceState != null) { // “内存重启”时调用
//            onSaveInstanceState = false;
//            tab1Fragment = (Tab1Fragment) getSupportFragmentManager().findFragmentByTag("1");
            tab1Fragment = (Tab1Fragment) getSupportFragmentManager().findFragmentByTag("1");
//            tab2Fragment = (Tab2Fragment) getSupportFragmentManager().findFragmentByTag("2");
            tab2Fragment_2 = (Tab2Fragment_2) getSupportFragmentManager().findFragmentByTag("2");
//            tab2Fragment_3 = (Tab2Fragment_3) getSupportFragmentManager().findFragmentByTag("2");
            tab3Fragment = (Tab3Fragment) getSupportFragmentManager().findFragmentByTag("3");
            tab4Fragment2 = (Tab4Fragment2) getSupportFragmentManager().findFragmentByTag("4");
            index = savedInstanceState.getInt(KEY_INDEX);
            LogUtil.i(TAG, "index = " + index +", " + tab1Fragment + ", " + tab2Fragment_2 +", " + tab3Fragment + ", " + tab4Fragment2 + ", mContent = "+mContent);
            // 根据下标判断离开前是显示哪个Fragment， // 解决重叠问题
            if(tab1Fragment != null){
                t.hide(tab1Fragment);
            }
            if(tab2Fragment_2 != null){
                t.hide(tab2Fragment_2);
            }
            if(tab3Fragment != null){
                t.hide(tab3Fragment);
            }
            if(tab4Fragment2 != null){
                t.hide(tab4Fragment2);
            }
            if(index == 1){
                mContent = tab1Fragment;
                t.show(tab1Fragment).commit();
            }else if(index == 2){
                mContent = tab2Fragment_2;
                t.show(tab2Fragment_2).commit();
            }else if(index == 3) {
                mContent = tab3Fragment;
                t.show(tab3Fragment).commit();
            }else if(index == 4){
                mContent = tab4Fragment2;
                t.show(tab4Fragment2).commit();
            }
        }else{ //正常时调用
            tab1Fragment = Tab1Fragment.newInstance(null, null);
            mContent = tab1Fragment;
            // 这里add时，tag可传可不传
            // .add(R.id.main_content, fragmentHome)
            t.add(R.id.main_content, tab1Fragment, "1").commit();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        Fragment to = null;
        Fragment from = mContent;

        switch (checkedId){
            case R.id.tab1:
                index = 1;
                to = tab1Fragment;

                break;
            case R.id.tab2:
                index = 2;
                if(tab2Fragment_2 == null){
                    tab2Fragment_2 = Tab2Fragment_2.newInstance(null, null);
                }
                to = tab2Fragment_2;

                break;
            case R.id.tab3:
                index = 3;
                if(tab3Fragment == null){
                    tab3Fragment = Tab3Fragment.newInstance(null, null);
                }
                to = tab3Fragment;

                break;
            case R.id.tab4:
                index = 4;
                if(tab4Fragment2 == null){
                    tab4Fragment2 = Tab4Fragment2.newInstance(null);
                }
                to = tab4Fragment2;

                break;
        }
        if(mContent != to){
            mContent = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); //fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, R.anim.slide_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.main_content, to, index+"").commit(); //transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); //transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    // 保存当前Fragment的下标,内存重启时调用
    private final String KEY_INDEX = "index";
    private int index;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, index);
    }

    public static void actionStart(Context context, Bundle data) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(data != null){
            intent.putExtras(data);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginBiz biz = new LoginBiz(null, null);
        biz.logout(null);
    }
}
