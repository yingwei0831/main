package com.jhhy.cuiweitourism.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.CheckCodeBiz;
import com.jhhy.cuiweitourism.biz.LoginBiz;
import com.jhhy.cuiweitourism.fragment.Tab1Fragment;
import com.jhhy.cuiweitourism.fragment.Tab2Fragment_2;
import com.jhhy.cuiweitourism.fragment.Tab3Fragment;
import com.jhhy.cuiweitourism.fragment.Tab4Fragment2;
import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener
        , View.OnClickListener
{

    private static final String TAG = MainActivity.class.getSimpleName();

//    private RadioGroup radioGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;

    private Fragment mContent;
    private Tab1Fragment tab1Fragment;
//    private Tab1Fragment_3 tab1Fragment_3;
//    private Tab1Fragment_2 tab1Fragment_2;
//    private Tab2Fragment tab2Fragment;
    private Tab2Fragment_2 tab2Fragment_2;
    private Tab3Fragment tab3Fragment;
//    private Tab4Fragment tab4Fragment;
    private Tab4Fragment2 tab4Fragment2;

    public static boolean netOk = false;
    public static boolean logged; //记录用户是否登录
    public static User user;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Consts.MESSAGE_LOGIN:
                    if (1 == msg.arg1) {
                        User user = (User) msg.obj;
                        logged = true;
                        MainActivity.user = user;
                        if (tab4Fragment2 != null) {
                            tab4Fragment2.refreshView();
                        }
                    } else {
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                    }
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请回到个人中心重新登录");
                    break;
                case Consts.MESSAGE_UPDATE_CODE:
//                    ToastUtil.show(getApplicationContext(), "检查更新");
                    if (msg.arg1 == 1){
                        ArrayList<String> list = (ArrayList<String>) msg.obj;
                        String versionName = list.get(0);
                        String url = list.get(1);
                        checkVersionName(versionName, url);
                    }
                    break;
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    loading = false;
                    // 安装文件
                    installApk();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        LogUtil.e("MainActivity", "onCreate");
        getData(savedInstanceState);
        setupView();
        addListener();
        setDefaultFragment(savedInstanceState);
        registerMyReceiver();
        checkUpdate();
    }


    private void getData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            LogUtil.e(TAG, "========= savedInstanceState == null ==============");
        } else {
            LogUtil.e(TAG, "========= savedInstanceState != null ==============");
        }
        Intent intent = getIntent();
        if (intent != null) {
            LogUtil.e(TAG, "========= intent != null ==============");
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                LogUtil.e(TAG, "========= bundle != null ==============");
                user = (User) bundle.getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    logged = true;
                    LogUtil.e(TAG, "========= user = " + user.toString() + " ==============");
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                } else {
                    LogUtil.e(TAG, "========= user = null ==============");
                }
            } else {
                LogUtil.e(TAG, "========= bundle == null ==============");
            }
        } else {
            LogUtil.e(TAG, "========= intent == null ==============");
        }
    }

    private void setupView() {
//        radioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        rb1 = (RadioButton) findViewById(R.id.tab1);
        rb2 = (RadioButton) findViewById(R.id.tab2);
        rb3 = (RadioButton) findViewById(R.id.tab3);
        rb4 = (RadioButton) findViewById(R.id.tab4);
    }

    private void addListener() {
//        radioGroup.setOnCheckedChangeListener(this);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);
    }

    private void setDefaultFragment(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        if (savedInstanceState != null) { // “内存重启”时调用
//            onSaveInstanceState = false;
//            tab1Fragment = (Tab1Fragment) getSupportFragmentManager().findFragmentByTag("1");
            tab1Fragment = (Tab1Fragment) getSupportFragmentManager().findFragmentByTag("1");
//            tab2Fragment = (Tab2Fragment) getSupportFragmentManager().findFragmentByTag("2");
            tab2Fragment_2 = (Tab2Fragment_2) getSupportFragmentManager().findFragmentByTag("2");
//            tab2Fragment_3 = (Tab2Fragment_3) getSupportFragmentManager().findFragmentByTag("2");
            tab3Fragment = (Tab3Fragment) getSupportFragmentManager().findFragmentByTag("3");
            tab4Fragment2 = (Tab4Fragment2) getSupportFragmentManager().findFragmentByTag("4");
            index = savedInstanceState.getInt(KEY_INDEX);
            LogUtil.i(TAG, "index = " + index + ", " + tab1Fragment + ", " + tab2Fragment_2 + ", " + tab3Fragment + ", " + tab4Fragment2 + ", mContent = " + mContent);
            // 根据下标判断离开前是显示哪个Fragment， // 解决重叠问题
            if (tab1Fragment != null) {
                t.hide(tab1Fragment);
            }
            if (tab2Fragment_2 != null) {
                t.hide(tab2Fragment_2);
            }
            if (tab3Fragment != null) {
                t.hide(tab3Fragment);
            }
            if (tab4Fragment2 != null) {
                t.hide(tab4Fragment2);
            }
            if (index == 1) {
                mContent = tab1Fragment;
                t.show(tab1Fragment).commit();
            } else if (index == 2) {
                mContent = tab2Fragment_2;
                t.show(tab2Fragment_2).commit();
            } else if (index == 3) {
                mContent = tab3Fragment;
                t.show(tab3Fragment).commit();
            } else if (index == 4) {
                mContent = tab4Fragment2;
                t.show(tab4Fragment2).commit();
            }
        } else { //正常时调用
            tab1Fragment = Tab1Fragment.newInstance(null, null);
            mContent = tab1Fragment;
            // 这里add时，tag可传可不传
            // .add(R.id.main_content, fragmentHome)
            t.add(R.id.main_content, tab1Fragment, "1").commit();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//        if (logged) {
            Fragment to = null;
            Fragment from = mContent;
            switch (checkedId) {
                case R.id.tab1:
                    index = 1;
                    to = tab1Fragment;
                    break;
                case R.id.tab2:
                    index = 2;
                    if (tab2Fragment_2 == null) {
                        tab2Fragment_2 = Tab2Fragment_2.newInstance(null, null);
                    }
                    to = tab2Fragment_2;
                    break;
                case R.id.tab3:
                    index = 3;
                    if (tab3Fragment == null) {
                        tab3Fragment = Tab3Fragment.newInstance(null, null);
                    }
                    to = tab3Fragment;
                    break;
                case R.id.tab4:
                    index = 4;
                    if (tab4Fragment2 == null) {
                        tab4Fragment2 = Tab4Fragment2.newInstance(null);
                    }
                    to = tab4Fragment2;
                    break;
            }
            if (mContent != to) {
                mContent = to;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); //fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, R.anim.slide_out);
                if (!to.isAdded()) {    // 先判断是否被add过
                    transaction.hide(from).add(R.id.main_content, to, index + "").commit(); //transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(from).show(to).commit(); //transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
                }
            }
//        }else{
//            ToastUtil.show(getApplicationContext(), "请登录后重试");
//        }
    }

    @Override
    public void onClick(View view) {
        Fragment to = null;
        Fragment from = mContent;
        switch (view.getId()){
            case R.id.tab1:
                index = 1;
                to = tab1Fragment;
                break;
            case R.id.tab2:
                index = 2;
//                if (tab2Fragment_2 == null) {
                    tab2Fragment_2 = Tab2Fragment_2.newInstance(null, null);
//                }
                to = tab2Fragment_2;
                break;
            case R.id.tab3:
                if (!logged) {
//                    ToastUtil.show(getApplicationContext(), "请登录后查看订单信息");
                    gotoLogin();
                    changeIndicator();
                    return;
                }
                index = 3;
                if (tab3Fragment == null) {
                    tab3Fragment = Tab3Fragment.newInstance(null, null);
                }
                to = tab3Fragment;
                break;
            case R.id.tab4:
                index = 4;
                if (tab4Fragment2 == null) {
                    tab4Fragment2 = Tab4Fragment2.newInstance(null);
                }else{
                    if(logged){
                        tab4Fragment2.refreshView();
                    }
                }
                to = tab4Fragment2;
                break;
        }
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); //fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, R.anim.slide_out);
            if (!to.isAdded()) {  // 先判断是否被add过
                transaction.hide(from).add(R.id.main_content, to, index + "").commit(); //transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else { //被添加过
                transaction.hide(from).show(to).commit(); //transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
//                transaction.hide(from).replace(R.id.main_content, to, index + ""); //此处是替换？待研究
            }
        }
    }

    private int REQUEST_LOGIN = 5462;

    public void gotoLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOGIN){
            if (RESULT_OK == resultCode){
//                getData(data.getExtras());
                user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    logged = true;
                    LogUtil.e(TAG, "========= user = " + user.toString() + " ==============");
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }
                if (tab4Fragment2 != null) {
                    tab4Fragment2.refreshView();
                }
            }
        }
    }

    private void changeIndicator() {
        if (index == 1){
            rb1.toggle();
        }else if (index == 2){
            rb2.toggle();
        }else if (index == 4){
            rb4.toggle();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Utils.setKeyboardInvisible();
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
        if (data != null) {
            intent.putExtras(data);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginBiz biz = new LoginBiz(null, null);
        biz.logout(null);
        logged = false;
        unregisterReceiver(receiver);
    }

    private NetWorkReceiver receiver;

    private void registerMyReceiver() {
        receiver = new NetWorkReceiver();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);
    }

    class NetWorkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                if (NetworkUtil.checkNetwork(context)) {
                    LogUtil.e(TAG, "网络已连接");
                    if (!logged) { //未登录
                        SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                        String mobile = sp.getTelephoneNumber();
                        String pwd = sp.getPassword();
                        if (mobile != null && pwd != null) {
                            LoginBiz biz = new LoginBiz(getApplicationContext(), handler);
                            biz.login(mobile, pwd);
                        }
                    }
                    if (!netOk) { //无网络
                        netOk = true;
                        if (tab1Fragment != null) {
                            tab1Fragment.getBannerData();
                            tab1Fragment.getData();
                        }
                        if (tab2Fragment_2 != null) {
//                            tab2Fragment_2.refreshView();
                            tab2Fragment_2 = Tab2Fragment_2.newInstance(null, null);
                        }
                    }
                } else {
                    netOk = false;
                    LogUtil.e(TAG, "网络已断开");
                    ToastCommon.toastShortShow(getApplicationContext(), null, "网络已断开");
                }
            }
        }
    }
    //版本对比
