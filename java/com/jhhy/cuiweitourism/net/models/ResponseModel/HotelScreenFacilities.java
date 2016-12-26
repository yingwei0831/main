package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/26.
 */
public class HotelScreenFacilities {

    private List<FacilityItemBean> Item;

    public List<FacilityItemBean> getItem() {
        return Item;
    }

    public void setItem(List<FacilityItemBean> Item) {
        this.Item = Item;
    }

    public static class FacilityItemBean {
        /**
         * ID : 1
         * Name : 免费wifi
         * NameEN :
         */

        private String ID;
        private String Name;
        private String NameEN;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getNameEN() {
            return NameEN;
        }

        public void setNameEN(String NameEN) {
            this.NameEN = NameEN;
        }
    }
}
