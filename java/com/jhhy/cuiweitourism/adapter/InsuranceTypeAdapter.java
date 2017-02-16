package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.InsuranceTypeResponse;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.List;

/**
 * Created by jiahe008_lvlanlan on 2017/2/15.
 */
public class InsuranceTypeAdapter extends MyBaseAdapter {

    private static final String TAG = "InsuranceTypeAdapter";

    private int position;
    private Drawable drawableLight;
    private Drawable drawableDark;

    public InsuranceTypeAdapter(Context ct, List list) {
        super(ct, list);
        drawableLight = ContextCompat.getDrawable(context, R.drawable.unchecked);
        drawableDark = ContextCompat.getDrawable(context, R.drawable.check);
        drawableLight.setBounds(0, 0, drawableLight.getMinimumWidth(), drawableLight.getMinimumHeight());
        drawableDark.setBounds(0, 0, drawableDark.getMinimumWidth(), drawableDark.getMinimumHeight());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_insurance_type, null);
            holder = new ViewHolder();
            holder.radioButton = (TextView) view.findViewById(R.id.radio_button_insurance_type);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if (i == position) {
            holder.radioButton.setTextColor(context.getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            holder.radioButton.setCompoundDrawables(null, null, drawableDark, null);
        } else {
            holder.radioButton.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.radioButton.setCompoundDrawables(null, null, drawableLight, null);
        }
        InsuranceTypeResponse item = (InsuranceTypeResponse) list.get(i);
        holder.radioButton.setText(item.getProductname());
        return view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    class ViewHolder{
        private TextView radioButton;
    }
}
