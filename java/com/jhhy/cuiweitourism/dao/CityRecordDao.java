package com.jhhy.cuiweitourism.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jhhy.cuiweitourism.moudle.CityRecordTrain;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.utils.Consts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahe008 on 2016/10/20.
 */
public class CityRecordDao {

    private DBOpenHelper helper;

    public CityRecordDao(Context context) {
        helper = new DBOpenHelper(context);
    }

    /**
     * 获取火车票历史记录
     * @return
     */
    public List<TrainStationInfo> getTrainCityRecord(){
        List<TrainStationInfo> list = null;
        List<String> listName;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(Consts.TABLE_CITY_RECORD,
                new String[]{CityRecord.CITY_ID, CityRecord.CITY_NAME, CityRecord.CITY_FULL_PINYIN, CityRecord.CITY_JIAN_PINYIN, CityRecord.CITY_HOT},
                CityRecord.CITY_RECORD_TYPE +"=? ", new String[]{"1"}, null, null, null);
        if (c != null){
            list = new ArrayList<>();
            listName = new ArrayList<>();
            while (c.moveToNext()){
                if (!listName.contains(c.getString(c.getColumnIndex(CityRecord.CITY_NAME)))){
                    TrainStationInfo record = new TrainStationInfo();
                    record.setId(c.getString(c.getColumnIndex(CityRecord.CITY_ID)));
                    String name = c.getString(c.getColumnIndex(CityRecord.CITY_NAME));
                    record.setName(name);
                    listName.add(name);
                    record.setFullPY(c.getString(c.getColumnIndex(CityRecord.CITY_FULL_PINYIN)));
                    record.setShortPY(c.getString(c.getColumnIndex(CityRecord.CITY_JIAN_PINYIN)));
                    record.setHot( "1".equals(c.getString(c.getColumnIndex(CityRecord.CITY_HOT))));
                    list.add(record);
                }
            }
        }
        c.close();
        db.close();
        helper.close();
        return list;
    }

    /**
     * 增加火车票历史记录
     * type = "1"
     * @param record
     */
    public void addTrainCityRecord(TrainStationInfo record){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CityRecord.CITY_ID, record.getId());
        values.put(CityRecord.CITY_NAME, record.getName());
        values.put(CityRecord.CITY_FULL_PINYIN, record.getFullPY());
        values.put(CityRecord.CITY_JIAN_PINYIN, record.getShortPY());
        values.put(CityRecord.CITY_HOT, record.isHot());
        values.put(CityRecord.CITY_RECORD_TYPE, "1");
        long result = db.insert(Consts.TABLE_CITY_RECORD, null, values);
        db.close();
        helper.close();
    }
}
