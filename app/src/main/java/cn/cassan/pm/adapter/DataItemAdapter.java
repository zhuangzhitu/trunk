package cn.cassan.pm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseListJsonAdapter;
import cn.cassan.pm.model.Data;

/**
 * @author Created by zhihao on 2016/11/5.
 * @describe 资料库
 * @version_
 **/
public class DataItemAdapter extends BaseListJsonAdapter<Data> {

    private final static String JPG="jpg";
    private final static String MP3="mp3";
    private final static String PDF="pdf";
    private final static String PNG="png";
    private final static String TXT="txt";
    private final static String XLS="xls";
    private final static String XLSX="xlsx";
    private final static String DOCX="docx";
    private final static String DWG="dwg";
    private final static String DOC="doc";
    private Context mContext;

    public DataItemAdapter(Context context) {
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {

        DataItemAdapter.ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_data, null);
            vh = new DataItemAdapter.ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (DataItemAdapter.ViewHolder) convertView.getTag();
        }
        Data data = mDatas.get(position);
        vh.data_title.setText(data.getDocumentname());
        vh.data_createtime.setText(data.getCreatetime());
        vh.data_type.setText(data.getExtension());
        switch (data.getExtension()){
            case JPG:
                vh.data_image_type.setImageResource(R.drawable.jpg);
                break;
            case MP3:
                vh.data_image_type.setImageResource(R.drawable.mp3);
                break;
            case PDF:
                vh.data_image_type.setImageResource(R.drawable.pdf);
                break;
            case DOC:
                vh.data_image_type.setImageResource(R.drawable.doc);
                break;
            case DWG:
                vh.data_image_type.setImageResource(R.drawable.dwg);
                break;
            case TXT:
                vh.data_image_type.setImageResource(R.drawable.txt);
                break;
            case PNG:
                vh.data_image_type.setImageResource(R.drawable.png);
                break;
            case XLS:
                vh.data_image_type.setImageResource(R.drawable.xls);
                break;
            case XLSX:
                vh.data_image_type.setImageResource(R.drawable.xlsx);
                break;
            case DOCX:
                vh.data_image_type.setImageResource(R.drawable.jpg);
                break;
            default:

                break;

        }
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.data_title)
        TextView data_title;
        @Bind(R.id.data_type)
        TextView data_type;
        @Bind(R.id.data_createtime)
        TextView data_createtime;
        @Bind(R.id.data_image_type)
        ImageView data_image_type;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
