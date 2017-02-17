package cn.cassan.pm.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import cn.cassan.pm.model.Contact;
import cn.cassan.pm.util.BitmapHelper;

/**
 * 常用联系人数据库操作
 * 每次与人聊天后就自动加入一条
 */
public class TopContactDao {

    //创建常用联系人表，字段定义如下
    //联系人编号 整型，跟后台数据库对应
    //名称 字符串；
    //个人图片：blob,直接存储图片
    //最后联系时间 字符串；

    public static final String TABLE_NAME = "cassan_frequent_contact";
    public static final String COLUMN_NAME_ID = "contact_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_AVATAR = "avator";
    public static final String COLUMN_NAME_LASTACTIVITY = "lastactivity";


    private final DatabaseHelper dbHelper;

    public TopContactDao(Context context) {
        super();
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 插入常用联系人表
     *
     * @param contactid    联系人编号，与后台保持一直
     * @param name         姓名
     * @param lastactivity 最近联系时间
     * @param avator       图片
     */
    public void insert(int contactid, String name, long lastactivity, Bitmap avator) {

        SQLiteDatabase db = dbHelper.getWritableDatabase(); //获取数据库
        String sql = "INSERT INTO " + TABLE_NAME + " (contact_id,name,lastactivity,avator) VALUES(?,?,?,?)";//插入语句
        SQLiteStatement insertStmt = db.compileStatement(sql); //编译插入语句
        insertStmt.clearBindings();
        insertStmt.bindLong(1, contactid);
        insertStmt.bindString(2, name);
        insertStmt.bindLong(3, lastactivity);
        byte[] data = BitmapHelper.getBitmapAsByteArray(avator);
        insertStmt.bindBlob(4, data);
        insertStmt.executeInsert();
        insertStmt.clearBindings();
    }

    /**
     * 插入一条记录
     *
     * @param contact
     */
    public void insert(Contact contact) {
        if (contact != null) {
            this.insert(contact.getId(), contact.getName(), System.currentTimeMillis(), contact.getAvatar());
        }
    }

    /**
     * 查询常用联系人
     *
     * @param where 查询条件
     * @return
     */
    public List<Contact> query(String where) {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        ArrayList<Contact> data = new ArrayList();
        Cursor cursor = sqlite.rawQuery("select * from " + TABLE_NAME + where, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setId(cursor.getInt(0));
            contact.setLastactivity(cursor.getLong(2));
            contact.setName(cursor.getString(1));
            byte[] imgByte = cursor.getBlob(3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            contact.setAvatar(bitmap);
            data.add(contact);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();
        return data;
    }

    /**
     * 查询所有联系人
     *
     * @return
     */
    public List<Contact> queryAll() {
        return query(" ");
    }

    /**
     * 重置常用联系人，特别是在更换用户登陆后
     *
     * @param data
     */
    public void reset(List<Contact> data) {
        if (data != null) {
            SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
            // 删除全部
            sqlite.execSQL("delete from " + TABLE_NAME);
            // 重新添加
            for (Contact c : data) {
                this.insert(c);
            }
            sqlite.close();
        }
    }

    /**
     * 删除一个联系人
     *
     * @param id
     */
    public void delete(int id) {
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        String sql = ("delete from " + TABLE_NAME + " where contact_id=?");
        sqlite.execSQL(sql, new Integer[]{id});
        sqlite.close();
    }

    /**
     * 更新联系人，首先查询是否已经存在，不存在插入，存在就修改
     * TODO 有时间再写，时间太赶了
     *
     * @param contact
     */
    public void update(Contact contact) {

    }

    public void destroy() {
        dbHelper.close();
    }
}