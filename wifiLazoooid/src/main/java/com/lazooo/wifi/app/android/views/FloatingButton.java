package com.lazooo.wifi.app.android.views;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    static final int MARGIN = 15;

    int windowwidth;
    int windowheight;
    private int lazy_x;
    private int lazy_y;
    private int current_x;
    private int current_y;
    private int hide_x;
    private int hide_y;
    private boolean measured;
    private boolean is_hide;
    private boolean is_tracking;

    private DummyTouchListener dummyTouchListener;

    private Context context;
    private int _xDelta;
    private int _yDelta;
    private int xStart;
    private int yStart;

    public FloatingButton(Context context) {
        this(context, null);
    }

    public FloatingButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        measured = false;
        is_hide = true;
        is_tracking = false;
        dummyTouchListener = new DummyTouchListener();
        this.context = context;
        setOnTouchListener(this);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) getLayoutParams();
        windowwidth = parentWidth;
        windowheight = parentHeight;
        lazy_x = parentWidth - MARGIN;
        lazy_y = parentHeight - MARGIN;
    }

    @Override
    protected void onVisibilityChanged (View changedView, int visibility){
        super.onVisibilityChanged(changedView, visibility);
        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) getLayoutParams();
        if (!measured){
            //initial margin
            measured = true;
            hide_x = current_x = lParams.leftMargin;
            hide_y = current_y = lParams.topMargin;
        }
    }

    public void showButton(){

        if (is_hide){

            is_hide = false;
            this.translate(current_x, lazy_y, current_y, lazy_y);
        }
    }

    public void hideButton(){

        if (is_hide == false){

            is_hide = true;
            this.translate(current_x, hide_x, current_y, hide_y);
        }
    }

    public void resetLazy(){

        this.translate(current_x, lazy_y, current_y, lazy_y);
    }

    public void trackDirection(int degrees){

        is_tracking = true;
    }

    private Point degreesToPosition(int degrees){

        double y;
        double x;
        if( windowheight > windowwidth){

            y = windowheight - ((windowheight/2) + ((windowheight/2) * Math.sin(degrees)));
            x = (windowwidth/2) + ((windowheight/2) * Math.cos(degrees));
            if(x < 0){

                x = 0;
            }
            if(x > windowwidth){

                x = windowwidth;
            }
        }else {

            y = windowheight - ((windowheight/2) + ((windowwidth/2) * Math.sin(degrees)));
            x = (windowwidth/2) + ((windowwidth/2) * Math.cos(degrees));
            if(y < 0){

                y = 0;
            }
            if(y > windowheight){

                y = windowheight;
            }
        }
        return new Point((int)x, (int)y);
    }

    private void translate(int from_x, int to_x, int from_y, int to_y) {

        TranslateAnimation animation = new TranslateAnimation(0,
                to_x - from_x, 0, to_y - from_y);
        animation.setDuration(250);
        current_x = to_x;
        current_y = to_y;
        this.setOnTouchListener(dummyTouchListener);
        startAnimation(animation);
    }

    public boolean onTouch(View view, MotionEvent event) {

        final int X = ((int) event.getRawX());
        final int Y = ((int) event.getRawY());
        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                current_x = lParams.leftMargin;
                current_y = lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                translate(X - _xDelta, current_x, Y - _yDelta, current_y);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                lParams.leftMargin = X - _xDelta;
                lParams.topMargin = Y - _yDelta;
                lParams.rightMargin = -250;
                lParams.bottomMargin = -250;
                view.setLayoutParams(lParams);
                break;
        }
        return false;
    }

    @Override
    public void onAnimationEnd(){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.leftMargin = current_x;
        layoutParams.topMargin = current_y;
        setLayoutParams(layoutParams);
        //reset correct touch listener
        setOnTouchListener(this);
    }

    private int getScaled(float difference){

        int windowmax = windowheight < windowwidth ? windowheight-_yDelta: windowwidth-_xDelta;
        double d;
        double f = ((difference / windowmax) * difference) / windowmax + 1;
        d = difference / f;
        return d > 0? (int)d: 0;
    }

    @Override
    public void onClick(View view) {
       // setBackgroundColor(getResources().getColor(R.color.wblue));
    }

    public static class DummyTouchListener implements OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //do nothing!
            return false;
        }
    }
}
