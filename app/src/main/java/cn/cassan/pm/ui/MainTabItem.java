package cn.cassan.pm.ui;

import cn.cassan.pm.R;
import cn.cassan.pm.ui.huanxin.ConversationListFragment;
import cn.cassan.pm.fragment.MyInformationFragment;
import cn.cassan.pm.ui.workbench.WorkBenchFragment;
import cn.cassan.pm.ui.contact.ContactFragment;

public enum MainTabItem {

    Conversations(0, cn.cassan.pm.R.string.main_tab_name_message, cn.cassan.pm.R.drawable.tab_icon_new, ConversationListFragment.class),
    Friends(1, R.string.main_tab_name_management, cn.cassan.pm.R.drawable.tab_icon_tweet, ContactFragment.class),
    Workbench(2, R.string.main_tab_name_workbench, cn.cassan.pm.R.drawable.tab_icon_explore, WorkBenchFragment.class),
    ME(3, cn.cassan.pm.R.string.main_tab_name_my, cn.cassan.pm.R.drawable.tab_icon_me, MyInformationFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    MainTabItem(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getTabItemClass() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
