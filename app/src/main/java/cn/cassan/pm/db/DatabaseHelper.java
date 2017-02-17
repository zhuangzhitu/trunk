package cn.cassan.pm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作帮助类
 * 主要包含常用联系人，记录最近看过的资料进行排序，存储一些聊天记录
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //数据版本
    private static final int DATABASE_VERSION = 1;
    //数据库名称
    public static final String CASSAN_DATABASE_NAME = "cassan";

    //创建联系人表
    public static final String CREATE_CONTACT_TABLE = "create table "
            + TopContactDao.TABLE_NAME + "( "
            + TopContactDao.COLUMN_NAME_ID+" interger,"
            + TopContactDao.COLUMN_NAME_NAME+" varchar(10),"
            + TopContactDao.COLUMN_NAME_AVATAR+"avator blob,"
            + TopContactDao.COLUMN_NAME_LASTACTIVITY+" varchar(10));";
//    //环信消息表
//    private static final String CREATE_INIVTE_MESSAGE_TABLE = "CREATE TABLE "
//            + InviteMessgeDao.TABLE_NAME + " ("
//            + InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
//            + InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
//            + InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
//            + InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
//            + InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
//            + InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
//            + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER, "
//            + InviteMessgeDao.COLUMN_NAME_TIME + " TEXT, "
//            + InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT); ";

    /**
     * 构造函数
     *
     * @param context
     */
    public DatabaseHelper(Context context) {

        super(context, CASSAN_DATABASE_NAME, null, 1);
    }

    /**
     * 创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACT_TABLE);
//        db.execSQL(CREATE_INIVTE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {

            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

}