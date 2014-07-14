package com.lazooo.wifi.app.android.fragments;/**
 * Lazooo copyright 2012
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.components.HeaderSlider;
import com.lazooo.wifi.app.android.components.SlidingTabs;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 30/06/14
 * Time: 18:04
 */
public class TabFragment extends Fragment implements FragmentSelectionChangeListener {

    private PagerAdapter pagerAdapter;
    private boolean isLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        WifiLazooo.getApplication().getSlidingTabs().setCurrentTabFragment(this, getTabPosition());
        super.onCreate(savedInstanceState);
    }

    public boolean isLoading(){
        return isLoading;
    }

    public void setLoading(Boolean isLoading){
        this.isLoading = isLoading;
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter){

        this.pagerAdapter = pagerAdapter;
    }

    public int getTabPosition(){

        throw new RuntimeException("Override this method");
    }

    public PagerAdapter getPagerAdapter(){
        return pagerAdapter;
    }

    public String getTabName(){

        return "default";
    }

    @Override
    public void onFragmentSelected() {

        if(isLoading())
            WifiLazooo.getApplication().getSlidingTabs().onStartLoading();
        else
            WifiLazooo.getApplication().getSlidingTabs().onStopLoading();
    }

    @Override
    public void onFragmentChangedFromThis() {

    }
}
