package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.CitySelectionGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.TrainStationAdapter;
import com.jhhy.cuiweitourism.dao.CityRecordDao;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.view.LetterIndexView;
import com.jhhy.cuiweitourism.view.PinnedSectionListView;
import com.just.sun.pricecalendar.ToastCommon;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class TrainCitySelectionActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = TrainCitySelectionActivity.class.getSimpleName();

    /**
     * 搜索栏
     */
    EditText edit_search;
    /**
     * 列表
     */
    PinnedSectionListView listView;
    /**
     * 右边字母列表
     */
    LetterIndexView letterIndexView;
    /**
     * 中间显示右边按的字母
     */
    TextView txt_center;

    /**
     * 所有名字集合
     */
    private ArrayList<TrainStationInfo> list_all;
    /**
     * 显示名字集合
     */
    private ArrayList<TrainStationInfo> list_show;
    /**
     * 列表适配器
     */
    private TrainStationAdapter adapter;
    /**
     * 保存名字首字母
     */
    public HashMap<String, Integer> map_IsHead;
    /**
     * item标识为0
     */
    public static final int ITEM = 0;
    /**
     * item标题标识为1
     */
    public static final int TITLE = 1;
    private TextView tvTitleTop;
    private ImageView ivTitleLeft;
    private ImageView ivSearchLeft; //搜索栏左边的返回图标

    private View headerView;
    private List<TrainStationInfo> citiesHot;
    private GridView mHotGridView; //热门城市
    private CitySelectionGridViewAdapter gvHotAdapter;

    private TextView tvCurrentCity; //当前定位城市
    private TrainStationInfo currentCity; //当前城市

    private View layoutHistory; //历史记录布局
    private GridView gvHistoryRecord; //历史记录
    private List<TrainStationInfo> listHistory = new ArrayList<>();
    private CitySelectionGridViewAdapter gvHistoryAdapter;

    private ListView listSearch; //显示搜索数据
    private TrainStationAdapter adapterSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        getData();
        setupView();
        getRecordData();
        initView();
        addListener();
        setupData();
    }

    private void setupView() {
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText("选择车站");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        edit_search = (EditText) findViewById(R.id.edit_search);
        edit_search.setHint("北京/beijing/bj");
        ivSearchLeft = (ImageView) findViewById(R.id.iv_title_search_left);
        ivSearchLeft.setVisibility(View.GONE);
        listView = (PinnedSectionListView) findViewById(R.id.phone_listview);
        letterIndexView = (LetterIndexView) findViewById(R.id.phone_LetterIndexView);
        txt_center = (TextView) findViewById(R.id.phone_txt_center);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_airport);
        radioGroup.setVisibility(View.GONE);
        //头部
        headerView = View.inflate(this, R.layout.header_city_selection, null);
        //当前城市
        tvCurrentCity = (TextView) headerView.findViewById(R.id.tv_current_city);
        tvCurrentCity.setText(currentCity.getName());
        //历史记录
        layoutHistory = headerView.findViewById(R.id.layout_history);
        gvHistoryRecord = (GridView) headerView.findViewById(R.id.gv_header_city_selection_record);
        gvHistoryAdapter = new CitySelectionGridViewAdapter(getApplicationContext(), listHistory);
        gvHistoryRecord.setAdapter(gvHistoryAdapter);
        gvHistoryRecord.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //热门城市
        mHotGridView = (GridView) headerView.findViewById(R.id.gv_header_city_selection_hot); //热门城市
        citiesHot = new ArrayList<>();
        gvHotAdapter = new CitySelectionGridViewAdapter(this, citiesHot);
        mHotGridView.setAdapter(gvHotAdapter);
        mHotGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.addHeaderView(headerView);

        adapter = new TrainStationAdapter(TrainCitySelectionActivity.this, list_show, map_IsHead);
        listView.setAdapter(adapter);

        adapterSearch = new TrainStationAdapter(TrainCitySelectionActivity.this, list_show, map_IsHead);
        listSearch = (ListView) findViewById(R.id.list_search);
        listSearch.setAdapter(adapterSearch);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        tvCurrentCity.setOnClickListener(this);
        gvHistoryRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TrainStationInfo selectCity = (TrainStationInfo) gvHistoryAdapter.getItem(i);
                if (selectCity == null){
                    setResult(RESULT_CANCELED);
                }else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectCity", selectCity);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                }
                LogUtil.e(TAG, "selectCity = " + selectCity);
                finish();
            }
        });

        mHotGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TrainStationInfo selectCity = (TrainStationInfo) gvHotAdapter.getItem(i);
                if (selectCity == null){
                    setResult(RESULT_CANCELED);
                }else {
                    CityRecordDao dao = new CityRecordDao(getApplicationContext());
                    dao.addTrainCityRecord(selectCity);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectCity", selectCity);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                }
                LogUtil.e(TAG, "selectCity = " + selectCity);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_current_city:
                Intent intent = new Intent();
                String selectCity = tvCurrentCity.getText().toString();
                currentCity.setName(selectCity);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectCity", currentCity);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                LogUtil.i(TAG, "select currentCity = " + selectCity);
                finish();
                break;
            case R.id.title_main_tv_left_location:
                finish();
                break;
        }
    }

    private void getData() {
        //列表    用到的数据
        list_all = new ArrayList<TrainStationInfo>();
        list_show = new ArrayList<TrainStationInfo>();
        map_IsHead = new HashMap<String, Integer>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            currentCity = bundle.getParcelable("currentCity");
        }
        if (currentCity == null) {
            currentCity = new TrainStationInfo();
            String city = SharedPreferencesUtils.getInstance(getApplicationContext()).getLocationCity();
            if (city == null) {
                city = "北京市";
            }
            currentCity.setName(city.substring(0, city.indexOf("市")));
        }
        list_all = TrainMainActivity.stations;
    }

    /**
     * 从本地数据库获取历史记录
     */
    private void getRecordData() {
        CityRecordDao dao = new CityRecordDao(getApplicationContext());
        List<TrainStationInfo> recordList = dao.getTrainCityRecord();
        LogUtil.e(TAG, "recordList = " + recordList);
        if (recordList == null || recordList.size() == 0){
            layoutHistory.setVisibility(View.GONE);
        }else{
            listHistory = recordList;
            gvHistoryAdapter.setData(listHistory);
            gvHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list_all = null;
    }

    private void initView() {
        // 输入监听
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                list_show.clear();
                map_IsHead.clear();
                //把输入的字符改成大写
                String search = editable.toString().trim().toLowerCase();
                LogUtil.e(TAG, "search = " + search);

                if (TextUtils.isEmpty(search)) {
                    listSearch.setVisibility(View.GONE);
                    for (int i = 0; i < list_all.size(); i++) {
                        TrainStationInfo bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (!map_IsHead.containsKey(bean.getHeadChar())) {// 如果不包含就添加一个标题
                            TrainStationInfo bean1 = new TrainStationInfo();
                            // 设置名字
                            bean1.setName(bean.getName());
                            bean1.setHeadChar(bean.getHeadChar());
                            // 设置标题type
                            bean1.setType(TrainCitySelectionActivity.TITLE);
                            list_show.add(bean1);
                            // map的值为标题的下标
                            map_IsHead.put(bean1.getHeadChar(), list_show.size() - 1);
                        }
                        // 设置Item type
                        bean.setType(TrainCitySelectionActivity.ITEM);
                        list_show.add(bean);
                    }
                } else {
                    for (int i = 0; i < list_all.size(); i++) {
                        TrainStationInfo bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (bean.getName().indexOf(search) != -1 || bean.getFullPY().indexOf(search) != -1 || bean.getShortPY().indexOf(search) != -1) {
                            if (!map_IsHead.containsKey(bean.getHeadChar())) { // 如果不包含就添加一个标题
                                TrainStationInfo bean1 = new TrainStationInfo();
                                // 设置名字
                                bean1.setName(bean.getName());
                                bean1.setHeadChar(bean.getHeadChar());
                                // 设置标题type
                                bean1.setType(TrainCitySelectionActivity.TITLE);
                                list_show.add(bean1);
                                // map的值为标题的下标
                                map_IsHead.put(bean1.getHeadChar(), list_show.size() - 1);
                            }
                            // 设置Item type
                            bean.setType(TrainCitySelectionActivity.ITEM);
                            list_show.add(bean);
                        }
                    }
                    listSearch.setVisibility(View.VISIBLE);
                }
//                adapter.notifyDataSetChanged();
                adapterSearch.notifyDataSetChanged();
            }
        });

        /** listview点击事件 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list_show.get(i-1).getType() == TrainCitySelectionActivity.ITEM) { // 标题点击不给操作
                    TrainStationInfo city = list_show.get(i-1);
                    ToastCommon.toastShortShow(getApplicationContext(), null, city.getName());
                    CityRecordDao dao = new CityRecordDao(getApplicationContext());
                    dao.addTrainCityRecord(city);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectCity", city);
                    intent.putExtras(bundle);
                    LogUtil.e(TAG, "selectCity = " + city);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.e(TAG, "--------------listSearch---onItemClick--------------");
                if (list_show.get(i).getType() == TrainCitySelectionActivity.ITEM) { // 标题点击不给操作
                    TrainStationInfo city = list_show.get(i);
                    ToastCommon.toastShortShow(getApplicationContext(), null, city.getName());
                    CityRecordDao dao = new CityRecordDao(getApplicationContext());
                    dao.addTrainCityRecord(city);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectCity", city);
                    intent.putExtras(bundle);
                    LogUtil.e(TAG, "selectCity = " + city);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        // 设置标题部分有阴影
        // listView.setShadowVisible(true);
    }

    private char[] headAry;

    private void setupData() {
        //按拼音排序
        MemberSortUtil sortUtil = new MemberSortUtil();
        Collections.sort(list_all, sortUtil);

        // 初始化数据，顺便放入把标题放入map集合
        for (int i = 0; i < list_all.size(); i++) {
            TrainStationInfo cityBean = list_all.get(i);
            if (!map_IsHead.containsKey(cityBean.getHeadChar())) { // 如果不包含就添加一个标题
                TrainStationInfo cityBean1 = new TrainStationInfo();
                // 设置名字
                cityBean1.setName(cityBean.getName());
                // 设置标题type
                cityBean1.setType(TrainCitySelectionActivity.TITLE);
                cityBean1.setHeadChar(cityBean.getHeadChar());
                list_show.add(cityBean1);

                // map的值为标题的下标
                map_IsHead.put(cityBean1.getHeadChar(), list_show.size() - 1);
            }
            if (cityBean.isHot()){
                citiesHot.add(cityBean);
            }
            list_show.add(cityBean);
        }

        headAry = new char[map_IsHead.size()];
        Set<String> keySet = map_IsHead.keySet();
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()){
            String s = it.next();
            sb.append(s);
        }
        headAry = order(sb.toString());

        //-------------在initview方法中，为了不空指针，放在此处-------------
        // 右边字母竖排的初始化以及监听
        letterIndexView.init(new LetterIndexView.OnTouchLetterIndex() {
            //实现移动接口
            @Override
            public void touchLetterWitch(String letter) {
                // 中间显示的首字母
                txt_center.setVisibility(View.VISIBLE);
                txt_center.setText(letter);
                // 首字母是否被包含
                if (adapter.map_IsHead.containsKey(letter)) {
                    // 设置首字母的位置
                    listView.setSelection(adapter.map_IsHead.get(letter) + 1);
                } else {
                    listView.setSelection(0);
                }
            }

            //实现抬起接口
            @Override
            public void touchFinish() {
                txt_center.setVisibility(View.GONE);
            }
        }, headAry);
        //-------------end-----------

        adapter.notifyDataSetChanged();
        gvHotAdapter.notifyDataSetChanged();
        LoadingIndicator.cancel(); //handler.sendMessage(handler.obtainMessage());
    }

        public class MemberSortUtil implements Comparator<TrainStationInfo> {
        /**
         * 按拼音排序
         */
        @Override
        public int compare(TrainStationInfo lhs, TrainStationInfo rhs) {
            Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
            return cmp.compare(lhs.getFullPY(), rhs.getFullPY());
        }
    }

    /**
     * 将输入的字符串，转化为char数组输出
     */
    private char[] order(String s){
        char[] ch = s.toCharArray();
        Arrays.sort(ch);
        return ch;
    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, TrainCitySelectionActivity.class);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
