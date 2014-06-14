package com.lazooo.wifi.app.android.components;/**
 * Lazooo copyright 2012
 */

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/06/14
 * Time: 15:13
 */
public class HomeSearchItem {

    private String text;
    private String iconText;

    public HomeSearchItem(String text, String iconText) {
        this.text = text;
        this.iconText = iconText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconText() {
        return iconText;
    }

    public void setIconText(String iconText) {
        this.iconText = iconText;
    }
}
