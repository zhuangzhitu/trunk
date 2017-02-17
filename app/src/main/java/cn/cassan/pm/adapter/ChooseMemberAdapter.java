package cn.cassan.pm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseListAdapter;
import cn.cassan.pm.model.Friend;
import cn.cassan.pm.util.Helper;

/**
 * @author Created by zhihao on 2016/10/27.
 * @describe 选择项目成员适配器
 * @version_
 **/
public class ChooseMemberAdapter extends BaseListAdapter<Friend> {

    private Context mContext;

    public ChooseMemberAdapter(Context context) {
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {

        ChooseMemberAdapter.ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_choosemembers, null);
            vh = new ChooseMemberAdapter.ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ChooseMemberAdapter.ViewHolder) convertView.getTag();
        }
        String username = mDatas.get(position).getName();
        vh.tv_name.setText(username);
        ImageLoader.getInstance().displayImage(mDatas.get(position).getAvatarurl(), vh.adver_image,
                Helper.getCircleOptions(R.drawable.defaultavatar));
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.avatar)
        ImageView adver_image;
        @Bind(R.id.name)
        TextView tv_name;
        @Bind(R.id.item_member)
        View view;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
