package com.jhhy.cuiweitourism.fragment;


import android.accounts.AccountAuthenticatorActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.ui.Tab4AccountCertificationActivity;
import com.jhhy.cuiweitourism.ui.Tab4AccountSecurityActivity;
import com.jhhy.cuiweitourism.ui.Tab4AllOrdersActivity;
import com.jhhy.cuiweitourism.ui.Tab4MyCollectionActivity;
import com.jhhy.cuiweitourism.ui.Tab4MyCommentActivity;
import com.jhhy.cuiweitourism.ui.Tab4MyMessageActivity;
import com.jhhy.cuiweitourism.ui.Tab4MyReleaseActivity;
import com.jhhy.cuiweitourism.ui.Tab4MyTourismCoinActivity;
import com.jhhy.cuiweitourism.ui.Tab4UserInfoActivity;
import com.jhhy.cuiweitourism.ui.UserContactsListActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab4Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab4Fragment2 extends Fragment implements View.OnClickListener {


    private static final String TITLE = "title";

    private Context mContext;
    private String title;

    public Tab4Fragment2() {
        // Required empty public constructor
    }


    public static Tab4Fragment2 newInstance(String param1) {
        Tab4Fragment2 fragment = new Tab4Fragment2();
        Bundle args = new Bundle();
        args.putString(TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4_fragment2, container, false);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        ImageView ivEditInfo = (ImageView) view.findViewById(R.id.title_main_iv_right_telephone);

        TextView tvAllOrders = (TextView) view.findViewById(R.id.tv_fragment_tab4_all_orders);
        TextView tvWaitPay = (TextView) view.findViewById(R.id.tv_fragment_tab4_wait_pay);
        TextView tvWaitComment = (TextView) view.findViewById(R.id.tv_fragment_tab4_wait_comment);
        TextView tvWaitRefund = (TextView) view.findViewById(R.id.tv_fragment_tab4_wait_refund);

        TextView tvMyTourismCoin = (TextView) view.findViewById(R.id.tv_tab4_fragment2_my_tourism_coin);

        TextView tvMessage = (TextView) view.findViewById(R.id.tv_user_message);
        TextView tvComment = (TextView) view.findViewById(R.id.tv_user_comment);
        TextView tvCollection = (TextView) view.findViewById(R.id.tv_user_collection);
        TextView tvRelease = (TextView) view.findViewById(R.id.tv_user_release);
        TextView tvInformation = (TextView) view.findViewById(R.id.tv_user_information);
        TextView tvSecurity = (TextView) view.findViewById(R.id.tv_user_security);
        TextView tvAuthentication = (TextView) view.findViewById(R.id.tv_user_authentication);
        TextView tvContacts = (TextView) view.findViewById(R.id.tv_user_contacts);

        ivEditInfo.setOnClickListener(this);

        tvMyTourismCoin.setOnClickListener(this);
        tvAllOrders.setOnClickListener(this);
        tvWaitPay.setOnClickListener(this);
        tvWaitComment.setOnClickListener(this);
        tvWaitRefund.setOnClickListener(this);

        tvMessage.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvCollection.setOnClickListener(this);
        tvRelease.setOnClickListener(this);
        tvInformation.setOnClickListener(this);
        tvSecurity.setOnClickListener(this);
        tvAuthentication.setOnClickListener(this);
        tvContacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_fragment_tab4_all_orders: //全部订单
                Bundle bundle = new Bundle();
                bundle.putInt("position", 0);
                Tab4AllOrdersActivity.actionStart(mContext, bundle);
                break;
            case R.id.tv_fragment_tab4_wait_pay: //等待付款
                Bundle bundle2 = new Bundle();
                bundle2.putInt("position", 1);
                Tab4AllOrdersActivity.actionStart(mContext, bundle2);
                break;
            case R.id.tv_fragment_tab4_wait_comment: ////待点评
                Bundle bundle3 = new Bundle();
                bundle3.putInt("position", 2);
                Tab4AllOrdersActivity.actionStart(mContext, bundle3);
                break;
            case R.id.tv_fragment_tab4_wait_refund: //待退款
                Bundle bundle4 = new Bundle();
                bundle4.putInt("position", 3);
                Tab4AllOrdersActivity.actionStart(mContext, bundle4);
                break;
            case R.id.tv_tab4_fragment2_my_tourism_coin: //我的旅游币
                Tab4MyTourismCoinActivity.actionStart(getContext(), null);
                break;
            case R.id.tv_user_message: //我的消息
                Tab4MyMessageActivity.actionStart(getContext(), null);
                break;
            case R.id.tv_user_comment: //我的评论
                Tab4MyCommentActivity.actionStart(getContext(), null);
                break;
            case R.id.tv_user_collection:
                Intent intentColl = new Intent(getContext(), Tab4MyCollectionActivity.class);
                startActivity(intentColl);
                break;
            case R.id.tv_user_release:
                Intent intentRele = new Intent(getContext(), Tab4MyReleaseActivity.class);
                startActivity(intentRele);
                break;
            case R.id.title_main_iv_right_telephone: //编辑用户信息
            case R.id.tv_user_information:
                Intent intentInfo = new Intent(getContext(), Tab4UserInfoActivity.class);
                startActivity(intentInfo);
                break;
            case R.id.tv_user_security:
                Intent intentSec = new Intent(getContext(), Tab4AccountSecurityActivity.class);
                startActivity(intentSec);
                break;
            case R.id.tv_user_authentication:
                Intent intentAut = new Intent(getContext(), Tab4AccountCertificationActivity.class);
                startActivity(intentAut);
                break;
            case R.id.tv_user_contacts: //常用联系人
                Intent intentCon = new Intent(getContext(), UserContactsListActivity.class);
                startActivity(intentCon);
                break;
        }
    }
}
