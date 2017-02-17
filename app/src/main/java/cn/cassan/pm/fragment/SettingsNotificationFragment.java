package cn.cassan.pm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.AppConfig;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.base.BaseFragment;
import cn.cassan.pm.widget.togglebutton.ToggleButton;

public class SettingsNotificationFragment extends BaseFragment {

    @Bind(cn.cassan.pm.R.id.tb_accept)
    ToggleButton mTbAccept;
    @Bind(cn.cassan.pm.R.id.tb_voice)
    ToggleButton mTbVoice;
    @Bind(cn.cassan.pm.R.id.tb_vibration)
    ToggleButton mTbVibration;
    @Bind(cn.cassan.pm.R.id.tb_app_exit)
    ToggleButton mTbAppExit;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(cn.cassan.pm.R.layout.fragment_settings_notifcation, container,
                false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        setToggleChanged(mTbAccept, AppConfig.KEY_NOTIFICATION_ACCEPT);
        setToggleChanged(mTbVoice, AppConfig.KEY_NOTIFICATION_SOUND);
        setToggleChanged(mTbVibration, AppConfig.KEY_NOTIFICATION_VIBRATION);
        setToggleChanged(mTbAppExit, AppConfig.KEY_NOTIFICATION_DISABLE_WHEN_EXIT);

        view.findViewById(cn.cassan.pm.R.id.rl_accept).setOnClickListener(this);
        view.findViewById(cn.cassan.pm.R.id.rl_voice).setOnClickListener(this);
        view.findViewById(cn.cassan.pm.R.id.rl_vibration).setOnClickListener(this);
        view.findViewById(cn.cassan.pm.R.id.rl_app_exit).setOnClickListener(this);
    }

    public void initData() {
        setToggle(AppContext.get(AppConfig.KEY_NOTIFICATION_ACCEPT, true), mTbAccept);
        setToggle(AppContext.get(AppConfig.KEY_NOTIFICATION_SOUND, true), mTbVoice);
        setToggle(AppContext.get(AppConfig.KEY_NOTIFICATION_VIBRATION, true), mTbVibration);
        setToggle(AppContext.get(AppConfig.KEY_NOTIFICATION_DISABLE_WHEN_EXIT, true), mTbAppExit);
    }

    private void setToggleChanged(ToggleButton tb, final String key) {
        tb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {

            @Override
            public void onToggle(boolean on) {
                AppContext.set(key, on);
            }
        });
    }

    private void setToggle(boolean value, ToggleButton tb) {
        if (value)
            tb.setToggleOn();
        else
            tb.setToggleOff();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case cn.cassan.pm.R.id.rl_accept:
                mTbAccept.toggle();
                break;
            case cn.cassan.pm.R.id.rl_voice:
                mTbVoice.toggle();
                break;
            case cn.cassan.pm.R.id.rl_vibration:
                mTbVibration.toggle();
                break;
            case cn.cassan.pm.R.id.rl_app_exit:
                mTbAppExit.toggle();
                break;
        }
    }
}
