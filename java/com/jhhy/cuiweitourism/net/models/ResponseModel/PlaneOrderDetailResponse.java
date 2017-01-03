package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/3.
 * 机票订单详情
 */
public class PlaneOrderDetailResponse {
//  {"field":{"ordersn":"82761722097063"},"head":{"code":"User_orderinfo"}} //国内
//  {"field":{"ordersn":"82069528773043"},"head":{"code":"User_orderinfo"}} //国际
    /**
     * depCode : 北京
     * flightNo : CZ6140
     * arrCode : 大连
     * seatClass : H
     * depDate : 2016-12-14
     * depTime : 2225
     * arrTime : 2355
     * OrderStatus :
     * infos : {"agencyCode":"BQLXS","sign":"cc8888dd08da80bef499c60350f1556a","policyId":"186292715","createdBy":"eee","linkMan":"eee","linkPhone":"13260398606","notifiedUrl":"http://www.cwly1118.com/service.php/Fly/notifiedUrl","paymentReturnUrl":"http://www.cwly1118.com/service.php/Fly/notifiedUrl","pnrInfo":{"airportTax":"50","fuelTax":"0","parPrice":"590","passengers":[{"birthday":"","identityNo":"341881197605145871","identityType":"1","name":"Lll","type":"0","param1":""},{"birthday":"","identityNo":"3t0123198210248577","identityType":"1","name":"Huh","type":"0","param1":""}],"segments":[{"arrCode":"DLC","arrTime":"2355","depCode":"PEK","depDate":"2016-12-14","depTime":"2225","flightNo":"CZ6140","planeModel":"321","seatClass":"H"}]},"outOrderNo":"82761722097063"}
     * prices : 1252.00
     * productname : 北京飞往大连的航班
     */

    private String depCode;
    private String flightNo;
    private String arrCode;
    private String seatClass;
    private String depDate;
    private String depTime;
    private String arrTime;
    private String OrderStatus;
    private InfosBean infos;
    private String prices;
    private String productname;

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getArrCode() {
        return arrCode;
    }

