package com.lazooo.wifi.app.android.data.storage;/**
 * Lazooo copyright 2012
 */

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
}
