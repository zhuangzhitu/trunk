package cn.cassan.pm.interf;

/**
 * 监听webview上的图片
 *
 * @author
 */
public interface OnWebViewImageListener {

    /**
     * 点击webview上的图片，传入该缩略图的大图Url
     *
     * @param bigImageUrl
     */
    void showImagePreview(String bigImageUrl);

}
