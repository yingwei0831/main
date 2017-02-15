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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.CitySelectionGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.PlaneAirportAdapter;
import com.jhhy.cuiweitourism.adapter.TrainStationAdapter;
import com.jhhy.cuiweitourism.dao.CityRecordDao;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
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


public class PlaneCitySelectionActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "PlaneCitySelectionActivity";
//    private TextView tvTitle;
//    private ImageView ivTitleLeft;
    private View layoutTitle;
    private RadioGroup radioGroup; //国内城市，国际城市
    private RadioButton radioButtonInner;
    private RadioButton radioButtonOuter;
    private int cityType = 1; //国内/国外
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
    private ArrayList<PlaneTicketCityInfo> list_all;
    /**
     * 显示名字集合
     */
    private ArrayList<PlaneTicketCityInfo> list_show;
    /**
     * 列表适配器
     */
    private PlaneAirportAdapter adapter;
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

    private ImageView ivSearchLeft; //搜索栏左边的返回图标

//    private View headerView;
//    private List<TrainStationInfo> citiesHot;
//    private GridView mHotGridView; //热门城市
//    private CitySelectionGridViewAdapter gvHotAdapter;

//    private TextView tvCurrentCity; //当前定位城市
//    private TrainStationInfo currentCity; //当前城市

//    private View layoutHistory; //历史记录布局
//    private GridView gvHistoryRecord; //历史记录
//    private List<TrainStationInfo> listHistory = new ArrayList<>();
//    private CitySelectionGridViewAdapter gvHistoryAdapter;

    private ListView listSearch; //显示搜索数据
    private PlaneAirportAdapter adapterSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        setupView();
        initView();
        getData();
        setupData();
    }

    private void setupView() {
        layoutTitle = findViewById(R.id.title_city_selection);
        layoutTitle.setVisibility(View.GONE);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_airport);
        radioButtonInner = (RadioButton) findViewById(R.id.rb_city_inner);
        radioButtonOuter = (RadioButton) findViewById(R.id.rb_city_outer);
//        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
//        tvTitle.setText(getString(R.string.tab4_account_certification_gender_notice));
//        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        View layoutSearch = findViewById(R.id.layout_search);
        layoutSearch.setBackgroundColor(Color.WHITE);
        layoutSearch.setPadding(0, 10, 20, 10);
        edit_search = (EditText) findViewById(R.id.edit_search);
        edit_search.setHint("北京/beijing/bj");
        edit_search.setBackgroundColor(getResources().getColor(R.color.colorTab2Space));
        ivSearchLeft = (ImageView) findViewById(R.id.iv_title_search_left);
        ivSearchLeft.setVisibility(View.VISIBLE);
        listView = (PinnedSectionListView) findViewById(R.id.phone_listview);
        letterIndexView = (LetterIndexView) findViewById(R.id.phone_LetterIndexView);
        txt_center = (TextView) findViewById(R.id.phone_txt_center);

        //头部
//        headerView = View.inflate(this, R.layout.header_city_selection, null);
        //当前城市
//        tvCurrentCity = (TextView) headerView.findViewById(R.id.tv_current_city);
        //历史记录
