package cn.cassan.pm.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import cn.cassan.pm.AppManager;
import cn.cassan.pm.R;
import cn.cassan.pm.interf.BaseViewInterface;
import cn.cassan.pm.ui.dialog.CommonToast;
import cn.cassan.pm.ui.dialog.DialogControl;
import cn.cassan.pm.util.DialogHelp;
import cn.cassan.pm.util.TDevice;

/**
 * @author Created by zhihao on 2016/10/22.
 * @describe
 * @version_
 **/
public abstract class BaseFragmentActivity extends AppCompatActivity implements DialogControl, BaseViewInterface {

    public static final String INTENT_ACTION_EXIT_APP = "INTENT_ACTION_EXIT_APP";
    protected LayoutInflater mInflater;

    private boolean _isVisible;
    private ProgressDialog _waitDialog;
    private Context context;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
    }

    protected Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyappTheme);
        context = this;
        AppManager.getAppManager().addActivity(this);
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            mInflater = getLayoutInflater();
            // 通过注解绑定控件
            ButterKnife.bind(this);
            init(savedInstanceState);
            initView();
            initData();
            _isVisible = true;
        }
    }

    protected void onBeforeSetContentLayout() {
    }


    protected int getLayoutId() {
        return 0;
    }

    /**
     * 填充视图
     *
     * @param resId
     * @return
     */
    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }


    protected void init(Bundle savedInstanceState) {
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()) {
            TDevice.hideSoftKeyboard(getCurrentFocus());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showToast(int msgResid, int icon, int gravity) {
        showToast(getString(msgResid), icon, gravity);
    }

    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }

    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
