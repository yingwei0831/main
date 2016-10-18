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
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.moudle.ClassifyArea;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class SelectAllCountryAreaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private ListView listViewFirst;
    private ListView listViewSecond;

    private List<ClassifyArea> listFirst = new ArrayList<>();
    private VisaConnectionListAdapter adapterFirst;

    private List<ClassifyArea> listSecond = new ArrayList<>();
    private VisaConnection2ListAdapter adapterSecond;

    private int firstSelection;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_VISA_MORE_COUNTRY:
                    if (msg.arg1 == 1){
                        listFirst = (List<ClassifyArea>) msg.obj;
                        if (listFirst == null || listFirst.size() == 0){
                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取全部签证地区失败");
                        }else{
                            adapterFirst.setSelection(0);
                            adapterFirst.setData(listFirst);
                            adapterSecond.setData(listFirst.get(0).getListProvince());
                        }
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
        getData();
        setupView();
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
                adapterSecond.setData(listFirst.get(i).getListProvince());
            }
        });

        listViewSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listSecond = listFirst.get(firstSelection).getListProvince();
                Intent intent = new Intent(getApplicationContext(), VisaListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nationId", listSecond.get(i).getAreaId());
                bundle.putString("nationName", listSecond.get(i).getAreaName());
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
        adapterFirst = new VisaConnectionListAdapter(getApplicationContext(), listFirst);
        adapterFirst.setSelection(0);
        listViewFirst.setAdapter(adapterFirst);

        listViewSecond = (ListView) findViewById(R.id.list_son);
        adapterSecond = new VisaConnection2ListAdapter(getApplicationContext(), listSecond);
        listViewSecond.setAdapter(adapterSecond);
    }

    private void getData() {
        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
        biz.getAllHotCountry();
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
