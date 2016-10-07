package com.jhhy.cuiweitourism.net.models.FetchModel;

import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangguang on 16/9/29.
 */

/**
 *  租车
 */
public class CarRentFetchModel extends BasicFetchModel {

    public JSONObject toBizJsonObject(){
        JSONObject jsonObject = super.toBizJsonObject();
        try {
            jsonObject.put(Consts.KEY_FIELD,new JSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }
}
