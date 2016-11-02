package com.jhhy.cuiweitourism.fragment;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab4FragmentAdapter;
import com.ns.fhvp.IPagerScroll;
import com.ns.fhvp.TouchPanelLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab4Fragment extends Fragment implements TouchPanelLayout.IConfigCurrentPagerScroll, TouchPanelLayout.OnViewUpdateListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Drawable mAbBg = null;
    private TouchPanelLayout mTouchPanelLayout;
    private ViewPager viewPager;
    private Tab4FragmentAdapter mAdapter;

    private ImageView ivEditInfo; //编辑个人信息

    public Tab4Fragment() {
        // Required empty public constructor
    }


    public static Tab4Fragment newInstance(String param1, String param2) {
        Tab4Fragment fragment = new Tab4Fragment();
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
        mAbBg = new ColorDrawable(getResources().getColor(R.color.colorActionBar));
        mAbBg.setAlpha(0);
//        getActivity().getActionBar().setBackgroundDrawable(mAbBg);
//        mTouchPanelLayout = (TouchPanelLayout) inflater.inflate(R.layout.fragment_tab4, container, false);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        View temp = view;
        mTouchPanelLayout = (TouchPanelLayout) temp;
        setupView(view);
        addListener();
        return view;
    }

    private void setupView(View view) {
        ivEditInfo = (ImageView) view.findViewById(R.id.title_main_iv_right_telephone);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mAdapter = new Tab4FragmentAdapter(getChildFragmentManager(), getContext());
        mAdapter.addTab(Tab4FragmentContent.class, "tab4FragmentContent", null);
        viewPager.setAdapter(mAdapter);
    }

    private void addListener() {
        ivEditInfo.setOnClickListener(this);
        mTouchPanelLayout.setConfigCurrentPagerScroll(this);
        mTouchPanelLayout.setOnViewUpdateListener(this);
    }

    @Override
    public IPagerScroll getCurrentPagerScroll() {
        Fragment fragment = mAdapter.getCurrentFragment(viewPager.getCurrentItem());
        if (fragment != null && fragment instanceof IPagerScroll) {
            return (IPagerScroll) fragment;
        }
        return null;
    }

    @Override
    public float getActionBarHeight() {
        return 0; //getActivity().getActionBar().getHeight();
    }

    @Override
    public void onAlphaChanged(int alpha) {
        if (mAbBg != null) {
            mAbBg.setAlpha(alpha);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_iv_right_telephone:

                break;
        }
    }
}
