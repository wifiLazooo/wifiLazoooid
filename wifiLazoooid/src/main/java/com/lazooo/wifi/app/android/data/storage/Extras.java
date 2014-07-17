package com.lazooo.wifi.app.android.data.storage;/**
 * Lazooo copyright 2012
 */

import com.orm.SugarRecord;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 13:54
 */
public class Extras extends SugarRecord<Extras> {

    static final long EXTRAS_ID = 147147147;

    static Extras extras = null;

    String currentImage;
    String currentSessionKey;
    String myUserId;
    boolean loggedIn;

    public static Extras getExtras(){

        if(extras == null){

            extras = findById(Extras.class, EXTRAS_ID);
        }
        return extras;
    }

    public String getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(String currentImage) {
        this.currentImage = currentImage;
    }

    public String getCurrentSessionKey() {
        return currentSessionKey;
    }

    public void setCurrentSessionKey(String currentSessionKey) {
        this.currentSessionKey = currentSessionKey;
    }

    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
