package cn.cassan.pm.util;

import cn.cassan.pm.AppContext;

/**
 * 字体工具包
 */
public class FontSizeUtils {

    public static final String WEBVIEW_FONT_SIZE_KEY = "webview_font_size_key";

    public static int getSaveFontSizeIndex() {
        return AppContext.getPreferences().getInt(WEBVIEW_FONT_SIZE_KEY, 3);
    }

    public static String getSaveFontSize() {
        return getFontSize(getSaveFontSizeIndex());
    }

    public static String getFontSize(int fontSizeIndex) {
        String fontSize = "";
        switch (fontSizeIndex) {
            case 0:
                fontSize = "javascript:showSuperBigSize()";
                break;
            case 1:
                fontSize = "javascript:showBigSize()";
                break;
            case 2:
                fontSize = "javascript:showMidSize()";
                break;
            default:
                fontSize = "javascript:showSmallSize()";
                break;
        }
        return fontSize;
    }

    public static void saveFontSize(int fontSizeIndex) {
        AppContext.set(WEBVIEW_FONT_SIZE_KEY, fontSizeIndex);
    }
}
