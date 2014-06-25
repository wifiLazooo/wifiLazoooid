package com.lazooo.wifi.app.android.storage;/**
 * Lazooo copyright 2012
 */

import android.widget.Adapter;

import com.lazooo.wifi.app.android.components.CustomAdapter;

import java.util.Date;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 25/06/14
 * Time: 16:49
 */
public class DataWrapper {

    private boolean refresh;
    private long lastInternetRefresh;
    private CustomAdapter adapter;

    public DataWrapper(CustomAdapter adapter){

        this.refresh = true;
        this.lastInternetRefresh = 1;
        this.adapter = adapter;
    }

    protected boolean refreshNeed(){

        return refresh;
    }

    protected long refreshFromLast(){

        return System.currentTimeMillis() - lastInternetRefresh;
    }

    protected void refreshedNow(){

        lastInternetRefresh = System.currentTimeMillis();
    }
}
