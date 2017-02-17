package cn.cassan.pm.ui.workbench;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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
import cn.cassan.pm.adapter.DataItemAdapter;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.model.Data;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.util.Helper;
import cn.cassan.pm.util.JSONUtil;
import cn.cassan.pm.widget.TitleView;
import cz.msebera.android.httpclient.Header;

/**
 * @author Created by zhihao on 2016/11/5.
 * @describe
 * @version_
 **/
public class DataListActivity extends BaseFragmentActivity implements android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.titleview)
    TitleView titleview;
    @Bind(R.id.empty)
    TextView empty;
    @Bind(R.id.mylist)
    ListView mylist;
    @Bind(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    private ArrayList<Data> dataList = new ArrayList<>();
    private DataItemAdapter dataAdapter;
    private int page = 1, pageSize = 10;


    private AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String response = new String(responseBody);
            Log.e("访问成功", "statusCode：" + statusCode);
            Log.e("访问成功", "responseBytes：" + response);
            ApiReturnResult result = JSONUtil.parseArrayString(response);
            SwipeRefreshLayout.setRefreshing(false);
            if (result != null) {
                handleDatalist(result, headers);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.e("访问失败", "statusCode：" + responseBody);
            SwipeRefreshLayout.setRefreshing(false);
            Helper.showToast(getContext(), "访问失败");
        }
    };

    @Override
    public void initView() {
        titleview.setTitleClickListener(new TitleView.TitleClickListener() {
            @Override
            public void onClick(String action) {
                switch (action) {
                    case TitleView.IMAGELEFT:
                    case TitleView.TEXTLEFT:
                        finish();
                }
            }
        });
        SwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void handleDatalist(ApiReturnResult apiReturnResult, Header[] headers) {
        if (apiReturnResult.isOK()) {
            String data = apiReturnResult.getData();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(data);
            if (json.isJsonArray()) {
                Data[] topNews = gson.fromJson(data, Data[].class);
                List<Data> topNewList = Arrays.asList(topNews);
                // 不这样做的话会addAll报错
                dataList = new ArrayList<Data>(topNewList);
            }
        } else {
            dataList = null;
        }
        updateUI(dataList);
    }

    private void updateUI(ArrayList<Data> dataList) {

        if (dataAdapter == null) {
            dataAdapter = new DataItemAdapter(getContext());
            mylist.setAdapter(dataAdapter);
            mylist.setEmptyView(empty);
            if (dataList != null && dataList.size() != 0) {
                dataAdapter.setData(dataList);
            }
        } else {
            if (dataList != null && dataList.size() != 0) {
                dataAdapter.setData(dataList);
                mylist.setAdapter(dataAdapter);
                mylist.setEmptyView(empty);
            }
        }
    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_datalist;
    }

    private void getData() {
        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {

            String token = userInfo.getToken();
            ProjectManagementApi.getDocumentByType(token, this.getDocumentType(), AppContext.getCurrentProjectId(), page, pageSize, httpResponseHandler);

        }
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private int documentType = 1;
    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }
}
