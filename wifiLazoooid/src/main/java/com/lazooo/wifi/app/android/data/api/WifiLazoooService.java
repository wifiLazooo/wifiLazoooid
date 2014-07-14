package com.lazooo.wifi.app.android.data.api;/**
 * Lazooo copyright 2014
 */

import retrofit.RestAdapter;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 21:35
 */
public interface WifiLazoooService extends Service {


    public static class WifiLazoooServiceFactory extends ServiceFactory {

        WifiLazoooService generateService(){

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://wifi.lazooo.com/api/v1")
                    .setConverter(getConverter())
                    .build();

            return restAdapter.create(WifiLazoooService.class);
        }

        public static WifiLazoooService get(){
            return (WifiLazoooService) ServiceFactory.get();
        }
    }
}
