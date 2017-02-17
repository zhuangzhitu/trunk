package cn.cassan.pm.model;

import cn.cassan.pm.fragment.AboutOSCFragment;
import cn.cassan.pm.fragment.MyInformationDetailFragment;
import cn.cassan.pm.fragment.MyInformationFragment;
import cn.cassan.pm.fragment.SettingsFragment;
import cn.cassan.pm.fragment.SettingsNotificationFragment;
import cn.cassan.pm.fragment.UserCenterFragment;

public enum SimpleBackPage {

//    COMMENT(1, cn.cassan.pm.R.string.actionbar_title_comment, CommentFrament.class),

 //   QUEST(2, cn.cassan.pm.R.string.actionbar_title_questions, QuestViewPagerFragment.class),

//    TWEET_PUB(3, R.string.actionbar_title_tweetpub, TweetPubFragment.class),

//    SOFTWARE_TWEETS(4, cn.cassan.pm.R.string.actionbar_title_softtweet,
//            SoftWareTweetsFrament.class),

    USER_CENTER(5, cn.cassan.pm.R.string.actionbar_title_user_center, UserCenterFragment.class),


    MY_INFORMATION(7, cn.cassan.pm.R.string.actionbar_title_my_information, MyInformationFragment.class),

//    MY_ACTIVE(8, cn.cassan.pm.R.string.actionbar_title_active, ActiveFragment.class),

  //  MY_MES(9, cn.cassan.pm.R.string.actionbar_title_mes, NoticeViewPagerFragment.class),

//    OPENSOURCE_SOFTWARE(10, cn.cassan.pm.R.string.actionbar_title_softwarelist,
//            OpensourceSoftwareFragment.class),

//    MY_FRIENDS(11, cn.cassan.pm.R.string.actionbar_title_my_friends,
//            FriendsViewPagerFragment.class),

    //QUESTION_TAG(12, cn.cassan.pm.R.string.actionbar_title_question, QuestionTagFragment.class),

//    MESSAGE_DETAIL(13, cn.cassan.pm.R.string.actionbar_title_message_detail,
//            MessageDetailFragment.class),

//    USER_FAVORITE(14, cn.cassan.pm.R.string.actionbar_title_user_favorite,
//            UserFavoriteViewPagerFragment.class),

    SETTING(15, cn.cassan.pm.R.string.actionbar_title_setting, SettingsFragment.class),

    SETTING_NOTIFICATION(16, cn.cassan.pm.R.string.actionbar_title_setting_notification, SettingsNotificationFragment.class),

    ABOUT_OSC(17, cn.cassan.pm.R.string.actionbar_title_about, AboutOSCFragment.class),

  //  BLOG(18, cn.cassan.pm.R.string.actionbar_title_blog_area, BlogViewPagerFragment.class),
//
//    RECORD(19, cn.cassan.pm.R.string.actionbar_title_tweetpub, TweetRecordFragment.class),

 //   SEARCH(20, cn.cassan.pm.R.string.actionbar_title_search, SearchViewPageFragment.class),
//
//    EVENT_LIST(21, cn.cassan.pm.R.string.actionbar_title_event, EventViewPagerFragment.class),
//
//    EVENT_APPLY(22, cn.cassan.pm.R.string.actionbar_title_event_apply,
//            EventAppliesFragment.class),

//    SAME_CITY(23, cn.cassan.pm.R.string.actionbar_title_same_city, EventFragment.class),

//    NOTE(24, cn.cassan.pm.R.string.actionbar_title_note, NoteBookFragment.class),
//
//    NOTE_EDIT(25, cn.cassan.pm.R.string.actionbar_title_noteedit, NoteEditFragment.class),

    //BROWSER(26, cn.cassan.pm.R.string.app_name, BrowserFragment.class),

//    DYNAMIC(27, cn.cassan.pm.R.string.team_dynamic, TeamActiveFragment.class),

    MY_INFORMATION_DETAIL(28, cn.cassan.pm.R.string.actionbar_title_my_information, MyInformationDetailFragment.class);

//    FEED_BACK(29, cn.cassan.pm.R.string.str_feedback_title, FeedBackFragment.class);

//    TEAM_USER_INFO(30, cn.cassan.pm.R.string.str_team_userinfo,
//            TeamMemberInformationFragment.class),
//
//    MY_ISSUE_PAGER(31, cn.cassan.pm.R.string.str_team_my_issue, MyIssuePagerfragment.class),
//
//    TEAM_PROJECT_MAIN(32, 0, TeamProjectViewPagerFragment.class),
//
//    TEAM_ISSUECATALOG_ISSUE_LIST(33, 0, TeamIssueFragment.class),
//
//    TEAM_ACTIVE(34, cn.cassan.pm.R.string.team_actvie, TeamActiveFragment.class),
//
//    TEAM_ISSUE(35, cn.cassan.pm.R.string.team_issue, TeamIssueViewPageFragment.class),
//
//    TEAM_DISCUSS(36, cn.cassan.pm.R.string.team_discuss, TeamDiscussFragment.class),
//
//    TEAM_DIRAY(37, cn.cassan.pm.R.string.team_diary, TeamDiaryFragment.class),
//
//    TEAM_DIRAY_DETAIL(38, cn.cassan.pm.R.string.team_diary_detail, TeamDiaryDetailFragment.class),
//
//    TEAM_PROJECT_MEMBER_SELECT(39, 0, TeamProjectMemberSelectFragment.class),
//
//    TEAM_PROJECT(40, cn.cassan.pm.R.string.team_project, TeamProjectFragment.class),

//    TWEET_LIKE_USER_LIST(41, 0, TweetLikeUsersFragment.class),

//    TWEET_TOPIC_LIST(42, 0, TweetsFragment.class),

//    MY_EVENT(43, cn.cassan.pm.R.string.actionbar_title_my_event, EventViewPagerFragment.class);

//    SOFTWARE_TWEETS_NEW(44, cn.cassan.pm.R.string.actionbar_title_softtweet, SoftWareForTweetsFragment.class);

    private int title;
    private Class<?> clz;
    private int value;

    SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
