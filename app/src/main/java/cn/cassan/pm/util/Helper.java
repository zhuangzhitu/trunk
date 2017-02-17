package cn.cassan.pm.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.model.ErrorString;

/**
 * @author Created by zhihao on 2016/10/22.
 * @describe
 * @version_
 **/
public class Helper {

    public static void hideSoftInput(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void showSoftInput(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED);
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断电话Format
     */
    public static boolean isPhoneNumber(String account) {

        /**
         *手机号码 移动：134, 135, 136, 137, 138, 139, 147,
         *150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188
         * 联通：130, 131, 132, 155, 156, 185, 186, 145, 176
         * 电信：133, 153, 177, 180, 181, 189
         */
        String MOBILE = "^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$";
        /**
         * 10 * 中国移动：China Mobile 11 * 134,135,136,137,138,139,147,
         * 150,151,152,157,158,159,178,182,183,184,187,188 12
         */
        String CM = "^1((3[4-9]|47|5[0127-9]|78|8[23478])\\d)\\d{7}$";
        /**
         * 15 * 中国联通：China Unicom 16 * 130,131,132,145,152,155,156,176,185,186
         * 17
         */
        String CU = "^1(3[0-2]|45|5[256]|76|8[56])\\d{8}$";
        /**
         * 20 * 中国电信：China Telecom 21 * 133,153,177,180,181,189 22
         */
        String CT = "^1((33|53|77|8[019])[0-9])\\d{7}$";
        /**
         * 25 * 大陆地区固话及小灵通 26 * 区号：010,020,021,022,023,024,025,027,028,029 27 *
         * 号码：七位或八位 28
         */
        Pattern pattern1 = Pattern.compile(MOBILE);
        Matcher matcher1 = pattern1.matcher(account);
        Pattern pattern2 = Pattern.compile(CM);
        Matcher matcher2 = pattern2.matcher(account);
        Pattern pattern3 = Pattern.compile(CU);
        Matcher matcher3 = pattern3.matcher(account);
        Pattern pattern4 = Pattern.compile(CT);
        Matcher matcher4 = pattern4.matcher(account);
        return matcher1.matches() | matcher2.matches() | matcher3.matches() | matcher4.matches();
    }

    /**
     * 获取圆形图片的设置
     *
     * @param defaultId
     * @return
     */
    public static DisplayImageOptions getCircleOptions(int defaultId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(defaultId)
                .showImageOnLoading(defaultId).showImageOnFail(defaultId).cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).displayer(new CircleBitmapDisplayer()).build();
        return options;
    }

    public static void showErrorToast(byte[] responseBody, Context context) {

        String response = new String(responseBody);
        ApiReturnResult apiReturnResult = JSONUtil.parseErrorString(response);
        String data = apiReturnResult.getData();
        Gson gson = new Gson();
        ErrorString errorString = gson.fromJson(data, ErrorString.class);
        if (errorString != null) {
            Toast.makeText(context, errorString.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
