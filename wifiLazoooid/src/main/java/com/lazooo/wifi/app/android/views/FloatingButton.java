package com.lazooo.wifi.app.android.views;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lazooo.wifi.app.android.R;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 01/07/14
 * Time: 21:24
 */
public class FloatingButton extends Button implements View.OnTouchListener, View.OnClickListener{

    int windowwidth;
    int windowheight;
    private int _xDelta;
    private int _yDelta;
    private int xStart;
    private int yStart;
    private int rMArgin;
    private int bMArgin;

    public FloatingButton(Context context) {
        this(context, null);
    }

    public FloatingButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnTouchListener(this);
        setOnClickListener(this);
    }

    @Override
    protected void onVisibilityChanged (View changedView, int visibility){
        super.onVisibilityChanged(changedView, visibility);

        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) getLayoutParams();
        rMArgin = lParams.rightMargin;
        bMArgin = lParams.bottomMargin;
        View parent = (View)getParent();
        windowwidth = parent.getMeasuredWidth();
        windowheight = parent.getMeasuredHeight();

    }
    public boolean onTouch(View view, MotionEvent event) {

        final int X = windowwidth - ((int) event.getRawX());
        final int Y = windowheight - ((int) event.getRawY());
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

                _xDelta = X - lParams.rightMargin;
                _yDelta = Y - lParams.bottomMargin;
                yStart = windowheight - lParams.bottomMargin;
                xStart = windowwidth - lParams.rightMargin;
                break;
            case MotionEvent.ACTION_UP:
                if((X - _xDelta) != rMArgin || (Y - _yDelta) != bMArgin) {

                    TranslateAnimation animation = new TranslateAnimation(0,
                            getScaled(X - _xDelta) - rMArgin, 0, getScaled(Y - _yDelta) - bMArgin);
                    animation.setDuration(250);
                    startAnimation(animation);
                }else {
                    //CLICK, no animation!
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.rightMargin = getScaled(X - _xDelta);
                layoutParams.bottomMargin = getScaled(Y - _yDelta);
                view.setLayoutParams(layoutParams);
                break;
        }

        return false;
    }

    @Override
    public void onAnimationEnd(){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.rightMargin = rMArgin;
        layoutParams.bottomMargin = bMArgin;
        setLayoutParams(layoutParams);
    }

    private int getScaled(float difference){

        int windowmax = windowheight < windowwidth ? windowheight-_yDelta: windowwidth-_xDelta;
        double f = (3.0*difference)/windowmax + 1;
        int d = (int) (difference / f);
        return d > 0? d: 0;
    }

    @Override
    public void onClick(View view) {
       // setBackgroundColor(getResources().getColor(R.color.wblue));
    }
}
