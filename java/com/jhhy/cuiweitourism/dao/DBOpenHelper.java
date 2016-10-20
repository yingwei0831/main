package com.jhhy.cuiweitourism.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jhhy.cuiweitourism.net.utils.Consts;

/**
 * Created by jiahe008 on 2016/10/20.
 */
public class DBOpenHelper extends SQLiteOpenHelper {


//    private final String SQL_STATION = "create table " + Consts.TABLE_TRAIN_STATION + " ( " +
//            Station._ID + " integer primary key, " +
//            Station.TRAIN_ID + " text, " +
//            Station.TRAIN_NAME + " text, " +
//            Station.TRAIN_FULL_PINYIN + " text, " +
//            Station.TRAIN_JIAN_PINYIN + " text, " +
//            Station.TRAIN_HOT_ADDRESS + " text" +
//            " );";

    private final String SQL_CITY_RECORD = "create table " + Consts.TABLE_CITY_RECORD + " ( " +
            CityRecord._ID + " integer primary key, " +
            CityRecord.CITY_ID + " text, " +
            CityRecord.CITY_NAME + " text, " +
            CityRecord.CITY_FULL_PINYIN + " text, " +
            CityRecord.CITY_JIAN_PINYIN + " text, " +
            CityRecord.CITY_HOT + " text, " +
            CityRecord.CITY_RECORD_TYPE + " text " +
            " ); ";

    public DBOpenHelper(Context context){
        super(context, Consts.DATABASE_NAME, null, Consts.DATABASE_VERSION);
    }


    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Create a helper object to create, open, and/or manage a database.
     * The database is not actually created or opened until one of
     * {@link #getWritableDatabase} or {@link #getReadableDatabase} is called.
     * <p/>
     * <p>Accepts input param: a concrete instance of {@link DatabaseErrorHandler} to be
     * used to handle corruption when sqlite reports database corruption.</p>
     *
     * @param context      to use to open or create the database
     * @param name         of the database file, or null for an in-memory database
     * @param factory      to use for creating cursor objects, or null for the default
     * @param version      number of the database (starting at 1); if the database is older,
     *                     {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                     newer, {@link #onDowngrade} will be used to downgrade the database
     * @param errorHandler the {@link DatabaseErrorHandler} to be used when sqlite reports database
     */
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);
    }

    /**
     * 创建表
     * @param db
     */
    private void createDB(SQLiteDatabase db) {
//        LogUtil.i("info", SQL_USER);
//        LogUtil.i("info", SQL_RECORDS);

        db.execSQL(SQL_CITY_RECORD);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
