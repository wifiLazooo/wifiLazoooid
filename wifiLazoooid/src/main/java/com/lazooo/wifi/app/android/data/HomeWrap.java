package com.lazooo.wifi.app.android.data;/**
 * Lazooo copyright 2012
 */

import com.lazooo.wifi.app.android.data.storage.Action;
import com.lazooo.wifi.app.android.data.storage.Tip;

import java.util.List;

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

    private NewDataListener<List<Action>> lPublicActivities;
    private NewDataListener<List<Tip>> lTips;
    private NewDataListener<String> lAroundWifis;

    public synchronized void setHomeListeners(NewDataListener<List<Action>> lPublicActivities,
                                          NewDataListener<List<Tip>> lTips,
                                          NewDataListener<String> lAroundWifis) {

        this.lPublicActivities = lPublicActivities;
        this.lTips = lTips;
        this.lAroundWifis = lAroundWifis;

        tryAutoRefresh();
    }

    @Override
    public void launchRefresh(){


    }

    int _getSecondsAutoRefresh(){

        return AUTO_REFRESH_SECONDS;
    }
}
