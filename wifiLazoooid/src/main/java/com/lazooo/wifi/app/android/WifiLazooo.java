package com.lazooo.wifi.app.android;/**
 * Lazooo copyright 2012
 */

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lazooo.wifi.app.android.components.SlidingTabs;
import com.lazooo.wifi.app.android.fragments.Home;
import com.lazooo.wifi.app.android.fragments.Info;
import com.lazooo.wifi.app.android.fragments.Trend;
import com.lazooo.wifi.app.android.fragments.User;

import java.util.LinkedList;
import java.util.List;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 11/06/14
 * Time: 23:14
 */
public class WifiLazooo extends android.app.Application {

    private static WifiLazooo application = null;
    private SlidingTabs mMainTab;
    private Typeface mTitleTypeface;
    private Typeface mFontelloTypeface;


    private static final List<SlidingTabs.TabItem> mMainTabs = new LinkedList<SlidingTabs.TabItem>();

    static {
        mMainTabs.add(new SlidingTabs.TabItem(
                        "Home", "This is the very first interaction view, do searches etc.", "!", Home.class)
        );
        mMainTabs.add(new SlidingTabs.TabItem(
                        "Info", "This is the very first interaction view, do searches etc.", "F", Info.class)
        );
        mMainTabs.add(new SlidingTabs.TabItem(
                        "Trend", "This is the very first interaction view, do searches etc.", "?", Trend.class)
        );
        mMainTabs.add(new SlidingTabs.TabItem(
                        "User", "This is the very first interaction view, do searches etc.", "I", User.class)
        );
    }
    @Override
    public void onCreate() {

        super.onCreate();
        mMainTab = new SlidingTabs();
        mMainTab.setTabs(mMainTabs);
        WifiLazooo.application = this;
        mTitleTypeface = Typeface.createFromAsset(getAssets(), "fonts/GoodDog.otf");
        mFontelloTypeface = Typeface.createFromAsset(getAssets(), "fonts/fontello.ttf");

    }


    public TextView getmTitle(){

        TextView mTitle = new TextView(this);
        mTitle.setTextColor(getResources().getColor(R.color.brown_background));
        mTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mTitle.setGravity(Gravity.CENTER_VERTICAL);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);

        mTitle.setText(mMainTabs.get(0).getName());
        mTitle.setTypeface(mTitleTypeface);
        return mTitle;
    }

    public SlidingTabs getmMainTab() {

        return mMainTab;
    }

    public static WifiLazooo getApplication(){

        return application;
    }

    public Typeface getmFontelloTypeface(){

        return mFontelloTypeface;
    }
}


