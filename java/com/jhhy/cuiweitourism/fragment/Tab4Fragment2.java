package com.jhhy.cuiweitourism.fragment;


import android.accounts.AccountAuthenticatorActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.LoginActivity;
import com.jhhy.cuiweitourism.ui.MainActivity;
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
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.view.CircleImageView;
import com.just.sun.pricecalendar.ToastCommon;

public class Tab4Fragment2 extends Fragment implements View.OnClickListener {

    private String TAG = Tab4Fragment2.class.getSimpleName();
    private static final String TITLE = "title";

    private Context mContext;
    private String title;

    private TextView tvLoginOrRegister;
    private View viewLoggedIn;
    private TextView tvUserName;
    private TextView tvCertificated;
    private CircleImageView civUserIcon;

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

        tvLoginOrRegister = (TextView) view.findViewById(R.id.tv_go_register_login); //去登录/注册
        viewLoggedIn = view.findViewById(R.id.layout_user_logged_in);
        tvUserName = (TextView) view.findViewById(R.id.tab4_user_nickname);
        tvCertificated = (TextView) view.findViewById(R.id.tv_user_certificated);

        civUserIcon = (CircleImageView) view.findViewById(R.id.tab4_user_icon);
        refreshView();
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

        tvLoginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //去登录/注册
                ((MainActivity)getActivity()).gotoLogin();
            }
        });
    }

    public void refreshView() {
        if (MainActivity.logged){ //已登录，显示昵称，是否认证
            tvLoginOrRegister.setVisibility(View.GONE);
            viewLoggedIn.setVisibility(View.VISIBLE);
            tvUserName.setText(MainActivity.user.getUserNickName());
            tvCertificated.setText(MainActivity.user.getStatus());

            ImageLoaderUtil.getInstance(getContext()).getImage(civUserIcon, MainActivity.user.getUserIconPath());
            ImageLoaderUtil.getInstance(getContext()).setCallBack(new ImageLoaderUtil.ImageLoaderCallBack() {
                @Override
                public void refreshAdapter(Bitmap loadedImage) {
                }
            });
        }else{ //未登录，显示注册登录
            tvLoginOrRegister.setVisibility(View.VISIBLE);
            viewLoggedIn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (MainActivity.logged) {
            switch (view.getId()) {
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
                case R.id.tv_fragment_tab4_wait_comment: //待点评
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
                case R.id.tv_user_collection: //我的收藏
                    Intent intentColl = new Intent(getContext(), Tab4MyCollectionActivity.class);
                    startActivity(intentColl);
                    break;
                case R.id.tv_user_release: //我的发布
                    Intent intentRele = new Intent(getContext(), Tab4MyReleaseActivity.class);
                    startActivity(intentRele);
                    break;
                case R.id.title_main_iv_right_telephone: //编辑用户信息
                case R.id.tv_user_information:
                    Intent intentInfo = new Intent(getContext(), Tab4UserInfoActivity.class);
                    startActivityForResult(intentInfo, VIEW_USER_INFO);
                    break;
                case R.id.tv_user_security: //账户安全
                    Intent intentSec = new Intent(getContext(), Tab4AccountSecurityActivity.class);
                    startActivityForResult(intentSec, LOGIN_OUT);
                    break;
                case R.id.tv_user_authentication: //账户认证
                    Intent intentAut = new Intent(getContext(), Tab4AccountCertificationActivity.class);
                    startActivity(intentAut);
                    break;
                case R.id.tv_user_contacts: //常用联系人
                    Intent intentCon = new Intent(getContext(), UserContactsListActivity.class);
                    startActivity(intentCon);
                    break;
            }
        }else{
            ToastCommon.toastShortShow(getContext(), null, "请登录后重试");
        }
    }

    private final int LOGIN_OUT = 6952; //是否登出
    private final int VIEW_USER_INFO = 6953; //查看用户信息，有可能修改用户信息，比如昵称

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "----------------------onActivityResult---------------------");
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == LOGIN_OUT){
//                gotoLogin();
                MainActivity.logged = false;
                MainActivity.user = null;
//                refreshView();
                tvLoginOrRegister.setVisibility(View.VISIBLE);
                viewLoggedIn.setVisibility(View.GONE);
                civUserIcon.setImageResource(R.mipmap.default_user_icon);
            }
        }
        if (requestCode == VIEW_USER_INFO){
            tvUserName.setText(MainActivity.user.getUserNickName());
            if (data != null){
                boolean tag = data.getBooleanExtra("tag", false);
                if (tag){
                    ImageLoaderUtil.getInstance(getContext()).getImage(civUserIcon, MainActivity.user.getUserIconPath());
                    ImageLoaderUtil.getInstance(getContext()).setCallBack(new ImageLoaderUtil.ImageLoaderCallBack() {
                        @Override
                        public void refreshAdapter(Bitmap loadedImage) {
                        }
                    });
                }
            }
        }
    }

//    private void gotoLogin() {
//
//        LoginActivity.actionStart(getContext(), null);
//        getActivity().finish();
//    }
}
