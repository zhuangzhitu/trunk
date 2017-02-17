package cn.cassan.pm.ui.project;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wj.refresh.OnRefreshListener;
import com.wj.refresh.PullRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseFragment;
import cn.cassan.pm.base.BaseListAdapter;
import cn.cassan.pm.model.Friend;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.util.JSONUtil;
import cz.msebera.android.httpclient.Header;

import static com.wj.refresh.PullRefreshLayout.BOTH;
import static com.wj.refresh.PullRefreshLayout.PULL_FROM_START;

/**
 * @author Created by zhihao on 2016/10/28.
 * @describe
 * @version_
 **/
public abstract class BaseFrimeberFragment extends BaseFragment {

    @Bind(R.id.PullRefreshLayout)
    protected PullRefreshLayout pullRefreshLayout;
    @Bind(R.id.ListView)
    protected ListView myFriends;
    @Bind(R.id.EmptyView)
    protected View emptyView;

    protected BaseListAdapter adapter;
    protected ArrayList<Friend> mFriendslist;
    protected int page = 1, pageSize = 10;
    protected int mListViewHeight;
    protected AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            pullRefreshLayout.onRefreshComplete();
            String response = new String(responseBody);
            Log.e("访问成功", "statusCode：" + statusCode);
            Log.e("访问成功", "responseBytes：" + response);

            ApiReturnResult result = JSONUtil.parseArrayString(response);

            //                        handleProjectBean(result, headers);
            if (result != null)
                handleFriendBean(result, headers);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            pullRefreshLayout.onRefreshComplete();
            Log.e("访问失败", "statusCode：" + responseBody);
            Toast.makeText(getContext(), "访问失败", Toast.LENGTH_LONG).show();
        }
    };

    private void handleFriendBean(ApiReturnResult result, Header[] headers) {

        if (mFriendslist == null) {
            mFriendslist = new ArrayList<>();
        }
        if (result.isOK()) {
            String data = result.getData();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(data);
            if (json.isJsonArray()) {
                Friend[] topNews = gson.fromJson(data, Friend[].class);
                List<Friend> topNewList = Arrays.asList(topNews);
                // 不这样做的话会addAll报错
                mFriendslist = new ArrayList<Friend>(topNewList);
            }
        } else {
            mFriendslist = null;
        }
        updateUI(mFriendslist);
    }

    private void updateUI(ArrayList<Friend> mFriendslist) {

        if (adapter == null) {
            adapter = getAdapter();
            myFriends.setAdapter(adapter);
            myFriends.setEmptyView(emptyView);
            if (mFriendslist != null && mFriendslist.size() != 0) {
                adapter.setData(mFriendslist);
            }
        } else {
            if (mFriendslist != null && mFriendslist.size() != 0) {
                adapter.setData(mFriendslist);
                myFriends.setAdapter(adapter);
                myFriends.setEmptyView(emptyView);
            }
        }
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_myfriends;
    }

    @Override
    public void initView(View view) {

        initListListner();

        myFriends.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mListViewHeight = myFriends.getHeight();

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    myFriends.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    myFriends.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        myFriends.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstVisibleItemView = myFriends.getChildAt(0);
                if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                    Log.d("ListView", "<----滚动到顶部----->");
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = myFriends.getChildAt(myFriends.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == mListViewHeight) {
                        Log.d("ListView", "#####滚动到底部######");
                        pullRefreshLayout.setMode(BOTH);
                    }
                }


            }
        });


        pullRefreshLayout.setMode(PULL_FROM_START);
        pullRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onPullDownRefresh() {
                /**
                 * 下拉刷新回调
                 */
                page = 1;
                pageSize = 10;
                getData(page, pageSize);
            }

            @Override
            public void onPullUpRefresh() {
                /**
                 * 上拉加载更多回调
                 */

                page = 1;
                pageSize = mFriendslist.size();
                getData(page, pageSize);

            }
        });
    }

    @Override
    public void initData() {

        getData(page, pageSize);
    }

    private void getData(int page, int pageSize) {

        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {

            String token = userInfo.getToken();
            ProjectManagementApi.getFriendsList(token, page, pageSize, httpResponseHandler);

        }

    }
   protected   void initListListner(){

   }
    public abstract BaseListAdapter getAdapter();
}
