package cn.cassan.pm.base;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.cassan.pm.adapter.ViewPageFragmentAdapter;
import cn.cassan.pm.ui.EmptyLayout;
import cn.cassan.pm.widget.PagerSlidingTabStrip;

/**
 * 带有导航条的基类
 *
 * @author
 * @created 2014年11月6日 下午4:59:50
 */
public abstract class BaseViewPagerFragment extends BaseFragment {

    protected PagerSlidingTabStrip mTabStrip;
    protected ViewPager mViewPager;
    protected ViewPageFragmentAdapter mTabsAdapter;
    protected EmptyLayout mErrorLayout;
    protected View mRoot;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            View root = inflater.inflate(cn.cassan.pm.R.layout.base_viewpage_fragment, null);

            mTabStrip = (PagerSlidingTabStrip) root
                    .findViewById(cn.cassan.pm.R.id.pager_tabstrip);

            mViewPager = (ViewPager) root.findViewById(cn.cassan.pm.R.id.pager);

            mErrorLayout = (EmptyLayout) root.findViewById(cn.cassan.pm.R.id.error_layout);

            mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
                    mTabStrip, mViewPager);
            setScreenPageLimit();
            onSetupTabAdapter(mTabsAdapter);

            mRoot = root;
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /*
        mTabStrip = (PagerSlidingTabStrip) view
                .findViewById(R.id.pager_tabstrip);

        mViewPager = (ViewPager) view.findViewById(R.id.pager);

        mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);

        mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
                mTabStrip, mViewPager);
        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
        */
        if (savedInstanceState != null) {
            int pos = savedInstanceState.getInt("position");
            mViewPager.setCurrentItem(pos, true);
        }
    }

    protected void setScreenPageLimit() {
    }

    // @Override
    // public void onSaveInstanceState(Bundle outState) {
    // //No call for super(). Bug on API Level > 11.
    // if (outState != null && mViewPager != null) {
    // outState.putInt("position", mViewPager.getCurrentItem());
    // }
    // //super.onSaveInstanceState(outState);
    // }

    protected abstract void onSetupTabAdapter(ViewPageFragmentAdapter adapter);
}