package com.lazooo.wifi.app.android.views;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 14/06/14
 * Time: 23:09
 */
public class ObservableScrollView extends ScrollView
{
    private ScrollCallbacks mCallbacks;

    public ObservableScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mCallbacks != null)
        {
            mCallbacks.onScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    public int computeVerticalScrollRange()
    {
        return super.computeVerticalScrollRange();
    }

    public void setCallbacks(ScrollCallbacks listener)
    {
        mCallbacks = listener;
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }

    public static interface ScrollCallbacks
    {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}