package cn.cassan.pm;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.easemob.redpacketsdk.RedPacket;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.util.Properties;
import java.util.UUID;

import cn.cassan.pm.api.ApiHttpClient;
import cn.cassan.pm.base.BaseApplication;
import cn.cassan.pm.cache.DataCleanManager;
import cn.cassan.pm.model.Constants;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.util.CyptoUtils;
import cn.cassan.pm.util.Huanxin.HuanxinHelper;
import cn.cassan.pm.util.MethodsCompat;
import cn.cassan.pm.util.StringUtils;
import cn.cassan.pm.util.TLog;
import cn.cassan.pm.util.UIHelper;

import static cn.cassan.pm.AppConfig.KEY_FRITST_START;
import static cn.cassan.pm.AppConfig.KEY_LOAD_IMAGE;


/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 *
 * @author
 * @created
 */
public class AppContext extends BaseApplication {


    public static final int PAGE_SIZE = 20;// 默认分页大小
    private static AppContext instance;
    private int loginUid;
    private boolean login;

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    //region other

    public static void setLoadImage(boolean flag) {

        set(KEY_LOAD_IMAGE, flag);
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public static boolean isFristStart() {
        return getPreferences().getBoolean(KEY_FRITST_START, true);
    }

    public static void setFristStart(boolean frist) {
        set(KEY_FRITST_START, frist);
    }


    /**
     * 获取当前的项目编号
     */
    public static int getCurrentProjectId() {
        return getPreferences().getInt(AppConfig.KEY_CURRENT_PROJECTID + getInstance().getLoginUid(), 0);
    }

    /**
     * 设置当前项目编号
     *
     * @param projectId
     */
    public static void setCurrentProjectId(int projectId) {
        set(AppConfig.KEY_CURRENT_PROJECTID + getInstance().getLoginUid(),  projectId);
    }


    //endregion

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        HuanxinHelper.getInstance().init(this); //环信初始化
        RedPacket.getInstance().initContext(this);//初始化红包上下文，
        RedPacket.getInstance().setDebugMode(true);//开启日志输出开关
        initLogin(); //初始化登陆
        initImageLoader(); //配置ImagerLoader参数
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler(this));
        UIHelper.sendBroadcastForNotice(this);
    }

    private void initImageLoader() {
        String path = getPackageResourcePath() + "/Pm/cache/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).diskCache(new UnlimitedDiskCache(dir))
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    private void init() {
        // 初始化网络请求
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        ApiHttpClient.setHttpClient(client);
        ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));
        // Log控制器
        KJLoger.openDebutLog(BuildConfig.DEBUG);
        TLog.DEBUG = BuildConfig.DEBUG;
        // Bitmap缓存地址
        HttpConfig.CACHEPATH = "CASSAN/imagecache";
    }


    //region property setting
    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    public String getProperty(String key) {
        String res = AppConfig.getAppConfig(this).get(key);
        return res;
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }
    //endregion


    //region userinfo

    /**
     * 初始化登陆
     */
    private void initLogin() {
        UserInfo user = getLoginUserInfo();
        if (null != user && user.getId() > 0) {
            login = true;
            loginUid = user.getId();
        } else {
            this.cleanLoginInfo();
        }
    }

    /**
     * 保存用户信息
     *
     * @param user
     */
    public void saveUserInfo(final UserInfo user) {
        this.loginUid = user.getId();
        this.login = true;
        setProperty("user.id", String.valueOf(user.getId()));
        setProperty("user.name", user.getNickname());
        setProperty("user.avatar", user.getAvatarurl());// 用户头像-文件名
        setProperty("user.username", user.getUsername());
        setProperty("user.password", CyptoUtils.encode("cassanApp", user.getPassword()));
        setProperty("user.huanxinusername", user.getHuanxinusername());
        setProperty("user.huanxinpassword", user.getHuanxinpassword());
        setProperty("user.gender", String.valueOf(user.getGender()));
        setProperty("user.isRememberMe", String.valueOf(user.isRememberMe()));// 是否记住我的信息
        setProperty("user.token", user.getToken());
        setProperty("user.baiduchannelid",user.getBaiduchannelid());
        setProperty("user.mobilesystem",String.valueOf(user.getMobileSystem()));
        setProperty("user.systemversion",user.getSystemVersion());
    }


    /**
     * 获得登录用户的信息
     *
     * @return
     */
    public UserInfo getLoginUserInfo() {
        String tem = getProperty("user.id");
        if (tem == null)
            return null;
        UserInfo user = new UserInfo();
        user.setId(StringUtils.toInt(getProperty("user.id"), 0));
        user.setNickname(getProperty("user.name"));
        user.setAvatarurl(getProperty("user.avatar"));
        user.setUsername(getProperty("user.username"));
        user.setHuanxinusername(getProperty("user.huanxinusername"));
        user.setHuanxinpassword(getProperty("user.huanxinpassword"));
        user.setRememberMe(StringUtils.toBool(getProperty("user.isRememberMe")));
        tem = getProperty("user.gender");
        if (tem != null)
            user.setGender(Integer.parseInt(tem));
        user.setToken(getProperty("user.token"));
        user.setBaiduchannelid(getProperty("user.baiduchannelid"));
        tem = getProperty("user.mobilesystem");
        if (tem != null)
           user.setMobileSystem(Integer.parseInt(tem));
        user.setSystemVersion(getProperty("user.systemversion"));
        return user;
    }


    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.loginUid = 0;
        this.login = false;
        removeProperty("user.id", "user.name", "user.avatar", "user.password", "user.huanxinpassword", "user.isRememberMe", "user.gender", "user.token");
    }

    public int getLoginUid() {
        return loginUid;
    }

    public boolean isLogin() {
        return login;
    }

    /**
     * 用户注销
     */
    public void Logout() {
        cleanLoginInfo();
        ApiHttpClient.cleanCookie();
        this.cleanCookie();
        this.login = false;
        this.loginUid = 0;
        Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
        sendBroadcast(intent);
    }

    //endregion

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 清除保存的缓存
     */
    public void cleanCookie() {
        removeProperty(AppConfig.CONF_COOKIE);
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
        Core.getKJBitmap().cleanCache();
    }

//    public static Gson createGson() {
//        com.google.gson.GsonBuilder gsonBuilder = new com.google.gson.GsonBuilder();
//        //gsonBuilder.setExclusionStrategies(new SpecificClassExclusionStrategy(null, Model.class));
//        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        return gsonBuilder.create();
//    }

}
