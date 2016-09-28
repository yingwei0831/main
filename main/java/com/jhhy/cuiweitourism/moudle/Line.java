package com.jhhy.cuiweitourism.moudle;

/**
 * Created by jiahe008 on 2016/9/9.
 * 线路类
 */
public class Line {
    private String lineId;
    private String lineTitle;
    private String linePicPath;
    private String linePrice;
    private String lineGroup; //聊天用，后台发布，此值为"";供应商发布，此值不为""

    public Line() {
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineTitle() {
        return lineTitle;
    }

    public void setLineTitle(String lineTitle) {
        this.lineTitle = lineTitle;
    }

    public String getLinePicPath() {
        return linePicPath;
    }

    public void setLinePicPath(String linePicPath) {
        this.linePicPath = linePicPath;
    }

    public String getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(String linePrice) {
        this.linePrice = linePrice;
    }

    public String getLineGroup() {
        return lineGroup;
    }

    public void setLineGroup(String lineGroup) {
        this.lineGroup = lineGroup;
    }

    @Override
    public String toString() {
        return "Line{" +
                "lineId ='" + lineId + '\'' +
                ", lineTitle ='" + lineTitle + '\'' +
                ", linePicPath ='" + linePicPath + '\'' +
                ", linePrice ='" + linePrice + '\'' +
                ", lineGroup ='" + lineGroup + '\'' +
                '}';
    }
}
