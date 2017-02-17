package cn.cassan.pm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.EaseUserUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseListAdapter;
import cn.cassan.pm.model.Friend;
import cn.cassan.pm.ui.huanxin.ChatActivity;
import cn.cassan.pm.util.Helper;

/**
 * @author Created by zhihao on 2016/10/25.
 * @describe
 * @version_
 **/
public class MyFriendAdapter extends BaseListAdapter<Friend> {


    private Context mContext;

    public MyFriendAdapter(Context context) {
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    protected View getRealView(final int position, View convertView, ViewGroup parent) {
        //每一行的视图填充
        MyFriendAdapter.ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_myfriends, null);
            vh = new MyFriendAdapter.ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (MyFriendAdapter.ViewHolder) convertView.getTag();
        }
        String username = mDatas.get(position).getName();

        EaseUserUtils.setUserNick(username, vh.tv_name);
        vh.tv_name.setText(mDatas.get(position).getName());
        String HXid = mDatas.get(position).getHxid();

        //        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(HXid);
        //        int unread = conversation.getUnreadMsgCount();
        //        if (unread > 0) {
        //            if (unread > 99) {
        //                vh.tv_unread.setText("99+");
        //            } else {
        //                vh.tv_unread.setText(String.valueOf(unread));
        //            }
        //            vh.tv_unread.setVisibility(View.VISIBLE);
        //        } else {
        //            vh.tv_unread.setVisibility(View.GONE);
        //        }


        ImageLoader.getInstance().displayImage(mDatas.get(position).getAvatarurl(), vh.adver_image,
                Helper.getCircleOptions(R.drawable.defaultavatar));

        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                String user_id = mDatas.get(position).getHxid();
                intent.putExtra("userId", user_id);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.avatar)
        ImageView adver_image;
        @Bind(R.id.name)
        TextView tv_name;
        @Bind(R.id.unread_msg_number)
        TextView tv_unread;
        @Bind(R.id.item_myfriend)
        View view;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
