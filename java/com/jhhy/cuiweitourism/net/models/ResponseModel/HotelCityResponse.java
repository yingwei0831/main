package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/16.
 */
public class HotelCityResponse {

    private List<CityBean> Item;

    public List<CityBean> getItem() {
        return Item;
    }

    public void setItem(List<CityBean> Item) {
        this.Item = Item;
    }

    public static class CityBean {
        /**
         * Code : 5448
         * IsDomc : D
         * IsHot : N
         * IsShow : N
         * JianPin : BH
         * Name : 博湖
         * QuanPin : BoHu
         */

        private String Code;
        private String IsDomc;
        private String IsHot;
        private String IsShow;
        private String JianPin;
        private String Name;
        private String QuanPin;

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getIsDomc() {
            return IsDomc;
        }

        public void setIsDomc(String IsDomc) {
            this.IsDomc = IsDomc;
        }

        public String getIsHot() {
            return IsHot;
        }

        public void setIsHot(String IsHot) {
            this.IsHot = IsHot;
        }

        public String getIsShow() {
            return IsShow;
        }

        public void setIsShow(String IsShow) {
            this.IsShow = IsShow;
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
