package com.jhhy.cuiweitourism.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

/**
 * Created by jiahe008 on 2016/10/26.
 */
public abstract class TrainContactsItem extends RelativeLayout implements View.OnClickListener {

    private TextView    tvName;
    private TextView    tvCardId;
    private ImageView   ivTrash;
    private ImageView   ivArrowRight;

    public TrainContactsItem(Context context) {
        super(context);
        init(context);
    }

    public TrainContactsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TrainContactsItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TrainContactsItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.item_train_contact, null);
        tvName = (TextView) view.findViewById(R.id.tv_contact_name);
        tvCardId = (TextView) view.findViewById(R.id.tv_contact_card_id);
        ivTrash = (ImageView) view.findViewById(R.id.iv_train_trash);
        ivArrowRight = (ImageView) view.findViewById(R.id.iv_contact_view_detail);
        addListener();
    }

    private void addListener() {
        ivTrash.setOnClickListener(this);
    }


}
