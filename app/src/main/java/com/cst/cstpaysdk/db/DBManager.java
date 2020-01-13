package com.cst.cstpaysdk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private final static String dbName = "CST_PAY_DB";
    private static DBManager mInstance;
    private static MyOpenHelper openHelper;
    private static Context mContext;

    private DBManager(Context context){
        mContext = context;
        openHelper = new MyOpenHelper(context, dbName, null);
    }

    public static DBManager getInstance(Context ctx) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(ctx);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new MyOpenHelper(mContext, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new MyOpenHelper(mContext, dbName, null);
        }
        return openHelper.getWritableDatabase();
    }

    public TradeRecordEntityDao getTradeRecordEntityDao(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getTradeRecordEntityDao();
    }

    public FaceInfoEntityDao getFaceInfoEntityDao(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getFaceInfoEntityDao();
    }

    public CardInfoEntityDao getCardInfoEntityDao(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getCardInfoEntityDao();
    }

    public EquipmentCreditEntityDao getEquipmentCreditEntityDao(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getEquipmentCreditEntityDao();
    }

    public UserCreditEntityDao getUserCreditEntityDao(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getUserCreditEntityDao();
    }
}
