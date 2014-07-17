package com.lazooo.wifi.app.android.data;/**
 * Lazooo copyright 2012
 */

import android.util.Log;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 16/07/14
 * Time: 00:14
 */
public class Callback<K> implements retrofit.Callback<K> {
    NewData newData;

    public Callback(NewData newData){

        this.newData = newData;
    }

    @Override
    public void success(K k, Response response) {

        newData.is_loading = false;
    }

    @Override
    public void failure(RetrofitError retrofitError) {

        newData.is_loading = false;
    }
}
