package com.lazooo.wifi.app.android.data.storage;/**
 * Lazooo copyright 2012
 */

import com.lazooo.wifi.app.android.utils.GeoPoint;

import java.util.Date;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 13:47
 */
public class Wifi extends LazoooRecord<Wifi> {

    String id;
    String name;
    String address;
    WifiStats statistics;
    String savedBundleId;

    /**
     * @param now          the current time when the api was called to create this instance.
     * @param currLocation the current location when the api was called to create this instance.
     */
    public Wifi(Date now, GeoPoint currLocation, String lazoooId) {
        super(now, currLocation, lazoooId);
    }
}
