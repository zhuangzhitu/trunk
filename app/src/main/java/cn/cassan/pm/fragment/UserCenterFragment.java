package cn.cassan.pm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.base.BaseFragment;
import cn.cassan.pm.model.UserInfo;
import cn.cassan.pm.ui.EmptyLayout;
import cn.cassan.pm.util.DialogHelp;
import cn.cassan.pm.util.StringUtils;
import cn.cassan.pm.util.UIHelper;
import cn.cassan.pm.widget.AvatarView;
import cz.msebera.android.httpclient.Header;

/**
 * @author
 */

public class UserCenterFragment extends BaseFragment implements
        OnItemClickListener, OnScrollListener {

    private static final Object FEMALE = "女";

    @Bind(cn.cassan.pm.R.id.error_layout)
    EmptyLayout mEmptyView;

    @Bind(cn.cassan.pm.R.id.lv_user_active)
    ListView mListView;
    private ImageView mIvGender;
    private AvatarView mIvAvatar;
    private TextView mTvName;
    private TextView mTvFollowing;
    private TextView mTvFollower;
    private TextView mTvSore;
    private TextView mBtnFollowUser;
    private TextView mTvLastestLoginTime;


    private int mHisUid;
    private String mHisName;
    private int mUid;
    private UserInfo mUser;
    private final AsyncHttpResponseHandler mActiveHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
//                UserInformation information = XmlUtils.toBean(
//                        UserInformation.class, new ByteArrayInputStream(arg2));
//                mUser = information.getUser();
                fillUI();

            } catch (Exception e) {
                onFailure(arg0, arg1, arg2, e);
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            mEmptyView.setErrorType(EmptyLayout.NETWORK_ERROR);
        }

        @Override
        public void onFinish() {
            mState = STATE_NONE;
        }
    };
    private int mActivePage = 0;
    private AlertDialog mInformationDialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(cn.cassan.pm.R.layout.fragment_user_center, container,
                false);

        Bundle args = getArguments();

        mHisUid = args.getInt("his_id", 0);
        mHisName = args.getString("his_name");
        mUid = AppContext.getInstance().getLoginUid();
        ButterKnife.bind(this, view);
        initView(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {

            case cn.cassan.pm.R.id.ly_following:case cn.cassan.pm.R.id.iv_avatar:
            UIHelper.showUserAvatar(getActivity(),mUser.getAvatarurl());
            break;
//                UIHelper.showFriends(getActivity(), mUser.getId(), 0);
//                break;
//            case cn.cassan.pm.R.id.ly_follower:
//                UIHelper.showFriends(getActivity(), mUser.getId(), 1);
//                break;
            case cn.cassan.pm.R.id.tv_follow_user:
                handleUserRelation();
                break;
            case cn.cassan.pm.R.id.tv_private_message:
                if (mHisUid == AppContext.getInstance().getLoginUid()) {
                    AppContext.showToast("不能给自己发送留言:)");
                    return;
                }
                if (!AppContext.getInstance().isLogin()) {
                    UIHelper.showLoginActivity(getActivity());
                    return;
                }
//                UIHelper.showMessageDetail(getActivity(), mHisUid, mHisName);
                break;
            case cn.cassan.pm.R.id.tv_blog:
                UIHelper.showUserBlog(getActivity(), mHisUid);
                break;
            case cn.cassan.pm.R.id.tv_information:
                showInformationDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void initView(View view) {
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);

        View header = LayoutInflater.from(getActivity()).inflate(
                cn.cassan.pm.R.layout.fragment_user_center_header, null, false);

        mIvAvatar = (AvatarView) header.findViewById(cn.cassan.pm.R.id.iv_avatar);
        mIvAvatar.setOnClickListener(this);
        mIvGender = (ImageView) header.findViewById(cn.cassan.pm.R.id.iv_gender);
        mTvName = (TextView) header.findViewById(cn.cassan.pm.R.id.tv_name);
        mTvFollowing = (TextView) header.findViewById(cn.cassan.pm.R.id.tv_following_count);
        header.findViewById(cn.cassan.pm.R.id.ly_following).setOnClickListener(this);
        mTvFollower = (TextView) header.findViewById(cn.cassan.pm.R.id.tv_follower_count);
        header.findViewById(cn.cassan.pm.R.id.ly_follower).setOnClickListener(this);
        mTvSore = (TextView) header.findViewById(cn.cassan.pm.R.id.tv_score);
        mTvLastestLoginTime = (TextView) header
                .findViewById(cn.cassan.pm.R.id.tv_latest_login_time);

        TextView mBtnPrivateMsg = (TextView) header
                .findViewById(cn.cassan.pm.R.id.tv_private_message);
        mBtnPrivateMsg.setOnClickListener(this);
        mBtnFollowUser = (TextView) header.findViewById(cn.cassan.pm.R.id.tv_follow_user);
        mBtnFollowUser.setOnClickListener(this);

        header.findViewById(cn.cassan.pm.R.id.tv_blog).setOnClickListener(this);
        header.findViewById(cn.cassan.pm.R.id.tv_information).setOnClickListener(this);

        mListView.addHeaderView(header);

        mBtnPrivateMsg.setOnClickListener(this);
        mBtnFollowUser.setOnClickListener(this);


        mEmptyView.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fristSendGetUserInfomation();
            }
        });
    }

    private void fristSendGetUserInfomation() {
        mState = STATE_REFRESH;
        mListView.setVisibility(View.GONE);
        mEmptyView.setErrorType(EmptyLayout.NETWORK_LOADING);
        sendGetUserInfomation();
    }

    private void sendGetUserInfomation() {
//        OSChinaApi.getUserInformation(mUid, mHisUid, mHisName, mActivePage,
//                mActiveHandler);
    }

    private void fillUI() {
        mListView.setVisibility(View.VISIBLE);
        mIvAvatar.setAvatarUrl(mUser.getAvatarurl());
        mHisUid = mUser.getId();
        mHisName = mUser.getUsername();
        mTvName.setText(mHisName);

        int genderIcon = cn.cassan.pm.R.drawable.userinfo_icon_male;
        if (FEMALE.equals(mUser.getGender())) {
            genderIcon = cn.cassan.pm.R.drawable.userinfo_icon_female;
        }
        mIvGender.setBackgroundResource(genderIcon);

//        mTvFollowing.setText(mUser.getFollowers() + "");
//        mTvFollower.setText(mUser.getFans() + "");
//        mTvSore.setText(mUser.getScore() + "");
        mTvLastestLoginTime.setText(getString(cn.cassan.pm.R.string.latest_login_time,
                StringUtils.friendly_time(mUser.getLatestonline())));
        updateUserRelation();
    }

    private void updateUserRelation() {
//        switch (mUser.getRelation()) {
//            case User.RELATION_TYPE_BOTH:
//                mBtnFollowUser.setCompoundDrawablesWithIntrinsicBounds(
//                        cn.cassan.pm.R.drawable.ic_follow_each_other, 0, 0, 0);
//                mBtnFollowUser.setText(cn.cassan.pm.R.string.follow_each_other);
//                mBtnFollowUser.setTextColor(getResources().getColor(cn.cassan.pm.R.color.black));
//                mBtnFollowUser
//                        .setBackgroundResource(cn.cassan.pm.R.drawable.btn_small_white_selector);
//                break;
//            case User.RELATION_TYPE_FANS_HIM:
//                mBtnFollowUser.setCompoundDrawablesWithIntrinsicBounds(
//                        cn.cassan.pm.R.drawable.ic_followed, 0, 0, 0);
//                mBtnFollowUser.setText(cn.cassan.pm.R.string.unfollow_user);
//                mBtnFollowUser.setTextColor(getResources().getColor(cn.cassan.pm.R.color.black));
//                mBtnFollowUser
//                        .setBackgroundResource(cn.cassan.pm.R.drawable.btn_small_white_selector);
//                break;
//            case User.RELATION_TYPE_FANS_ME:
//                mBtnFollowUser.setCompoundDrawablesWithIntrinsicBounds(
//                        cn.cassan.pm.R.drawable.ic_add_follow, 0, 0, 0);
//                mBtnFollowUser.setText(cn.cassan.pm.R.string.follow_user);
//                mBtnFollowUser.setTextColor(getResources().getColor(cn.cassan.pm.R.color.white));
//                mBtnFollowUser
//                        .setBackgroundResource(cn.cassan.pm.R.drawable.btn_small_green_selector);
//                break;
//            case User.RELATION_TYPE_NULL:
//                mBtnFollowUser.setCompoundDrawablesWithIntrinsicBounds(
//                        cn.cassan.pm.R.drawable.ic_add_follow, 0, 0, 0);
//                mBtnFollowUser.setText(cn.cassan.pm.R.string.follow_user);
//                mBtnFollowUser.setTextColor(getResources().getColor(cn.cassan.pm.R.color.white));
//                mBtnFollowUser
//                        .setBackgroundResource(cn.cassan.pm.R.drawable.btn_small_green_selector);
//                break;
//        }
//        int padding = (int) TDevice.dpToPixel(20);
//        mBtnFollowUser.setPadding(padding, 0, padding, 0);
    }

    private void showInformationDialog() {
        if (mInformationDialog == null) {
            mInformationDialog = DialogHelp.getDialog(getActivity()).show();
            View view = LayoutInflater.from(getActivity()).inflate(
                    cn.cassan.pm.R.layout.fragment_user_center_information, null);
//            ((TextView) view.findViewById(cn.cassan.pm.R.id.tv_join_time))
//                    .setText(StringUtils.friendly_time(mUser.getJointime()));
//            ((TextView) view.findViewById(cn.cassan.pm.R.id.tv_location))
//                    .setText(StringUtils.getString(mUser.getFrom()));
//            ((TextView) view.findViewById(cn.cassan.pm.R.id.tv_development_platform))
//                    .setText(StringUtils.getString(mUser.getDevplatform()));
//            ((TextView) view.findViewById(cn.cassan.pm.R.id.tv_academic_focus))
//                    .setText(StringUtils.getString(mUser.getExpertise()));
            mInformationDialog.setContentView(view);
        }

        mInformationDialog.show();
    }

    private void handleUserRelation() {
        if (mUser == null)
            return;
        // 判断登录
        final AppContext ac = AppContext.getInstance();
        if (!ac.isLogin()) {
            UIHelper.showLoginActivity(getActivity());
            return;
        }
        String dialogTitle = "";
        int relationAction = 0;
//        switch (mUser.getRelation()) {
//            case User.RELATION_TYPE_BOTH:
//                dialogTitle = "确定取消互粉吗？";
//                relationAction = User.RELATION_ACTION_DELETE;
//                break;
//            case User.RELATION_TYPE_FANS_HIM:
//                dialogTitle = "确定取消关注吗？";
//                relationAction = User.RELATION_ACTION_DELETE;
//                break;
//            case User.RELATION_TYPE_FANS_ME:
//                dialogTitle = "确定关注Ta吗？";
//                relationAction = User.RELATION_ACTION_ADD;
//                break;
//            case User.RELATION_TYPE_NULL:
//                dialogTitle = "确定关注Ta吗？";
//                relationAction = User.RELATION_ACTION_ADD;
//                break;
//        }
//        final int ra = relationAction;
//
//        DialogHelp.getConfirmDialog(getActivity(), dialogTitle, new DialogInterface
//                .OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                sendUpdateRelcationRequest(ra);
//            }
//        }).show();
    }

    private void sendUpdateRelcationRequest(int ra) {
//        OSChinaApi.updateRelation(mUid, mHisUid, ra,
//                new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//                        try {
//                            Result result = XmlUtils.toBean(ResultBean.class,
//                                    new ByteArrayInputStream(arg2)).getResult();
//                            if (result.OK()) {
//                                switch (mUser.getRelation()) {
//                                    case User.RELATION_TYPE_BOTH:
//                                        mBtnFollowUser
//                                                .setCompoundDrawablesWithIntrinsicBounds(
//                                                        cn.cassan.pm.R.drawable.ic_add_follow,
//                                                        0, 0, 0);
//                                        mBtnFollowUser
//                                                .setText(cn.cassan.pm.R.string.follow_user);
//                                        mBtnFollowUser.setTextColor(getResources()
//                                                .getColor(cn.cassan.pm.R.color.white));
//                                        mBtnFollowUser
//                                                .setBackgroundResource(cn.cassan.pm.R.drawable
//                                                        .btn_small_green_selector);
//                                        mUser.setRelation(User.RELATION_TYPE_FANS_ME);
//                                        break;
//                                    case User.RELATION_TYPE_FANS_HIM:
//                                        mBtnFollowUser
//                                                .setCompoundDrawablesWithIntrinsicBounds(
//                                                        cn.cassan.pm.R.drawable.ic_add_follow,
//                                                        0, 0, 0);
//                                        mBtnFollowUser
//                                                .setText(cn.cassan.pm.R.string.follow_user);
//                                        mBtnFollowUser.setTextColor(getResources().getColor(cn.cassan.pm.R
//                                                .color.white));
//                                        mBtnFollowUser
//                                                .setBackgroundResource(cn.cassan.pm.R.drawable
//                                                        .btn_small_green_selector);
//                                        mUser.setRelation(User.RELATION_TYPE_NULL);
//                                        break;
//                                    case User.RELATION_TYPE_FANS_ME:
//                                        mBtnFollowUser
//                                                .setCompoundDrawablesWithIntrinsicBounds(
//                                                        cn.cassan.pm.R.drawable.ic_followed, 0,
//                                                        0, 0);
//                                        mBtnFollowUser
//                                                .setText(cn.cassan.pm.R.string.follow_each_other);
//                                        mBtnFollowUser.setTextColor(getResources()
//                                                .getColor(cn.cassan.pm.R.color.black));
//                                        mBtnFollowUser
//                                                .setBackgroundResource(cn.cassan.pm.R.drawable
//                                                        .btn_small_white_selector);
//                                        mUser.setRelation(User.RELATION_TYPE_BOTH);
//                                        break;
//                                    case User.RELATION_TYPE_NULL:
//                                        mBtnFollowUser
//                                                .setCompoundDrawablesWithIntrinsicBounds(
//                                                        cn.cassan.pm.R.drawable.ic_followed, 0,
//                                                        0, 0);
//                                        mBtnFollowUser
//                                                .setText(cn.cassan.pm.R.string.unfollow_user);
//                                        mBtnFollowUser.setTextColor(getResources()
//                                                .getColor(cn.cassan.pm.R.color.black));
//                                        mBtnFollowUser
//                                                .setBackgroundResource(cn.cassan.pm.R.drawable
//                                                        .btn_small_white_selector);
//                                        mUser.setRelation(User.RELATION_TYPE_FANS_HIM);
//                                        break;
//                                }
//                                int padding = (int) TDevice.dpToPixel(20);
//                                mBtnFollowUser.setPadding(padding, 0, padding,
////                                        0);
//                            }
//                            AppContext.showToastShort(result.getErrorMessage());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            onFailure(arg0, arg1, arg2, e);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//                                          Throwable arg3) {
//                    }
//                });
    }

    @Override
    public void initData() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (position - 1 < 0) {
            return;
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        if (mState == STATE_NOMORE || mState == STATE_LOADMORE
                || mState == STATE_REFRESH) {
            return;
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
