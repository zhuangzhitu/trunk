package cn.cassan.pm.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import com.cassan.circlefriend.activity.CircleFriendActivity;
import com.dtr.zxing.activity.CaptureActivity;

import org.kymjs.kjframe.utils.DensityUtils;

import java.net.URLDecoder;

import cn.cassan.pm.AppConfig;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.interf.ICallbackResult;
import cn.cassan.pm.interf.OnWebViewImageListener;
import cn.cassan.pm.model.ShakeObject;
import cn.cassan.pm.myphonecontact.MyPhoneBookActivity;
import cn.cassan.pm.service.DownloadService;
import cn.cassan.pm.service.NoticeService;
import cn.cassan.pm.ui.workbench.DataFileRoomActivity;
import cn.cassan.pm.ui.account.LoginActivity;
import cn.cassan.pm.ui.contact.MyGroupActivity;
import cn.cassan.pm.ui.PhotosActivity;
import cn.cassan.pm.ui.message.NewMessageActivity;
import cn.cassan.pm.ui.project.CreateProjectActivity;
import cn.cassan.pm.ui.project.FriendsMembActivity;
import cn.cassan.pm.ui.project.ManageProjectActivity;
import cn.cassan.pm.widget.AvatarView;

/**
 * 界面帮助类
 *
 * @author
 */
public class UIHelper {

    /**
     * 全局web样式
     */
    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" " +
            "src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/detail_page" +
            ".js\"></script>"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window" +
            ".location.url= url;}</script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" " +
            "href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore" +
            ".css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common" +
            ".css\">";
    public final static String WEB_STYLE = linkCss;

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var " +
            "allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    private static final String SHOWIMAGE = "ima-api:action=showImage&data=";

    /**
     * 显示登录界面
     *
     * @param context
     */
    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void showMyPhoneBookActivity(Context context) {
        Intent intent = new Intent(context, MyPhoneBookActivity.class);
        context.startActivity(intent);
    }

    public static void showCreateProjectActivity(Context context) {
        Intent intent = new Intent(context, CreateProjectActivity.class);
        context.startActivity(intent);
    }

    public static void showMyGroupActivity(Context context) {
        Intent intent = new Intent(context, MyGroupActivity.class);
        context.startActivity(intent);
    }

    public static void showMyFriendsActivity(Context context) {
        Intent intent = new Intent(context, FriendsMembActivity.class);
        intent.putExtra("isFriendlist", true);
        context.startActivity(intent);
    }

    public static void showManagerProjectActivity(Context context, int projectId) {
        Intent intent = new Intent(context, ManageProjectActivity.class);
        intent.putExtra("projectId", projectId);
        context.startActivity(intent);
    }
    public static void showCirclefriendActivity(Context context) {
        Intent intent = new Intent(context, CircleFriendActivity.class);
        context.startActivity(intent);
    }
    /**
     * 发送消息
     *
     * @param context
     */
    public static void showNewMessageActivity(Context context) {
        Intent intent = new Intent(context, NewMessageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 签到
     *
     * @param context
     */
    public static void showSigninActivity(Context context) {

    }

    /**
     * 查资料
     *
     * @param context
     */
    public static void showDocumentActivity(Context context) {
        Intent intent = new Intent(context, DataFileRoomActivity.class);
        context.startActivity(intent);
    }

    /**
     * 知识库
     *
     * @param contex
     */
    public static void showKnowledgeActivity(Context contex) {

    }

    /**
     * 获取一个环形进度条等待窗口
     */
    public static ProgressDialog getprogress(Activity aty, String msg) {
        // 实例化一个ProgressBarDialog
        ProgressDialog progressDialog = new ProgressDialog(aty);
        progressDialog.setMessage(msg);
        progressDialog.getWindow().setLayout(
                DensityUtils.getScreenW(aty),
                DensityUtils.getScreenH(aty));
        progressDialog.setCancelable(true);
        // 设置ProgressBarDialog的显示样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        return progressDialog;
    }


    /**
     * show detail  method
     *
     * @param context context
     * @param type    type
     * @param id      id
     */
    public static void showDetail(Context context, int type, long id, String href) {
        //        switch (type) {
        //            case 0:
        //新闻链接
        //                showUrlRedirect(context, id, href);
        //                break;
        //            case 1:
        //                //软件推荐
        //                SoftwareDetailActivity.show(context, id);
        //                //UIHelper.showSoftwareDetailById(context, (int) id);
        //                break;
        //            case 2:
        //                //问答
        //                QuestionDetailActivity.show(context, id);
        //                break;
        //            case 3:
        //                //博客
        //                BlogDetailActivity.show(context, id);
        //                break;
        //            case 4:
        //                //4.翻译
        //                TranslateDetailActivity.show(context, id);
        //                break;
        //            case 5:
        //                //活动
        //                EventDetailActivity.show(context, id);
        //                break;
        //            default:
        //                //6.资讯
        //                NewsDetailActivity.show(context, id);
        //                break;
        //        }
    }


    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    public static void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(14);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 11) {
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
        //webView.setWebViewClient(UIHelper.getWebViewClient());
    }

    /**
     * 添加网页的点击图片展示支持
     */
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @JavascriptInterface
    public static void addWebImageShow(final Context cxt, WebView wv) {
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new OnWebViewImageListener() {
            @Override
            @JavascriptInterface
            public void showImagePreview(String bigImageUrl) {
                if (bigImageUrl != null && !StringUtils.isEmpty(bigImageUrl)) {
                    PhotosActivity.showImagePreview(cxt, bigImageUrl);
                }
            }
        }, "mWebViewImageListener");
    }

