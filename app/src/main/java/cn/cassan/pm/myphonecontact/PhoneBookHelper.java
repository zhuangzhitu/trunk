package cn.cassan.pm.myphonecontact;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Created by zhihao on 2016/10/29.
 * @describe
 * @version_
 **/
public class PhoneBookHelper {


    private static ArrayList<PhoneInfo> list;

    public static ArrayList<PhoneInfo> getPhoneNumberFromMobile(Context context) {


        list = new ArrayList<PhoneInfo>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String firstLetter = String.valueOf(FirstLetterUtil.chineseToPinyin(name));
            PhoneInfo phoneInfo = new PhoneInfo(name, number, firstLetter);
            list.add(phoneInfo);
        }

        return list;
    }

    /**
     * 去重数据
     *
     * @param list
     * @param <t>
     * @return
     */
    public static <t> List removeDuplicate(List<t> list) {

        Set<t> h = new HashSet<>(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
