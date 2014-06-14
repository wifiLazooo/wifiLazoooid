package com.lazooo.wifi.app.android.components;/**
 * Lazooo copyright 2012
 */

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.view.SlidingTabLayout;

import java.util.LinkedList;
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

    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    private ActionBar mActionBar;
    private SamplePagerAdapter samplePagerAdapter;

    List<TabItem> mTabs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabs = WifiLazooo.getApplication().getmMainTab().tabs;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        return inflater.inflate(R.layout.fragment_sample, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        samplePagerAdapter = new SamplePagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(samplePagerAdapter);
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.populateTabStrip(tabs);
        mSlidingTabLayout.setOnPageChangeListener(this);
        mActionBar.setTitle(tabs.get(0).name);
        // END_INCLUDE (setup_slidingtablayout)

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        TextView custom = WifiLazooo.getApplication().getmTitle();
        custom.setText(tabs.get(position).name);
        mActionBar.setCustomView(custom);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setTabs(List<TabItem> tabs){

        this.tabs = tabs;
    }


    public class SamplePagerAdapter extends FragmentPagerAdapter {
        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i){

            Fragment fragment = null;
            try {

                fragment =(Fragment) tabs.get(i).fragment.newInstance();
                Bundle args = new Bundle();
                args.putInt("object", i + 1);
                fragment.setArguments(args);
            }catch (Exception e){

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
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

        public TabItem(String name, String description, String text, Class fragment) {
            this.name = name;
            this.description = description;
            this.text = text;
            this.fragment = fragment;
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
    }
}
