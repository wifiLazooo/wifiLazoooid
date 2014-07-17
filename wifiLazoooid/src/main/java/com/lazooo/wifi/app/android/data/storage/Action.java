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
 * Time: 13:46
 */
public class Action extends LazoooRecord<Action> {

    static final String V_PRIVATE = "private";
    static final String V_PUBLIC = "public";

    static final String T_WIFI_CONNECTION = "wifi_connection";
    static final String T_WIFI_DISCOVERY = "wifi_discovery";
    static final String T_MAKE_COMMENT = "make_comment";
    static final String T_ADD_MEDIA = "add_media";
    static final String T_GAIN_POINTS = "gain_points";
    static final String T_UP_LEVEL = "up_level";
    static final String T_WL_JOIN = "wl_join";

    String icon;
    String typeIcon;
    String type;
    String userId;
    String richText;
    String visibility;

    public Action(Date now, GeoPoint currLocation, String lazoooId, String icon, String visibility,
                  String typeIcon, String actionType, String richText, String userId) {
        super(now, currLocation, lazoooId);
        this.icon = icon;
        this.userId = userId;
        this.visibility = visibility;
        this.typeIcon = typeIcon;
        this.type = actionType;
        this.richText = richText;
    }
}