//        layoutHistory = headerView.findViewById(R.id.layout_history);
//        gvHistoryRecord = (GridView) headerView.findViewById(R.id.gv_header_city_selection_record);
//        gvHistoryAdapter = new CitySelectionGridViewAdapter(getApplicationContext(), listHistory);
//        gvHistoryRecord.setAdapter(gvHistoryAdapter);
//        gvHistoryRecord.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //热门城市
//        mHotGridView = (GridView) headerView.findViewById(R.id.gv_header_city_selection_hot); //热门城市
//        citiesHot = new ArrayList<>();
//        gvHotAdapter = new CitySelectionGridViewAdapter(this, citiesHot);
//        mHotGridView.setAdapter(gvHotAdapter);
//        mHotGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        listView.addHeaderView(headerView);

        //列表    用到的数据
        list_all = new ArrayList<PlaneTicketCityInfo>();
        list_show = new ArrayList<PlaneTicketCityInfo>();
        map_IsHead = new HashMap<String, Integer>();

        adapter = new PlaneAirportAdapter(PlaneCitySelectionActivity.this, list_show, map_IsHead);
        listView.setAdapter(adapter);

        adapterSearch = new PlaneAirportAdapter(PlaneCitySelectionActivity.this, list_show, map_IsHead);
        listSearch = (ListView) findViewById(R.id.list_search);
        listSearch.setAdapter(adapterSearch);

        radioGroup.setOnCheckedChangeListener(this);
        ivSearchLeft.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_search_left:
                finish();
                break;
        }
    }
    private int type = 1; //1:国内机场 2:国外机场
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            type = bundle.getInt("type");
        }
        if (type == 1 || type == 3) {
            list_all = PlaneMainActivity.airportInner;
            cityType = 1;
            radioButtonInner.setChecked(true);
        } else if (type == 2) {
            list_all = PlaneMainActivity.airportOuter;
            cityType = 2;
            radioButtonOuter.setChecked(true);
        }else if (type == 10){ //保险，国内
            radioGroup.setVisibility(View.GONE);
            list_all = InsuranceMainActivity.airportInner;
        }else if (type == 11){ //保险，国际
            radioGroup.setVisibility(View.GONE);
            list_all = InsuranceMainActivity.airportOuter;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        list_show.clear();
        map_IsHead.clear();
        switch (i){
            case R.id.rb_city_inner:
                list_all = PlaneMainActivity.airportInner;
                cityType = 1;
                break;
            case R.id.rb_city_outer:
                list_all = PlaneMainActivity.airportOuter;
                cityType = 2;
                break;
        }
        setupData();
    }

    private void initView() {
//        ivTitleLeft.setOnClickListener(this);
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
                String search = editable.toString().trim().toUpperCase();
                LogUtil.e(TAG, "search = " + search);

                if (TextUtils.isEmpty(search)) {
                    listSearch.setVisibility(View.GONE);
                    for (int i = 0; i < list_all.size(); i++) {
                        PlaneTicketCityInfo bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (!map_IsHead.containsKey(bean.headChar)) {// 如果不包含就添加一个标题
                            PlaneTicketCityInfo bean1 = new PlaneTicketCityInfo();
                            // 设置名字
                            bean1.setName(bean.getName());
                            bean1.headChar = bean.headChar;
                            // 设置标题type
                            bean1.type = PlaneCitySelectionActivity.TITLE;
                            list_show.add(bean1);
                            // map的值为标题的下标
                            map_IsHead.put(bean1.headChar, list_show.size() - 1);
                        }
                        // 设置Item type
                        bean.type = PlaneCitySelectionActivity.ITEM;
                        list_show.add(bean);
                    }
                } else {
                    for (int i = 0; i < list_all.size(); i++) {
                        PlaneTicketCityInfo bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (bean.getName().contains(search) || bean.fullPY.contains(search) || bean.shortPY.contains(search)) {
                            if (!map_IsHead.containsKey(bean.headChar)) { // 如果不包含就添加一个标题
                                PlaneTicketCityInfo bean1 = new PlaneTicketCityInfo();
                                // 设置名字
                                bean1.setName(bean.getName());
                                bean1.headChar = bean.headChar;
                                // 设置标题type
                                bean1.type = PlaneCitySelectionActivity.TITLE;
                                list_show.add(bean1);
                                // map的值为标题的下标
                                map_IsHead.put(bean1.headChar, list_show.size() - 1);
                            }
                            // 设置Item type
                            bean.type = PlaneCitySelectionActivity.ITEM;
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
                LogUtil.e(TAG, "--------------listView---onItemClick--------------");
                if (list_show.get(i).type == PlaneCitySelectionActivity.ITEM) { // 标题点击不给操作
                    PlaneTicketCityInfo city = list_show.get(i);
                    ToastCommon.toastShortShow(getApplicationContext(), null, city.getName());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectCity", city);
                    bundle.putInt("typeSearch", cityType);
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
                if (list_show.get(i).type == PlaneCitySelectionActivity.ITEM) { // 标题点击不给操作
                    PlaneTicketCityInfo city = list_show.get(i);
                    ToastCommon.toastShortShow(getApplicationContext(), null, city.getName());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectCity", city);
                    bundle.putInt("typeSearch", cityType);
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
        if (list_all == null){
            ToastUtil.show(getApplicationContext(), "获取城市列表失败，请返回重试");
            return;
        }
        //按拼音排序
        MemberSortUtil sortUtil = new MemberSortUtil();
        Collections.sort(list_all, sortUtil);

        // 初始化数据，顺便放入把标题放入map集合
        for (int i = 0; i < list_all.size(); i++) {
            PlaneTicketCityInfo cityBean = list_all.get(i);
            if (!map_IsHead.containsKey(cityBean.headChar)) { // 如果不包含就添加一个标题
                PlaneTicketCityInfo cityBean1 = new PlaneTicketCityInfo();
                // 设置名字
                cityBean1.setName(cityBean.getName());
                // 设置标题type
                cityBean1.type = PlaneCitySelectionActivity.TITLE;
                cityBean1.headChar = cityBean.headChar;
                list_show.add(cityBean1);

                // map的值为标题的下标
                map_IsHead.put(cityBean1.headChar, list_show.size() - 1);
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
                    listView.setSelection(adapter.map_IsHead.get(letter));
                } else {
                    listView.setSelection(0);
                }
            }

            //实现抬起接口
            @Override
            public void touchFinish() {
                txt_center.setVisibility(View.GONE);
            }
        }, headAry, 0);
        //-------------end-----------

        adapter.notifyDataSetChanged();
        LoadingIndicator.cancel(); //handler.sendMessage(handler.obtainMessage());
    }


    public class MemberSortUtil implements Comparator<PlaneTicketCityInfo> {
        /**
         * 按拼音排序
         */
        @Override
        public int compare(PlaneTicketCityInfo lhs, PlaneTicketCityInfo rhs) {
            Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
            return cmp.compare(lhs.fullPY, rhs.fullPY);
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
        Intent intent = new Intent(context, PlaneCitySelectionActivity.class);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
