package com.jhhy.cuiweitourism.net.biz;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by birney1 on 16/9/28.
 */
public class BasicActionBiz {

    protected Context context;
    protected Handler handler;

    public BasicActionBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public BasicActionBiz() {
    }

    public JsonElement parseJsonBody(FetchResponseModel model){
        JsonParser parser = new JsonParser();
        if(model.body != null && !("null".equals(model.body)) && model.body.length() > 0){
            JsonElement element = parser.parse(model.body);
            return element;
        }
        else {
            return null;
        }


    }

    public  ArrayList<ArrayList<String>> parseJsonTotwoLevelArray(FetchResponseModel model){
        JsonElement element = parseJsonBody(model);
        if (element != null && element.isJsonArray()) {
            ArrayList<ArrayList<String>> array = new  ArrayList<ArrayList<String>>();
            array = new Gson().fromJson(element, array.getClass());
            return array;
        }
        else {
            return null;
        }
    }

    public <T> T getOBject(Class<T> c) throws InstantiationException,IllegalAccessException{
        T t = c.newInstance();
        return t;
    }

    public <T> ArrayList<T>  parseJsonToObjectArray(FetchResponseModel model,Class<T> c){
        JsonElement element = parseJsonBody(model);
        ArrayList<T> array = new ArrayList<T>();
        JsonArray jsonArray = element.getAsJsonArray();
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JsonElement e = (JsonElement) it.next();
            T detail = new Gson().fromJson(e, c);
            array.add(detail);
        }
        return  array;
    }

    public <T> T parseJsonToObject(FetchResponseModel model,Class<T> c){
        JsonElement element = parseJsonBody(model);
        T object = new Gson().fromJson(element,c);
        return object;
    }

//    public void getBizMap{
//        Map<String, Object> headMap = new HashMap<>();
//        headMap.put(Consts.KEY_CODE, CODE_ORDER_DETAIL);
//        Map<String, Object> fieldMap = new HashMap<>();
//
//    }

}
