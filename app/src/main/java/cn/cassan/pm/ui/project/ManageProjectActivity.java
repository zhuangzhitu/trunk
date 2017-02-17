package cn.cassan.pm.ui.project;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.socialize.utils.Log;

import butterknife.Bind;
import butterknife.OnClick;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.model.Project;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.util.JSONUtil;
import cn.cassan.pm.widget.TitleView;
import cz.msebera.android.httpclient.Header;

/**
 * @author Created by zhihao on 2016/10/23.
 * @describe 管理项目
 * @version_
 **/
public class ManageProjectActivity extends BaseFragmentActivity {


    @Bind(R.id.titleview)
    TitleView titleView;
    @Bind(R.id.item_projecteidt)
    RelativeLayout item_projectedit;

    @Bind(R.id.item_projectsetanim)
    RelativeLayout item_projectsetanim;

    @Bind(R.id.item_projectsetmember)
    RelativeLayout item_projectsetmember;

    @Bind(R.id.project_avatar)
    ImageView project_avatar;

    @Bind(R.id.projectname)
    TextView projectname;
    @Bind(R.id.projectlocation)
    TextView projectlocation;

    private int projectId;
    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String tem = new String(responseBody);
            ApiReturnResult result = JSONUtil.parseArrayString(tem);
            if (result != null) {
                handlerProjectInfo(result, headers);
            }
            Log.d("debug", result.getMessage() + "\n" + result.getStatus());
        }


        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
        }
    };

    private void handlerProjectInfo(ApiReturnResult result, Header[] headers) {
        if (result.isOK()) {
            String data = result.getData();
            if (data != null) {
                Gson gson = new Gson();
                Project[] project = gson.fromJson(data, Project[].class);
                if (project[0].getCityname() != null && project[0].getProjectname() != null) {
                    projectname.setText(project[0].getProjectname());
                    projectlocation.setText(project[0].getStateprovincename() + " " + project[0].getCityname());
                }

            }
        }
    }

    @Override
    public void initView() {
        titleView.setTitleClickListener(new TitleView.TitleClickListener() {
            @Override
            public void onClick(String action) {

                switch (action) {
                    case TitleView.IMAGELEFT:
                    case TitleView.TEXTLEFT:
                        finish();
                }
            }
        });
    }

    @OnClick({R.id.item_projecteidt, R.id.item_projectsetanim, R.id.item_projectsetmember})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_projecteidt:
                startActivity(new Intent(ManageProjectActivity.this, Project_Acc_ManageActivity.class));
                break;
            case R.id.item_projectsetanim:
                showToast("部门和成员管理", 0, 0);
                break;
            case R.id.item_projectsetmember:
                showToast("设置管理员", 0, 0);
                break;
        }
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_managerproject;
    }

    @Override
    public void initData() {

        projectId = getIntent().getIntExtra("projectId", 0);
        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {
            ProjectManagementApi.getProjectInfo(userInfo.getToken(), projectId, mHandler);
        }
    }
}
