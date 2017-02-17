package cn.cassan.pm.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.adapter.MyGroupAdapter;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseFragment;
import cn.cassan.pm.model.Group;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.util.JSONUtil;
import cz.msebera.android.httpclient.Header;

/**
 * @author Created by zhihao on 2016/10/22.
 * @describe 群组或者讨论组
 * @version_
 **/
public class MyGroupFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.mylist)
    ListView listView;
    @Bind(R.id.empty)
    TextView emptyView;
    ArrayList<Group> groupList = new ArrayList<>();
    private String TAG = "MyGroupFragment";
    protected MyGroupAdapter mAdapter;
    private boolean isGroup;
    private AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String response = new String(responseBody);
            Log.e("访问成功", "statusCode：" + statusCode);
            Log.e("访问成功", "responseBytes：" + response);

            ApiReturnResult result = JSONUtil.parseArrayString(response);
            refreshLayout.setRefreshing(false);
            if (result != null) {
                //                        handleProjectBean(result, headers);

                handleGroupBean(result, headers);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.e("访问失败", "statusCode：" + responseBody);
            refreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "访问失败", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refreshlist;
    }


    @Override
    public void initData() {

        isGroup = getArguments().getBoolean("isMyGroup");
        refreshLayout.setOnRefreshListener(this);
        getdata();
    }

    private void getdata() {
        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {

            String token = userInfo.getToken();
            if (isGroup) {
                ProjectManagementApi.getChatGroupList(token, httpResponseHandler);
            } else {
                ProjectManagementApi.getChatProjectGroupList(token, httpResponseHandler);
            }
        }
    }

    private void updateUI(ArrayList<Group> groupList) {

        if (mAdapter == null) {
            mAdapter = new MyGroupAdapter(getContext());
            listView.setAdapter(mAdapter);
            listView.setEmptyView(emptyView);
            if (groupList != null && groupList.size() != 0) {
                mAdapter.setData(groupList);
            }
        } else {
            if (groupList != null && groupList.size() != 0) {
                mAdapter.setData(groupList);
                listView.setAdapter(mAdapter);
                listView.setEmptyView(emptyView);
            }
        }
    }

    private void handleGroupBean(ApiReturnResult apiReturnResult, Header[] headers) {
        if (apiReturnResult.isOK()) {
            String data = apiReturnResult.getData();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(data);
            if (json.isJsonArray()) {
                Group[] topNews = gson.fromJson(data, Group[].class);
                List<Group> topNewList = Arrays.asList(topNews);
                // 不这样做的话会addAll报错
                groupList = new ArrayList<Group>(topNewList);
            }
        } else {
            groupList = null;
        }
        updateUI(groupList);
    }

    @Override
    public void onRefresh() {
        getdata();
    }
}



