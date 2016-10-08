package com.jhhy.cuiweitourism.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.jhhy.cuiweitourism.R;
import com.ns.fhvp.IPagerScroll;
import com.ns.fhvp.PagerScrollUtils;

public class WebViewFragment extends Fragment implements IPagerScroll {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private String title;

    private WebView webView;
    private String content;

    String start = "<html><style>body img{width:100%;}</style><body>";
    String end = "</body></html>";

    private String jsonString = "\"<div class=\\\"para\\\" label-module=\\\"para\\\" style=\\\"font-size: 14px; word-wrap: break-word; color: rgb(51, 51, 51); margin-bottom: 15px; text-indent: 28px; line-height: 24px; zoom: 1; font-family: arial, 宋体, sans-serif; white-space: normal; background-color: rgb(255, 255, 255);\\\">颐和园，中国<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/subview/5405/13482963.htm\\\" data-lemmaid=\\\"175141\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">清朝</a>时期<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/646836.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">皇家园林</a>，前身为<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/1476143.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">清漪园</a>，坐落在北京西郊，距城区十五公里，占地约二百九十公顷，与<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/subview/2476/5824630.htm\\\" data-lemmaid=\\\"9328\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">圆明园</a>毗邻。它是以<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/188034.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">昆明湖</a>、<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/956201.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">万寿山</a>为基址，以杭州<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/1598.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">西湖</a>为蓝本，汲取江南园林的设计手法而建成的一座大型山水园林，也是保存最完整的一座皇家行宫御苑，被誉为“皇家园林博物馆”，也是国家重点旅游景点。</div><div class=\\\"para\\\" label-module=\\\"para\\\" style=\\\"font-size: 14px; word-wrap: break-word; color: rgb(51, 51, 51); margin-bottom: 15px; text-indent: 28px; line-height: 24px; zoom: 1; font-family: arial, 宋体, sans-serif; white-space: normal; background-color: rgb(255, 255, 255);\\\">清朝<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/2436.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">乾隆皇帝</a>继位以前，在北京西郊一带，建起了四座大型<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/646836.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">皇家园林</a>。<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/2677.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">乾隆</a>十五年（1750年），乾隆皇帝为孝敬其母孝圣皇后动用448万两白银在这里改建为清漪园，形成了从现<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/32477.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">清华园</a>到香山长达二十公里的皇家园林区。<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/84502.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">咸丰</a>十年（1860年），清漪园被<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/586244.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">英法联军</a>焚毁。<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/29852.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">光绪</a>十四年（1888年）重建，改称颐和园，作消夏游乐地。光绪二十六年（1900年），颐和园又遭“八国联军”的破坏，珍宝被劫掠一空。清朝灭亡后，颐和园在军阀混战和国民党统治时期，又遭破坏。</div><div class=\\\"para\\\" label-module=\\\"para\\\" style=\\\"font-size: 14px; word-wrap: break-word; color: rgb(51, 51, 51); margin-bottom: 15px; text-indent: 28px; line-height: 24px; zoom: 1; font-family: arial, 宋体, sans-serif; white-space: normal; background-color: rgb(255, 255, 255);\\\">1961年3月4日，颐和园被公布为第一批<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/163959.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">全国重点文物保护单位</a>，与同时公布的<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/15110.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">承德避暑山庄</a>、<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/4131.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">拙政园</a>、<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/23789.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">留园</a>并称为<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/400248.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">中国四大名园</a>，1998年11月被列入《<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/340391.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">世界遗产名录</a>》。2007年5月8日，颐和园经国家旅游局正式批准为国家5A级旅游景区。 2009年，颐和园入选中国世界纪录协会中国现存最大的皇家园林。<span style=\\\"font-size: 12px; line-height: 0; position: relative; vertical-align: baseline; top: -0.5em; margin-left: 2px; color: rgb(51, 102, 204); cursor: default; padding: 0px 2px;\\\">[1]</span><a style=\\\"color: rgb(19, 110, 194); position: relative; top: -50px; font-size: 0px; line-height: 0;\\\" name=\\\"ref_[1]_9572580\\\"></a> </div><p><br/></p>\"";


    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(String title, String data) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            content = getArguments().getString(CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        webView = (WebView) view.findViewById(R.id.webview_content);
        webView.loadDataWithBaseURL(null, start+jsonString+end, "text/html", "utf-8", null);
    }

    @Override
    public boolean isFirstChildOnTop() {
        return PagerScrollUtils.isFirstChildOnTop(webView);
    }
}
