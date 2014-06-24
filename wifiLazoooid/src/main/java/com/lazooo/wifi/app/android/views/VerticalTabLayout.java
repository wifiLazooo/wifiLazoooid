package com.lazooo.wifi.app.android.views;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.IconTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.components.SlidingTabs;
import java.util.List;

public class VerticalTabLayout extends TabLayout {

    public interface TabColorizer {

        int getIndicatorColor(int position);

        int getDividerColor(int position);

    }

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_LEFT_PADDING_DIPS = 48;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
    private static final int TAB_ICON_HEIGHT = 32;

    private int mTitleOffset;
    private TextView mCurrentTab;
    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private int mWidth;
    private int mHeight;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;
    private VerticalTabStrip mTabStrip;

    public VerticalTabLayout(Context context) {
        this(context, null);
    }

    public VerticalTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        setBackgroundResource(R.color.brown_background_trans);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mWidth = getContext().getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        mHeight = getContext().getApplicationContext().getResources().getDisplayMetrics().heightPixels;
    }


    public void addView(){

        mTabStrip = new VerticalTabStrip(getContext());
        addView(mTabStrip, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }


    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    public void setDividerColors(int... colors) {
        mTabStrip.setDividerColors(colors);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
        }
    }

    protected TextView createDefaultTabView(Context context, int color, String text) {
        TextView textView = new IconTextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/fontello.ttf");
        textView.setTypeface(myTypeface);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // If we're running on Honeycomb or newer, then we can use the Theme's
            // selectableItemBackground to ensure that the View has a pressed state
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // If we're running on ICS or newer, enable all-caps to match the Action Bar tab style
            textView.setAllCaps(true);
        }
        //todo fix it
        int barheight = (int) (74 * getResources().getDisplayMetrics().density + 0.5f);
        int height = mHeight < mWidth ? mHeight: mWidth;
        int tabHeight = (height - barheight) / mViewPager.getAdapter().getCount();
        int iconWidth = (int) (TAB_ICON_HEIGHT * getResources().getDisplayMetrics().density);
        int leftRightPadding = (int) (TAB_LEFT_PADDING_DIPS * getResources().getDisplayMetrics().density);
        //int padding = tabWidth - iconWidth / 2;
        textView.setWidth(leftRightPadding);
        textView.setTextColor(color);
        textView.setHeight(tabHeight);
        textView.setText(text);
        return textView;
    }


    public void populateTabStrip(List<SlidingTabs.TabItem> tabs) {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();

        boolean first = true;
        for (SlidingTabs.TabItem tab: tabs){

            int color = getResources().getColor(R.color.grey);
            TextView tabView = createDefaultTabView(getContext(), color, tab.getText());
            tabView.setOnClickListener(tabClickListener);
            if (first){

                color = getResources().getColor(R.color.brown_bar);
                tabView.setTextColor(color);

                mCurrentTab = tabView;
            }
            first = false;
            mTabStrip.addView(tabView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollY = selectedChild.getTop() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollY -= mTitleOffset;
            }
            scrollTo(0, targetScrollY);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }
            mCurrentTab.setTextColor(getResources().getColor(R.color.grey));
            mCurrentTab.setShadowLayer(0, 0, 0, R.color.darker_grey);


            TextView tab = (TextView) mTabStrip.getTouchables().get(position);
            tab.setTextColor(getResources().getColor(R.color.brown_bar));
            mCurrentTab = tab;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }
    }

    class TabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }
}
