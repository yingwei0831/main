package com.jhhy.cuiweitourism.moudle;

/**
 * Created by jiahe008 on 2016/9/26.
 * 热门签证
 */
public class VisaHotCountry {

    private String id;
    private String price;
    private String litpic;
    private String title;

    public VisaHotCountry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "VisaHotCountry{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", litpic='" + litpic + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
