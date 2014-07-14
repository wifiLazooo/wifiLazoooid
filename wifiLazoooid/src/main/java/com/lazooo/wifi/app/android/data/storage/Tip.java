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
 * Time: 18:34
 */
public class Tip extends LazoooRecord<Tip> {

    static final String DEFAULT = "default";
    static final String LOCATION_DEPENDENT = "location";
    static final String TIME_DEPENDENT = "time";
    static final String BUTTON = "BUTTON";
    static final String BUTTON_DEFAULT = "BUTTON_DEFAULT";

    String iconBack;
    String iconFront;
    String title;
    String text;

    String actionToLauch;

    String tipType;

    public Tip(Date now, GeoPoint currLocation, String iconBack, String iconFront,
               String title, String text, String actionToLauch, String tipType) {

        super(now, currLocation);
        this.iconBack = iconBack;
        this.iconFront = iconFront;
        this.title = title;
        this.text = text;
        this.actionToLauch = actionToLauch;
        this.tipType = tipType;
    }
}
