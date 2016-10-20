package com.jhhy.cuiweitourism.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.utils.Consts;

import java.util.ArrayList;

/**
 * Created by jiahe008 on 2016/10/20.
 */
public class TrainStationDao {

    private String TAG = TrainStationDao.class.getSimpleName();

    private DBOpenHelper helper;

    public TrainStationDao(Context context) {
        this.helper = new DBOpenHelper(context);
    }

    public ArrayList<ArrayList<String>> getStation(){
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<ArrayList<String>> lists = null;
        Cursor c = db.query(Consts.TABLE_TRAIN_STATION, new String[]{}, null, null, null, null, null);
        if (c != null){
            lists = new ArrayList<>();

        }
        c.close();
        db.close();
        helper.close();
        return lists;
    }
}
