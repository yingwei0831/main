package com.jhhy.cuiweitourism.net.models.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.jhhy.cuiweitourism.net.models.FetchModel.BasicFetchModel;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/11.
 * 酒店图片返回数据
 */
public class HotelImagesResponse {
    /**
     * Images : {"Image":[{"ImageID":"162481528","IsCoverImage":"0","Size":"3","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel120_120/00053qkz.jpg","WaterMark":"0"},{"ImageID":"162481528","IsCoverImage":"0","Size":"2","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel70_70/00053qkz.jpg","WaterMark":"0"},{"ImageID":"162481528","IsCoverImage":"0","Size":"1","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel350_350/00053qkz.jpg","WaterMark":"1"},{"ImageID":"162481528","IsCoverImage":"0","Size":"7","Type":"8","Url":"http://pavo.elongstatic.com/i/Mobile640_960/00053qkz.jpg","WaterMark":"1"},{"ImageID":"162481529","IsCoverImage":"0","Size":"3","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel120_120/00053qkj.jpg","WaterMark":"0"},{"ImageID":"162481529","IsCoverImage":"0","Size":"2","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel70_70/00053qkj.jpg","WaterMark":"0"},{"ImageID":"162481529","IsCoverImage":"0","Size":"1","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel350_350/00053qkj.jpg","WaterMark":"1"},{"ImageID":"162481529","IsCoverImage":"0","Size":"7","Type":"8","Url":"http://pavo.elongstatic.com/i/Mobile640_960/00053qkj.jpg","WaterMark":"1"},{"ImageID":"162481530","IsCoverImage":"0","Size":"1","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel350_350/00053qkf.jpg","WaterMark":"1"},{"ImageID":"162481530","IsCoverImage":"0","Size":"2","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel70_70/00053qkf.jpg","WaterMark":"0"},{"ImageID":"162481530","IsCoverImage":"0","Size":"3","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel120_120/00053qkf.jpg","WaterMark":"0"},{"ImageID":"162481530","IsCoverImage":"0","Size":"7","Type":"8","Url":"http://pavo.elongstatic.com/i/Mobile640_960/00053qkf.jpg","WaterMark":"1"},{"ImageID":"162481531","IsCoverImage":"0","Size":"1","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel350_350/00053p3o.jpg","WaterMark":"1"},{"ImageID":"162481531","IsCoverImage":"0","Size":"2","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel70_70/00053p3o.jpg","WaterMark":"0"},{"ImageID":"162481531","IsCoverImage":"0","Size":"3","Type":"8","Url":"http://pavo.elongstatic.com/i/Hotel120_120/00053p3o.jpg","WaterMark":"0"},{"ImageID":"162481531","IsCoverImage":"0","Size":"7","Type":"8","Url":"http://pavo.elongstatic.com/i/Mobile640_960/00053p3o.jpg","WaterMark":"1"}]}
     */

    private ImagesBean Images;

    public ImagesBean getImages() {
        return Images;
    }

    public void setImages(ImagesBean Images) {
        this.Images = Images;
    }

    public static class ImagesBean extends BasicFetchModel{
        private List<HotelDetailResponse.HotelImageBean> Image;

        public List<HotelDetailResponse.HotelImageBean> getImage() {
            return Image;
        }

        public void setImage(List<HotelDetailResponse.HotelImageBean> Image) {
            this.Image = Image;
        }

    }
}
