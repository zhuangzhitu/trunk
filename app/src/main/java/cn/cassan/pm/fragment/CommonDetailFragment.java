package cn.cassan.pm.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseFragment;
import cn.cassan.pm.cache.CacheManager;
import cn.cassan.pm.emoji.OnSendClickListener;
import cn.cassan.pm.ui.DetailActivity;
import cn.cassan.pm.ui.EmptyLayout;
import cn.cassan.pm.util.DialogHelp;
import cn.cassan.pm.util.FontSizeUtils;
import cn.cassan.pm.util.HTMLUtil;
import cn.cassan.pm.util.TDevice;
import cn.cassan.pm.util.UIHelper;
import cz.msebera.android.httpclient.Header;

/**
 * 通用的详情fragment
 */
public abstract class CommonDetailFragment<T extends Serializable> extends BaseFragment implements OnSendClickListener {

    protected int mId;

    protected EmptyLayout mEmptyLayout;

    protected int mCommentCount = 0;

    protected WebView mWebView;

    protected T mDetail;
    protected AsyncHttpResponseHandler mCommentHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
//                ResultBean rsb = XmlUtils.toBean(ResultBean.class,
//                        new ByteArrayInputStream(arg2));
//                Result res = rsb.getResult();
//                if (res.OK()) {
//                    hideWaitDialog();
//                    AppContext.showToastShort(res.getErrorMessage());
//                    // 评论成功之后，评论数加1
//                    setCommentCount(mCommentCount + 1);
//                } else {
//                    hideWaitDialog();
//                    AppContext.showToastShort(res.getErrorMessage());
//                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(arg0, arg1, arg2, e);
            }
            ((DetailActivity) getActivity()).emojiFragment.clean();
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            hideWaitDialog();

        }

        @Override
        public void onFinish() {
            ((DetailActivity) getActivity()).emojiFragment.hideAllKeyBoard();
        }

    };
    int i = 0;
    AlertDialog fontSizeChange;
    private AsyncTask<String, Void, T> mCacheTask;
    protected AsyncHttpResponseHandler mDetailHeandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
                T detail = parseData(new ByteArrayInputStream(arg2));
                if (detail != null) {
                    mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                    executeOnLoadDataSuccess(detail);
                    saveCache(detail);
                } else {
                    executeOnLoadDataError();
                }
            } catch (Exception e) {
                e.printStackTrace();
                executeOnLoadDataError();
            }
        }

        @Override
        public void onStart() {
            super.onStart();
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            readCacheData(getCacheKey());
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container,
                false);
        mCommentCount = getActivity().getIntent().getIntExtra("comment_count",
                0);
        mId = getActivity().getIntent().getIntExtra("id", 0);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        requestData(false);
        return view;
    }

    @Override
    public void initView(View view) {
        mEmptyLayout = (EmptyLayout) view.findViewById(R.id.error_layout);
        setCommentCount(mCommentCount);
        mWebView = (WebView) view.findViewById(R.id.webview);
        UIHelper.initWebView(mWebView);
    }

    private void requestData(boolean refresh) {
        String key = getCacheKey();
        if (TDevice.hasInternet()
                && (!CacheManager.isExistDataCache(getActivity(), key) || refresh)) {
            sendRequestDataForNet();
        } else {
            readCacheData(key);
        }
    }



    private void recycleWebView() {
        if (mWebView != null) {
            mWebView.setVisibility(View.GONE);
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    private void readCacheData(String cacheKey) {
        cancelReadCache();
        mCacheTask = new CacheTask(getActivity()).execute(cacheKey);
    }

    private void cancelReadCache() {
        if (mCacheTask != null) {
            mCacheTask.cancel(true);
            mCacheTask = null;
        }
    }

    protected void executeOnLoadDataSuccess(T detail) {
        this.mDetail = detail;
        if (this.mDetail == null || TextUtils.isEmpty(this.getWebViewBody(detail))) {
            executeOnLoadDataError();
            return;
        }

        if (mWebView != null) {
            mWebView.loadDataWithBaseURL("", this.getWebViewBody(detail), "text/html", "UTF-8", "");
            // 显示存储的字体大小
            mWebView.loadUrl(FontSizeUtils.getSaveFontSize());
            boolean favoriteState = getFavoriteState() == 1;
            setFavoriteState(favoriteState);

            // 判断最新的评论数是否大于
            if (getCommentCount() > mCommentCount) {
                mCommentCount = getCommentCount();
            }
            setCommentCount(mCommentCount);
        }
    }

    protected void executeOnLoadDataError() {
        mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mState = STATE_REFRESH;
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                requestData(true);
            }
        });
    }

    protected void saveCache(T detail) {
        new SaveCacheTask(getActivity(), detail, getCacheKey()).execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.common_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                sendRequestDataForNet();
                return false;
            case R.id.font_size:
                showChangeFontSize();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChangeFontSize() {

        final String[] items = getResources().getStringArray(
                R.array.font_size);
        fontSizeChange = DialogHelp.getSingleChoiceDialog(getActivity(), items, FontSizeUtils.getSaveFontSizeIndex(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 更改字体大小
                FontSizeUtils.saveFontSize(i);
                mWebView.loadUrl(FontSizeUtils.getFontSize(i));
                fontSizeChange.dismiss();
            }
        }).show();
    }

    // 收藏或者取消收藏
    public void handleFavoriteOrNot() {
        if (mDetail == null) {
            return;
        }
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return;
        }
        if (!AppContext.getInstance().isLogin()) {
            UIHelper.showLoginActivity(getActivity());
            return;
        }
        int uid = AppContext.getInstance().getLoginUid();
        final boolean isFavorited = getFavoriteState() == 1;
        AsyncHttpResponseHandler mFavoriteHandler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                try {
//                    Result res = XmlUtils.toBean(ResultBean.class,
//                            new ByteArrayInputStream(arg2)).getResult();
//                    if (res.OK()) {
//                        AppContext.showToast(res.getErrorMessage());
//                        boolean newFavorited = !isFavorited;
//                        setFavoriteState(newFavorited);
//                        // 更新收藏的状态
//                        updateFavoriteChanged(!newFavorited ? 0 : 1);
//                    } else {
//                        onFailure(arg0, arg1, arg2, null);
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(arg0, arg1, arg2, e);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {

            }

            @Override
            public void onStart() {
                super.onStart();
                showWaitDialog("请稍候...");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                hideWaitDialog();
            }
        };

