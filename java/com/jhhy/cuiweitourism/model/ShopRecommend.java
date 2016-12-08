package com.jhhy.cuiweitourism.model;

/**
 * Created by birney1 on 16/9/29.
 * 找商铺——>商铺
 */
public class ShopRecommend {

    private String id;
    private String name;
    private String litpic;

    public ShopRecommend() {
    }

    public ShopRecommend(String id, String name, String litpic) {
        this.id = id;
        this.name = name;
        this.litpic = litpic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }
}