    public void setArrCode(String arrCode) {
        this.arrCode = arrCode;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public InfosBean getInfos() {
        return infos;
    }

    public void setInfos(InfosBean infos) {
        this.infos = infos;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public static class InfosBean {
        /**
         * agencyCode : BQLXS
         * sign : cc8888dd08da80bef499c60350f1556a
         * policyId : 186292715
         * createdBy : eee
         * linkMan : eee
         * linkPhone : 13260398606
         * notifiedUrl : http://www.cwly1118.com/service.php/Fly/notifiedUrl
         * paymentReturnUrl : http://www.cwly1118.com/service.php/Fly/notifiedUrl
         * pnrInfo : {"airportTax":"50","fuelTax":"0","parPrice":"590","passengers":[{"birthday":"","identityNo":"341881197605145871","identityType":"1","name":"Lll","type":"0","param1":""},{"birthday":"","identityNo":"3t0123198210248577","identityType":"1","name":"Huh","type":"0","param1":""}],"segments":[{"arrCode":"DLC","arrTime":"2355","depCode":"PEK","depDate":"2016-12-14","depTime":"2225","flightNo":"CZ6140","planeModel":"321","seatClass":"H"}]}
         * outOrderNo : 82761722097063
         */

        private String agencyCode;
        private String sign;
        private String policyId;
        private String createdBy;
        private String linkMan;
        private String linkPhone;
        private String notifiedUrl;
        private String paymentReturnUrl;
        private PnrInfoBean pnrInfo;
        private String outOrderNo;

        public String getAgencyCode() {
            return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
            this.agencyCode = agencyCode;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPolicyId() {
            return policyId;
        }

        public void setPolicyId(String policyId) {
            this.policyId = policyId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getLinkMan() {
            return linkMan;
        }

        public void setLinkMan(String linkMan) {
            this.linkMan = linkMan;
        }

        public String getLinkPhone() {
            return linkPhone;
        }

        public void setLinkPhone(String linkPhone) {
            this.linkPhone = linkPhone;
        }

        public String getNotifiedUrl() {
            return notifiedUrl;
        }

        public void setNotifiedUrl(String notifiedUrl) {
            this.notifiedUrl = notifiedUrl;
        }

        public String getPaymentReturnUrl() {
            return paymentReturnUrl;
        }

        public void setPaymentReturnUrl(String paymentReturnUrl) {
            this.paymentReturnUrl = paymentReturnUrl;
        }

        public PnrInfoBean getPnrInfo() {
            return pnrInfo;
        }

        public void setPnrInfo(PnrInfoBean pnrInfo) {
            this.pnrInfo = pnrInfo;
        }

        public String getOutOrderNo() {
            return outOrderNo;
        }

        public void setOutOrderNo(String outOrderNo) {
            this.outOrderNo = outOrderNo;
        }

        public static class PnrInfoBean {
            /**
             * airportTax : 50
             * fuelTax : 0
             * parPrice : 590
             * passengers : [{"birthday":"","identityNo":"341881197605145871","identityType":"1","name":"Lll","type":"0","param1":""},{"birthday":"","identityNo":"3t0123198210248577","identityType":"1","name":"Huh","type":"0","param1":""}]
             * segments : [{"arrCode":"DLC","arrTime":"2355","depCode":"PEK","depDate":"2016-12-14","depTime":"2225","flightNo":"CZ6140","planeModel":"321","seatClass":"H"}]
             */

            private String airportTax;
            private String fuelTax;
            private String parPrice;
            private List<PassengersBean> passengers;
            private List<SegmentsBean> segments;

            public String getAirportTax() {
                return airportTax;
            }

            public void setAirportTax(String airportTax) {
                this.airportTax = airportTax;
            }

            public String getFuelTax() {
                return fuelTax;
            }

            public void setFuelTax(String fuelTax) {
                this.fuelTax = fuelTax;
            }

            public String getParPrice() {
                return parPrice;
            }

            public void setParPrice(String parPrice) {
                this.parPrice = parPrice;
            }

            public List<PassengersBean> getPassengers() {
                return passengers;
            }

            public void setPassengers(List<PassengersBean> passengers) {
                this.passengers = passengers;
            }

            public List<SegmentsBean> getSegments() {
                return segments;
            }

            public void setSegments(List<SegmentsBean> segments) {
                this.segments = segments;
            }

            public static class PassengersBean {
                /**
                 * birthday :
                 * identityNo : 341881197605145871
                 * identityType : 1
                 * name : Lll
                 * type : 0
                 * param1 :
                 */

                private String birthday;
                private String identityNo;
                private String identityType;
                private String name;
                private String type;
                private String param1;

                public String getBirthday() {
                    return birthday;
                }

                public void setBirthday(String birthday) {
                    this.birthday = birthday;
                }

                public String getIdentityNo() {
                    return identityNo;
                }

                public void setIdentityNo(String identityNo) {
                    this.identityNo = identityNo;
                }

                public String getIdentityType() {
                    return identityType;
                }

                public void setIdentityType(String identityType) {
                    this.identityType = identityType;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getParam1() {
                    return param1;
                }

                public void setParam1(String param1) {
                    this.param1 = param1;
                }
            }

            public static class SegmentsBean {
                /**
                 * arrCode : DLC
                 * arrTime : 2355
                 * depCode : PEK
                 * depDate : 2016-12-14
                 * depTime : 2225
                 * flightNo : CZ6140
                 * planeModel : 321
                 * seatClass : H
                 */

                private String arrCode;
                private String arrTime;
                private String depCode;
                private String depDate;
                private String depTime;
                private String flightNo;
                private String planeModel;
                private String seatClass;

                public String getArrCode() {
                    return arrCode;
                }

                public void setArrCode(String arrCode) {
                    this.arrCode = arrCode;
                }

                public String getArrTime() {
                    return arrTime;
                }

                public void setArrTime(String arrTime) {
                    this.arrTime = arrTime;
                }

                public String getDepCode() {
                    return depCode;
                }

                public void setDepCode(String depCode) {
                    this.depCode = depCode;
                }

                public String getDepDate() {
                    return depDate;
                }

                public void setDepDate(String depDate) {
                    this.depDate = depDate;
                }

                public String getDepTime() {
                    return depTime;
                }

                public void setDepTime(String depTime) {
                    this.depTime = depTime;
                }

                public String getFlightNo() {
                    return flightNo;
                }

                public void setFlightNo(String flightNo) {
                    this.flightNo = flightNo;
                }

                public String getPlaneModel() {
                    return planeModel;
                }

                public void setPlaneModel(String planeModel) {
                    this.planeModel = planeModel;
                }

                public String getSeatClass() {
                    return seatClass;
                }

                public void setSeatClass(String seatClass) {
                    this.seatClass = seatClass;
                }
            }
        }
    }
}
