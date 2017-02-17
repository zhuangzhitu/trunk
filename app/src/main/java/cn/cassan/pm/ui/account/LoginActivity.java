package cn.cassan.pm.ui.account;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.utils.Log;

import org.kymjs.kjframe.http.HttpConfig;

import butterknife.Bind;
import butterknife.OnClick;
import cn.cassan.pm.AppConfig;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.api.ApiHttpClient;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseActivity;
import cn.cassan.pm.model.Constants;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.ui.MainActivity;
import cn.cassan.pm.util.Helper;
import cn.cassan.pm.util.JSONUtil;
import cn.cassan.pm.util.TDevice;
import cn.cassan.pm.util.TLog;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * 用户登录界面
 *
 * @author
 */
public class LoginActivity extends BaseActivity {

    protected static final String TAG = LoginActivity.class.getSimpleName();
    public static final int REQUEST_CODE_INIT = 0;
    private static final String BUNDLE_KEY_REQUEST_CODE = "BUNDLE_KEY_REQUEST_CODE";
    private final int requestCode = REQUEST_CODE_INIT;

    @Bind(R.id.et_username)
    EditText mEtUserName;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    private String mUserName = "";
    private String mPassword = "";

    BroadcastReceiver receiver;
    private UMSocialService mController;


    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String tem = new String(responseBody);
            ApiReturnResult result = JSONUtil.parseJSONString(tem);
            if (result != null) {
                handleLoginResult(result, headers);
            }
            Log.d("debug", result.getMessage() + "\n" + result.getStatus());
        }


        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            hideWaitDialog();
        }
    };

    @Override
    public void initData() {
        //读取保存的数据
        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {
            this.mEtUserName.setText(userInfo.getUsername());
            this.mEtPassword.setText(userInfo.getPassword());
        } else {
            this.mEtUserName.setText("");
            this.mEtPassword.setText("");
        }
    }

    @Override
    public void initView() {
        this.mEtPassword.setText("");
        this.mEtUserName.setText("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.btn_login, R.id.register, R.id.forgetpassword})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login: //登陆按钮
                handleLogin();
                break;
            case R.id.register: //注册
                handleRegister();
                break;
            case R.id.forgetpassword: //忘记密码
                handleForgetPwd();
                break;
            default:
                break;
        }
    }

    /**
     * 处理登陆按钮单击
     */
    private void handleLogin() {
        if (prepareForLogin()) {
            return;
        }
        mUserName = mEtUserName.getText().toString();
        mPassword = mEtPassword.getText().toString();
        showWaitDialog(R.string.progress_login);
        //登陆后台系统
        ProjectManagementApi.login(mUserName, mPassword, android.os.Build.MODEL, mHandler);
        Helper.hideSoftInput(this);


    }

    /**
     * 处理注册按钮单击
     */
    private void handleRegister() {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 处理忘记密码单击
     */
    private void handleForgetPwd() {
        Intent intent = new Intent(this, FindPwdActivity.class);
        startActivity(intent);
    }

    /**
     * 登陆成功后处理
     */
    private void handleLoginSuccess() {
        //登陆到环信
        Intent data = new Intent();
        data.putExtra(BUNDLE_KEY_REQUEST_CODE, requestCode);
        setResult(RESULT_OK, data);
        this.sendBroadcast(new Intent(Constants.INTENT_ACTION_USER_CHANGE));
        TDevice.hideSoftKeyboard(getWindow().getDecorView());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 登陆前检查
     *
     * @return
     */
    private boolean prepareForLogin() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return true;
        }
        if (mEtUserName.length() == 0) {
            mEtUserName.setError("请输入邮箱/用户名");
            mEtUserName.requestFocus();
            return true;
        }

        if (mEtPassword.length() == 0) {
            mEtPassword.setError("请输入密码");
            mEtPassword.requestFocus();
            return true;
        }
        return false;
    }

    private void handleLoginResult(ApiReturnResult apiReturnResult, Header[] headers) {
        if (apiReturnResult.isOK()) {
            AsyncHttpClient client = ApiHttpClient.getHttpClient();
            HttpContext httpContext = client.getHttpContext();
            CookieStore cookies = (CookieStore) httpContext.getAttribute(HttpClientContext.COOKIE_STORE);
            if (cookies != null) {
                String tmpcookies = "";
                for (Cookie c : cookies.getCookies()) {
                    TLog.log(TAG, "cookie:" + c.getName() + " " + c.getValue());
                    tmpcookies += (c.getName() + "=" + c.getValue()) + ";";
                }
                if (TextUtils.isEmpty(tmpcookies)) {
                    if (headers != null) {
                        for (Header header : headers) {
                            String key = header.getName();
                            String value = header.getValue();
                            if (key.contains("Set-Cookie"))
                                tmpcookies += value + ";";
                        }
                        if (tmpcookies.length() > 0) {
                            tmpcookies = tmpcookies.substring(0, tmpcookies.length() - 1);
                        }
                    }
                }
                TLog.log(TAG, "cookies:" + tmpcookies);
                AppContext.getInstance().setProperty(AppConfig.CONF_COOKIE, tmpcookies);
                ApiHttpClient.setCookie(ApiHttpClient.getCookie(AppContext.getInstance()));
                HttpConfig.sCookie = tmpcookies;
            }
            // 保存登录信息
            String data = apiReturnResult.getData();
            if (data != null) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement json = parser.parse(data);
                if (json.isJsonObject()) {
                    UserInfo userInfo = gson.fromJson(data, UserInfo.class);
                    userInfo.setRememberMe(true);
                    userInfo.setPassword(mPassword);
                    AppContext.getInstance().saveUserInfo(userInfo);

                }
            }
            handleLoginSuccess();
        } else {
            AppContext.getInstance().cleanLoginInfo();
            AppContext.showToast(apiReturnResult.getMessage());
        }
        this.hideWaitDialog();
    }
}
