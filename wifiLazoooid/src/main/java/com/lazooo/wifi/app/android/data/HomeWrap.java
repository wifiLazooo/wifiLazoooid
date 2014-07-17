package com.lazooo.wifi.app.android.data;/**
 * Lazooo copyright 2012
 */

import android.util.Log;

import com.lazooo.wifi.app.android.data.api.WifiLazoooService;
import com.lazooo.wifi.app.android.data.api.bean.PublicWifiItem;
import com.lazooo.wifi.app.android.data.api.bean.Result;
import com.lazooo.wifi.app.android.data.storage.Action;
import com.lazooo.wifi.app.android.data.storage.LazoooRecord;
import com.lazooo.wifi.app.android.data.storage.Media;
import com.lazooo.wifi.app.android.data.storage.Tip;
import com.lazooo.wifi.app.android.data.storage.User;
import com.lazooo.wifi.app.android.utils.GeoPoint;

import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 18:19
 */
public class HomeWrap extends DataWrap {
    private static int AUTO_REFRESH_SECONDS = 5 * 60;

    private NewData<List<Action>> lPublicActivities;
    private NewData<List<Tip>> lTips;
    private NewData<String> lAroundWifis;

    public HomeWrap(){

        lPublicActivities = new NewData<List<Action>>();
        lTips = new NewData<List<Tip>>();
        lAroundWifis = new NewData<String>();
    }


    public synchronized void setHomeListeners(NewData.NewDataListener<List<Action>> lPublicActivities,
                                          NewData.NewDataListener<List<Tip>> lTips,
                                          NewData.NewDataListener<String> lAroundWifis) {

        this.lPublicActivities.newDataListener = lPublicActivities;
        this.lTips.newDataListener = lTips;
        this.lAroundWifis.newDataListener = lAroundWifis;

        //tryAutoRefresh();
    }

    private Callback<Result<Action>> publicActivitiesCallback(){

        Callback<Result<Action>> callback = new Callback<Result<Action>>(lPublicActivities) {
            @Override
            public void success(Result<Action> map, Response response) {
                super.success(map, response);

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                super.failure(retrofitError);

            }
        };
        return callback;
    }

    private Callback<Map> tipsCallback(){

        Callback<Map> callback = new Callback<Map>(lTips) {
            @Override
            public void success(Map map, Response response) {
                super.success(map, response);

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                super.failure(retrofitError);

            }
        };
        return callback;
    }

    private Callback<Map> aroundWifisCallback(){

        Callback<Map> callback = new Callback<Map>(lAroundWifis) {
            @Override
            public void success(Map map, Response response) {
                super.success(map, response);

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                super.failure(retrofitError);

            }
        };
        return callback;
    }

    @Override
    public void refresh(){

        User user = new User(new Date(), new GeoPoint(2f,2f,2), "LAZOOOID123", "favo");
        user.lazoooSave();
        Log.e("user", User.lazoooFindById(User.class, "LAZOOOID123").userName);
        WifiLazoooService.WifiLazoooServiceFactory.get().wifiSearch(20f, 20f, "", 12, new Callback<Result<PublicWifiItem>>(lPublicActivities));
    }

    int _getSecondsAutoRefresh(){

        return AUTO_REFRESH_SECONDS;
    }
}
