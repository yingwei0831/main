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
import com.jhhy.cuiweitourism.adapter.VisaConnection2ListAdapter;
import com.jhhy.cuiweitourism.adapter.VisaConnectionListAdapter;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.moudle.ClassifyArea;
import com.jhhy.cuiweitourism.moudle.VisaHotCountryCity;
import com.jhhy.cuiweitourism.utils.Consts;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class SelectAllCountryAreaActivity extends BaseActivity {

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
        ActionBar bar =  getSupportActionBar();
        //自定义一个布局，并居中
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        setContentView(R.layout.activity_select_all_country_area);
        getData();
        setupView();
        addListener();
    }

    private void addListener() {
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
}
