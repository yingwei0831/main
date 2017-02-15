package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.InsuranceTypeResponse;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.List;

/**
 * Created by jiahe008_lvlanlan on 2017/2/15.
 */
public class InsuranceTypeAdapter extends MyBaseAdapter {

    public InsuranceTypeAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_insurance_type, null);
            holder = new ViewHolder();
            holder.radioButton = (RadioButton) view.findViewById(R.id.radio_button_insurance_type);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        InsuranceTypeResponse item = (InsuranceTypeResponse) list.get(i);
        holder.radioButton.setText(item.getProductname());
        return view;
    }

    class ViewHolder{
        private RadioButton radioButton;
    }
}
