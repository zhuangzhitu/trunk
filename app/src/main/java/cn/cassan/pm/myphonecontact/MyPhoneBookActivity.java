package cn.cassan.pm.myphonecontact;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.R;
import cn.cassan.pm.api.ApiReturnResult;
import cn.cassan.pm.api.ProjectManagementApi;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.model.MobileContact;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.myphonecontact.listener.OnQuickSideBarTouchListener;
import cn.cassan.pm.myphonecontact.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import cn.cassan.pm.myphonecontact.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import cn.cassan.pm.myphonecontact.tipsview.DividerDecoration;
import cn.cassan.pm.myphonecontact.tipsview.QuickSideBarTipsView;
import cn.cassan.pm.myphonecontact.tipsview.QuickSideBarView;
import cn.cassan.pm.util.Helper;
import cn.cassan.pm.util.JSONUtil;
import cn.cassan.pm.widget.SearchView;
import cn.cassan.pm.widget.TitleView;
import cz.msebera.android.httpclient.Header;

/**
 * @author Created by zhihao on 2016/10/23.
 * @describe
 * @version_
 **/
public class MyPhoneBookActivity extends BaseFragmentActivity implements OnQuickSideBarTouchListener {

    @Bind(R.id.titleview)
    TitleView titleView;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.quickSideBarTipsView)
    QuickSideBarTipsView quickSideBarTipsView;
    @Bind(R.id.quickSideBarView)
    QuickSideBarView quickSideBarView;
    @Bind(R.id.searchView)
    SearchView searchView;
    private MyPhoneBooksAdapter adapter;
    private HashMap<String, Integer> letters = new HashMap<>();
    private ArrayList<PhoneInfo> list = new ArrayList<PhoneInfo>();
    private List<MobileContact> mobileContacts = new ArrayList<>();

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
            Toast.makeText(getContext(), "访问失败", Toast.LENGTH_SHORT).show();
        }
    };

    private void handleListBean(ApiReturnResult apiReturnResult, Header[] headers) {

        if (apiReturnResult.isOK()) {
            String data = apiReturnResult.getData();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(data);

            if (json.isJsonArray()) {
                MobileContact[] topNews = gson.fromJson(data, MobileContact[].class);

                List<MobileContact> topNewList = Arrays.asList(topNews);

                // 不这样做的话会addAll报错
                mobileContacts = new ArrayList<MobileContact>(topNewList);

            }
        } else {

            Helper.showToast(getContext(), "网络出错");
        }
    }

    @Override
    public void initView() {

        list = PhoneBookHelper.getPhoneNumberFromMobile(this);
        MobileContact moblie;
        for (PhoneInfo phoneInfo : list) {
            moblie = new MobileContact();
            moblie.setMobliephone(phoneInfo.getMobilephone());
            moblie.setName(phoneInfo.getName());
            mobileContacts.add(moblie);
        }

        // 将联系人列表的标题字母排序
        Collections.sort(list, new Comparator<PhoneInfo>() {
            @Override
            public int compare(PhoneInfo lhs, PhoneInfo rhs) {
                return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
            }
        });
        //设置监听
        quickSideBarView.setOnQuickSideBarTouchListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyPhoneBooksAdapter(mobileContacts);
        List<String> customLetters = new ArrayList<>();
        int position = 0;
        for (PhoneInfo phoneInfo : list) {
            String letter = phoneInfo.getFirstLetter();
            //如果没有这个key则加入并把位置也加入
            if (!letters.containsKey(letter)) {
                letters.put(letter, position);
                customLetters.add(letter);
            }
            position++;
        }
        customLetters = PhoneBookHelper.removeDuplicate(customLetters);

        // 将联系人列表的字母标题排序
        Collections.sort(customLetters, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                if (lhs.equals("#")) {
                    return 1;
                } else if (rhs.equals("#")) {
                    return -1;
                } else {
                    return lhs.compareTo(rhs);
                }
            }
        });
        quickSideBarView.setLetters(customLetters);
        adapter.addAll(list);
        recyclerView.setAdapter(adapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);
        // Add decoration for dividers between list items
        recyclerView.addItemDecoration(new DividerDecoration(this));
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
        // 根据输入框输入值的改变来过滤搜索
        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phonebook;
    }

    @Override
    public void initData() {

        netWork();
    }

    private void netWork() {

        UserInfo userInfo = AppContext.getInstance().getLoginUserInfo();
        if (userInfo != null) {
            String token = userInfo.getToken();
            ProjectManagementApi.getMobileAppUser(token, mobileContacts, httpResponseHandler);
        }
    }

    @Override
    public void onLetterChanged(String letter, int position, float y) {

        quickSideBarTipsView.setText(letter, position, y);
        //有此key则获取位置并滚动到该位置
        if (letters.containsKey(letter)) {
            recyclerView.scrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {

        //可以自己加入动画效果渐显渐隐
        quickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.INVISIBLE);
    }

    private class MyPhoneBooksAdapter extends PhoneBooksAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
        /**
         * 传递可以申请加好友的列表
         */
        private List<MobileContact> mobileContacts;

        public MyPhoneBooksAdapter(List<MobileContact> mobileContacts) {
            this.mobileContacts = mobileContacts;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_phonebook, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            cn.cassan.pm.myphonecontact.MobileContact contac = getItem(position);
            TextView name_tv = (TextView) holder.itemView.findViewById(R.id.name);
            TextView addmember_tv = (TextView) holder.itemView.findViewById(R.id.addmember);
            name_tv.setText(getItem(position).getName());
            for (MobileContact contact : mobileContacts) {
                if (contact.getMobliephone() == contac.getMobliephone() && contact.isFriendind()) {
                    addmember_tv.setVisibility(View.VISIBLE);
                    addmember_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Helper.showToast(getContext(), "点击了添加好友");
                        }
                    });
                }
            }
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).getFirstLetter().charAt(0);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_header, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(String.valueOf(getItem(position).getFirstLetter()));
            holder.itemView.setBackgroundColor(getResources().getColor(R.color.trans40));
        }
    }

}
