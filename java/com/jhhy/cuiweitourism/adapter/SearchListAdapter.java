package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForceEndSearchInfo;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/11/9.
 */
public class SearchListAdapter extends MyBaseAdapter {

    private ArgumentOnClick argument;

    public SearchListAdapter(Context ct, List list, ArgumentOnClick listener) {
        super(ct, list);
        this.argument = listener;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.tab1_inner_travel_listview_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.iv_tab1_2_inner_travel);
            holder.tvTitle = (TextView)view.findViewById(R.id.tv_tab1_2_inner_travle_title);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_tab1_2_inner_travle_prirce);
            holder.tvType = (TextView) view.findViewById(R.id.tv_tab1_2_inner_travel_type);
            holder.tvArgument = (TextView) view.findViewById(R.id.tv_inner_travel_item_argument);
//            holder.tvPrice.setVisibility(View.GONE);
            holder.tvType.setVisibility(View.GONE);
//            holder.tvArgument.setVisibility(View.GONE);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final int id = holder.tvArgument.getId();
        holder.tvArgument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(argument != null){
                    argument.goToArgument(view, viewGroup, position, id);
                }
            }
        });

        ForceEndSearchInfo searchInfo = (ForceEndSearchInfo) getItem(position);
        if(searchInfo != null){
            if (searchInfo.getDescription() != null && !"null".equals(searchInfo.getDescription())) {
                holder.tvType.setVisibility(View.VISIBLE);
                if (searchInfo.getDescription().contains("跟团游")) {
                    holder.tvType.setBackgroundColor(context.getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                    holder.tvType.setText("跟团游");
                } else if (searchInfo.getDescription().contains("自由游")) {
                    holder.tvType.setBackgroundColor(context.getResources().getColor(R.color.colorFreedom));
                    holder.tvType.setText("自由游");
                }else {
                    holder.tvType.setVisibility(View.GONE);
                }
            }
            holder.tvPrice.setText(searchInfo.getPrice());
            holder.tvTitle.setText(searchInfo.getTitle());
            ImageLoaderUtil.getInstance(context).displayImage(searchInfo.getLitpic(), holder.imageView);
        }
        return view;
    }
    class ViewHolder{
        private ImageView imageView;
        private TextView tvTitle;
        private TextView  tvPrice;
        private TextView  tvType;
        private TextView  tvArgument;
    }
}