//    {"head":{"code":"Publics_versioncompare"},"field":[]}
    private void checkUpdate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getUpdate();
            }
        }, 4000);
    }

    private void getUpdate() {
        CheckCodeBiz biz = new CheckCodeBiz(getApplicationContext(), handler);
        biz.checkUpdate();
    }

    //比较版本号
    private void checkVersionName(String versionName, String url) {
        try {
            String myName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            if (!myName.equals(versionName)) { //如果不相等，则显示下载
                apkUrl = url;
                showNoticeDialog();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean loading; //正在检查更新？还是正在下载？
    private String apkUrl;

    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("软件更新");
        builder.setMessage("检测到新版本，立即更新");
        // 更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                loading = false;
            }
        });
        builder.setCancelable(true);

        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("正在更新");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelUpdate = true;
            }
        });
        builder.setCancelable(false);
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 下载文件
        downloadApk();
    }
    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 下载中 */
    private static final int DOWNLOAD = 111;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 222;
    private String fileName = "cuiweitourism";
    /**
     * 下载文件线程
     *
     * @author dingliping
     * @date 2016-2-22
     */

    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    mSavePath = Environment.getExternalStorageDirectory() + "/" + "download";
//                    URL url = new URL("http://ans.hdvcc.cn:3580/upload/studentApp/" + URLEncoder.encode("明思答题器_2016_11_18", "UTF-8") + ".apk");
                    apkUrl = "http://" + apkUrl;
                    URL url = new URL(apkUrl);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        // 获取文件大小
                        int length = conn.getContentLength();
                        // 创建输入流
                        InputStream is = conn.getInputStream();

                        File file = new File(mSavePath);
                        // 判断文件目录是否存在
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        File apkFile = new File(mSavePath, fileName + ".apk"); //+ newVersionCode
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        // 缓存
                        byte buf[] = new byte[8192];
                        // 写入到文件中
                        do {
                            int numread = is.read(buf);
                            count += numread;
                            // 计算进度条位置
                            progress = (int) (((float) count / length) * 100);
                            // 更新进度
                            handler.sendEmptyMessage(DOWNLOAD);
                            if (numread <= 0) {
                                // 下载完成
                                handler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            // 写入文件
                            fos.write(buf, 0, numread);
                        } while (!cancelUpdate);// 点击取消就停止下载.
                        fos.close();
                        is.close();
                    } else {
                        loading = false;
                        LogUtil.e(TAG, "responseCode = " + conn.getResponseCode());
                    }
                } else {

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                loading = false;
            } catch (IOException e) {
                e.printStackTrace();
                loading = false;
            } finally {
                // 取消下载对话框显示
                mDownloadDialog.dismiss();
                LogUtil.e(TAG, "loading = " + loading);
            }
        }
    }
    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, fileName + ".apk"); //+ newVersionCode
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        startActivity(i);
    }
}
