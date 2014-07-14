package com.lazooo.wifi.app.android.fragments;
/**
 * Lazooo copyright 2012
*/

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.components.SlidingTabs;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 14/06/14
 * Time: 12:44
 */
public class User extends TabFragment{

    public int getTabPosition(){

        return 3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_user, container, false);

        return rootView;
    }
}
