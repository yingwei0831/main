package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaConnection2ListAdapter;
import com.jhhy.cuiweitourism.adapter.VisaConnectionListAdapter;
import com.jhhy.cuiweitourism.adapter.VisaHotCountryGridAdapter;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.moudle.ClassifyArea;
import com.jhhy.cuiweitourism.net.biz.VisaActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.VisaCountry;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaCountryInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SelectAllCountryAreaActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = SelectAllCountryAreaActivity.class.getSimpleName();

    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private ListView listViewFirst;
    private ListView listViewSecond;

    private List listFirst = new ArrayList<>();
    private VisaConnectionListAdapter adapterFirst;
//
    private List<VisaCountryInfo> listSecond = new ArrayList<>();
    private VisaConnection2ListAdapter adapterSecond;

    private List<VisaCountryInfo> listCountry = new ArrayList<>(); //所有list
    private LinkedHashMap<String, List<VisaCountryInfo>> leftContinent = new LinkedHashMap<>(); //左边洲,对应右边国家集合

    private int firstSelection;
    private VisaActionBiz biz; //热门签证国家，查看全部国家和地区

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_VISA_MORE_COUNTRY:
                    if (msg.arg1 == 1){
//                        listFirst = (List<ClassifyArea>) msg.obj;
//                        if (listFirst == null || listFirst.size() == 0){
//                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取全部签证地区失败");
//                        }else{
//                            adapterFirst.setSelection(0);
//                            adapterFirst.setData(listFirst);
//                            adapterSecond.setData(listFirst.get(0).getListProvince());
//                        }
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
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影

        setContentView(R.layout.activity_select_all_country_area);
        setupView();
        getData();
        addListener();
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        listViewFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                firstSelection = i;
                adapterFirst.setSelection(i);
                adapterFirst.notifyDataSetChanged();
                listSecond = leftContinent.get(leftContinent.keySet().toArray()[firstSelection]);
                adapterSecond.setData(listSecond);
            }
        });

        listViewSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                listSecond = listFirst.get(firstSelection).getListProvince();
                VisaCountryInfo info = listSecond.get(i);
                Intent intent = new Intent(getApplicationContext(), VisaListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nationId", info.getCountryCode());
                bundle.putString("nationName", info.getCountryName());
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_VISA_CITY);
            }
        });
    }

    private int REQUEST_VISA_CITY = 1811;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VISA_CITY){
            if (resultCode == RESULT_OK){

            }
        }
    }

    private void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.visa_hot_all_country_title));
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        listViewFirst = (ListView) findViewById(R.id.list_father);
        listFirst = new ArrayList(Arrays.asList(leftContinent.keySet().toArray()));
        adapterFirst = new VisaConnectionListAdapter(getApplicationContext(), listFirst);
        adapterFirst.setSelection(0);
        listViewFirst.setAdapter(adapterFirst);

        listViewSecond = (ListView) findViewById(R.id.list_son);
        adapterSecond = new VisaConnection2ListAdapter(getApplicationContext(), listSecond);
        listViewSecond.setAdapter(adapterSecond);
        biz = new VisaActionBiz(getApplicationContext(), handler);
    }



    private void getData() {
//        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
//        biz.getAllHotCountry();
        VisaCountry visaCountry = new VisaCountry();
        visaCountry.setIsHot("N");
        biz.getVisaCountry(visaCountry, new BizGenericCallback<ArrayList<VisaCountryInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<VisaCountryInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    listCountry = model.body;
                    LogUtil.e(TAG,"visaCountryInfo: " + listCountry.toString());
                    screenCountry();
                    refreshView();
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "热门签证国家数据出错");
                }
                LogUtil.e(TAG, "visaCountryInfo: " + error.toString());
            }
        });
    }

    private void refreshView() {
        adapterFirst.setData(listFirst);
        adapterSecond.setData(leftContinent.get(leftContinent.keySet().toArray()[0]));
    }

    private void screenCountry() {
        for (VisaCountryInfo country : listCountry){
            String continentName = country.getContinentName(); //洲名字
            if (leftContinent.containsKey(continentName)){ //如果包含，则放入左边大洲对应的右边国家集合里面
               leftContinent.get(continentName).add(country);
            }else{ //如果不包含，则新建右边国家集合，再放入左边大洲 集合里面
                List<VisaCountryInfo> continentCountry = new ArrayList<>();
                continentCountry.add(country);
                leftContinent.put(continentName, continentCountry);
            }
        }
        listFirst = new ArrayList(Arrays.asList(leftContinent.keySet().toArray()));
        listSecond = leftContinent.get(leftContinent.keySet().toArray()[0]);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
        }
    }
}
