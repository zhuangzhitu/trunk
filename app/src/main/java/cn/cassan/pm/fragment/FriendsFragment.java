package cn.cassan.pm.fragment;

import cn.cassan.pm.adapter.MyFriendAdapter;
import cn.cassan.pm.base.BaseListAdapter;
import cn.cassan.pm.ui.project.BaseFrimeberFragment;

/**
 * @author Created by zhihao on 2016/10/28.
 * @describe 我的好友
 * @version_
 **/
public class FriendsFragment extends BaseFrimeberFragment {


    private static FriendsFragment friendsFragment;

    @Override
    public BaseListAdapter getAdapter() {

        return new MyFriendAdapter(getActivity());
    }

    public static FriendsFragment getInstance() {
        if (friendsFragment == null)
            friendsFragment = new FriendsFragment();
        return friendsFragment;
    }

    @Override
    protected void initListListner() {

    }
}
