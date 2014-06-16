package com.lazooo.wifi.app.android.views;/**
 * Lazooo copyright 2012
 */

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 14/06/14
 * Time: 23:10
 */

import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class AnotherView extends LinearLayout {

    private ScrollCallbacks mCallbacks;

    public static interface ScrollCallbacks {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public void setCallbacks(ScrollCallbacks listener) {
        mCallbacks = listener;
    }

    public AnotherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mCallbacks != null) {
            mCallbacks.onScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

}