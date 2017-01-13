package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/11.
 * 酒店产品价格明细返回价格
 */
public class HotelProductPriceResponse {

    /**
     * BaseTotalPrice : 32.4
     * DaysBasePrice : 32.4
     * DaysBreakfast : 无早
     * DaysPrice : 32.4
     * OtherService : 不提供加床服务||预付|无早
     * Prices : {"Item":{"Date":"2017-02-13","IsHave":"无早","IsOwn":"0","Price":"32.4"}}
     * TotalPrice : 32.4
     */

    private String BaseTotalPrice;
    private String DaysBasePrice;
    private String DaysBreakfast;
    private String DaysPrice;
    private String OtherService;
    private PricesBean Prices;
    private String TotalPrice;

    public String getBaseTotalPrice() {
        return BaseTotalPrice;
    }

    public void setBaseTotalPrice(String BaseTotalPrice) {
        this.BaseTotalPrice = BaseTotalPrice;
    }

    public String getDaysBasePrice() {
        return DaysBasePrice;
    }

    public void setDaysBasePrice(String DaysBasePrice) {
        this.DaysBasePrice = DaysBasePrice;
    }

    public String getDaysBreakfast() {
        return DaysBreakfast;
    }

    public void setDaysBreakfast(String DaysBreakfast) {
        this.DaysBreakfast = DaysBreakfast;
    }

    public String getDaysPrice() {
        return DaysPrice;
    }

    public void setDaysPrice(String DaysPrice) {
        this.DaysPrice = DaysPrice;
    }

    public String getOtherService() {
        return OtherService;
    }

    public void setOtherService(String OtherService) {
        this.OtherService = OtherService;
    }

    public PricesBean getPrices() {
        return Prices;
    }

    public void setPrices(PricesBean Prices) {
        this.Prices = Prices;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public static class PricesBean {
        /**
         * Item : {"Date":"2017-02-13","IsHave":"无早","IsOwn":"0","Price":"32.4"}
         */

        private List<ItemBean> Item;

        public List<ItemBean> getItem() {
            return Item;
        }

        public void setItem(List<ItemBean> Item) {
            this.Item = Item;
        }

        @Override
        public String toString() {
            return "PricesBean{" +
                    "Item=" + Item +
                    '}';
        }
    }
    public static class ItemBean {
        /**
         * Date : 2017-02-13    入住日期
         * IsHave : 无早
         * IsOwn : 0
         * Price : 32.4
         */

        private String Date;
        private String IsHave;
        private String IsOwn;
        private String Price;

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getIsHave() {
            return IsHave;
        }

        public void setIsHave(String IsHave) {
            this.IsHave = IsHave;
        }

        public String getIsOwn() {
            return IsOwn;
        }

        public void setIsOwn(String IsOwn) {
            this.IsOwn = IsOwn;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        @Override
        public String toString() {
            return "ItemBean{" +
                    "Date='" + Date + '\'' +
                    ", IsHave='" + IsHave + '\'' +
                    ", IsOwn='" + IsOwn + '\'' +
                    ", Price='" + Price + '\'' +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "HotelProductPriceResponse{" +
                "BaseTotalPrice='" + BaseTotalPrice + '\'' +
                ", DaysBasePrice='" + DaysBasePrice + '\'' +
                ", DaysBreakfast='" + DaysBreakfast + '\'' +
                ", DaysPrice='" + DaysPrice + '\'' +
                ", OtherService='" + OtherService + '\'' +
                ", Prices=" + Prices +
                ", TotalPrice='" + TotalPrice + '\'' +
                '}';
    }
}
