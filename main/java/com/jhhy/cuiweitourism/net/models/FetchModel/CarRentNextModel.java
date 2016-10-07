package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/9/29.
 */

/**
 *   租车下一步
 */
public class CarRentNextModel extends BasicFetchModel {
    //:{"carid":"5","days":"2","startaddress":"北京市昌平区史各庄","endaddress":"辽宁省凌源市火车站"}}
    public String carid;
    public String days;
    public String startaddress;
    public String endaddress;

    public CarRentNextModel(String carid, String days, String startaddress, String endaddress) {
        this.carid = carid;
        this.days = days;
        this.startaddress = startaddress;
        this.endaddress = endaddress;
    }

    public CarRentNextModel() {
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getStartaddress() {
        return startaddress;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }

    public String getEndaddress() {
        return endaddress;
    }

    public void setEndaddress(String endaddress) {
        this.endaddress = endaddress;
    }
}
