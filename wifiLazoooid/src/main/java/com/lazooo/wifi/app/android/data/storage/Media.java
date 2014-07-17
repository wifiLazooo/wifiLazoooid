package com.lazooo.wifi.app.android.data.storage;/**
 * Lazooo copyright 2012
 */

import com.lazooo.wifi.app.android.utils.GeoPoint;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 14:27
 */
public class Media extends SugarRecord<Media> {

    String fava;
    public Media(){
    }
    /**
     * @param now          the current time when the api was called to create this instance.
     * @param currLocation the current location when the api was called to create this instance.
     */
    public Media(String fava) {

        this.fava = fava;
    }
}
