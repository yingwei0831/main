package com.jhhy.cuiweitourism.net.models.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/12/9.
 * 国内机票退改签说明返回数据
 */
public class PlaneTicketOfChinaChangeBackRespond implements Serializable{

    /**
     * return : {"returnCode":"S","modifyAndRefundStipulateList":{"airlineCode":"CZ","changePercentAfter":50,"changePercentBefore":30,"changeStipulate":"按照当前舱位票面价收取变更费;起飞前2.0小时（含）以外收取当前舱位票面价的30.0%改期费,起飞前2.0小时以内及起飞后收取当前舱位票面价的50.0%改期费;","changeTimePoint":2,"modifyStipulate":"不能改签;","refundPercentAfter":100,"refundPercentBefore":50,"refundStipulate":"取消座位时间计算手续费;按照当前舱位票面价收取退票费;起飞前2.0小时（含）以外收取当前舱位票面价的50.0%退票费,起飞前2.0小时以内及起飞后不得退票;","refundTimePoint":2,"routType":"0","seatCode":"E","seatId":1750024,"seatType":1,"serviceLevel":"3","suitableAirline":"适用全部航线"}}
     */

    @SerializedName("return")
    private ReturnBean returnX;

    public ReturnBean getReturnX() {
        return returnX;
    }

    public void setReturnX(ReturnBean returnX) {
        this.returnX = returnX;
    }

    public static class ReturnBean {
        /**
         * returnCode : S
         * modifyAndRefundStipulateList : {"airlineCode":"CZ","changePercentAfter":50,"changePercentBefore":30,"changeStipulate":"按照当前舱位票面价收取变更费;起飞前2.0小时（含）以外收取当前舱位票面价的30.0%改期费,起飞前2.0小时以内及起飞后收取当前舱位票面价的50.0%改期费;","changeTimePoint":2,"modifyStipulate":"不能改签;","refundPercentAfter":100,"refundPercentBefore":50,"refundStipulate":"取消座位时间计算手续费;按照当前舱位票面价收取退票费;起飞前2.0小时（含）以外收取当前舱位票面价的50.0%退票费,起飞前2.0小时以内及起飞后不得退票;","refundTimePoint":2,"routType":"0","seatCode":"E","seatId":1750024,"seatType":1,"serviceLevel":"3","suitableAirline":"适用全部航线"}
         */

        private String returnCode;
        private ModifyAndRefundStipulateListBean modifyAndRefundStipulateList;

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public ModifyAndRefundStipulateListBean getModifyAndRefundStipulateList() {
            return modifyAndRefundStipulateList;
        }

        public void setModifyAndRefundStipulateList(ModifyAndRefundStipulateListBean modifyAndRefundStipulateList) {
            this.modifyAndRefundStipulateList = modifyAndRefundStipulateList;
        }
    }
    public static class ModifyAndRefundStipulateListBean {
        /**
         * airlineCode : CZ
         * changePercentAfter : 50
         * changePercentBefore : 30
         * changeStipulate : 按照当前舱位票面价收取变更费;起飞前2.0小时（含）以外收取当前舱位票面价的30.0%改期费,起飞前2.0小时以内及起飞后收取当前舱位票面价的50.0%改期费;
         * changeTimePoint : 2
         * modifyStipulate : 不能改签;
         * refundPercentAfter : 100
         * refundPercentBefore : 50
         * refundStipulate : 取消座位时间计算手续费;按照当前舱位票面价收取退票费;起飞前2.0小时（含）以外收取当前舱位票面价的50.0%退票费,起飞前2.0小时以内及起飞后不得退票;
         * refundTimePoint : 2
         * routType : 0
         * seatCode : E
         * seatId : 1750024
         * seatType : 1
         * serviceLevel : 3
         * suitableAirline : 适用全部航线
         */

        private String airlineCode;
        private int changePercentAfter;
        private int changePercentBefore;
        private String changeStipulate;
        private int changeTimePoint;
        private String modifyStipulate;
        private int refundPercentAfter;
        private int refundPercentBefore;
        private String refundStipulate;
        private int refundTimePoint;
        private String routType;
        private String seatCode;
        private int seatId;
        private int seatType;
        private String serviceLevel;
        private String suitableAirline;

        public String getAirlineCode() {
            return airlineCode;
        }

        public void setAirlineCode(String airlineCode) {
            this.airlineCode = airlineCode;
        }

        public int getChangePercentAfter() {
            return changePercentAfter;
        }

        public void setChangePercentAfter(int changePercentAfter) {
            this.changePercentAfter = changePercentAfter;
        }

        public int getChangePercentBefore() {
            return changePercentBefore;
        }

        public void setChangePercentBefore(int changePercentBefore) {
            this.changePercentBefore = changePercentBefore;
        }

        public String getChangeStipulate() {
            return changeStipulate;
        }

        public void setChangeStipulate(String changeStipulate) {
            this.changeStipulate = changeStipulate;
        }

        public int getChangeTimePoint() {
            return changeTimePoint;
        }

        public void setChangeTimePoint(int changeTimePoint) {
            this.changeTimePoint = changeTimePoint;
        }

        public String getModifyStipulate() {
            return modifyStipulate;
        }

        public void setModifyStipulate(String modifyStipulate) {
            this.modifyStipulate = modifyStipulate;
        }

        public int getRefundPercentAfter() {
            return refundPercentAfter;
        }

        public void setRefundPercentAfter(int refundPercentAfter) {
            this.refundPercentAfter = refundPercentAfter;
        }

        public int getRefundPercentBefore() {
            return refundPercentBefore;
        }

        public void setRefundPercentBefore(int refundPercentBefore) {
            this.refundPercentBefore = refundPercentBefore;
        }

        public String getRefundStipulate() {
            return refundStipulate;
        }

        public void setRefundStipulate(String refundStipulate) {
            this.refundStipulate = refundStipulate;
        }

        public int getRefundTimePoint() {
            return refundTimePoint;
        }

        public void setRefundTimePoint(int refundTimePoint) {
            this.refundTimePoint = refundTimePoint;
        }

        public String getRoutType() {
            return routType;
        }

        public void setRoutType(String routType) {
            this.routType = routType;
        }

        public String getSeatCode() {
            return seatCode;
        }

        public void setSeatCode(String seatCode) {
            this.seatCode = seatCode;
        }

        public int getSeatId() {
            return seatId;
        }

        public void setSeatId(int seatId) {
            this.seatId = seatId;
        }

        public int getSeatType() {
            return seatType;
        }

        public void setSeatType(int seatType) {
            this.seatType = seatType;
        }

        public String getServiceLevel() {
            return serviceLevel;
        }

        public void setServiceLevel(String serviceLevel) {
            this.serviceLevel = serviceLevel;
        }

        public String getSuitableAirline() {
            return suitableAirline;
        }

        public void setSuitableAirline(String suitableAirline) {
            this.suitableAirline = suitableAirline;
        }
    }
}
