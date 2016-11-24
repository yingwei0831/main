package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.biz.CuiweiInfoBiz;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CuiweiInfoResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends BaseActionBarActivity {

    private String TAG = "WebViewActivity";

    private WebView webView;
    private ProgressBar progressBar;
    private CuiweiInfoResponse item;
    private String url;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_view);
        getData();
        super.onCreate(savedInstanceState);
        loadUrl();
    }

    private void loadUrl() {
        webView.loadUrl(url); //"http://www.baidu.com"
        if ("http://www.cwly1118.com".equals(url)) {
            ToastUtil.show(getApplicationContext(), "找不到目的地啦，请返回重试~");
        }
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                CuiweiInfoResponse item = (CuiweiInfoResponse) bundle.getSerializable("data");
                if (item != null){
                    url = item.getUrl();
                }else{
                    type = bundle.getInt("type");
                    if (type == 2){
                        getInternetData();
                    }else{
                        url = "http://www.cwly1118.com";
                    }
                }
            }
        }
    }

    private void getInternetData() {
        //        {"head":{"code":"Publics_service"},"field":[]}
        LoadingIndicator.show(WebViewActivity.this, getString(R.string.http_notice));
        CuiweiInfoBiz biz = new CuiweiInfoBiz();
        biz.fetchCuiweiInfo(new BizGenericCallback<ArrayList<CuiweiInfoResponse>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<CuiweiInfoResponse>> model) {
                LogUtil.e(TAG,"homePageCustomAdd =" + model.body.toString());
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<CuiweiInfoResponse> listService = model.body;
                    separator(listService);
                }else if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求失败，请返回重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }

    private void separator(ArrayList<CuiweiInfoResponse> listService) {
            for (int i = 0; i< listService.size(); i++) {
                item = listService.get(i);
                if ("法律声明".equals(item.getServername())) {
                    url = item.getUrl();
                    break;
                }
            }
        if (url == null){
            url = "http://www.cwly1118.com";
            ToastUtil.show(getApplicationContext(), "找不到目的地啦，请返回重试~");
        }
        webView.loadUrl(url);
    }

    @Override
    protected void setupView() {
        super.setupView();
        if (item == null) {
            tvTitle.setText("法律声明");
        }else{
            tvTitle.setText(item.getTitle());
        }
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        webView = (WebView) findViewById(R.id.about_web_view);
        webView.setWebViewClient(new WebViewClient(){
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting = webView.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
                LogUtil.e(TAG, "newProgress = " + newProgress);
            }
        });
    }

    @Override
    protected void addListener() {
        super.addListener();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    //设置返回键动作（防止按返回键直接退出程序)
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO 自动生成的方法存根
//        if(keyCode == KeyEvent.KEYCODE_BACK) {
//            if(webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
//                webView.goBack();
//                return true;
//            } else {//当webview处于第一页面时,直接退出程序
//                System.exit(0);
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
