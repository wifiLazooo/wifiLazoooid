package com.lazooo.wifi.app.android.views;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.lazooo.wifi.app.android.components.SlidingTabs;

import java.util.List;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 19/06/14
 * Time: 14:18
 */
public class TabLayout extends ScrollView {
    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addView(){

    }

    public void setViewPager(ViewPager viewPager) {

    }

    public void populateTabStrip(List<SlidingTabs.TabItem> tabs) {

    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {

    }
}
