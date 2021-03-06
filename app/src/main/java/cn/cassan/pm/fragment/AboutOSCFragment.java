package cn.cassan.pm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.base.BaseFragment;
import cn.cassan.pm.util.TDevice;
import cn.cassan.pm.util.UIHelper;
import cn.cassan.pm.util.UpdateManager;

public class AboutOSCFragment extends BaseFragment {

    @Bind(cn.cassan.pm.R.id.tv_version)
    TextView mTvVersionStatus;

    @Bind(cn.cassan.pm.R.id.tv_version_name)
    TextView mTvVersionName;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(cn.cassan.pm.R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(cn.cassan.pm.R.id.rl_check_update).setOnClickListener(this);
        //view.findViewById(cn.cassan.pm.R.id.rl_grade).setOnClickListener(this);
        //view.findViewById(cn.cassan.pm.R.id.rl_gitapp).setOnClickListener(this);
        view.findViewById(cn.cassan.pm.R.id.tv_oscsite).setOnClickListener(this);
        view.findViewById(cn.cassan.pm.R.id.tv_knowmore).setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTvVersionName.setText("V " + TDevice.getVersionName());
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
//            case cn.cassan.pm.R.id.rl_check_update:
//                onClickUpdate();
//                break;
//            case cn.cassan.pm.R.id.rl_grade:
//                TDevice.openAppInMarket(getActivity());
//                break;
           // case cn.cassan.pm.R.id.rl_gitapp:
//                boolean res = TDevice.openAppActivity(getActivity(),
//                        "net.oschina.gitapp", "net.oschina.gitapp.WelcomePage");

//                if (!res) {
//                    if (!TDevice.isHaveMarket(getActivity())) {
//                        UIHelper.openSysBrowser(getActivity(),
//                                "http://git.oschina.net/appclient");
//                    } else {
//                        TDevice.gotoMarket(getActivity(), "net.oschina.gitapp");
//                    }
//                }
     //           break;
            case cn.cassan.pm.R.id.tv_oscsite:
                //UIHelper.openBrowser(getActivity(), "https://www.oschina.net");
                break;
            case cn.cassan.pm.R.id.tv_knowmore:
               // UIHelper.openBrowser(getActivity(),
               //         "https://www.oschina.net/home/aboutosc");
                break;
            default:
                break;
        }
    }

    private void onClickUpdate() {
        new UpdateManager(getActivity(), true).checkUpdate();
    }
}
