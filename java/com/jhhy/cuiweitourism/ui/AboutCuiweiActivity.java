package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.biz.CuiweiInfoBiz;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CuiweiInfoResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Locale;

public class AboutCuiweiActivity extends BaseActionBarActivity {

    private String TAG = "AboutCuiweiActivity";
    private TextView aboutUs;
    private TextView contactUs;
    private TextView joinUs;
    private TextView aboutIcon;
    private TextView statement;

    private TextView tvCuiweiLeve; //版本号
    private String name;
    private ArrayList<CuiweiInfoResponse> listService = new ArrayList<>();
    private String nameKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_cuiwei);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.fragment_mine_about_cuiwei));
        aboutUs = (TextView) findViewById(R.id.tv_about_us);
        contactUs = (TextView) findViewById(R.id.tv_contact_us);
        joinUs = (TextView) findViewById(R.id.tv_join_us_rule);
        aboutIcon = (TextView) findViewById(R.id.tv_about_travel_icon);
        statement = (TextView) findViewById(R.id.tv_about_law_statement);

        tvCuiweiLeve = (TextView) findViewById(R.id.tv_cuiwei_level);
//        SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
        try {
            name = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvCuiweiLeve.setText(String.format(Locale.getDefault(), "翠微 V%s", name));
        getInternetData();
    }

    private void getInternetData() {
//        {"head":{"code":"Publics_service"},"field":[]}
        CuiweiInfoBiz biz = new CuiweiInfoBiz();
        biz.fetchCuiweiInfo(new BizGenericCallback<ArrayList<CuiweiInfoResponse>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<CuiweiInfoResponse>> model) {
                LogUtil.e(TAG,"homePageCustomAdd =" + model.body.toString());
                if ("0000".equals(model.headModel.res_code)) {
                    listService = model.body;
                }else if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求失败，请返回重试");
                }
            }
        });
    }

    @Override
    protected void addListener() {
        super.addListener();
        aboutUs.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        joinUs.setOnClickListener(this);
        aboutIcon.setOnClickListener(this);
        statement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_about_us:
                nameKey = "关于我们";
                separatorUrl();
                break;
            case R.id.tv_contact_us:
                nameKey = "联系我们";
                separatorUrl();
                break;
            case R.id.tv_join_us_rule:
                nameKey = "入驻规则";
                separatorUrl();
                break;
            case R.id.tv_about_travel_icon:
                nameKey = "关于旅游币";
                separatorUrl();
                break;
            case R.id.tv_about_law_statement:
                nameKey = "法律声明";
                separatorUrl();
                break;
        }
    }

    private void separatorUrl() {
        for (int i = 0; i< listService.size(); i++){
            CuiweiInfoResponse item = listService.get(i);
            if (nameKey.equals(item.getServername())){
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", item);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            }
        }

    }
}