//        if (isFavorited) {
//            OSChinaApi.delFavorite(uid, mId,
//                    getFavoriteTargetType(), mFavoriteHandler);
//        } else {
//            OSChinaApi.addFavorite(uid, mId,
//                    getFavoriteTargetType(), mFavoriteHandler);
//        }
    }

    // 举报帖子
    public void onReportMenuClick() {
        if (mId == 0 || mDetail == null) {
            AppContext.showToast("正在加载，请稍等...");
        }
        if (!AppContext.getInstance().isLogin()) {
            UIHelper.showLoginActivity(getActivity());
            return;
        }
        int reportId = mId;

//        final ReportDialog dialog = new ReportDialog(getActivity(),
//                getRepotrUrl(), reportId, getReportType());
//        dialog.setCancelable(true);
//        dialog.setTitle(R.string.report);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.setNegativeButton(R.string.cancle, null);
        final TextHttpResponseHandler mReportHandler = new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, String arg2) {
                if (TextUtils.isEmpty(arg2)) {
                    AppContext.showToastShort(R.string.tip_report_success);
                } else {
                    AppContext.showToastShort(new String(arg2));
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2,
                                  Throwable arg3) {
                AppContext.showToastShort(R.string.tip_report_faile);
            }

            @Override
            public void onFinish() {
                hideWaitDialog();
            }
        };
//        dialog.setPositiveButton(R.string.ok,
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface d, int which) {
//                        Report report = null;
//                        if ((report = dialog.getReport()) != null) {
//                            showWaitDialog(R.string.progress_submit);
//                            OSChinaApi.report(report, mReportHandler);
//                        }
//                        d.dismiss();
//                    }
//                });
//        dialog.show();
    }



    // 显示评论列表
    public void onCilckShowComment() {
        showCommentView();
    }

    // 刷新数据
    protected void refresh() {
        sendRequestDataForNet();
    }

    // 发表评论
    @Override
    public void onClickSendButton(Editable str) {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_network_error);
            return;
        }
        if (!AppContext.getInstance().isLogin()) {
            UIHelper.showLoginActivity(getActivity());
            return;
        }

        showWaitDialog(R.string.progress_submit);

//        OSChinaApi.publicComment(getCommentType(), mId, AppContext
//                        .getInstance().getLoginUid(), str.toString(), 0,
//                mCommentHandler);
    }

    @Override
    public void onClickFlagButton() {

    }

    /***
     * 获取去除html标签的body
     *
     * @param body
     * @return
     */
    protected String getFilterHtmlBody(String body) {
        if (body == null)
            return "";
        return HTMLUtil.delHTMLTag(body.trim());
    }

    // 获取缓存的key
    protected abstract String getCacheKey();

    // 从网络中读取数据
    protected abstract void sendRequestDataForNet();

    // 解析数据
    protected abstract T parseData(InputStream is);

    // 返回填充到webview中的内容
    protected abstract String getWebViewBody(T detail);

    // 显示评论列表
    protected abstract void showCommentView();

    // 获取评论的类型
    protected abstract int getCommentType();

    protected abstract String getShareTitle();

    protected abstract String getShareContent();

    protected abstract String getShareUrl();

    // 返回举报的url
    protected String getRepotrUrl() {
        return "";
    }

    // 返回举报的类型
//    protected byte getReportType() {
//        return Report.TYPE_QUESTION;
//    }

    // 获取收藏类型（如新闻、博客、帖子）
    protected abstract int getFavoriteTargetType();

    protected abstract int getFavoriteState();

    private void setFavoriteState(boolean isFavorited) {
        ((DetailActivity) getActivity()).toolFragment.setFavorite(isFavorited);
    }

    protected abstract void updateFavoriteChanged(int newFavoritedState);

    protected abstract int getCommentCount();

    protected void setCommentCount(int commentCount) {
        ((DetailActivity) getActivity()).toolFragment
                .setCommentCount(commentCount);
    }



    private class CacheTask extends AsyncTask<String, Void, T> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<Context>(context);
        }

        @Override
        protected T doInBackground(String... params) {
            if (mContext.get() != null) {
                Serializable seri = CacheManager.readObject(mContext.get(),
                        params[0]);
                if (seri == null) {
                    return null;
                } else {
                    return (T) seri;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(T detail) {
            super.onPostExecute(detail);
            mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            if (detail != null) {
                executeOnLoadDataSuccess(detail);
            } else {
                executeOnLoadDataError();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        }
    }

    private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<Context> mContext;
        private final Serializable seri;
        private final String key;

        private SaveCacheTask(Context context, Serializable seri, String key) {
            mContext = new WeakReference<Context>(context);
            this.seri = seri;
            this.key = key;
        }

        @Override
        protected Void doInBackground(Void... params) {
            CacheManager.saveObject(mContext.get(), seri, key);
            return null;
        }
    }
}
