package com.lazooo.wifi.app.android.data.api;/**
 * Lazooo copyright 2012
 */

import retrofit.RestAdapter;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 21:54
 */
public interface WifiLazoooPrivateService extends Service {



    public static class WifiLazoooPrivateServiceFactory extends ServiceFactory {

        WifiLazoooPrivateService generateService(){

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://wifi.lazooo.com/api/v1/private")
                    .setConverter(getConverter())
                    .build();

            return restAdapter.create(WifiLazoooPrivateService.class);
        }

        public static WifiLazoooPrivateService get(){
            return (WifiLazoooPrivateService) ServiceFactory.get();
        }
    }
}
