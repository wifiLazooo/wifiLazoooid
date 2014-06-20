package com.lazooo.wifi.app.android.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.IconTextView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.components.SlidingTabs;
import java.util.List;

public class HorizontalTabLayout extends TabLayout {

    public interface TabColorizer {

        /**
         * @return return the color of the indicator used when {@code position} is selected.
         */
        int getIndicatorColor(int position);

        /**
         * @return return the color of the divider drawn to the right of {@code position}.
         */
        int getDividerColor(int position);

    }

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_UP_PADDING_DIPS = 48;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
    private static final int TAB_ICON_WIDTH = 32;

    private int mTitleOffset;
    private TextView mCurrentTab;
    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private int mWidth;
    private int mHeight;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;
    private HoriziontalTabStrip mTabStrip;

    public HorizontalTabLayout(Context context) {
        this(context, null);
    }

    public HorizontalTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        setBackgroundResource(R.color.brown_background);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mWidth = getContext().getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        mHeight = getContext().getApplicationContext().getResources().getDisplayMetrics().heightPixels;
    }

    public void addView(){

        mTabStrip = new HoriziontalTabStrip(getContext());
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TextView createDefaultTabView(Context context, int color, String text) {
        TextView textView = new IconTextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/fontello.ttf");
        textView.setTypeface(myTypeface);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // If we're running on ICS or newer, enable all-caps to match the Action Bar tab style
            textView.setAllCaps(true);
        }
        int tabWidth = mWidth / mViewPager.getAdapter().getCount();
        int iconWidth = (int) (TAB_ICON_WIDTH * getResources().getDisplayMetrics().density);
        int upBottomPadding = (int) (TAB_UP_PADDING_DIPS * getResources().getDisplayMetrics().density);
        int padding = tabWidth - iconWidth / 2;
        textView.setWidth(tabWidth);
        textView.setTextColor(color);
        textView.setHeight(upBottomPadding);
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
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= mTitleOffset;
            }

            scrollTo(targetScrollX, 0);
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
