package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketCityFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInfoForChinalRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInfoInternationalRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.net.utils.HanziToPinyin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by birney on 2016-10-10.
 */
public class PlaneTicketActionBiz extends BasicActionBiz {

    public PlaneTicketActionBiz(Context context, Handler handler) {
        super(context, handler);
    }

    public PlaneTicketActionBiz(){
    }

    /**
     * 飞机出发城市、到达城市
     */
    public void getPlaneTicketCityInfo(PlaneTicketCityFetch request, BizGenericCallback<ArrayList<PlaneTicketCityInfo>> callback){
        request.code = "Plane_flyarea";
        FetchGenericResponse<ArrayList<PlaneTicketCityInfo>> fetchGenericResponse = new FetchGenericResponse<ArrayList<PlaneTicketCityInfo>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<PlaneTicketCityInfo> array = parseJsonToObjectArray(response,PlaneTicketCityInfo.class);
                HanziToPinyin hzToPyConvertor = HanziToPinyin.getInstance();
                for (PlaneTicketCityInfo itemInfo : array){
                    HanziToPinyin.PinYinCollection collection = hzToPyConvertor.getPinYin(itemInfo.name);
                    itemInfo.fullPY = collection.fullPY;
                    itemInfo.shortPY = collection.shortPY;
                    itemInfo.headChar = String.valueOf(collection.headChar);
                }
                GenericResponseModel<ArrayList<PlaneTicketCityInfo>> returnModel = new GenericResponseModel<ArrayList<PlaneTicketCityInfo>>(response.head,array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(request,new FetchGenericCallback<>(fetchGenericResponse));
    }

    /**
     *  国内机票
     */

    public void planeTicketInfoInChina(PlaneTicketInfoForChinalRequest request, BizGenericCallback<PlaneTicketInfoOfChina> callback) {
        request.code = "Fly_index";
        FetchGenericResponse<PlaneTicketInfoOfChina> fetchResponse = new FetchGenericResponse<PlaneTicketInfoOfChina>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                PlaneTicketInfoOfChina info = parseJsonToObject(response, PlaneTicketInfoOfChina.class);
                GenericResponseModel<PlaneTicketInfoOfChina> returnModel = new GenericResponseModel<PlaneTicketInfoOfChina>(response.head, info);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(request, new FetchGenericCallback<>(fetchResponse));
    }

    /**
     *  国际机票
     */

    public void planeTicketInfoOfInternational(final PlaneTicketInfoInternationalRequest request, BizGenericCallback<PlaneTicketInternationalInfo> callback){
        request.code = "Plane_gjhb";
        FetchGenericResponse<PlaneTicketInternationalInfo> fetchGenericResponse = new FetchGenericResponse<PlaneTicketInternationalInfo>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                final Gson gson = new Gson();
                final Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
                final HashMap<String,Object> map = gson.fromJson(response.body,type);
                PlaneTicketInternationalInfo info = new PlaneTicketInternationalInfo();
                if (map.containsKey("P")){
                    LinkedTreeMap<String,LinkedTreeMap<String,ArrayList<String>>> content =  (LinkedTreeMap<String,LinkedTreeMap<String,ArrayList<String>>>)map.get("P");;
                    Iterator iter = content.entrySet().iterator();
                    ArrayList<PlaneTicketInternationalInfo.AirportInfo> aiports = new ArrayList<>();
                    while (iter.hasNext()) {
                        Map.Entry<String,ArrayList<String>> entry = (Map.Entry<String,ArrayList<String>>) iter.next();
                        ArrayList<String> value = entry.getValue();
                        PlaneTicketInternationalInfo.AirportInfo airportInfo = new PlaneTicketInternationalInfo.AirportInfo();
                        airportInfo.airportCode = entry.getKey();
                        airportInfo.abbreviation = value.get(0);
                        airportInfo.fullName = value.get(1);
                        airportInfo.city = value.get(2);
                        airportInfo.cityCode = value.get(3);
                        aiports.add(airportInfo);
                    }
                    info.P = aiports;
                }

                if (map.containsKey("J")){
                    LinkedTreeMap<String,LinkedTreeMap<String,ArrayList<String>>> content =  (LinkedTreeMap<String,LinkedTreeMap<String,ArrayList<String>>>)map.get("J");;
                    Iterator iter = content.entrySet().iterator();
                    ArrayList<PlaneTicketInternationalInfo.AircraftTypeInfo> aircrafts = new ArrayList<>();
                    while (iter.hasNext()) {
                        Map.Entry<String,ArrayList<String>> entry = (Map.Entry<String,ArrayList<String>>) iter.next();
                        ArrayList<String> value = entry.getValue();
                        PlaneTicketInternationalInfo.AircraftTypeInfo aircraft = new PlaneTicketInternationalInfo.AircraftTypeInfo();
                        aircraft.typeCode = entry.getKey();
                        aircraft.typeName = value.get(0);
                        aircraft.typeNum = value.get(1);
                        aircraft.airframe = value.get(2);
                        aircraft.minNum = value.get(3);
                        aircraft.maxNum = value.get(4);
                        aircrafts.add(aircraft);
                    }
                    info.J = aircrafts;
                }


                if(map.containsKey("A")) {
                    LinkedTreeMap<String, LinkedTreeMap<String, ArrayList<String>>> content = (LinkedTreeMap<String, LinkedTreeMap<String, ArrayList<String>>>) map.get("A");
                    ;
                    Iterator iter = content.entrySet().iterator();
                    ArrayList<PlaneTicketInternationalInfo.AirlineCompanyInfo> airlineCompanyInfos = new ArrayList<>();
                    while (iter.hasNext()) {
                        Map.Entry<String, ArrayList<String>> entry = (Map.Entry<String, ArrayList<String>>) iter.next();
                        ArrayList<String> value = entry.getValue();
                        PlaneTicketInternationalInfo.AirlineCompanyInfo airlineCompany = new PlaneTicketInternationalInfo.AirlineCompanyInfo();
                        airlineCompany.airlineCompanyCode = entry.getKey();
                        airlineCompany.companyName = value.get(0);
                        airlineCompany.shortName = value.get(1);
                        airlineCompany.isOnlineCheckin = value.get(2);
                        airlineCompanyInfos.add(airlineCompany);
                    }
                    info.A = airlineCompanyInfos;
                }

                if (map.containsKey("R")){
                    LinkedTreeMap<String, LinkedTreeMap<String, String>> content = (LinkedTreeMap<String, LinkedTreeMap<String, String>>) map.get("R");
                    Iterator iter = content.entrySet().iterator();
                    ArrayList<PlaneTicketInternationalInfo.AircraftCabinInfo> aircraftCabinInfos = new ArrayList<>();
                    while (iter.hasNext()) {
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                        PlaneTicketInternationalInfo.AircraftCabinInfo aircraftCabinInfo = new PlaneTicketInternationalInfo.AircraftCabinInfo();
                        aircraftCabinInfo.levelCode = entry.getKey();
                        aircraftCabinInfo.levelDescription = entry.getValue();
                        aircraftCabinInfos.add(aircraftCabinInfo);
                    }
                    info.R = aircraftCabinInfos;
                }


                GenericResponseModel<PlaneTicketInternationalInfo> returnModel = new GenericResponseModel<PlaneTicketInternationalInfo>(response.head, info);
                this.bizCallback.onCompletion(returnModel);

            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };

        HttpUtils.executeXutils(request, new FetchGenericCallback<>(fetchGenericResponse));

    }

    /**
     *  国际飞机票政策
     */

    public void planeTicketInternationalPolicyInfo(){

    }

    /**
     *  出发城市，到达城市
     */
    public void planeTicketDepartureAndReachCity(){

    }
}
