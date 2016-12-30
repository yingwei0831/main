package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.IOnItemClickListener;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailResponse;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by birney1 on 16/10/19.
 */
public abstract class HotelDetailInnerListAdapter extends MyBaseAdapter implements ArgumentOnClick {

    private int type;

    public HotelDetailInnerListAdapter(Context ct, List list) {
        super(ct, list);
    }

    public void setType(int type){
        this.type = type;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_hotel_detail_inner_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.iv_item_hotel_list);
            holder.tvRoomType = (TextView) view.findViewById(R.id.tv_hotel_room_type);
            holder.tvBedType = (TextView) view.findViewById(R.id.tv_hotel_bed_type);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_hotel_detail_price);
            holder.btnReserve = (Button) view.findViewById(R.id.btn_hotel_detail_reserve);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        if(type == 2){
            holder.btnReserve.setVisibility(View.GONE);
        }else {
            final int id = holder.btnReserve.getId();
            holder.btnReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToArgument(view, viewGroup, i, id);
                }
            });
        }
        HotelDetailResponse.HotelProductBean product = (HotelDetailResponse.HotelProductBean) getItem(i);
        if (product != null){
            ImageLoaderUtil.getInstance(context).displayImage(product.getRoomImgUrl(), holder.imageView);
            holder.tvRoomType.setText(product.getRoomName());
            holder.tvBedType.setText(String.format("%s %s", product.getName(), product.getBedType()));
            holder.tvPrice.setText(String.format("￥%s",product.getPrice()));
            if ("1".equals(product.getIsBooking())){ //房间是否可预订 0已订完 1 可预订
                holder.btnReserve.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_reserve_border_selector)); //黄色
            }else{
                holder.btnReserve.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_reserve_not_border_selector)); //灰色
            }
        }
        return view;
    }

    class ViewHolder{
        private ImageView imageView;
        private TextView tvRoomType;
        private TextView tvBedType;
        private TextView tvPrice;
        private Button   btnReserve;
    }
}
