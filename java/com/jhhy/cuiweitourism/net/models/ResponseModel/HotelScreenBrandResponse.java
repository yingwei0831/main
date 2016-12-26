package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/26.
 */
public class HotelScreenBrandResponse {

    private List<BrandItemBean> Item;

    public List<BrandItemBean> getItem() {
        return Item;
    }

    public void setItem(List<BrandItemBean> Item) {
        this.Item = Item;
    }

    public static class BrandItemBean {
        /**
         * CityCode : 0101
         * HotelNum : 31
         * ID : 32
         * JianPin : RJ
         * Name : 如家
         * QuanPin : RuJia
         * Shortname : 如家
         */

        private String CityCode;
        private String HotelNum;
        private String ID;
        private String JianPin;
        private String Name;
        private String QuanPin;
        private String Shortname;

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
            this.CityCode = CityCode;
        }

        public String getHotelNum() {
            return HotelNum;
        }

        public void setHotelNum(String HotelNum) {
            this.HotelNum = HotelNum;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getJianPin() {
            return JianPin;
        }

        public void setJianPin(String JianPin) {
            this.JianPin = JianPin;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getQuanPin() {
            return QuanPin;
        }

        public void setQuanPin(String QuanPin) {
            this.QuanPin = QuanPin;
        }

        public String getShortname() {
            return Shortname;
        }

        public void setShortname(String Shortname) {
            this.Shortname = Shortname;
        }
    }
}
