package cn.cassan.pm.base;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cassan.pm.model.BaseEntity;
import cn.cassan.pm.util.StringUtils;
import cn.cassan.pm.util.TDevice;

/**
 * JSON序列化的适配器，纯JSON
 *
 * @param <T>
 */
public class BaseListJsonAdapter<T extends BaseEntity> extends BaseAdapter {

    public static final int STATE_EMPTY_ITEM = 0;
    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_NO_DATA = 3;
    public static final int STATE_LESS_ONE_PAGE = 4;
    public static final int STATE_NETWORK_ERROR = 5;
    public static final int STATE_OTHER = 6;


    protected int state = STATE_LESS_ONE_PAGE;

    protected int _loadmoreText;
    protected int _loadFinishText;
    protected int _noDateText;
    protected int mScreenWidth;

    protected Context mContext;
    protected ArrayList<T> mDatas = new ArrayList<T>();
    private LayoutInflater mInflater;
    private LinearLayout mFooterView;

    /**
     * 构造函数
     *
     * @param context
     */
    public BaseListJsonAdapter(Context context) {
        mContext = context;
    }

    /**
     * 默认构造函数
     */
    public BaseListJsonAdapter() {
        _loadmoreText = cn.cassan.pm.R.string.loading;
        _loadFinishText = cn.cassan.pm.R.string.loading_no_more;
        _noDateText = cn.cassan.pm.R.string.error_view_no_data;
    }

    /**
     * 获取填充视图
     *
     * @param context
     * @return
     */
    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    /**
     * 设置屏幕宽度
     *
     * @param width
     */
    public void setScreenWidth(int width) {
        mScreenWidth = width;
    }


    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }


    //region items

    /**
     * 获取数量
     */
    @Override
    public int getCount() {
        switch (getState()) {
            case STATE_EMPTY_ITEM:
                return getDataSizePlus1();
            case STATE_NETWORK_ERROR:
            case STATE_LOAD_MORE:
                return getDataSizePlus1();
            case STATE_NO_DATA:
                return 1;
            case STATE_NO_MORE:
                return getDataSizePlus1();
            case STATE_LESS_ONE_PAGE:
                return getDataSize();
            default:
                break;
        }
        return getDataSize();
    }

    /**
     * 获取数据大小+1
     *
     * @return
     */
    public int getDataSizePlus1() {
        if (hasFooterView()) {
            return getDataSize() + 1;
        }
        return getDataSize();
    }

    /**
     * 获取数据大小
     *
     * @return
     */
    public int getDataSize() {
        return mDatas.size();
    }

    /**
     * 获取某一项
     */

    @Override
    public T getItem(int arg0) {
        if (mDatas.size() > arg0) {
            return mDatas.get(arg0);
        }
        return null;
    }

    /**
     * 获取项编号
     */
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    /**
     * 获取数据列表
     *
     * @return
     */

    public ArrayList<T> getData() {
        return mDatas == null ? (mDatas = new ArrayList<T>()) : mDatas;
    }

    /**
     * 设置数据列表
     */
    public void setData(ArrayList<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    //添加一批数据
    public void addData(List<T> data) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    //添加一个数据
    public void addItem(T obj) {
        if (mDatas != null) {
            mDatas.add(obj);
        }
        notifyDataSetChanged();
    }

    //在某个位置添加一项
    public void addItem(int pos, T obj) {
        if (mDatas != null) {
            mDatas.add(pos, obj);
        }
        notifyDataSetChanged();
    }

    //移除一项
    public void removeItem(Object obj) {
        mDatas.remove(obj);
        notifyDataSetChanged();
    }

    //清除
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    //endregion

    //region loadmore

    /**
     * 设置加载更多文本
     *
     * @param loadmoreText
     */
    public void setLoadmoreText(int loadmoreText) {
        _loadmoreText = loadmoreText;
    }

    /**
     * 设置加载结束文本
     *
     * @param loadFinishText
     */
    public void setLoadFinishText(int loadFinishText) {
        _loadFinishText = loadFinishText;
    }

    /**
     * 设置没有数据提醒文本
     *
     * @param noDataText
     */
    public void setNoDataText(int noDataText) {
        _noDateText = noDataText;
    }

    /**
     * 加载更多
     *
     * @return
     */
    protected boolean loadMoreHasBg() {
        return true;
    }

    //endregion

    //region view

    /**
     * 获取项目视图
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == getCount() - 1 && hasFooterView()) {// 最后一条
            // if (position < _data.size()) {
            // position = getCount() - 2; // footview
            // }
            if (getState() == STATE_LOAD_MORE || getState() == STATE_NO_MORE
                    || state == STATE_EMPTY_ITEM
                    || getState() == STATE_NETWORK_ERROR) {
                this.mFooterView = (LinearLayout) LayoutInflater.from(
                        parent.getContext()).inflate(cn.cassan.pm.R.layout.list_cell_footer,
                        null);
                if (!loadMoreHasBg()) {
                    mFooterView.setBackgroundDrawable(null);
                }
                ProgressBar progress = (ProgressBar) mFooterView
                        .findViewById(cn.cassan.pm.R.id.progressbar);
                TextView text = (TextView) mFooterView.findViewById(cn.cassan.pm.R.id.text);
                switch (getState()) {
                    case STATE_LOAD_MORE:
                        setFooterViewLoading();
                        break;
                    case STATE_NO_MORE:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        text.setText(_loadFinishText);
                        break;
                    case STATE_EMPTY_ITEM:
                        progress.setVisibility(View.GONE);
                        mFooterView.setVisibility(View.VISIBLE);
                        text.setText(_noDateText);
                        break;
                    case STATE_NETWORK_ERROR:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        if (TDevice.hasInternet()) {
                            text.setText("加载出错了");
                        } else {
                            text.setText("没有可用的网络");
                        }
                        break;
                    default:
                        progress.setVisibility(View.GONE);
                        mFooterView.setVisibility(View.GONE);
                        text.setVisibility(View.GONE);
                        break;
                }
                return mFooterView;
            }
        }
        if (position < 0) {
            position = 0; // 若列表没有数据，是没有footview/headview的
        }
        return getRealView(position, convertView, parent);
    }


    /**
     * 获取实际视图
     *
     * @return
     */
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    //endregion

    //region 脚视图
    protected boolean hasFooterView() {
        return true;
    }

    public View getFooterView() {
        return this.mFooterView;
    }

    public void setFooterViewLoading(String loadMsg) {
        ProgressBar progress = (ProgressBar) mFooterView
                .findViewById(cn.cassan.pm.R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(cn.cassan.pm.R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        if (StringUtils.isEmpty(loadMsg)) {
            text.setText(_loadmoreText);
        } else {
            text.setText(loadMsg);
        }
    }

    public void setFooterViewLoading() {
        setFooterViewLoading("");
    }

    public void setFooterViewText(String msg) {
        ProgressBar progress = (ProgressBar) mFooterView
                .findViewById(cn.cassan.pm.R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(cn.cassan.pm.R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText(msg);
    }

    //endregion


    protected void setText(TextView textView, String text, boolean needGone) {
        if (text == null || TextUtils.isEmpty(text)) {
            if (needGone) {
                textView.setVisibility(View.GONE);
            }
        } else {
            textView.setText(text);
        }
    }

    protected void setText(TextView textView, String text) {
        setText(textView, text, false);
    }

    /**
     * 设置图片资源
     *
     * @param imageRes
     * @param resId
     */
    protected void setImageRes(ImageView imageRes, @DrawableRes int resId) {
        imageRes.setImageResource(resId);
    }


}
