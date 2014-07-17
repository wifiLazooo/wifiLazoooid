package com.lazooo.wifi.app.android.data.api;/**
 * Lazooo copyright 2012
 */

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 21:54
 */
public interface WifiLazoooPrivateService {



    public static class WifiLazoooPrivateServiceFactory {

        static WifiLazoooPrivateService wifiLazoooPrivateService = null;

        private static WifiLazoooPrivateService generateService(){

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://wifi.lazooo.com/api/v1/private")
                    .setConverter(new GsonConverter(new GsonBuilder().create()))
                    .build();

            return restAdapter.create(WifiLazoooPrivateService.class);
        }

        public static WifiLazoooPrivateService get(){

            if(wifiLazoooPrivateService == null)
                wifiLazoooPrivateService = generateService();
            return wifiLazoooPrivateService;
        }
    }
}
