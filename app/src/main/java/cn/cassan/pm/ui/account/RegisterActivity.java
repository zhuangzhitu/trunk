package cn.cassan.pm.ui.account;

import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.widget.MyEditText;

/**
 * @author Created by zhihao on 2016/10/23.
 * @describe 注册
 * @version_
 **/
public class RegisterActivity extends BaseFragmentActivity {

    private MyEditText phone_EditText, password_EditText;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_register;
    }

    @Override
    public void initView() {

        phone_EditText = (MyEditText) findViewById(R.id.phonenum);
        password_EditText = (MyEditText) findViewById(R.id.password);
        phone_EditText.setClear(false);
        password_EditText.setClear(false);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        phone_EditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        password_EditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }
}
