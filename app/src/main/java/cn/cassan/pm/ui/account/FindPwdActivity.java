package cn.cassan.pm.ui.account;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.widget.TitleView;

/**
 * @author Created by zhihao on 2016/10/23.
 * @describe 找回密码，获取验证码
 * @version_
 **/
public class FindPwdActivity extends BaseFragmentActivity {

    @Bind(R.id.phonenum)
    EditText phone_num;
    @Bind(R.id.verification_Code)
    EditText vefi_code;
    @Bind(R.id.send_code)
    TextView tv_send_code;
    @Bind(R.id.btn_next)
    Button btn_next;

    @Bind(R.id.titleview)
    TitleView titleView;

    private int waitingTime = 60;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {


        }
    };

    @Override
    protected int getLayoutId() {

        return R.layout.activity_findpwd;
    }


    @OnClick({R.id.send_code, R.id.btn_next})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_code:
                break;
            case R.id.btn_next:

                break;
        }
    }

    @Override
    public void initView() {

        titleView.setTitleClickListener(new TitleView.TitleClickListener() {
            @Override
            public void onClick(String action) {

                switch (action) {
                    case TitleView.IMAGELEFT:
                    case TitleView.TEXTLEFT:
                        finish();
                }
            }
        });
    }

    @Override
    public void initData() {

    }
}
