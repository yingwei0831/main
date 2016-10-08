package com.jhhy.cuiweitourism.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowVisaMaterial;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisaMaterialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisaMaterialFragment extends Fragment implements View.OnClickListener {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";


    private String title;
    private String content;

    private Button btnDeliver;
    private TextView tvContent;

    private View parent; //PopupWindow用
    private PopupWindow popupWindow;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Consts.MESSAGE_VISA_SEND_MATERIAL: //发送材料到我的邮箱
                    ToastUtil.show(getContext(), String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                    break;
            }
        }
    };
    public VisaMaterialFragment() {
        // Required empty public constructor
    }

    public static VisaMaterialFragment newInstance(String title, String content) {
        VisaMaterialFragment fragment = new VisaMaterialFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
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
        View view = inflater.inflate(R.layout.fragment_visa_material, container, false);
        setupView(view);
        addListener();
        return view;
    }

    private void addListener() {
        btnDeliver.setOnClickListener(this);
    }

    private void setupView(View view){
        btnDeliver = (Button) view.findViewById(R.id.btn_deliver_to_owner_mail);
        tvContent = (TextView) view.findViewById(R.id.tv_material_detail);
        tvContent.setText(content);

    }

    public void setParent(View view){
        this.parent = view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_deliver_to_owner_mail:
                if (popupWindow == null) {
                    popupWindow = new PopupWindowVisaMaterial(getActivity(), parent, handler);
                }else{
                    popupWindow.showAsDropDown(parent, 0, -Utils.getScreenHeight(getContext())); //, Gravity.BOTTOM, 0, 0
                }
                break;
        }
    }


}
