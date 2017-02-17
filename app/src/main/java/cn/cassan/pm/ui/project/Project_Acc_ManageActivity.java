package cn.cassan.pm.ui.project;

import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.socialize.utils.Log;

import butterknife.Bind;
import butterknife.OnClick;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.util.JSONUtil;
import cn.cassan.pm.widget.InfoItemLayout;
import cn.cassan.pm.widget.TitleView;
import cz.msebera.android.httpclient.Header;

/**
 * @author Created by zhihao on 2016/10/29.
 * @describe 项目账户管理
 * @version_
 **/
public class Project_Acc_ManageActivity extends BaseFragmentActivity {

    @Bind(R.id.titleview)
    TitleView titleView;

    @Bind(R.id.tv_project_adver)
    InfoItemLayout tv_project_adver;
    @Bind(R.id.tv_project_name)
    InfoItemLayout tv_project_name;
    @Bind(R.id.tv_project_location)
    InfoItemLayout tv_project_location;
    @Bind(R.id.getLocation)
    InfoItemLayout getLocation;
    @Bind(R.id.tv_project_manager)
    InfoItemLayout tv_project_manager;
    @Bind(R.id.tv_project_company)
    InfoItemLayout tv_project_company;
    @Bind(R.id.tv_project_starttime)
    InfoItemLayout tv_project_starttime;
    @Bind(R.id.tv_project_alltime)
    InfoItemLayout tv_project_alltime;
    @Bind(R.id.tv_project_built_area)
    InfoItemLayout tv_project_built_area;
    @Bind(R.id.tv_project_contract_cost)
    InfoItemLayout tv_project_contract_cost;

    @Bind(R.id.btn_projectdele)
    Button btn_projectdelete;

    private int projectid;


    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String tem = new String(responseBody);
            ApiReturnResult result = JSONUtil.parseJSONString(tem);
            if (result != null) {

            }
            Log.d("debug", result.getMessage() + "\n" + result.getStatus());
        }


        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            hideWaitDialog();
        }
    };


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

        projectid= getIntent().getExtras().getInt("projectid");
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_projectdele, R.id.tv_project_adver, R.id.tv_project_name})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tv_project_adver:

                break;
            case R.id.tv_project_name:

                break;
            case R.id.tv_project_location:

                break;
            case R.id.getLocation:

                break;
            case R.id.tv_project_manager:

                break;
            case R.id.tv_project_company:

                break;
            case R.id.tv_project_starttime:

                break;
            case R.id.tv_project_alltime:

                break;
            case R.id.tv_project_built_area:

                break;
            case R.id.tv_project_contract_cost:
                break;
            case R.id.btn_projectdele:
            {
                UserInfo userInfo= AppContext.getInstance().getLoginUserInfo();
                if (userInfo!=null) {
                    ProjectManagementApi.deleteProject(userInfo.getToken(),this.projectid,mHandler);
                }
            }break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pro_acc_manage;
    }
}
