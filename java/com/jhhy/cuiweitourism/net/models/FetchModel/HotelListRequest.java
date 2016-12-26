package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/12/26.
 * 酒店列表请求
 */
public class HotelListRequest extends BasicFetchModel{

    /**
     * City : 0101
     * CheckInDate : 2016-12-13
     * CheckOutDate : 2016-12-20
     * Keywords :           关键字
     * District :           行政区编码
     * Downtown :           商业区编码
     * Landmark :           景点/标志物
     * PriceRange :         房价范围：格式：0-150
     * StarLevel : 4        酒店星级：经济/客栈: 0,1,2  三星/舒适: 3   四星/高档: 4  五星/豪华: 5
     * IsEconomy :          是否经济；1是0否
     * IsApartment : 0      是否公寓；1是0否
     * BrandName :          酒店品牌编号
     * CheckInNum :         入住人数
     * Facilities :         设施ID 多个设施用逗号,隔开；1 免费wifi； 2 收费wifi 3 免费宽带；4 收费宽带； 5 免费停车场；6 收费停车场； 7 免费接机服务；8 收费接机服务； 9 室内游泳池；10 室外游泳池； 11 健身房；12 商务中心； 13 会议室；14 酒店餐厅
     * CurrentPage : 1
     * PageNumber : 10
     * SortBy : P           排序项: 价格P/星级S/好评G
     * SortType : A         排序顺序:升序A 降序 D
     */

    private String City;
    private String CheckInDate;
    private String CheckOutDate;
    private String Keywords;
    private String District;
    private String Downtown;
    private String Landmark;
    private String PriceRange;
    private String StarLevel;
    private String IsEconomy;
    private String IsApartment;
    private String BrandName;
    private String CheckInNum;
    private String Facilities;
    private String CurrentPage;
    private String PageNumber;
    private String SortBy;
    private String SortType;

    public HotelListRequest() {
    }

    public HotelListRequest(String city, String checkInDate, String checkOutDate, String keywords, String district, String downtown, String landmark, String priceRange, String starLevel, String isEconomy, String isApartment, String brandName, String checkInNum, String facilities, String currentPage, String pageNumber, String sortBy, String sortType) {
        City = city;
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
        Keywords = keywords;
        District = district;
        Downtown = downtown;
        Landmark = landmark;
        PriceRange = priceRange;
        StarLevel = starLevel;
        IsEconomy = isEconomy;
        IsApartment = isApartment;
        BrandName = brandName;
        CheckInNum = checkInNum;
        Facilities = facilities;
        CurrentPage = currentPage;
        PageNumber = pageNumber;
        SortBy = sortBy;
        SortType = sortType;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String CheckInDate) {
        this.CheckInDate = CheckInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String CheckOutDate) {
        this.CheckOutDate = CheckOutDate;
    }

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String Keywords) {
        this.Keywords = Keywords;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    public String getDowntown() {
        return Downtown;
    }

    public void setDowntown(String Downtown) {
        this.Downtown = Downtown;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String Landmark) {
        this.Landmark = Landmark;
    }

    public String getPriceRange() {
        return PriceRange;
    }

    public void setPriceRange(String PriceRange) {
        this.PriceRange = PriceRange;
    }

    public String getStarLevel() {
        return StarLevel;
    }

    public void setStarLevel(String StarLevel) {
        this.StarLevel = StarLevel;
    }

    public String getIsEconomy() {
        return IsEconomy;
    }

    public void setIsEconomy(String IsEconomy) {
        this.IsEconomy = IsEconomy;
    }

    public String getIsApartment() {
        return IsApartment;
    }

    public void setIsApartment(String IsApartment) {
        this.IsApartment = IsApartment;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public String getCheckInNum() {
        return CheckInNum;
    }

    public void setCheckInNum(String CheckInNum) {
        this.CheckInNum = CheckInNum;
    }

    public String getFacilities() {
        return Facilities;
    }

    public void setFacilities(String Facilities) {
        this.Facilities = Facilities;
    }

    public String getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(String CurrentPage) {
        this.CurrentPage = CurrentPage;
    }

    public String getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(String PageNumber) {
        this.PageNumber = PageNumber;
    }

    public String getSortBy() {
        return SortBy;
    }

    public void setSortBy(String SortBy) {
        this.SortBy = SortBy;
    }

    public String getSortType() {
        return SortType;
    }

    public void setSortType(String SortType) {
        this.SortType = SortType;
    }
}
