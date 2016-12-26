package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotelProvinceAdapter;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelCityRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.HanziToPinyin;
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


public class HotelCitySelectionActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "HotelCitySelectionActivity";

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
    private List<HotelProvinceResponse.ProvinceBean> list_all;
    /**
     * 显示名字集合
     */
    private List<HotelProvinceResponse.ProvinceBean> list_show;
    /**
     * 列表适配器
     */
    private HotelProvinceAdapter adapter;
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

    private ListView listSearch; //显示搜索数据
    private HotelProvinceAdapter adapterSearch;

    private int type; //省——>市

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
        View layoutTitle = findViewById(R.id.title_city_selection);
        layoutTitle.setVisibility(View.GONE);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_airport);
        radioGroup.setVisibility(View.GONE);

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

        //列表    用到的数据
        list_all = new ArrayList<>();
        list_show = new ArrayList<>();
        map_IsHead = new HashMap<>();

        adapter = new HotelProvinceAdapter(getApplicationContext(), list_show, map_IsHead);
        listView.setAdapter(adapter);

        adapterSearch = new HotelProvinceAdapter(HotelCitySelectionActivity.this, list_show, map_IsHead);
        listSearch = (ListView) findViewById(R.id.list_search);
        listSearch.setAdapter(adapterSearch);

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

    private void getData() {
        list_all = HotelMainActivity.listHotelProvince;
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
                    listSearch.setVisibility(View.GONE);
                    for (int i = 0; i < list_all.size(); i++) {
                        HotelProvinceResponse.ProvinceBean bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (!map_IsHead.containsKey(bean.getHeadChar())) {// 如果不包含就添加一个标题
                            HotelProvinceResponse.ProvinceBean bean1 = new HotelProvinceResponse.ProvinceBean();
                            // 设置名字
                            bean1.setName(bean.getName());
                            bean1.setHeadChar(bean.getHeadChar());
                            // 设置标题type
                            bean1.setType(HotelCitySelectionActivity.TITLE);
                            list_show.add(bean1);
                            // map的值为标题的下标
                            map_IsHead.put(bean1.getHeadChar(), list_show.size() - 1);
                        }
                        // 设置Item type
                        bean.setType(HotelCitySelectionActivity.ITEM);
                        list_show.add(bean);
                    }
                } else {
                    for (int i = 0; i < list_all.size(); i++) {
                        HotelProvinceResponse.ProvinceBean bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (bean.getName().contains(search) || bean.getQuanPin().contains(search) || bean.getJianPin().contains(search)) {
                            if (!map_IsHead.containsKey(bean.getHeadChar())) { // 如果不包含就添加一个标题
                                HotelProvinceResponse.ProvinceBean bean1 = new HotelProvinceResponse.ProvinceBean();
                                // 设置名字
                                bean1.setName(bean.getName());
                                bean1.setHeadChar(bean.getHeadChar());
                                // 设置标题type
                                bean1.setType(HotelCitySelectionActivity.TITLE);
                                list_show.add(bean1);
                                // map的值为标题的下标
                                map_IsHead.put(bean1.getHeadChar(), list_show.size() - 1);
                            }
                            // 设置Item type
                            bean.setType(HotelCitySelectionActivity.ITEM);
                            list_show.add(bean);
                        }
                    }
                    listSearch.setVisibility(View.VISIBLE);
                }
                adapterSearch.notifyDataSetChanged();
            }
        });

        /** listview点击事件 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.e(TAG, "--------------listView---onItemClick--------------");
                if (list_show.get(i).getType() == HotelCitySelectionActivity.ITEM) { // 标题点击不给操作
                    HotelProvinceResponse.ProvinceBean city = list_show.get(i);
                    if (type == 0){ //省份
                        getCityData(city.getID());
                    }else if (type == 1) { //城市
                        ToastCommon.toastShortShow(getApplicationContext(), null, city.getName());
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("selectCity", city);
                        intent.putExtras(bundle);
                        LogUtil.e(TAG, "selectCity = " + city);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.e(TAG, "--------------listSearch---onItemClick--------------");
                if (list_show.get(i).getType() == HotelCitySelectionActivity.ITEM) { // 标题点击不给操作
                    HotelProvinceResponse.ProvinceBean city = list_show.get(i);
                    if (type == 0){ //省份
                        getCityData(city.getID());
                    }else if (type == 1) { //城市
                        ToastCommon.toastShortShow(getApplicationContext(), null, city.getName());
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("selectCity", city);
                        intent.putExtras(bundle);
                        LogUtil.e(TAG, "selectCity = " + city);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
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
            HotelProvinceResponse.ProvinceBean cityBean = list_all.get(i);
            if (!map_IsHead.containsKey(cityBean.getHeadChar())) { // 如果不包含就添加一个标题
                HotelProvinceResponse.ProvinceBean cityBean1 = new HotelProvinceResponse.ProvinceBean();
                // 设置名字
                cityBean1.setName(cityBean.getName());
                // 设置标题type
                cityBean1.setType(HotelCitySelectionActivity.TITLE);
                cityBean1.setHeadChar(cityBean.getHeadChar());
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (type == 1){ //城市
                type--;
                //设置为省份
                list_show.clear();
                map_IsHead.clear();
                list_all = HotelMainActivity.listHotelProvince;
                setupData();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getCityData(String provinceId) {
//        LoadingIndicator.show(HotelCitySelectionActivity.this, getString(R.string.http_notice));
        //网络请求，获取城市数据
        HotelActionBiz biz = new HotelActionBiz();
        HotelCityRequest request = new HotelCityRequest(provinceId);
        biz.getHotelCityList(request, new BizGenericCallback<HotelProvinceResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelProvinceResponse> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    type++;
                    list_all = model.body.getItem();
                    list_show.clear();
                    map_IsHead.clear();
                    setupData();
                }else if ("0001".equals(model.headModel.res_code)){
                    ToastUtil.show(getApplicationContext(), model.headModel.res_arg);
                }
//                LoadingIndicator.cancel();
                LogUtil.e(TAG, model.body.getItem());
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "获取城市信息失败，请重试");
                }
//                LoadingIndicator.cancel();
                LogUtil.e(TAG, error.toString());
            }
        });
    }

    public class MemberSortUtil implements Comparator<HotelProvinceResponse.ProvinceBean> {
        /**
         * 按拼音排序
         */
        @Override
        public int compare(HotelProvinceResponse.ProvinceBean lhs, HotelProvinceResponse.ProvinceBean rhs) {
            Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
            String q1 = lhs.getQuanPin().replace("(", "").replace(")", "");
            String q2 = rhs.getQuanPin().replace("(", "").replace(")", "");
            return cmp.compare(q1, q2);
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
        Intent intent = new Intent(context, HotelCitySelectionActivity.class);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
