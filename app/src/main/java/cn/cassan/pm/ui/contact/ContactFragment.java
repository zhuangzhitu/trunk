package cn.cassan.pm.ui.contact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.adapter.ProjectAdapter;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseListJsonAdapter;
import cn.cassan.pm.model.ProjectInfo;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.util.JSONUtil;
import cn.cassan.pm.util.UIHelper;
import cn.cassan.pm.widget.MyListViewWithScrollView;
import cz.msebera.android.httpclient.Header;


/**
 * 联系人
 */
public class ContactFragment extends Fragment {
    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    protected static final String TAG = ContactFragment.class.getSimpleName();
    public static int mState = STATE_NONE;

    //列表控件
    @Bind(R.id.listview_project)
    protected MyListViewWithScrollView listview_project;

    @Bind(R.id.ll_pm_create)
    protected LinearLayout ll_pm_create;

    @Bind(R.id.ll_pm_friend)
    protected LinearLayout ll_pm_friend;

    @Bind(R.id.ll_pm_contact)
    protected LinearLayout ll_pm_contact;

    @Bind(R.id.ll_pm_group)
    protected LinearLayout ll_pm_group;

    //适配器
    protected BaseListJsonAdapter<ProjectInfo> mAdapter;

    protected LayoutInflater mInflater;

    ArrayList<ProjectInfo> projectList = new ArrayList<>();

    //请求数据
    protected void requestData() {
        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {
            String token = userInfo.getToken();
            ProjectManagementApi.getProjectList(token, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
                    String response = new String(responseBytes);
                    Log.e("访问成功", "statusCode：" + statusCode);
                    Log.e("访问成功", "responseBytes：" + response);

                    ApiReturnResult result = JSONUtil.parseArrayString(response);
                    if (result != null) {
                        handleProjectBean(result, headers);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                    Log.e("访问失败", "statusCode：" + arg2);
                    Toast.makeText(ContactFragment.this.getActivity(), "访问失败", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void handleProjectBean(ApiReturnResult apiReturnResult, Header[] headers) {
        if (apiReturnResult.isOK()) {
            String data = apiReturnResult.getData();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(data);
            if (json.isJsonArray()) {
                ProjectInfo[] topNews = gson.fromJson(data, ProjectInfo[].class);
                List<ProjectInfo> topNewList = Arrays.asList(topNews);
                // 不这样做的话会addAll报错
                projectList = new ArrayList<ProjectInfo>(topNewList);

            } else if (json.isJsonObject()) {
                ProjectInfo topNew = gson.fromJson(data, ProjectInfo.class);
                if (topNew != null && topNew.getProjectname() != null && !topNew.getProjectname().equals("")) {
                    ArrayList<ProjectInfo> topNewList = new ArrayList<ProjectInfo>();
                    topNewList.add(topNew);
                    projectList = topNewList;
                } else {
                    projectList = null;
                }
            } else {
                projectList = null;
            }

            updateUI(projectList);
        }
    }

    private void updateUI(ArrayList<ProjectInfo> projectList) {
        if (mAdapter == null) {
            mAdapter = new ProjectAdapter(getContext());
            listview_project.setAdapter(mAdapter);
            if (projectList != null && projectList.size() != 0) {
                mAdapter.setData(projectList);
            }
        } else {
            if (projectList != null && projectList.size() != 0) {
                mAdapter.setData(projectList);
                listview_project.setAdapter(mAdapter);
            }
        }
    }

    @OnClick({R.id.ll_pm_create, R.id.ll_pm_contact, R.id.ll_pm_group, R.id.ll_pm_friend})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_pm_create:     // 创建项目

                UIHelper.showCreateProjectActivity(this.getContext());
                break;
            case R.id.ll_pm_group:      // 我的群组
                UIHelper.showMyGroupActivity(this.getContext());
                break;
            case R.id.ll_pm_contact:    // 手机通讯录
                UIHelper.showMyPhoneBookActivity(this.getContext());
                break;
            case R.id.ll_pm_friend:     // 广众好友
                //                Toast.makeText(ContactFragment.this.getActivity(), "广众好友", Toast.LENGTH_LONG).show();
                //                startActivity(new Intent(getActivity(), FriendsMembActivity.class).putExtra("isFriendlist", true));
                UIHelper.showMyFriendsActivity(this.getContext());
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_contact_refresh_listview, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        updateUI(projectList);
        requestData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
