package com.jhhy.cuiweitourism.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.view.InnerTravelDetailDescribeView;
import com.ns.fhvp.IPagerScroll;
import com.ns.fhvp.PagerScrollUtils;

public class BlankFragment extends Fragment implements IPagerScroll{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private int days = 3;
    private ScrollView layoutContainer;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param param1 需要的步数
     * @param param2
     * @return
     */
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);

        layoutContainer = (ScrollView) view.findViewById(R.id.layout_container);
//        for(int i = 0; i < days; i++){
//            InnerTravelDetailDescribeView viewStep = new InnerTravelDetailDescribeView(getContext());
//            if(i == 0){
//                viewStep.isFirstStep(true);
//            }else if(i == days - 1){
//                viewStep.isLastStep(true);
//            }else{
//                viewStep.isCenterStep(true);
//            }
//            viewStep.setTvDayText("第"+ (i + 1) +"天");
//            viewStep.setTvTitleText("飞往三亚");
//            viewStep.setTvContent("一日三餐请自理");
//            layoutContainer.addView(viewStep);
//        }

        InnerTravelDetailDescribeView viewStep1 = new InnerTravelDetailDescribeView(getContext());
        viewStep1.setTvDayText("第一天");
        viewStep1.setTvTitleText("飞往三亚");
        viewStep1.setTvContent("一日三餐请自理");
        viewStep1.isFirstStep(true);
        layoutContainer.addView(viewStep1);

        InnerTravelDetailDescribeView viewStep2 = new InnerTravelDetailDescribeView(getContext());
        viewStep2.setTvDayText("第二天");
        viewStep2.setTvTitleText("娱支洲岛自由活动一整天");
        viewStep2.setTvContent("大皇宫巴拉巴拉对对对快点快点快点快点快点快点快点快点快点快点快点快点快点快点快点快点快点快点滴滴快的看得开的扣扣上搜搜欧嗖嗖嗖嗖嗖哦顶顶顶顶顶是啦啦啦啦啦酸辣粉你发了啥你发快递佛得角佛的南方多念佛是否能");
        viewStep2.isCenterStep(true);
        layoutContainer.addView(viewStep2);

        InnerTravelDetailDescribeView viewStep3 = new InnerTravelDetailDescribeView(getContext());
        viewStep3.setTvDayText("第三天");
        viewStep3.setTvTitleText("娱支洲岛自由活动一整天");
        viewStep3.setTvContent("大皇宫巴拉巴拉对对对快点快ddddddddd不不不不不不不吧啊啊啊啊啊点快点快点快点快点快点快点快点快点快点快点快点快点快点快点快点快点滴滴快的看得开的扣扣上搜搜欧嗖嗖嗖嗖嗖哦顶顶顶顶顶是啦啦啦啦啦酸辣粉你发了啥你发快递佛得角佛的南方多念佛是否能");
        viewStep3.isLastStep(true);
        layoutContainer.addView(viewStep3);

        return view;
    }

    @Override
    public boolean isFirstChildOnTop() {
        return PagerScrollUtils.isFirstChildOnTop(layoutContainer);
    }
}
