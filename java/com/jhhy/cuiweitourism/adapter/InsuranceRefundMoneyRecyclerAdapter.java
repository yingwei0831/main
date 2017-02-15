package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.InsuranceTypeResponse;

import java.util.List;

/**
 * Created by jiahe008_lvlanlan on 2017/2/15.
 */
public class InsuranceRefundMoneyRecyclerAdapter  extends RecyclerView.Adapter{

    private Context context;
    private List mDates;

    public InsuranceRefundMoneyRecyclerAdapter(Context context, List mDates) {
        this.context = context;
        this.mDates = mDates;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_insurance_refund_money, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        if (position % 2 == 1) {
            vh.itemView.setBackgroundColor(Color.YELLOW);
        }else{
            vh.itemView.setBackgroundColor(Color.WHITE);
        }
        if (position == 0){

        }else {
            InsuranceTypeResponse item = (InsuranceTypeResponse) mDates.get(position - 1);
            vh.textView1.setText(item.getOurprice().get(0));
            vh.textView2.setText(item.getOurprice().get(1));
            vh.textView3.setText(item.getOurprice().get(2));
            vh.textView4.setText(item.getOurprice().get(3));
            vh.textView5.setText(item.getDefaultprice());
        }
    }


    @Override
    public int getItemCount() {
        return mDates.size() + 1;
    }

    public void setData(List mDates){
        this.mDates = mDates;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.tv_insurance_refund_1);
            textView2 = (TextView) itemView.findViewById(R.id.tv_insurance_refund_2);
            textView3 = (TextView) itemView.findViewById(R.id.tv_insurance_refund_3);
            textView4 = (TextView) itemView.findViewById(R.id.tv_insurance_refund_4);
            textView5 = (TextView) itemView.findViewById(R.id.tv_insurance_refund_5);
        }
    }
}
