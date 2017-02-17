package cn.cassan.pm.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.Bind;
import cn.cassan.pm.AppConfig;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.AppManager;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseActivity;
import cn.cassan.pm.base.BaseApplication;
import cn.cassan.pm.cache.DataCleanManager;
import cn.cassan.pm.model.Constants;
import cn.cassan.pm.model.SimpleBackPage;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.service.NoticeUtils;
import cn.cassan.pm.util.BaiduPushUtils;
import cn.cassan.pm.util.TLog;
import cn.cassan.pm.util.UpdateManager;


public class MainActivity extends BaseActivity implements OnTabChangeListener, OnTouchListener {

    protected static final String TAG = MainActivity.class.getSimpleName();
    //通过后台服务获取的提示信息，比如消息数目等
//    public static Notice mNotice;

    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    //标题，不同的tab页显示不同的内容
    private CharSequence mTitle;

    //当前选中的tab
    private int currentTab = 0;
    private long mBackPressedTime;


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.INTENT_ACTION_NOTICE)) {
                //                mNotice = (Notice) intent.getSerializableExtra("notice_bean");
                //                int atmeCount = mNotice.getAtmeCount();// @我
                //                int msgCount = mNotice.getMsgCount();// 留言
                //                int reviewCount = mNotice.getReviewCount();// 评论
                //                int newFansCount = mNotice.getNewFansCount();// 新粉丝
                //                int newLikeCount = mNotice.getNewLikeCount();// 收到赞
                //int activeCount = atmeCount + reviewCount + msgCount + newFansCount + newLikeCount;
            }
        }
    };

    /**
     * 自动登陆到环信
     */
    private void AutoLoginToHuanxin() {

        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {
            // 登陆到环信
            userInfo.setHuanxinusername("13000000000");
            userInfo.setHuanxinpassword("123");
            EMClient.getInstance().login(userInfo.getHuanxinusername(), userInfo.getHuanxinpassword(), new EMCallBack() {

                @Override
                public void onSuccess() {
                    // 加载所有组和会话
                    //                    EMClient.getInstance().groupManager().loadAllGroups();
                    //                    EMClient.getInstance().chatManager().loadAllConversations();
                    TLog.log(TAG, "login to huanxin succeed");
                }

                @Override
                public void onProgress(int progress, String status) {
                    TLog.log(TAG, "logining into huanxin");
                }

                @Override
                public void onError(final int code, final String message) {
                    TLog.log(TAG, "login to huanxin fail" + code + message);
                }
            });
        }

    }

    /**
     * 自动登陆到百度推送
     */
    private void AutoLoginBaiduPush() {
        // Push: 无账号初始化，用api key绑定
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, BaiduPushUtils.getMetaValue(MainActivity.this, "api_key"));
        // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
        // PushManager.enableLbs(getApplicationContext());
    }

    /**
     * 初始化TABs
     */
    private void initTabs() {
        MainTabItem[] tabs = MainTabItem.values();
        int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTabItem mainTab = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = View.inflate(this, cn.cassan.pm.R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(cn.cassan.pm.R.id.tab_title);
            ImageView icon = (ImageView) indicator.findViewById(cn.cassan.pm.R.id.iv_icon);
            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            icon.setImageDrawable(drawable);
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(tab, mainTab.getTabItemClass(), null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {
            String token = userInfo.getToken();
            Log.e(getClass().getSimpleName(), token);
        }
        mTabHost.setup(this, getSupportFragmentManager(), cn.cassan.pm.R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        initTabs();
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
        //注册两个过滤器
        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_NOTICE);
        filter.addAction(Constants.INTENT_ACTION_LOGOUT);
        registerReceiver(mReceiver, filter);
        NoticeUtils.bindToService(this);
        //首次启动
        if (AppContext.isFristStart()) {
            DataCleanManager.cleanInternalCache(AppContext.getInstance());
            AppContext.setFristStart(false);
        }
        //检查更新
        checkUpdate();
        //自动登陆到换新
        AutoLoginToHuanxin();
        //自动登陆到百度推送
        AutoLoginBaiduPush();
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    /**
     * 处理传进来的intent
     */

    private void handleIntent(Intent intent) {
        if (intent == null)
            return;
        String action = intent.getAction();
        //        if (action != null && action.equals(Intent.ACTION_VIEW)) {
        //            UIHelper.showUrlRedirect(this, intent.getDataString());
        //        } else if (intent.getBooleanExtra("NOTICE", false)) {
        //            notifitcationBarClick(intent);
        //        }
    }



    /**
     * 检查更新
     */
    private void checkUpdate() {
        if (!AppContext.get(AppConfig.KEY_CHECK_UPDATE, true)) {
            return;
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new UpdateManager(MainActivity.this, false).checkUpdate();
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (this.currentTab >= 0 && this.currentTab < 4) {
            MainTabItem mainTab = MainTabItem.values()[this.currentTab];
            switch (this.currentTab) {
                case 0: //消息
                {
                    //自定义选项菜单
                    // getMenuInflater().inflate(cn.cassan.pm.R.menu.main_activity_menu, menu);
                    //自定义工具栏，绘制一个按钮
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                        actionBar.setCustomView(R.layout.actionbar_title_center);
                        actionBar.setDisplayShowCustomEnabled(true);
                        actionBar.setDisplayUseLogoEnabled(false);
                        TextView title = (TextView) actionBar.getCustomView().findViewById(R.id.title);
                        int titleRes = mainTab.getResName();
                        if (titleRes != 0 && title != null) {
                            title.setText(titleRes);
                        }
                    }

                }
                break;
                default: //标题居中
                {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                        actionBar.setCustomView(R.layout.actionbar_title_center);
                        actionBar.setDisplayShowCustomEnabled(true);
                        actionBar.setDisplayUseLogoEnabled(false);
                        TextView title = (TextView) actionBar.getCustomView().findViewById(R.id.title);
                        int titleRes = mainTab.getResName();
                        if (titleRes != 0 && title != null) {
                            title.setText(titleRes);
                        }
                    }
                }
                break;
            }
        } else {
            return false;
        }


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case cn.cassan.pm.R.id.search:
                //                UIHelper.showSimpleBack(this, SimpleBackPage.SEARCH);
                //                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
                this.currentTab = i;
            } else {
                v.setSelected(false);
            }
        }
        //重新绘制选项菜单
        supportInvalidateOptionsMenu();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            //            Fragment currentFragment = getCurrentFragment();
            //            if (currentFragment != null
            //                    && currentFragment instanceof OnTabReselectListener) {
            //                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
            //                listener.onTabReselect();
            //                consumed = true;
            //            }
        }
        return consumed;
    }


    @Override
    public void onBackPressed() {

        boolean isdoubleclick = BaseApplication.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true);
        if (isdoubleclick) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                finish();
            } else {
                mBackPressedTime = curTime;
                Toast.makeText(this, cn.cassan.pm.R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
            }
        } else {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        NoticeUtils.unbindFromService(this);
        unregisterReceiver(mReceiver);
        mReceiver = null;

        NoticeUtils.tryToShutDown(this);
        AppManager.getAppManager().removeActivity(this);
    }
}