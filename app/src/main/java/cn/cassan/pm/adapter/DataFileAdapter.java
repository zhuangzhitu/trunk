package cn.cassan.pm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.R;

/**
 * @author Created by zhihao on 2016/11/1.
 * @describe 资料室
 * @version_
 **/
public class DataFileAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public String[] img_text = {"施工方案", "进度计划", "技术交流", "技术变更", "规范图集",
            "文件通知", "会议记录", "宣传报道", "其他文件"};
    public int[] imgs = {R.drawable.book01, R.drawable.book02,
            R.drawable.book03, R.drawable.book04,
            R.drawable.book05, R.drawable.book06,
            R.drawable.book07, R.drawable.book08, R.drawable.book09};


    @Override
    public int getCount() {
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageGriViewAdapter.ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.grid_item, null);
            //            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null);
            vh = new MessageGriViewAdapter.ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (MessageGriViewAdapter.ViewHolder) convertView.getTag();
        }
        vh.im_item.setBackgroundResource(imgs[position]);

        vh.tv_item.setText(img_text[position]);
        return convertView;
    }

    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }


    static class ViewHolder {

        @Bind(R.id.tv_item)
        TextView tv_item;
        @Bind(R.id.iv_item)
        ImageView im_item;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
