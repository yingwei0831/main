package com.jhhy.cuiweitourism.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jhhy.cuiweitourism.moudle.CityRecordTrain;
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
    public List<CityRecordTrain> getTrainCityRecord(){
        List<CityRecordTrain> list = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(Consts.TABLE_CITY_RECORD,
                new String[]{CityRecord.CITY_ID, CityRecord.CITY_NAME, CityRecord.CITY_FULL_PINYIN, CityRecord.CITY_JIAN_PINYIN, CityRecord.CITY_HOT},
                CityRecord.CITY_RECORD_TYPE +"=? ", new String[]{"1"}, null, null, null);
        if (c != null){
            list = new ArrayList<>();
            while (c.moveToNext()){
                if (!list.contains(c.getString(c.getColumnIndex(CityRecord.CITY_NAME)))){
                    CityRecordTrain record = new CityRecordTrain();
                    record.setCityId(c.getString(c.getColumnIndex(CityRecord.CITY_ID)));
                    record.setCityName(c.getString(c.getColumnIndex(CityRecord.CITY_NAME)));
                    record.setCityFullPinyin(c.getString(c.getColumnIndex(CityRecord.CITY_FULL_PINYIN)));
                    record.setCityJianPinyin(c.getString(c.getColumnIndex(CityRecord.CITY_JIAN_PINYIN)));
                    record.setCityHot(c.getString(c.getColumnIndex(CityRecord.CITY_HOT)));
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
    public void addTrainCityRecord(CityRecordTrain record){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CityRecord.CITY_ID, record.getCityId());
        values.put(CityRecord.CITY_NAME, record.getCityName());
        values.put(CityRecord.CITY_FULL_PINYIN, record.getCityFullPinyin());
        values.put(CityRecord.CITY_JIAN_PINYIN, record.getCityJianPinyin());
        values.put(CityRecord.CITY_HOT, record.getCityHot());
        values.put(CityRecord.CITY_RECORD_TYPE, "1");
        long result = db.insert(Consts.TABLE_CITY_RECORD, null, values);
        db.close();
        helper.close();
    }
}
