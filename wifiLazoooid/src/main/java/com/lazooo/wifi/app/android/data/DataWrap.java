package com.lazooo.wifi.app.android.data;/**
 * Lazooo copyright 2012
 */

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 13:45
 */
public class DataWrap {

    private long lastUpdateTime;

    public DataWrap(){

        this.lastUpdateTime = 0;
    }

    int _getSecondsAutoRefresh(){

        throw new RuntimeException("Plz override this method.");
    }

    public void tryAutoRefresh(){

        if(needRefresh() == false){
            return;
        }
        refresh();
    }

    public void refresh(){


    }

    boolean needRefresh(){

        long currenTime = System.currentTimeMillis();
        int diffTime = ((int) (currenTime - lastUpdateTime) / 1000);
        if (diffTime > _getSecondsAutoRefresh()){

            return true;
        }
        return false;
    }
}
