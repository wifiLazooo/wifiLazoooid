package com.lazooo.wifi.app.android.components;/**
 * Lazooo copyright 2012
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.data.storage.Wifi;
import com.lazooo.wifi.app.android.fragments.TabFragment;
import com.lazooo.wifi.app.android.views.HorizontalTabLayout;
import com.lazooo.wifi.app.android.views.TabLayout;

import java.util.List;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 08/06/14
 * Time: 16:15
 */
public class SlidingTabs extends Fragment implements ViewPager.OnPageChangeListener{

    static final String LOG_TAG = "SlidingTabs";

    private List<SlidingTabs.TabItem> tabs;

    TabFragment[] fragments;
    private boolean _isLoading;

    private TabLayout mTabLayout;
    private TabFragment current;

    private RelativeLayout mainLayout;
    private ViewPager mViewPager;
    private SamplePagerAdapter mSamplePagerAdapter;
    private SwipeRefreshLayout mPullToRefreshLayout;

    private ActionBar mActionBar;

    List<TabItem> mTabs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new TabFragment[WifiLazooo.getApplication().getmMainTabs().size()];
        tabs = WifiLazooo.getApplication().getmMainTabs();
        WifiLazooo.getApplication().setSlidingTabs(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        //onPageSelected(0);
        View rootView = inflater.inflate(R.layout.fragment_main_tabs, container, false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(3);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mainLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //mViewPager.setPageTransformer(true, new DepthPagerTransformer());
        mSamplePagerAdapter = new SamplePagerAdapter(getFragmentManager(), this);
        mSamplePagerAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(mSamplePagerAdapter);
        mTabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        mTabLayout.addView();
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.populateTabStrip(tabs);
        mTabLayout.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        WifiLazooo.getApplication().getActionBar().setCustomView(WifiLazooo.getApplication().getmTitle(tabs.get(0).getName()));
        // END_INCLUDE (setup_slidingtablayout)
    }

    public void setCurrentTabFragment(TabFragment tabFragment, int position){

        fragments[position] = tabFragment;
    }

    public void onStartLoading(){

        if(_isLoading == false && mTabLayout instanceof HorizontalTabLayout){

            final float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
            TranslateAnimation animation = new TranslateAnimation(0,0,0,px);
            animation.setDuration(100);
            animation.setFillEnabled(true);
            animation.setFillAfter(true);

            mTabLayout.startAnimation(animation);
            _isLoading = true;
        }
    }

    public void onStopLoading(){

        if(_isLoading && mTabLayout instanceof HorizontalTabLayout){

            final float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
            TranslateAnimation animation = new TranslateAnimation(0,0,px,0);
            animation.setDuration(100);
            animation.setFillEnabled(true);
            animation.setFillAfter(true);
            mTabLayout.startAnimation(animation);
            _isLoading = false;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        TabFragment selected = fragments[position];
        if (current != null && current.getTabPosition() != selected.getTabPosition()){

            current.onFragmentChangedFromThis();
        }
        selected.onFragmentSelected();
        current = selected;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setTabs(List<TabItem> tabs){

        this.tabs = tabs;
    }


    public class SamplePagerAdapter extends FragmentStatePagerAdapter {

        SlidingTabs slidingTabs;

        public SamplePagerAdapter(FragmentManager fm, SlidingTabs slidingTabs) {
            super(fm);
            this.slidingTabs = slidingTabs;
        }


        @Override
        public Fragment getItem(int position){

            //call onpageselected before to fill the array of tabfragments
            //slidingTabs.onPageSelected(position);

            TabFragment fragment = null;
            try {

                fragment = (TabFragment)tabs.get(position).getFragment().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bundle args = new Bundle();
            fragment.setArguments(args);
            fragment.setPagerAdapter(this);
            return fragment;
        }

        public void refreshPages(){

            this.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return WifiLazooo.getApplication().getMainTabs().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return "OBJECT " + (position + 1);
        }


    }


    public static class TabItem{

        private String name;
        private String description;
        private String text;
        private Class fragment;
        private String color;

        public TabItem(String name, String description, String text, Class fragment, String color) {
            this.name = name;
            this.description = description;
            this.text = text;
            this.fragment = fragment;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String  getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Class getFragment() {
            return fragment;
        }

        public void setFragment(Class fragment) {
            this.fragment = fragment;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
