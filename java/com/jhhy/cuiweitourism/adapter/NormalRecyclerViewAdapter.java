package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailResponse;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/11.
 */
public class NormalRecyclerViewAdapter<T> extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalImageHolder> {

    private static final String TAG = "NormalRecyclerViewAdapter";

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<T> list;

    public NormalRecyclerViewAdapter(Context context, List<T> urls) {
        this.list = urls;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalImageHolder(mLayoutInflater.inflate(R.layout.item_hotel_image, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalImageHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.mPicture.getLayoutParams();
        String url = ((HotelDetailResponse.HotelImageBean)list.get(position)).getUrl();
        String imageUrl = url.substring(url.indexOf("/i/")+3, url.lastIndexOf("/"));
        String sizeStr = imageUrl.replaceAll("[^(0-9_)]", ""); //replace("[^a-zA-Z]", "")
        String[] size = sizeStr.split("_");
        params.height = Integer.parseInt(size[1]);
        holder.mPicture.setLayoutParams(params);

        ImageLoaderUtil.getInstance(mContext).displayImage(((HotelDetailResponse.HotelImageBean)list.get(position)).getUrl(), holder.mPicture);
        setUpEvent(holder);
    }

    protected void setUpEvent(final NormalImageHolder holder) {
        if(mOnItemClickListener != null){
            holder.mPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.mPicture, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }
    private onItemClickListener mOnItemClickListener;
    //供外部调用接口，回调方法
    public void setOnItemClickListener(onItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public static class NormalImageHolder extends RecyclerView.ViewHolder {
        ImageView mPicture;

        public NormalImageHolder(View view) {
            super(view);
            mPicture = (ImageView) view.findViewById(R.id.picture);
//            ButterKnife.bind(this, view);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    LogUtil.e("NormalTextViewHolder", "onClick--> position = " + getPosition());
//                }
//            });
        }

    }
}
