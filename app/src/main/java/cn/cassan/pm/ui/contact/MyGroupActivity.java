package cn.cassan.pm.ui.contact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.fragment.MyGroupFragment;
import cn.cassan.pm.widget.TitleView;

/**
 * @author Created by zhihao on 2016/10/22.
 * @describe 我的群组
 * @version_
 **/
public class MyGroupActivity extends BaseFragmentActivity {

    private TitleView titleView;
    private LinearLayout[] tabs = new LinearLayout[2];
    private ViewPager myViewpager;
    private int pos = 0;
    private MyGroupFragment[] myGroupFragments = new MyGroupFragment[2];

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mygroup;
    }


    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        initTab();
        initViewPager();
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
    }


    private void initTab() {
        tabs[0] = (LinearLayout) findViewById(R.id.MyGroup);
        tabs[1] = (LinearLayout) findViewById(R.id.DiscussionGroup);
        tabs[pos].setSelected(true);

    }

    private void initViewPager() {

        myViewpager = (ViewPager) findViewById(R.id.viewPager);
        myViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position >= 0 && position < tabs.length) {
                    if (myGroupFragments[position] == null) {
                        myGroupFragments[position] = new MyGroupFragment();
                        Bundle bundle = new Bundle();
                        //传递bundle判断是群还是讨论组
                        bundle.putBoolean("isMyGroup", position == 0);
                        myGroupFragments[position].setArguments(bundle);
                    }
                    return myGroupFragments[position];
                }
                return null;
            }

            @Override
            public int getCount() {
                return tabs.length;
            }
        });
        myViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void click(View v) {
        int index = -1;
        switch (v.getId()) {
            case R.id.MyGroup:
                index = 0;
                break;
            case R.id.DiscussionGroup:
                index = 1;
                break;
        }
        if (index > -1 && index < tabs.length) {
            changeTab(index);
        }
    }

    private void changeTab(int index) {
        // TODO Auto-generated method stub
        tabs[pos].setSelected(false);
        tabs[index].setSelected(true);
        pos = index;
        myViewpager.setCurrentItem(index, false);
    }


}
