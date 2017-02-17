package cn.cassan.pm.adapter;

/**
 * @author Created by zhihao on 2016/11/1.
 * @describe 消息
 * @version_
 **/

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


public class MessageGriViewAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    public String[] img_text = {"报告", "施工报告", "任务", "审批", "施工检验",
            "施工日志", "施工动态", "施工验收"};
    public int[] imgs = {R.drawable.baogao, R.drawable.gonggao,
            R.drawable.renwu, R.drawable.shenpi,
            R.drawable.jiancha, R.drawable.rzhi,
            R.drawable.dongtai, R.drawable.yangshou};


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
