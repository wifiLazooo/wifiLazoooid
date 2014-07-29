package com.lazooo.wifi.app.android.fragments;/**
 * Lazooo copyright 2012
 */

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lazooo.wifi.app.android.MainActivity;
import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.SearchActivity;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.animations.ResizeAnimation;
import com.lazooo.wifi.app.android.components.HeaderSlider;
import com.lazooo.wifi.app.android.data.HomeWrap;
import com.lazooo.wifi.app.android.data.NewData;
import com.lazooo.wifi.app.android.data.storage.Action;
import com.lazooo.wifi.app.android.data.storage.Tip;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/06/14
 * Time: 10:19
 */
public class Home extends TabFragment {

    private static List<HomeSearchItem> quickConnectItems = new LinkedList<HomeSearchItem>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout mainLinearLayout;
    private LinearLayout homeSearch;
    private ScrollView mainScrollView;
    private ImageView transPlaceholder;
    private float actionBarHeight;
    private float searchFormHeight;
    private HomeWrap homeWrap;
    private boolean initialized;
    private SearchDim searchDim;
    private LinearLayout linearLayoutScroll;


    int mainHeight;
    int mainWidth;

    private NewData.NewDataListener<List<Action>> lPublicActivity;
    private NewData.NewDataListener<List<Tip>> lTips;
    private NewData.NewDataListener<String> lAroundWifis;

