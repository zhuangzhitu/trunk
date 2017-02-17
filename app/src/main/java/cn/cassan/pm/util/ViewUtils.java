package cn.cassan.pm.util;

import android.widget.TextView;

/**
 * View工具类
 */
public class ViewUtils {

    /***
     * 设置TextView的划线状态
     *
     */
    public static void setTextViewLineFlag(TextView tv, int flags) {
        tv.getPaint().setFlags(flags);
        tv.invalidate();
    }
}

