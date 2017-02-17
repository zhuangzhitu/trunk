package cn.cassan.pm.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseListAdapter;
import cn.cassan.pm.model.Group;
import cn.cassan.pm.widget.CircleImageView;

/**
 * @author Created by zhihao on 2016/10/25.
 * @describe
 * @version_
 **/
public class MyGroupAdapter extends BaseListAdapter<Group> {


    private Context mContext;

    public MyGroupAdapter(Context context) {
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected View getRealView(final int position, View convertView, ViewGroup parent) {
        //每一行的视图填充
        MyGroupAdapter.ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_projectinfo, null);
            vh = new MyGroupAdapter.ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (MyGroupAdapter.ViewHolder) convertView.getTag();
        }

        vh.group_icon.setImageDrawable(getContext().getDrawable(R.drawable.ease_default_avatar));
        vh.group_name.setText(mDatas.get(position).getGroupname());
        //取当前位置的项目信息对象
        final Group projectInfo = mDatas.get(position);


        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.iv_project)
        CircleImageView group_icon;
        @Bind(R.id.tv_project_title)
        TextView group_name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
