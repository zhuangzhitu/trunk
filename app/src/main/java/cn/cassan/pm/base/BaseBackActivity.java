package cn.cassan.pm.base;

import android.support.v7.app.ActionBar;
import android.view.View;

/**
 * 有返回的基类窗口
 */

public abstract class BaseBackActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public void initView() {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setHomeButtonEnabled(true);
                }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {

    }
}
