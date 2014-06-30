package com.lazooo.wifi.app.android.components;/**
 * Lazooo copyright 2012
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.fragments.User;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 26/06/14
 * Time: 17:52
 */
public class HeaderSlider extends Fragment {

    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_header_pager, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) root.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //Bind the title indicator to the adapter
        CirclePageIndicator circleIndicator = (CirclePageIndicator)root.findViewById(R.id.indicator);
        circleIndicator.setViewPager(mPager);
        //set up arrows
        setUpRows(root);
        return root;
    }

    private void setUpRows(View root){

        TextView leftArrow = (TextView)root.findViewById(R.id.left_arrow);
        leftArrow.setTypeface(WifiLazooo.getApplication().getTypefaceFontello());
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onArrowClicked(view, false);
            }
        });
        TextView rightArrow = (TextView)root.findViewById(R.id.right_arrow);
        rightArrow.setTypeface(WifiLazooo.getApplication().getTypefaceFontello());
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onArrowClicked(view, true);
            }
        });
    }

    private void onArrowClicked(View view, boolean right){

        if (right){

            int curr = mPager.getCurrentItem();
            if(curr < mPagerAdapter.getCount() - 1){

                mPager.setCurrentItem(curr + 1);
            }else {

                mPager.setCurrentItem(0);
            }
        }else {

            int curr = mPager.getCurrentItem();
            if(curr > 0){

                mPager.setCurrentItem(curr - 1);
            }else {

                mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new User();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}