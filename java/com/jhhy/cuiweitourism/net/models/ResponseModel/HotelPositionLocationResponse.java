package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/27.
 */
public class HotelPositionLocationResponse {

    private List<HotelDistrictItemBean> Item;

    public List<HotelDistrictItemBean> getItem() {
        return Item;
    }

    public void setItem(List<HotelDistrictItemBean> Item) {
        this.Item = Item;
    }

    public static class HotelDistrictItemBean {
        /**
         * ID : 0030
         * JianPin : PGQ
         * Name : 平谷区
         * QuanPin : PingGuQu
         */

        private String ID;
        private String JianPin;
        private String Name;
        private String QuanPin;

        public HotelDistrictItemBean(String name) {
            Name = name;
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
    }
}
