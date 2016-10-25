package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.jhhy.cuiweitourism.net.models.FetchModel.NullArrayFetchModel;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainStationFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainStopsFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStopsInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketOrderInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.FetchResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by birney on 2016-10-10.
 */
public class TrainTicketActionBiz extends BasicActionBiz {

    public TrainTicketActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    public TrainTicketActionBiz(){

    }

    /**
     *  火车票
     */

    public void trainTicketInfo(TrainTicketFetch fetch, BizGenericCallback<ArrayList<TrainTicketDetailInfo>> callback) {
        fetch.code = "Train_index";

        FetchGenericResponse<ArrayList<TrainTicketDetailInfo>> fetchResponse = new FetchGenericResponse<ArrayList<TrainTicketDetailInfo>>(callback) {
            @Override
            public void onCompletion(final FetchResponseModel response) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        ArrayList array = parseJsonTotwoLevelArray(response);
                        ArrayList ticketsArray = new ArrayList<TrainTicketDetailInfo>();
                        for(int i = 0; i < array.size(); i++){
                            ArrayList ticketItem = (ArrayList) array.get(i);
                            TrainTicketDetailInfo ticketInfo = new TrainTicketDetailInfo();
                            ticketInfo.trainNum = (String)ticketItem.get(0);
                            ticketInfo.trainTypeName =(String)ticketItem.get(1);
                            ticketInfo.trainTypeCode = (String )ticketItem.get(2);
                            ticketInfo.departureStation = (String )ticketItem.get(3);
                            ticketInfo.departureDate = (String )ticketItem.get(4);
                            ticketInfo.departureTime = (String )ticketItem.get(5);
                            ticketInfo.arrivalStation = (String )ticketItem.get(6);
                            ticketInfo.arrivalDate = (String )ticketItem.get(7);
                            ticketInfo.arrivalTime = (String )ticketItem.get(8);
                            ticketInfo.duration = (String )ticketItem.get(9);
                            ticketInfo.startingStation = (String )ticketItem.get(10);
                            ticketInfo.startingTime = (String )ticketItem.get(11);
                            ticketInfo.terminus = (String )ticketItem.get(12);
                            ticketInfo.terminalTime = (String )ticketItem.get(13);
                            ticketInfo.numberOfRemainingSeats = (String)ticketItem.get(14);
                            ArrayList seatArray = (ArrayList)ticketItem.get(15);
                            for (int j = 0; j < seatArray.size();j++){
                                ArrayList seatItem = (ArrayList)seatArray.get(j);

                                TrainTicketDetailInfo.SeatInfo seatInfo = new TrainTicketDetailInfo.SeatInfo();

                                seatInfo.seatType = (String )seatItem.get(0);
                                seatInfo.seatName = (String )seatItem.get(1);
                                seatInfo.seatCode = (String )seatItem.get(2);
                                seatInfo.seatCount = (String )seatItem.get(3);
                                seatInfo.floorPrice = (String )seatItem.get(4);

                                ArrayList seatPrice = (ArrayList) seatItem.get(5);
                                if (seatPrice.size() >= 3){
                                    seatInfo.topBedPrice = (String )seatPrice.get(0);
                                    seatInfo.midBedprice = (String )seatPrice.get(1);
                                    seatInfo.downBenPrice = (String )seatPrice.get(2);
                                }
                                ticketInfo.seatInfoArray.add(seatInfo);
                            }

                            ticketsArray.add(ticketInfo);
                        }
                        GenericResponseModel model = new GenericResponseModel<>(response.head,ticketsArray);
                        bizCallback.onCompletion(model);
                    }
                }.start();

            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(fetch, new FetchGenericCallback<>(fetchResponse));
    }


    /**
     *  火车站
     */

    public void trainStationInfo(BizGenericCallback<ArrayList<TrainStationInfo>> callback){
        NullArrayFetchModel fetchModel = new NullArrayFetchModel();
        fetchModel.code = "Train_station";
        FetchGenericResponse<ArrayList<TrainStationInfo>> fetchResponse = new FetchGenericResponse<ArrayList<TrainStationInfo>>(callback) {
            @Override
            public void onCompletion(final FetchResponseModel response) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        ArrayList<ArrayList<String>> array = parseJsonTotwoLevelArray(response);
                        ArrayList<TrainStationInfo> statios = new ArrayList<TrainStationInfo>();
                        for (ArrayList<String> station : array){
                            TrainStationInfo itemStation = new TrainStationInfo();
                            itemStation.id = station.get(0);
                            itemStation.name = station.get(1);
                            itemStation.fullPY = station.get(2);
                            itemStation.shortPY = station.get(3);
                            itemStation.isHot = Integer.parseInt(station.get(4)) > 0 ;
                            itemStation.headChar = itemStation.fullPY.substring(0,1).toUpperCase();
                            itemStation.type = 0;
                            statios.add(itemStation);
                        }

                        GenericResponseModel<ArrayList<TrainStationInfo>> model = new GenericResponseModel<>(response.head,statios);
                        bizCallback.onCompletion(model);
                    }
                }.start();

            }

            @Override
            public void onError(FetchError error) {
                this.onError(error);
            }
        };

        HttpUtils.executeXutils(fetchModel,new FetchGenericCallback<>(fetchResponse));
    }

    /**
     *  火车途经站
     */
    public void trainViaStations(TrainStopsFetch fetch, BizGenericCallback<TrainStopsInfo> callback){
        fetch.code = "Train_subwaystation";

        FetchGenericResponse<TrainStopsInfo> fetchResponse = new FetchGenericResponse<TrainStopsInfo>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<TrainStopsInfo.Stop>  array = parseJsonToObjectArray(response,TrainStopsInfo.Stop.class);
                TrainStopsInfo info = new TrainStopsInfo(array);
                GenericResponseModel<TrainStopsInfo> returnModel = new GenericResponseModel<>(response.head,info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(fetch, new FetchGenericCallback<>(fetchResponse));
    }

    /**
     *  火车票订单提交,下单到平台
     */

    public void trainTicketOrderSubmit(TrainTicketOrderFetch fetch, BizGenericCallback<TrainTicketOrderInfo> callback){
        fetch.code = "Train_traininto";
        FetchGenericResponse<TrainTicketOrderInfo> fetchResponse = new FetchGenericResponse<TrainTicketOrderInfo>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                TrainTicketOrderInfo info = parseJsonToObject(response,TrainTicketOrderInfo.class);
                GenericResponseModel<TrainTicketOrderInfo> returnModel = new GenericResponseModel<TrainTicketOrderInfo>(response.head,info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(fetch, new FetchGenericCallback<>(fetchResponse));
    }

    /**
     *  火车票订单查询
     */

    public void trainTicketOrderQuery(){

    }

    /**
     *  火车票退票
     */

    public void trainTicketCancel(){

    }

}
