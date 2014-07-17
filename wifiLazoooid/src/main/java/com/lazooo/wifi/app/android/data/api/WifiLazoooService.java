package com.lazooo.wifi.app.android.data.api;/**
 * Lazooo copyright 2014
 */

import com.google.gson.GsonBuilder;
import com.lazooo.wifi.app.android.data.api.bean.PublicWifiItem;
import com.lazooo.wifi.app.android.data.api.bean.Result;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 21:35
 */
public interface WifiLazoooService {

    @GET("/wifi/search")
    public void wifiSearch(@Query("lat") Float latitude, @Query("lon") Float longitude, @Query("radius") String type,
                           @Query("radius") Integer radius, Callback<Result<PublicWifiItem>> csu);

    public static class WifiLazoooServiceFactory {

        static WifiLazoooService wifiLazoooService = null;

        private static WifiLazoooService generateService(){

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://wifi.lazooo.com/api/v1")
                    .setConverter(new GsonConverter(new GsonBuilder().create()))
                    .build();

            return restAdapter.create(WifiLazoooService.class);
        }

        public static WifiLazoooService get(){

            if(wifiLazoooService == null)
                wifiLazoooService = generateService();
            return wifiLazoooService;
        }
    }
}
