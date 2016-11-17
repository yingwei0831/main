package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.CitySelectionGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.PhoneAdapter;
import com.jhhy.cuiweitourism.biz.CityBiz;
import com.jhhy.cuiweitourism.dao.CityRecordDao;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
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


public class CitySelectionActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = CitySelectionActivity.class.getSimpleName();

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
    private ArrayList<PhoneBean> list_all;
    /**
     * 显示名字集合
     */
    private ArrayList<PhoneBean> list_show;
    /**
     * 列表适配器
     */
    private PhoneAdapter adapter;
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


    private View headerView;
    private TextView tvTitleTop;
    private ImageView ivTitleLeft;
//    private List<String> citiesHot;
//    private GridView mGridView; //热门城市
//    private CitySelectionGridViewAdapter gvAdapter;

    private TextView tvCurrentCity; //当前定位城市
    private PhoneBean locationCity; //当前城市

    private GridView gvHistoryRecord; //历史记录
    private List<PhoneBean> listHistory = new ArrayList<>();
    private CitySelectionGridViewAdapter gvHistoryAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingIndicator.cancel();
            switch (msg.what){
                case Consts.MESSAGE_GET_CITY:
                    if (msg.arg1 == 1){
                        list_all = (ArrayList<PhoneBean>) msg.obj;
                        if (list_all.size() != 0){
                            setupData();
                            return;
                        }
                    }
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取城市列表失败");
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    break;
                default:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        getData();
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText("选择城市");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        edit_search = (EditText) findViewById(R.id.edit_search);
        edit_search.setHint("北京/beijing");

        listView = (PinnedSectionListView) findViewById(R.id.phone_listview);
        letterIndexView = (LetterIndexView) findViewById(R.id.phone_LetterIndexView);
        txt_center = (TextView) findViewById(R.id.phone_txt_center);

        //头部
        headerView = View.inflate(this, R.layout.header_city_selection, null);
//        mGridView = (GridView) headerView.findViewById(R.id.gv_header_city_selection_hot);
        tvCurrentCity = (TextView) headerView.findViewById(R.id.tv_current_city);

        gvHistoryRecord = (GridView) headerView.findViewById(R.id.gv_header_city_selection_record);

        gvHistoryAdapter = new CitySelectionGridViewAdapter(getApplicationContext(), listHistory);
        gvHistoryAdapter.setType(1);
        gvHistoryRecord.setAdapter(gvHistoryAdapter);
        gvHistoryRecord.setSelector(new ColorDrawable(Color.TRANSPARENT));
        View layoutHotCity = headerView.findViewById(R.id.layout_hot_city);
        layoutHotCity.setVisibility(View.GONE);

        //头部数据
        tvCurrentCity.setText(locationCity.getName());
//        citiesHot = new ArrayList<>();
//        for (int i = 0; i < 13; i++) {
//            citiesHot.add("海南" + i);
//        }
//        gvAdapter = new CitySelectionGridViewAdapter(this, citiesHot);
//        gvAdapter.setType(1);
//        mGridView.setAdapter(gvAdapter);
//        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.addHeaderView(headerView);

        initView();
        initData();
        addListener();
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        tvCurrentCity.setOnClickListener(this);
        gvHistoryRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PhoneBean selectCity = (PhoneBean) gvHistoryAdapter.getItem(i);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectCity", selectCity);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                LogUtil.e(TAG, "selectCity = " + selectCity);
                finish();
            }
        });
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectCity = null;
//                selectCity = (String) gvAdapter.getItem(i);
//                if (selectCity == null){
//                    setResult(RESULT_CANCELED);
//                }else {
//                    Intent intent = new Intent();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("selectCity", selectCity);
//                    intent.putExtras(bundle);
//                    setResult(RESULT_OK, intent);
//                }
//                LogUtil.i(TAG, "selectCity = " + selectCity);
//                finish();
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_current_city:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectCity", locationCity);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                LogUtil.e(TAG, "select currentCity = " + locationCity);
                finish();
                break;
            case R.id.title_main_tv_left_location:
                finish();
                break;
        }
    }

    private void getData() {
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null){
//            currentCity = bundle.getString("currentCity");
//        }
//        if(currentCity == null){
//            currentCity = "北京";
//        }
        SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
        locationCity = sp.getCity();
        if (locationCity == null) {
            locationCity = new PhoneBean();
            locationCity.setName("北京");
            locationCity.setCity_id("20");
        }
        CityRecordDao dao = new CityRecordDao(getApplicationContext());
        dao.getTrainCityRecord();
        listHistory = dao.getSelectionCityRecord();
        if (listHistory.size() == 0) {
            listHistory.add(locationCity);
        }
        getInternetData();
    }

    private void getInternetData() {
        LoadingIndicator.show(CitySelectionActivity.this, getString(R.string.http_notice));
        CityBiz biz = new CityBiz(getApplicationContext(), handler);
        biz.getCitySelection();
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
                String search = editable.toString().trim().toUpperCase();

                if (TextUtils.isEmpty(search)) {
                    for (int i = 0; i < list_all.size(); i++) {
                        PhoneBean bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (!map_IsHead.containsKey(bean.getHeadChar())) {// 如果不包含就添加一个标题
                            PhoneBean bean1 = new PhoneBean();
                            // 设置名字
                            bean1.setName(bean.getName());
                            // 设置标题type
                            bean1.setType(CitySelectionActivity.TITLE);
                            list_show.add(bean1);
                            // map的值为标题的下标
                            map_IsHead.put(bean1.getHeadChar(),
                                    list_show.size() - 1);
                        }
                        // 设置Item type
                        bean.setType(CitySelectionActivity.ITEM);
                        list_show.add(bean);
                    }
                } else {
                    for (int i = 0; i < list_all.size(); i++) {
                        PhoneBean bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (bean.getName().indexOf(search) != -1 || bean.getName_en().indexOf(search) != -1) {
                            if (!map_IsHead.containsKey(bean.getHeadChar())) {// 如果不包含就添加一个标题
                                PhoneBean bean1 = new PhoneBean();
                                // 设置名字
                                bean1.setName(bean.getName());
                                // 设置标题type
                                bean1.setType(CitySelectionActivity.TITLE);
                                list_show.add(bean1);
                                // map的值为标题的下标
                                map_IsHead.put(bean1.getHeadChar(),
                                        list_show.size() - 1);
                            }
                            // 设置Item type
                            bean.setType(CitySelectionActivity.ITEM);
                            list_show.add(bean);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        /** listview点击事件 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list_show.get(i-1).getType() == CitySelectionActivity.ITEM) { // 标题点击不给操作
                    PhoneBean city = list_show.get(i-1);
                    CityRecordDao dao = new CityRecordDao(getApplicationContext());
                    dao.addSelectionCityRecord(city);
                    ToastCommon.toastShortShow(getApplicationContext(), null, city.getName());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectCity", city);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        // 设置标题部分有阴影
        // listView.setShadowVisible(true);
    }

    private char[] headAry;

    protected void initData() {
        list_all = new ArrayList<PhoneBean>();
        list_show = new ArrayList<PhoneBean>();
        map_IsHead = new HashMap<String, Integer>();

        adapter = new PhoneAdapter(CitySelectionActivity.this, list_show, map_IsHead);
        listView.setAdapter(adapter);

        // 开启异步加载数据，不在一个线程，而且不是主线程，容易崩溃
//        new Thread(runnable).start();
//        setupData();

    }

    private void setupData() {
//        String[] str = getResources().getStringArray(R.array.phone_all);

//        for (int i = 0; i < str.length; i++) {
//            PhoneBean cityBean = new PhoneBean();
//            cityBean.setName(str[i]);
//            list_all.add(cityBean);
//        }
        //按拼音排序
        MemberSortUtil sortUtil = new MemberSortUtil();
        Collections.sort(list_all, sortUtil);

        // 初始化数据，顺便放入把标题放入map集合
        for (int i = 0; i < list_all.size(); i++) {
            PhoneBean cityBean = list_all.get(i);
            if (!map_IsHead.containsKey(cityBean.getHeadChar())) { // 如果不包含就添加一个标题
                PhoneBean cityBean1 = new PhoneBean();
                // 设置名字
                cityBean1.setName(cityBean.getName());
                // 设置标题type
                cityBean1.setType(CitySelectionActivity.TITLE);
                list_show.add(cityBean1);

                // map的值为标题的下标
                map_IsHead.put(cityBean1.getHeadChar(), list_show.size() - 1);
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
//                    listView.smoothScrollToPosition(0);
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
//            handler.sendMessage(handler.obtainMessage());
    }

        public class MemberSortUtil implements Comparator<PhoneBean> {
        /**
         * 按拼音排序
         */
        @Override
        public int compare(PhoneBean lhs, PhoneBean rhs) {
            Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
            return cmp.compare(lhs.getName_en(), rhs.getName_en());
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
        Intent intent = new Intent(context, CitySelectionActivity.class);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
