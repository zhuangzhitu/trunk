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
import cn.cassan.pm.base.BaseListJsonAdapter;
import cn.cassan.pm.model.ProjectInfo;
import cn.cassan.pm.util.UIHelper;
import cn.cassan.pm.widget.CircleImageView;

/**
 * 项目信息适配器
 */
public class ProjectAdapter extends BaseListJsonAdapter<ProjectInfo> {

    private Context mContext;

    public ProjectAdapter(Context context) {
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    protected View getRealView(final int position, View convertView, ViewGroup parent) {
        //每一行的视图填充
        ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_projectinfo, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //取当前位置的项目信息对象
        final ProjectInfo projectInfo = mDatas.get(position);
        vh.iv_project.setVisibility(View.VISIBLE);
        vh.iv_project.setImageResource(R.drawable.defaultavatar);

        vh.tv_project_title.setText(projectInfo.getProjectname());
        vh.tv_project_content.setText(projectInfo.getProvincename());

        vh.bt_project_manage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                //                Log.v("MyListViewBase", "你点击了按钮" + position);
                UIHelper.showManagerProjectActivity(getContext(), projectInfo.getProjectid());
            }
        });
        return convertView;
    }


    static class ViewHolder {

        @Bind(R.id.iv_project)
        CircleImageView iv_project;
        @Bind(R.id.tv_project_title)
        TextView tv_project_title;
        @Bind(R.id.tv_project_content)
        TextView tv_project_content;
        @Bind(R.id.bt_project_manage)
        TextView bt_project_manage;
        @Bind(R.id.tv_news_title)
        View view;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