    public static String setHtmlCotentSupportImagePreview(String body) {
        // 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
        if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)
                || TDevice.isWifiOpen()) {
            // 过滤掉 img标签的width,height属性
            body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
            body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
            // 添加点击图片放大支持
            // 添加点击图片放大支持
            body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                    "$1$2\" onClick=\"showImagePreview('$2')\"");
        } else {
            // 过滤掉 img标签
            body = body.replaceAll("<\\s*img\\s+([^>]*)\\s*>", "");
        }

        // 过滤table的内部属性
        body = body.replaceAll("(<table[^>]*?)\\s+border\\s*=\\s*\\S+", "$1");
        body = body.replaceAll("(<table[^>]*?)\\s+cellspacing\\s*=\\s*\\S+", "$1");
        body = body.replaceAll("(<table[^>]*?)\\s+cellpadding\\s*=\\s*\\S+", "$1");

        return body;
    }

    /**
     * 摇一摇点击跳转
     *
     * @param obj
     */
    public static void showUrlShake(Context context, ShakeObject obj) {
        if (StringUtils.isEmpty(obj.getUrl())) {
            if (ShakeObject.RANDOMTYPE_NEWS.equals(obj.getRandomtype())) {
                //                UIHelper.showNewsDetail(context,
                //                        StringUtils.toInt(obj.getId()),
                //                        StringUtils.toInt(obj.getCommentCount()));
            }
        } else {
            if (!StringUtils.isEmpty(obj.getUrl())) {
                //UIHelper.showUrlRedirect(context, obj.getUrl());
            }
        }
    }


    /**
     * 打开内置浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {

        if (StringUtils.isImgUrl(url)) {
            PhotosActivity.showImagePreview(context, url);
            return;
        }

        if (url.startsWith("http://www.oschina.net/tweet-topic/")) {
            Bundle bundle = new Bundle();
            int i = url.lastIndexOf("/");
            if (i != -1) {
                bundle.putString("topic",
                        URLDecoder.decode(url.substring(i + 1)));
            }
            //            UIHelper.showSimpleBack(context, SimpleBackPage.TWEET_TOPIC_LIST,
            //                    bundle);
            return;
        }
        try {
            // 启用外部浏览器
            // Uri uri = Uri.parse(url);
            // Intent it = new Intent(Intent.ACTION_VIEW, uri);
            // context.startActivity(it);
           // Bundle bundle = new Bundle();
           // bundle.putString(BrowserFragment.BROWSER_KEY, url);
            // showSimpleBack(context, SimpleBackPage.BROWSER, bundle);
        } catch (Exception e) {
            e.printStackTrace();
            AppContext.showToastShort("无法浏览此网页");
        }
    }

    /**
     * 打开系统中的浏览器
     *
     * @param context
     * @param url
     */
    public static void openSysBrowser(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
            AppContext.showToastShort("无法浏览此网页");
        }
    }


    /**
     * 发送App异常崩溃报告
     *
     * @param context
     */
    public static void sendAppCrashReport(final Context context) {

        DialogHelp.getConfirmDialog(context, "程序发生异常", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 退出
                //   System.exit(-1);
            }
        }).show();
    }

    /**
     * 发送通知广播
     *
     * @param context
     * @param notice
     */
    //    public static void sendBroadCast(Context context, Notice notice) {
    //        if (!((AppContext) context.getApplicationContext()).isLogin()
    //                || notice == null)
    //            return;
    //        Intent intent = new Intent(Constants.INTENT_ACTION_NOTICE);
    //        Bundle bundle = new Bundle();
    //        bundle.putSerializable("notice_bean", notice);
    //        intent.putExtras(bundle);
    //        context.sendBroadcast(intent);
    //    }

    /**
     * 发送通知广播
     *
     * @param context
     */
    public static void sendBroadcastForNotice(Context context) {
        Intent intent = new Intent(NoticeService.INTENT_ACTION_BROADCAST);
        context.sendBroadcast(intent);
    }

    /**
     * 显示用户中心页面
     *
     * @param context
     * @param hisuid
     * @param hisuid
     * @param hisname
     */
    public static void showUserCenter(Context context, long hisuid,
                                      String hisname) {
        if (hisuid == 0 && hisname.equalsIgnoreCase("匿名")) {
            AppContext.showToast("提醒你，该用户为非会员");
            return;
        }
        Bundle args = new Bundle();
        args.putInt("his_id", (int) hisuid);
        args.putString("his_name", hisname);
        // showSimpleBack(context, SimpleBackPage.USER_CENTER, args);
    }

    /**
     * 显示用户的博客列表
     *
     * @param context
     * @param uid
     */
    public static void showUserBlog(Context context, int uid) {
        Bundle args = new Bundle();
        //        args.putInt(UserBlogFragment.USER_ID, uid);
        //        showSimpleBack(context, SimpleBackPage.USER_BLOG, args);
    }

    /**
     * 显示用户头像大图
     *
     * @param context
     * @param avatarUrl
     */
    public static void showUserAvatar(Context context, String avatarUrl) {
        if (StringUtils.isEmpty(avatarUrl)) {
            return;
        }
        String url = AvatarView.getLargeAvatar(avatarUrl);
        PhotosActivity.showImagePreview(context, url);
    }

    /**
     * 显示登陆用户的个人中心页面
     *
     * @param context
     */
    public static void showMyInformation(Context context) {
        // showSimpleBack(context, SimpleBackPage.MY_INFORMATION);
    }


    /**
     * 显示扫一扫界面
     *
     * @param context
     */
    public static void showScanActivity(Context context) {
        Intent intent = new Intent(context, CaptureActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示设置界面
     *
     * @param context
     */
    public static void showSetting(Context context) {
        //showSimpleBack(context, SimpleBackPage.SETTING);
    }

    /**
     * 显示通知设置界面
     *
     * @param context
     */
    public static void showSettingNotification(Context context) {
        //showSimpleBack(context, SimpleBackPage.SETTING_NOTIFICATION);
    }

    /**
     * 显示关于界面
     *
     * @param context
     */
    public static void showAboutOSC(Context context) {
        //showSimpleBack(context, SimpleBackPage.ABOUT_OSC);
    }

    /**
     * 清除app缓存
     *
     * @param activity
     */
    public static void clearAppCache(Activity activity) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    AppContext.showToastShort("缓存清除成功");
                } else {
                    AppContext.showToastShort("缓存清除失败");
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    AppContext.getInstance().clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    public static void openDownLoadService(Context context, String downurl,
                                           String tilte) {
        final ICallbackResult callback = new ICallbackResult() {

            @Override
            public void OnBackResult(Object s) {
            }
        };
        ServiceConnection conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                binder.addCallback(callback);
                binder.start();

            }
        };
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, downurl);
        intent.putExtra(DownloadService.BUNDLE_KEY_TITLE, tilte);
        context.startService(intent);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 发送广播告知评论发生变化
     *
     * @param context
     * @param isBlog
     * @param id
     * @param catalog
     * @param operation
     * @param replyComment
     */
    //    public static void sendBroadCastCommentChanged(Context context,
    //                                                   boolean isBlog, int id, int catalog, int
    //                                                           operation,
    //                                                   Comment replyComment) {
    //        Intent intent = new Intent(Constants.INTENT_ACTION_COMMENT_CHANGED);
    //        Bundle args = new Bundle();
    //        args.putInt(Comment.BUNDLE_KEY_ID, id);
    //        args.putInt(Comment.BUNDLE_KEY_CATALOG, catalog);
    //        args.putBoolean(Comment.BUNDLE_KEY_BLOG, isBlog);
    //        args.putInt(Comment.BUNDLE_KEY_OPERATION, operation);
    //        args.putParcelable(Comment.BUNDLE_KEY_COMMENT, replyComment);
    //        intent.putExtras(args);
    //        context.sendBroadcast(intent);
    //    }

}
