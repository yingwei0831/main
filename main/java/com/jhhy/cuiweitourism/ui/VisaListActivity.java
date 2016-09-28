package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaListAdapter;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.moudle.VisaHotCountryCity;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.LogUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class VisaListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private String TAG = VisaListActivity.class.getSimpleName();

    private String nationId;

    private ListView listVisa;
    private VisaListAdapter adapter;

    private List<VisaHotCountryCity> lists = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_VISA_HOT_COUNTRY_LIST:
                    if (msg.arg1 == 1){
                        lists = (List<VisaHotCountryCity>) msg.obj;
                        if (lists.size() != 0){
                            adapter.setData(lists);
                            LogUtil.e(TAG, "list = " + lists.get(0));
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取数据失败");
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
        ActionBar bar =  getSupportActionBar();
        //自定义一个布局，并居中
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

        setContentView(R.layout.activity_visa_list);
        setupView();
        getData();
        addListener();
    }

    private void addListener() {
        listVisa.setOnItemClickListener(this);
    }

    private void setupView() {
        listVisa = (ListView) findViewById(R.id.list_view_visa);
        adapter = new VisaListAdapter(getApplicationContext(), lists);
        listVisa.setAdapter(adapter);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        nationId = bundle.getString("nationId");
        getInternetData();
    }

    private void getInternetData() {
        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
        biz.getCountryList(nationId);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String id = lists.get(i).getId();
        Intent intent = new Intent(getApplicationContext(), VisaItemDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQEUST_VIES_DETAIL);
    }

    private int REQEUST_VIES_DETAIL = 1881;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQEUST_VIES_DETAIL){
            if (resultCode == RESULT_OK){

            }
        }
    }
}
