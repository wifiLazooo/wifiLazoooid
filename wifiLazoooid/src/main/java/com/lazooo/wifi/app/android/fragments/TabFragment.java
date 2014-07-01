package com.lazooo.wifi.app.android.fragments;/**
 * Lazooo copyright 2012
 */

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;

import com.lazooo.wifi.app.android.components.SlidingTabs;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 30/06/14
 * Time: 18:04
 */
public class TabFragment extends Fragment {

    private PagerAdapter pagerAdapter;
    public void setPagerAdapter(PagerAdapter pagerAdapter){

        this.pagerAdapter = pagerAdapter;
    }

    public PagerAdapter getPagerAdapter(){
        return pagerAdapter;
    }
}
