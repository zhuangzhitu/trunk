package cn.cassan.pm.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.Bind;
import cn.cassan.pm.R;
import cn.cassan.pm.adapter.ChooseMemberAdapter;
import cn.cassan.pm.base.BaseListAdapter;
import cn.cassan.pm.model.Friend;
import cn.cassan.pm.ui.project.BaseFrimeberFragment;

/**
 * @author Created by zhihao on 2016/10/28.
 * @describe 好友列表或者选择项目伙伴
 * @version_
 **/
public class ChooseMembersFragment extends BaseFrimeberFragment {

    @Bind(R.id.btn_addMembers)
    Button btn_addMembers;
    private static ChooseMembersFragment friendsFragment;
    private LinkedList<String> friendid_list;
    private ArrayList<Friend> friendList_sel;

    @Override
    public BaseListAdapter getAdapter() {

        return new ChooseMemberAdapter(getActivity());
    }

    public static ChooseMembersFragment getInstance() {
        if (friendsFragment == null)
            friendsFragment = new ChooseMembersFragment();

        return friendsFragment;
    }

    @Override
    protected void initListListner() {

        friendid_list = new LinkedList<>();
        friendList_sel = new ArrayList<>();
        btn_addMembers.setVisibility(View.VISIBLE);
        myFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View item_View = view.findViewById(R.id.line);
                item_View.setSelected(!item_View.isSelected());
                ImageView memberseIcon = (ImageView) view.findViewById(R.id.memberseIcon);
                memberseIcon.setSelected(item_View.isSelected());
                if (item_View.isSelected()) {
                    friendid_list.add(String.valueOf(mFriendslist.get(position).getFriendid()));
                    friendList_sel.add(mFriendslist.get(position));
                } else {
                    friendid_list.remove(String.valueOf(mFriendslist.get(position).getFriendid()));
                    friendList_sel.remove(mFriendslist.get(position));
                }
            }

        });
        btn_addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("friendList_id", getProjectMemberIds(friendid_list));
                bundle.putParcelableArrayList("friendList_sel", friendList_sel);
                intent.putExtras(bundle);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });
    }

    private String getProjectMemberIds(LinkedList<String> linkedList) {

        String friendListid = null;

        for (String o : linkedList) {
            if (friendListid == null)
                friendListid = o;
            else
                friendListid = friendListid + "," + o;
        }
        return friendListid;
    }
}
