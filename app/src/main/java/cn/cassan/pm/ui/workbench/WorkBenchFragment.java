package cn.cassan.pm.ui.workbench;


import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import butterknife.OnClick;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.adapter.WorkBenchNoticeAdapter;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseFragment;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.model.WorkBenchNotice;
import cn.cassan.pm.util.JSONUtil;
import cn.cassan.pm.util.UIHelper;
import cz.msebera.android.httpclient.Header;


/**
 * 工作台
 */

public class WorkBenchFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    //适配器
    protected WorkBenchNoticeAdapter mAdapter;
    protected ArrayList<WorkBenchNotice> workBenchNoticeArrayList = new ArrayList<>();

    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.mylist)
    ListView listView;

    @Bind(R.id.empty)
    TextView emptyView;

    @Bind(R.id.ll_pm_newmessage)
    LinearLayout ll_pm_newmessage;
    @Bind(R.id.ll_pm_signin)
    LinearLayout ll_pm_signin;

    @Bind(R.id.ll_pm_doc)
    LinearLayout ll_pm_doc;

    @Bind(R.id.ll_pm_knowledge)
    LinearLayout ll_pm_knowledge;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_workbench_refresh_listview;
    }


    private void updateUI(ArrayList<WorkBenchNotice> groupList) {

        if (mAdapter == null) {
            mAdapter = new WorkBenchNoticeAdapter(getContext());
            listView.setAdapter(mAdapter);
            listView.setEmptyView(emptyView);
            if (groupList != null && groupList.size() != 0) {
                mAdapter.setData(workBenchNoticeArrayList);
            }
        } else {
            listView.setAdapter(mAdapter);
            listView.setEmptyView(emptyView);
            if (groupList != null && groupList.size() != 0) {
                mAdapter.setData(workBenchNoticeArrayList);

            }
        }
    }

    //请求数据
    public void requestData() {
        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {
            String token = userInfo.getToken();
            int ProjectId = 1, NotifyMessageType = 1, PageSize = 10, ContentLength = 100;
            boolean UpdateStatus = false;
            ProjectManagementApi.GetNewProjectMessageList(token, ProjectId, NotifyMessageType, PageSize, ContentLength, UpdateStatus, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
                    refreshLayout.setRefreshing(false);
                    String response = new String(responseBytes);
                    Log.e("访问成功", "statusCode：" + statusCode);
                    Log.e("访问成功", "responseBytes：" + response);

                    ApiReturnResult result = JSONUtil.parseArrayString(response);
                    if (result != null) {
                        handleWorkBenchNoticeBean(result, headers);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                    Log.e("访问失败", "statusCode：" + arg2);
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(WorkBenchFragment.this.getActivity(), "访问失败", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void handleWorkBenchNoticeBean(ApiReturnResult apiReturnResult, Header[] headers) {
        if (apiReturnResult.isOK()) {
            String data = apiReturnResult.getData();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(data);
            if (json.isJsonArray()) {
                WorkBenchNotice[] topNews = gson.fromJson(data, WorkBenchNotice[].class);
                List<WorkBenchNotice> topNewList = Arrays.asList(topNews);
                // 不这样做的话会addAll报错
                workBenchNoticeArrayList = new ArrayList<WorkBenchNotice>(topNewList);
            } else if (json.isJsonObject()) {
                WorkBenchNotice topNew = gson.fromJson(data, WorkBenchNotice.class);
                if (topNew != null) {
                    ArrayList<WorkBenchNotice> topNewList = new ArrayList<>();
                    topNewList.add(topNew);
                    workBenchNoticeArrayList = topNewList;
                } else {
                    workBenchNoticeArrayList = null;
                }
            } else {
                workBenchNoticeArrayList = null;
            }

            updateUI(workBenchNoticeArrayList);
        }
    }

    @OnClick({R.id.ll_pm_newmessage, R.id.ll_pm_signin, R.id.ll_pm_doc, R.id.ll_pm_knowledge})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_pm_newmessage://发送信息
                UIHelper.showNewMessageActivity(this.getContext());
                break;
            case R.id.ll_pm_signin:     //签到
                UIHelper.showSigninActivity(this.getContext());
                break;
            case R.id.ll_pm_doc:      //  查资料
                UIHelper.showDocumentActivity(this.getContext());
                break;
            case R.id.ll_pm_knowledge:    // 知识库
                UIHelper.showKnowledgeActivity(this.getContext());
                break;
            default:
                break;
        }
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(this);
        requestData();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
