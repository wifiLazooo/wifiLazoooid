package com.lazooo.wifi.app.android.views;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 01/07/14
 * Time: 21:24
 */
public class FloatingButton extends RelativeLayout implements View.OnTouchListener, View.OnClickListener{

    static final String WIFI_ICON = ";";
    static final int MARGIN = 10;
    static final float MOVE_SCALE_FACTOR = 3;

    int windowwidth;
    int windowheight;
    private int lazy_x;
    private int lazy_y;
    private int current_x;
    private int current_y;
    int currentWidth;
    int currentHeight;
    private int hide_x;
    private int hide_y;
    private int mMargin;
    private boolean _measured;
    private boolean _translated;
    private boolean _moving;
    private boolean _pulling;
    private TextView internalView;
    private float _rotation_degrees;

    private String currentIcon;
    private boolean is_hide;
    private boolean is_tracking;
    private boolean is_start_tracking;
    private boolean is_stop_tracking;

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
        _measured = false;
        is_hide = true;
        is_tracking = false;
        is_start_tracking =false;
        is_stop_tracking = false;
        _translated = false;
        dummyTouchListener = new DummyTouchListener();
        mMargin = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN, getResources().getDisplayMetrics());
        this.context = context;
        setOnTouchListener(this);
        setOnClickListener(this);
        WifiLazooo.getApplication().setFloatingButton(this);
    }


    @Override
    protected void onAttachedToWindow (){
        //some init code
        internalView = (TextView) findViewById(R.id.floating_button_text);
        internalView.setTypeface(WifiLazooo.getApplication().getTypefaceFontello());
        currentIcon = WIFI_ICON;
        internalView.setText(currentIcon);
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                if (!_measured){
                    //initial
                    _measured = true;
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }else {
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) getLayoutParams();
                    View parent =(View) getParent();
                    windowwidth = parent.getWidth();
                    windowheight = parent.getHeight();
                    currentWidth = getWidth();
                    currentHeight = getHeight();
                    current_x = lazy_x = windowwidth - mMargin - currentWidth;
                    current_y = lazy_y = windowheight - mMargin - currentHeight;
                    hide_x = windowwidth - mMargin - currentWidth;
                    hide_y = windowheight + currentHeight;
                    lParams.leftMargin = lazy_x;
                    lParams.topMargin = lazy_y;
                    lParams.bottomMargin = - 250;
                    lParams.rightMargin = - 250;
                    setLayoutParams(lParams);
                }
                }
            });
        }
    }

    public void showButton(){

        if (is_hide){

            is_hide = false;
            this.translate(hide_x, lazy_y, hide_y, lazy_y);
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

    private void startTracking(int degrees){

        is_tracking = true;
    }

    private void stopTracking(int degrees){

        is_tracking = false;
    }

    private void tryStartTracking(){

        is_start_tracking = true;
        is_stop_tracking = false;
        _moving = false;
    }

    private void tryStopTracking(){

        is_stop_tracking = false;
        is_start_tracking = false;
        _moving = false;

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

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new OvershootInterpolator());
        animationSet.setDuration(400);
        TranslateAnimation animation = new TranslateAnimation(0,
                to_x - from_x, 0, to_y - from_y);
        animation.setDuration(250);
        animationSet.addAnimation(animation);
        current_x = to_x;
        current_y = to_y;
        _translated = true;
        this.setOnTouchListener(dummyTouchListener);
        startAnimation(animationSet);
    }

    public boolean onTouch(View view, MotionEvent event) {

        final int X = ((int) event.getRawX());
        final int Y = ((int) event.getRawY());
        Point p;
        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                current_x = lParams.leftMargin;
                current_y = lParams.topMargin;
                _moving = true;
                break;
            case MotionEvent.ACTION_UP:
                if(_pulling){
                    RotateAnimation rotateAnimation = new RotateAnimation(_rotation_degrees, 0, currentWidth/2, currentHeight/2);
                    rotateAnimation.setDuration(200);
                    rotateAnimation.setFillAfter(true);
                    internalView.startAnimation(rotateAnimation);
                    _pulling = false;
                    _rotation_degrees = -1000;
                }
                if(_moving){
                    p = processMove(X-_xDelta, Y-_yDelta);
                    if(p != null){
                        internalView.setText(currentIcon);
                        translate(p.x, current_x, p.y, current_y);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if(_moving) {

                    p = processMove(X-_xDelta, Y-_yDelta);
                    if(p != null) {
                        lParams.leftMargin = p.x;
                        lParams.topMargin = p.y;
                        lParams.bottomMargin = -250;
                        lParams.rightMargin = -250;
                        view.setLayoutParams(lParams);
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void onAnimationEnd(){
        if(_translated){

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
            layoutParams.leftMargin = current_x;
            layoutParams.topMargin = current_y;
            setLayoutParams(layoutParams);
            setOnTouchListener(this);
            _translated = false;
        }
    }

    private Point processMove(float diff_x, float diff_y){

        int windowmin = windowheight < windowwidth ? windowheight: windowwidth;
        float move_x = current_x - diff_x;
        float move_y = current_y - diff_y;
        double fx;
        double fy;
        fx = (MOVE_SCALE_FACTOR * Math.abs(move_x)) / windowmin + 1;
        fy = (MOVE_SCALE_FACTOR * Math.abs(move_y)) / windowmin + 1;
        if(fx > fy)
            fy = fx;
        move_y = (int) (move_y / fy);
        move_x = (int) (move_x / fy);
        int x = windowwidth-currentWidth;
        if(current_x-move_x < x)
            x =(int) (current_x-move_x);
        int y = windowheight-currentHeight;
        if(current_y-move_y < y)
            y =(int) (current_y-move_y);

        if(dragging(fy, x, y))
            return null;

        return new Point(x, y);
    }

    private boolean dragging(double scale, float x, float y){

        boolean ret = false;
        if(scale > 3.0){
            setOnTouchListener(dummyTouchListener);
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setInterpolator(new OvershootInterpolator(3));
            animationSet.setDuration(400);
            _translated = true;
            if(!is_start_tracking && !is_stop_tracking) {
                //start tracking
                tryStartTracking();

                current_x = (windowwidth / 2)-(currentWidth/2);
                current_y = lazy_y;
                TranslateAnimation animation = new TranslateAnimation(0, current_x - x, 0, current_y - y);
                animation.setDuration(250);
                animationSet.addAnimation(animation);
                startAnimation(animationSet);
                internalView.setText(currentIcon);
                ret =  false;

            }else if(!is_stop_tracking){
                //stop tracking
                tryStopTracking();

                TranslateAnimation animation = new TranslateAnimation(0,  lazy_x-x, 0,  lazy_y-y);
                current_x = lazy_x;
                current_y = lazy_y;
                animation.setDuration(250);
                animationSet.addAnimation(animation);
                startAnimation(animationSet);
                internalView.setText(currentIcon);
                ret = false;
            }
            RotateAnimation rotateAnimation = new RotateAnimation(_rotation_degrees, 0, currentWidth/2, currentHeight/2);
            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            internalView.startAnimation(rotateAnimation);
            _pulling = false;
            _rotation_degrees = -1000;

        }else if(scale > 1.0 && _pulling == false) {

            if(_rotation_degrees == -1000){
                _rotation_degrees = 0;
            }else {

                _pulling = true;
                int f = windowwidth / 2 - (current_x+currentWidth/2);
                if(f == 0){
                    f = 1;
                }
                _rotation_degrees = (float) Math.abs(Math.toDegrees(Math.atan((windowheight / 2 - current_y) / f)))-90;
                internalView.setText("B");
                RotateAnimation rotateAnimation = new RotateAnimation(0, _rotation_degrees, currentWidth / 2, currentHeight / 2);
                rotateAnimation.setDuration(200);
                rotateAnimation.setFillAfter(true);
                internalView.startAnimation(rotateAnimation);
            }
        }
        return ret;
    }

    @Override
    public void onClick(View view) {
    }

    public static class DummyTouchListener implements OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //do nothing!
            return false;
        }
    }
}
