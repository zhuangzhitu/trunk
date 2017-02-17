package cn.cassan.pm.ui.project;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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

import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.adapter.MyFriendAdapter;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.model.City;
import cn.cassan.pm.model.Province;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.util.Helper;
import cn.cassan.pm.util.JSONUtil;
import cn.cassan.pm.wheelPicker.WheelAreaPopWindow;
import cn.cassan.pm.widget.InfoItemLayout;
import cn.cassan.pm.widget.TitleView;
import cz.msebera.android.httpclient.Header;


/**
 * 创建项目窗体
 */

public class CreateProjectActivity extends BaseFragmentActivity implements View.OnClickListener, WheelAreaPopWindow.SelectAreaListener {


    private TitleView titleView;

    private InfoItemLayout project_name;


    private InfoItemLayout project_location;

    private InfoItemLayout project_unit;

    private InfoItemLayout project_manager;

    private int province_id, city_id;

    private ListView memberlist;

    private ArrayList friendListsel;
    private TextView addMember;
    private Button createProject;
    private WheelAreaPopWindow wheelAreaPopWindow;
    private ArrayList<Province> provinceList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private MyFriendAdapter adapter;
    private final static int chooseMember = 1;
    private String friendListid;
    private AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String response = new String(responseBody);
            Log.e("访问成功", "statusCode：" + statusCode);
            Log.e("访问成功", "responseBytes：" + response);

            ApiReturnResult result = JSONUtil.parseArrayString(response);
            if (result != null) {
                handleListBean(result, headers);
            } else {
                Helper.showErrorToast(responseBody, getContext());
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.e("访问失败", "statusCode：" + responseBody);

            //            Toast.makeText(getContext(), "访问失败", Toast.LENGTH_SHORT).show();
            Helper.showErrorToast(responseBody, getContext());
        }
    };

    private void netWorkgetList() {

        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {

            String token = userInfo.getToken();
            ProjectManagementApi.getProvinceCityInfo(token, httpResponseHandler);

        }
    }

    private void handleListBean(ApiReturnResult apiReturnResult, Header[] headers) {

        if (apiReturnResult.isOK()) {
            String data = apiReturnResult.getData();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(data);

            if (json.isJsonArray()) {
                Province[] topNews = gson.fromJson(data, Province[].class);

                List<Province> topNewList = Arrays.asList(topNews);


                // 不这样做的话会addAll报错
                provinceList = new ArrayList<Province>(topNewList);
                if (wheelAreaPopWindow == null)
                    wheelAreaPopWindow = new WheelAreaPopWindow(this);

                if (provinceList != null && provinceList.size() > 0) {
                    wheelAreaPopWindow.setData(provinceList);
                }
            }
        } else {
            provinceList = null;
            wheelAreaPopWindow.setData(null);
            Helper.showToast(getContext(), "网络出错");
        }

    }


    /**
     * 编辑点击事件
     *
     * @param v
     */
    public void click(View v) {


        switch (v.getId()) {
            case R.id.tv_project_name:
                project_name.setFocus();
                project_name.setEditVisible(true);
                break;
            case R.id.tv_project_location:
                Helper.hideSoftInput(this);
                if (wheelAreaPopWindow.getData() == null) {
                    Helper.showToast(getContext(), "省份和城市为空");
                    return;
                }
                project_location.setRightTextVisable(true);
                wheelAreaPopWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                wheelAreaPopWindow.setOnSelectDataListener(this);
                break;
            case R.id.tv_project_unit:
                project_unit.setFocus();
                project_unit.setEditVisible(true);
                break;
            case R.id.tv_project_manager:
                project_manager.setFocus();
                project_manager.setEditVisible(true);
                break;

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addmember:
                //                startActivityForResult(new Intent(CreateProjectActivity.this, FriendsMembActivity.class).putExtra("isFriendlist", false));
                startActivityForResult(new Intent(CreateProjectActivity.this, FriendsMembActivity.class).putExtra("isFriendlist", false), chooseMember);
                break;
            case R.id.btn_createProject:
                if (project_location.getRightText().equals("")) {
                    Helper.showToast(getContext(), "地点不能为空");
                    return;
                }

                btnCreateProject();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case chooseMember:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    friendListid = bundle.getString("friendList_id");
                    friendListsel = bundle.getParcelableArrayList("friendList_sel");
                    updateUI();
                }
                break;
            default:
                break;
        }
    }

    public void initView() {

        project_name = (InfoItemLayout) findViewById(R.id.tv_project_name);
        project_location = (InfoItemLayout) findViewById(R.id.tv_project_location);
        project_manager = (InfoItemLayout) findViewById(R.id.tv_project_manager);
        project_unit = (InfoItemLayout) findViewById(R.id.tv_project_unit);
        memberlist = (ListView) findViewById(R.id.memberlist);
        addMember = (TextView) findViewById(R.id.addmember);
        createProject = (Button) findViewById(R.id.btn_createProject);
        titleView = (TitleView) findViewById(R.id.titleview);
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
        addMember.setOnClickListener(this);
        createProject.setOnClickListener(this);
    }

    @Override
    public void initData() {

        netWorkgetList();


    }

    private void updateUI() {
        if (friendListsel != null) {
            if (adapter == null) {
                adapter = new MyFriendAdapter(this);


            }
            adapter.setData(friendListsel);
            memberlist.setAdapter(adapter);

        }
    }

    private void btnCreateProject() {

        String Name = project_name.getEditText();
        String ManagerName = project_manager.getEditText();
        String PlanToBegin = null;
        String PlanToEnd = null;
        int ConstructionCompanyId = 0;
        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {
            String token = userInfo.getToken();
            ProjectManagementApi.createProject(token, Name, ManagerName, PlanToBegin, PlanToEnd, province_id, city_id, ConstructionCompanyId, friendListid,
                    new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            Log.e("访问成功", "statusCode：" + statusCode);
                            Log.e("访问成功", "responseBytes：" + response);
                            ApiReturnResult result = JSONUtil.parseErrorString(response);

                            if (!result.getStatus().equals("2000000")) {

                                Toast.makeText(CreateProjectActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            } else {

                                Toast.makeText(CreateProjectActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Helper.showErrorToast(responseBody, getContext());

                        }
                    });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //        project_name.setFocus();
        //        project_name.setEditVisible(true);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_project;
    }


    @Override
    public void confirm(String provice_name, String city_name, int provice_id, int city_id) {

        project_location.setRightText(provice_name + "--" + city_name);
        this.province_id = provice_id;
        this.city_id = city_id;
    }
}
