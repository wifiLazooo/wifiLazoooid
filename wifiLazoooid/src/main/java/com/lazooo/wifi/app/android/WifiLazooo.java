package com.lazooo.wifi.app.android;/**
 * Lazooo copyright 2012
 */

import android.app.Application;
import android.graphics.Typeface;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lazooo.wifi.app.android.components.SlidingTabs;
import com.lazooo.wifi.app.android.data.DataWrap;
import com.lazooo.wifi.app.android.data.HomeWrap;
import com.lazooo.wifi.app.android.fragments.Home;
import com.lazooo.wifi.app.android.fragments.Info;
import com.lazooo.wifi.app.android.fragments.Trend;
import com.lazooo.wifi.app.android.fragments.User;
import com.lazooo.wifi.app.android.views.FloatingButton;
import com.orm.SugarApp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 11/06/14
 * Time: 23:14
 */
public class WifiLazooo extends SugarApp {

    private static WifiLazooo application = null;
    private Typeface typefaceBariol;
    private Typeface typefaceFontello;
    private Typeface typefaceBariolThin;
    private SlidingTabs slidingTabs;
    private FloatingButton floatingButton;
    private Map<Class, DataWrap> dataWrapMap;


    private static final List<SlidingTabs.TabItem> mMainTabs = new LinkedList<SlidingTabs.TabItem>();

    public WifiLazooo(){


    }

    static {
        mMainTabs.add(new SlidingTabs.TabItem(
                        "Home", "This is the very first interaction view, do searches etc.", "C", Home.class, "#F0645A")
        );
        mMainTabs.add(new SlidingTabs.TabItem(
                        "Info", "This is the very first interaction view, do searches etc.", "F", Info.class, "#80C8FE")
        );
        mMainTabs.add(new SlidingTabs.TabItem(
                        "Trend", "This is the very first interaction view, do searches etc.", "?", Trend.class, "#00B366")
        );
        mMainTabs.add(new SlidingTabs.TabItem(
                        "User", "This is the very first interaction view, do searches etc.", "I", User.class, "#FFCC00")
        );
    }
    @Override
    public void onCreate() {

        super.onCreate();
        WifiLazooo.application = this;
        typefaceBariol = Typeface.createFromAsset(getAssets(), "fonts/Bariol_Bold.otf");
        typefaceFontello = Typeface.createFromAsset(getAssets(), "fonts/fontello.ttf");
        typefaceBariolThin = Typeface.createFromAsset(getAssets(), "fonts/Bariol_Regular.otf");
        dataWrapMap = new HashMap<Class, DataWrap>();
        dataWrapMap.put(HomeWrap.class, new HomeWrap());
    }

    public <K extends DataWrap> K getDataWrap(Class<K> cls) {

        DataWrap dataWrap = dataWrapMap.get(cls);
        return dataWrap == null ? null: cls.cast(dataWrap);
    }

    public SlidingTabs getSlidingTabs() {
        return slidingTabs;
    }

    public FloatingButton getFloatingButton() {
        return floatingButton;
    }

    public void setFloatingButton(FloatingButton floatingButton) {
        this.floatingButton = floatingButton;
    }

    public void setSlidingTabs(SlidingTabs slidingTabs) {
        this.slidingTabs = slidingTabs;
    }

    public  List<SlidingTabs.TabItem> getMainTabs(){

        return mMainTabs;
    }
    public static WifiLazooo getApplication(){

        return application;
    }

    public Typeface getTypefaceFontello(){

        return typefaceFontello;
    }

    public Typeface getTypefaceBAriol(){

        return typefaceBariol;
    }

    public Typeface getTypefaceBariolThin(){

        return typefaceBariolThin;
    }

    public List<SlidingTabs.TabItem> getmMainTabs(){
        return mMainTabs;
    }

    public View getmTitle(String text, String color){

        TextView mTitle = new TextView(this);
        //mTitle.setTextColor(getResources().getColor(R.color.brown_background));
        mTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mTitle.setGravity(Gravity.CENTER_VERTICAL);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
        String htmlText = "</font><font color=#FCF3E1>" + text + "</font><font color=" + color + "></font>";
        mTitle.setText(Html.fromHtml(htmlText));

        //mTitle.setText(text);
        mTitle.setTypeface(typefaceBariol);
        return mTitle;
    }

}