    static {
        quickConnectItems.add(new HomeSearchItem("Around you", "N", "AY"));
        quickConnectItems.add(new HomeSearchItem("Explore the city", ">", "ETC"));
        quickConnectItems.add(new HomeSearchItem("Popular hotSpots", "G", "PH"));
        quickConnectItems.add(new HomeSearchItem("Open map", "V", "OM"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialized = true;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_header_to_replace, new HeaderSlider());
        transaction.addToBackStack(null);
        transaction.commit();
        setLoading(false);
        _initListeners();
        actionBarHeight = getResources().getDimension(R.dimen.actionbar_height);
        searchFormHeight = getResources().getDimension(R.dimen.search_form_height);
        homeWrap = WifiLazooo.getApplication().getDataWrap(HomeWrap.class);
        homeWrap.setHomeListeners(lPublicActivity, lTips, lAroundWifis);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.ptr_layout);
        mainLinearLayout = (LinearLayout) rootView.findViewById(R.id.home_main_layout);
        linearLayoutScroll = (LinearLayout) rootView.findViewById(R.id.home_quick_connect);
        homeSearch = (LinearLayout) rootView.findViewById(R.id.home_search);
        mainScrollView = (ScrollView) rootView.findViewById(R.id.main_scroll_view);
        transPlaceholder = (ImageView) rootView.findViewById(R.id.transparent_placeholder);
        _stopLoadingView();
        swipeRefreshLayout.setColorScheme(R.color.brown_bar, R.color.wred, R.color.wyellow, R.color.wgreen);
        final Home home = this;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                _startLoadingView();
                _startLoading();
                homeWrap.refresh();
            }
        });
        _initializeDimensions();
        Bundle args = getArguments();
        _createQuickConnectMenu(rootView);
        _createTipsMenu(rootView);
        _createSearchZone(rootView);
        _createActivitiesMenu(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle saved) {
        super.onActivityCreated(saved);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void _initializeDimensions() {
        ViewTreeObserver viewTreeObserver = mainLinearLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mainLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mainLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                searchDim = new SearchDim();
                searchDim.searchTopMargin = (mainLinearLayout.getWidth() * 2) / 7;
                searchDim.placeHeight = searchDim.searchTopMargin + (int) actionBarHeight;;
                searchDim.mainHeight = (mainLinearLayout.getWidth() * 5) / 7;

                mainLinearLayout.getLayoutParams().height = searchDim.mainHeight;
                //placeHolder.getLayoutParams().height = searchDim.placeHeight;
                final ResizeAnimation resizeAnimation = new ResizeAnimation(transPlaceholder, transPlaceholder.getLayoutParams().width, transPlaceholder.getLayoutParams().height, transPlaceholder.getLayoutParams().width, searchDim.placeHeight);
                new Handler().postDelayed(new Runnable() {
                            public void run() {
                                transPlaceholder.startAnimation(resizeAnimation);
                            }
                        }, 80
                );
                ((LinearLayout.LayoutParams) homeSearch.getLayoutParams()).topMargin = (int) ((searchDim.searchTopMargin / 2) - (searchFormHeight / 2));
                //awake the layout
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            }
        });
    }


    private void _initListeners() {

        lPublicActivity = new NewData.NewDataListener<List<Action>>() {
            @Override
            public void onNewDataComes(List<Action> newData) {

            }
        };
        lTips = new NewData.NewDataListener<List<Tip>>() {
            @Override
            public void onNewDataComes(List<Tip> newData) {

            }
        };
        lAroundWifis = new NewData.NewDataListener<String>() {
            @Override
            public void onNewDataComes(String newData) {

            }
        };
    }

    private void _startLoading() {

    }

    private void _startLoadingView() {

        setLoading(true);
        WifiLazooo.getApplication().getSlidingTabs().onStartLoading();
        final float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainLinearLayout.getLayoutParams();
        layoutParams.topMargin = (int) (actionBarHeight + px);
        mainLinearLayout.setLayoutParams(layoutParams);
    }

    private void _stopLoadingView() {

        setLoading(false);
        WifiLazooo.getApplication().getSlidingTabs().onStopLoading();
        final float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainLinearLayout.getLayoutParams();
        layoutParams.topMargin = (int) (actionBarHeight);
        mainLinearLayout.setLayoutParams(layoutParams);
    }

    public String getTabName() {

        return "Home";
    }

    public int getTabPosition() {

        return 0;
    }


    @Override
    public void onFragmentSelected() {
        super.onFragmentSelected();

        if (isLoading()) {
            _startLoadingView();
        } else {
            _stopLoadingView();
        }
    }

    @Override
    public void onFragmentChangedFromThis() {
        super.onFragmentChangedFromThis();
    }

    public void _createQuickConnectMenu(View rootView) {

        TextView title = (TextView) linearLayoutScroll.findViewById(R.id.home_quick_connect_title);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
        Iterator<HomeSearchItem> iter = quickConnectItems.iterator();
        while (iter.hasNext()) {

            final HomeSearchItem hsi = iter.next();
            View item = ((LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.search_item, null, false);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    quickConnectClick(view, hsi.getText());
                }
            });
            TextView itemText = (TextView) item.findViewById(R.id.text);
            TextView itemIcon = (TextView) item.findViewById(R.id.icon_text);
            itemIcon.setTypeface(WifiLazooo.getApplication().getTypefaceFontello());
            itemText.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
            itemText.setText(hsi.getText());
            itemIcon.setText(hsi.getIconText());

            if (iter.hasNext() == false) {

                item.findViewById(R.id.divider).setVisibility(View.INVISIBLE);
            }
            linearLayoutScroll.addView(item);
        }
    }

    private void quickConnectClick(View view, String id) {
        view.setBackgroundColor(getResources().getColor(R.color.brown_trasparent));
    }


    public void _createTipsMenu(View rootView) {

        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.home_tips);
        TextView title = (TextView) linearLayout.findViewById(R.id.home_tips_title);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
    }

    public void _createSearchZone(View rootView) {

        LinearLayout linear = (LinearLayout) rootView.findViewById(R.id.home_search);
        TextView title = (TextView) linear.findViewById(R.id.home_search_title);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
        transPlaceholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transPlaceholder.setBackgroundColor(getResources().getColor(R.color.semi_trans));
                onSearchClick(view, "");
            }
        });

        TextView titleIcon = (TextView) linear.findViewById(R.id.home_search_title_icon);
        titleIcon.setTypeface(WifiLazooo.getApplication().getTypefaceFontello());
    }

    private void onSearchClick(final View view, String id) {

        view.getBackground().setAlpha(128);
        // Create new fragment and transaction
        final Intent myIntent = new Intent(getActivity(), SearchActivity.class);
        if(initialized){
            getActivity().startActivity(myIntent);
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            new Handler().postDelayed(new Runnable() {
                                          public void run() {
                                              initialized = true;
                                              view.getBackground().setAlpha(0);
                                          }
                                      }, 200
            );
            initialized = false;
        }
    }



    public void _createActivitiesMenu(View rootView) {

        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.home_activities);
        TextView title = (TextView) linearLayout.findViewById(R.id.home_activities_title);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
    }

    public static class HomeSearchItem {

        private String slug;
        private String text;
        private String iconText;

        public HomeSearchItem(String text, String iconText, String slug) {
            this.text = text;
            this.slug = slug;
            this.iconText = iconText;
        }

        public String getText() {
            return text;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
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

    static class SearchDim{

        int mainHeight;
        int placeHeight;
        int searchTopMargin;
    }

}
