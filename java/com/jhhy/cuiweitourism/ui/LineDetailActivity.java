package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.view.CircleImageView;
import com.jhhy.cuiweitourism.view.MyGridView;

public class LineDetailActivity extends BaseActivity {

    private String TAG = getClass().getSimpleName();

    private ScrollView mScrollView;
    private View content;

    private TextView tvCollection; //收藏
    private TextView tvShare; //分享
    private Button btnArgument; //讨价还价
    private Button btnReserve; //立即预定
    //顶部导航栏
    private LinearLayout layoutIndicatorTop;
    private Button btnProductTop;
    private Button btnPriceTop;
    private Button btnDescribeTop;
    private Button btnNoticeTop;
    private View viewProductTop;
    private View viewPriceTop;
    private View viewDescribeTop;
    private View viewNoticeTop;
    //Scrollview中导航栏
    private LinearLayout layoutIndicatorBottom;
    private Button tvProduct;
    private Button btnPrice;
    private Button tvDescribe;
    private Button tvNotice;
    private View viewProduct;
    private View viewPrice;
    private View viewDescribe;
    private View viewNotice;

    private WebView mWebViewProduct; //商品详情
    private TextView tvPriceInclude; //费用说明——>费用包含
    private TextView tvPriceNotInclude; //费用说明——>费用不包含
    private LinearLayout layoutTravelDescribe; //行程描述
    private TextView tvReserveNotice; //预订须知

    private TextView tvTitle;
    private TextView tvPrice; //价格
    private TextView tvCommentCount;
    private CircleImageView civIcon;
    private TextView tvNickName;
    private TextView tvAddTime;
    private TextView tvCommentContent;
    private MyGridView gvImages;


    private String[] titles = new String[]{"商品详情", "费用说明", "行程描述", "预订须知"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_detail);


    }
}
