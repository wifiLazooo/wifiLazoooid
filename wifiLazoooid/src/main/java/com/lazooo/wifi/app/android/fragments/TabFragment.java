package com.lazooo.wifi.app.android.fragments;/**
 * Lazooo copyright 2012
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.lazooo.wifi.app.android.WifiLazooo;

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
        super.onCreate(savedInstanceState);

        WifiLazooo.getApplication().getSlidingTabs().setCurrentTabFragment(this, getTabPosition());
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
        View custom = WifiLazooo.getApplication().getmTitle(getTabName());
        WifiLazooo.getApplication().getActionBar().setCustomView(custom);
        if(isLoading())
            WifiLazooo.getApplication().getSlidingTabs().onStartLoading();
        else
            WifiLazooo.getApplication().getSlidingTabs().onStopLoading();
    }

    @Override
    public void onFragmentChangedFromThis() {

    }
}
