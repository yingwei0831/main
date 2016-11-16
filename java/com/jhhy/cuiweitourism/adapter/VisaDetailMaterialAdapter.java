package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaMaterial;

import java.util.List;

/**
 * Created by birney1 on 16/11/16.
 */
public class VisaDetailMaterialAdapter extends MyBaseAdapter {

    public VisaDetailMaterialAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_visa_detail_material, null);
            holder = new ViewHolder();
            holder.tvMaterialName = (TextView) view.findViewById(R.id.tv_visa_detail_material_name_value);
            holder.tvMaterialRequired = (TextView) view.findViewById(R.id.tv_visa_detail_material_required_value);
            holder.tvMaterialModel = (TextView) view.findViewById(R.id.tv_visa_detail_material_model_value);
            holder.tvMaterialRemark = (TextView) view.findViewById(R.id.tv_visa_detail_material_remark_value);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        VisaMaterial material = (VisaMaterial) getItem(i);
        holder.tvMaterialName.setText(material.getMaterialName());
        holder.tvMaterialRequired.setText("Y".equals(material.getMaterialMust())?"是":"否");
        holder.tvMaterialModel.setText(material.getMaterialModel());
        holder.tvMaterialRemark.setText(material.getMaterialRemark());
        return view;
    }

    class ViewHolder{
        private TextView tvMaterialName;
        private TextView tvMaterialRequired;
        private TextView tvMaterialModel;
        private TextView tvMaterialRemark;
    }
}
