package com.jhhy.cuiweitourism.moudle;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiahe008 on 2016/9/8.
 */
public class ClassifyArea {
    //分类页面，地区类
    private String areaId;
    private String areaName;
    private List<ClassifyArea> listProvince; //id, name, pic
    private String areaPic; //图片，周边使用

    public ClassifyArea() {
    }

    public String getAreaPic() {
        return areaPic;
    }

    public void setAreaPic(String areaPic) {
        this.areaPic = areaPic;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<ClassifyArea> getListProvince() {
        return listProvince;
    }

    public void setListProvince(List<ClassifyArea> listProvince) {
        this.listProvince = listProvince;
    }

    @Override
    public String toString() {
        return "ClassifyArea{" +
                "areaId='" + areaId + '\'' +
                ", areaName ='" + areaName + '\'' +
                ", listProvince =" + ((listProvince == null || listProvince.size() == 0) ? 0 : Arrays.toString(listProvince.toArray()) )+
                '}';
    }
}
