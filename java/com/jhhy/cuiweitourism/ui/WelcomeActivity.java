package com.jhhy.cuiweitourism.ui;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.LoginBiz;
import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LocationUtil;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Permission;

public class WelcomeActivity extends BaseActivity {

    private String TAG = WelcomeActivity.class.getSimpleName();

    private AMapLocation aMapLocation;
    private SharedPreferencesUtils sp;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e(TAG, "-------handleMessage------");
            switch (msg.what) {
                case Consts.MESSAGE_LOGIN:
                    if (0 == msg.arg1) {
                        gotoLogin();
                    } else if (1 == msg.arg1) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Consts.KEY_REQUEST, (User) msg.obj);
                        MainActivity.actionStart(getApplicationContext(), bundle);
                    }
                    finish();
                    break;
                case Consts.MESSAGE_LOCATION:
                    updateLocation(msg);
                    break;
                case Consts.MESSAGE_LOCATION_FAILED:
                    showLocationMessage((AMapLocation) msg.obj);
                    break;
                case Consts.MESSAGE_NETWORK_CONNECT:
                    new LocationUtil(getApplicationContext(), handler).start();
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
        setContentView(R.layout.activity_welcome);
        sp = SharedPreferencesUtils.getInstance(getApplicationContext());
        if(NetworkUtil.checkNetwork(getApplicationContext())){
            new LocationUtil(getApplicationContext(), handler).start();
        }else{
            ToastUtil.show(getApplicationContext(), getString(R.string.Network_error));
        }
        initImagePath();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                checkFirstIn();
            }
        };
        handler.postDelayed(runnable, 2000);
        // 2秒钟之后进行登录，如果登录成功，进入主页面，并赋值；如果登录失败，可以进入主页面，登录标志为false;网络重新连接，发送广播，主业请求数据进行填充
    }

    private void checkFirstIn() {
        if(isFirst()){ //第一次登录
            LogUtil.e("info", "----isFirst = true");
            gotoSplash();
        } else { //非第一次登录
            LogUtil.i("info", "----isFirst = false");
            //登录——>主页面（成功）
            //登录——>登录页面（失败）
            SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
            if(TextUtils.isEmpty(sp.getTelephoneNumber()) || TextUtils.isEmpty(sp.getPassword())){
                gotoLogin();
            } else {
                LoginBiz biz = new LoginBiz(getApplicationContext(), handler);
                biz.login(sp.getTelephoneNumber(), sp.getPassword());
            }
        }
    }

    private void gotoSplash() {
        FlashActivity.actionStart(getApplicationContext(), null);
        finish();
    }

    public void gotoLogin() {
        LoginActivity.actionStart(getApplicationContext(), null);
        finish();
    }

    /**
     * 判断是否第一次登录
     * @return
     */
    private boolean isFirst() {
        SharedPreferences sp = getSharedPreferences(Consts.SP_NAME, MODE_PRIVATE);
        Boolean user_first = sp.getBoolean("FIRST", true);
        LogUtil.i("info", "---- user_first = " + user_first);
        if (user_first) {// 第一次
            sp.edit().putBoolean("FIRST", false).commit();
            return true;
        } else {
            return false;
        }
    }

    private void updateLocation(Message msg) {
        AMapLocation aml = (AMapLocation)msg.obj;
        this.aMapLocation = aml;
        sp.saveLocation(Double.toString(aMapLocation.getLatitude()), Double.toString(aMapLocation.getLongitude()));
    }

    /**
     * 定位失败
     * @param aMapLocation
     */
    private void showLocationMessage(AMapLocation aMapLocation) {
        ToastUtil.show(getApplicationContext(), "请求定位失败"); //+", "+aMapLocation.getErrorInfo()
    }
    private final String FILE_NAME = "/app_logo.png";
    public static String TEST_IMAGE;

    private void initImagePath() {
        FileOutputStream fos = null;
        try {
//			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//					&& Environment.getExternalStorageDirectory().exists()) {
//				TEST_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_NAME;
//			} else {
            TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath() + FILE_NAME;
//			}
            File file = new File(TEST_IMAGE);
            // 创建图片文件夹
            if (!file.exists()) {
                if(file.createNewFile()){
                    Bitmap pic = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);
                    fos = new FileOutputStream(file);
                    pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            TEST_IMAGE = null;
        } finally {
            try {
                if(fos != null){
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
