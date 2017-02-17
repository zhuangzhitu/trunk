package cn.cassan.pm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseListJsonAdapter;
import cn.cassan.pm.model.WorkBenchNotice;
import cn.cassan.pm.util.UIHelper;
import cn.cassan.pm.widget.CircleImageView;

/**
 * Created by anqin on 2016/10/18.
 */

public class WorkBenchNoticeAdapter extends BaseListJsonAdapter<WorkBenchNotice> {


    private Context mContext;

    public WorkBenchNoticeAdapter(Context context) {
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    protected View getRealView(final int position, View convertView, ViewGroup parent) {
        //每一行的视图填充
        WorkBenchNoticeAdapter.ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_workbenchnotice, null);
            vh = new WorkBenchNoticeAdapter.ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (WorkBenchNoticeAdapter.ViewHolder) convertView.getTag();
        }
        //取当前位置的项目信息对象
        WorkBenchNotice workBenchNotice = mDatas.get(position);
        vh.iv_image.setVisibility(View.VISIBLE);
        vh.iv_image.setImageResource(R.drawable.defaultavatar);
        vh.tv_title.setText(workBenchNotice.getTitle());
        vh.tv_content.setText(workBenchNotice.getContent());
        vh.tv_time.setText(workBenchNotice.getCreatetime());
        if (workBenchNotice.getFollowstatus() <= 0) {
            vh.tv_count.setVisibility(View.GONE);
        } else {
            vh.tv_count.setText(String.valueOf(workBenchNotice.getFollowstatus()));
        }


        vh.project_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UIHelper.showCirclefriendActivity(getContext());
            }
        });

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.iv_workbenchnotice)
        CircleImageView iv_image;
        @Bind(R.id.tv_workbenchnotice_title)
        TextView tv_title;
        @Bind(R.id.tv_workbenchnotice_content)
        TextView tv_content;
        @Bind(R.id.tv_workbenchnotice_time)
        TextView tv_time;
        @Bind(R.id.tv_workbenchnotice_count)
        TextView tv_count;
        @Bind(R.id.project_item)
        LinearLayout project_item;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
