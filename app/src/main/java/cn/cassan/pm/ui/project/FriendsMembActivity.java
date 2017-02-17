package cn.cassan.pm.ui.project;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import butterknife.Bind;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.fragment.ChooseMembersFragment;
import cn.cassan.pm.fragment.FriendsFragment;
import cn.cassan.pm.widget.TitleView;

/**
 * @author Created by zhihao on 2016/10/27.
 * @describe 好友和选择项目成员列表
 * @version_
 **/
public class FriendsMembActivity extends BaseFragmentActivity {

    @Bind(R.id.titleview)
    TitleView titleView;

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
        boolean isFriendlsit = getIntent().getBooleanExtra("isFriendlist", false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isFriendlsit) {
            transaction.add(R.id.fragment,
                    FriendsFragment.getInstance(),
                    "Fragment");
            titleView.setTitle("我的好友");
        } else {
            transaction.add(R.id.fragment,
                    ChooseMembersFragment.getInstance(),
                    "Fragment");
            titleView.setTitle("选择伙伴");
        }
        transaction.commit();
        //  String titleText = getFragmentManager().findFragmentByTag("Fragment").getArguments().getString("titletext");

    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_showchosememberlist;
    }
}
