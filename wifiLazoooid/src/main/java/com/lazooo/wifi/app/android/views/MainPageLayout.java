package com.lazooo.wifi.app.android.views;/**
 * Lazooo copyright 2012
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 08/06/14
 * Time: 18:25
 */
public class MainPageLayout extends LinearLayout {
    public MainPageLayout(Context context) {
        super(context);
    }

    public MainPageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MainPageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
