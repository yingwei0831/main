package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/17.
 */
public class HoutelPropertiesInfo {

//    {
//        "id": "190",
//            "attrname": "品牌",
//            "son": [
//        {
//            "id": "191",
//                "attrname": "品牌1"
//        }
//        ]
//    },


    public static class Son{
        public String id;
        public String attrname;

        public Son(String id, String attrname) {
            this.id = id;
            this.attrname = attrname;
        }

        public Son() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAttrname() {
            return attrname;
        }

        public void setAttrname(String attrname) {
            this.attrname = attrname;
        }

        @Override
        public String toString() {
            return "Son{" +
                    "id='" + id + '\'' +
                    ", attrname='" + attrname + '\'' +
                    '}';
        }
    }

    public String id;
    public String attrname;
    public ArrayList<Son> son;

    public HoutelPropertiesInfo(String id, String attrname, ArrayList<Son> son) {
        this.id = id;
        this.attrname = attrname;
        this.son = son;
    }

    public HoutelPropertiesInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public ArrayList<Son> getSon() {
        return son;
    }

    public void setSon(ArrayList<Son> son) {
        this.son = son;
    }

    @Override
    public String toString() {
        return "HoutelPropertiesInfo{" +
                "id='" + id + '\'' +
                ", attrname='" + attrname + '\'' +
                ", son=" + son +
                '}';
    }
}
