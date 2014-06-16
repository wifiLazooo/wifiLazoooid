package com.lazooo.wifi.app.android.fragments;/**
 * Lazooo copyright 2012
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.components.CustomAdapter;
import com.lazooo.wifi.app.android.components.HomeSearchItem;
import com.lazooo.wifi.app.android.views.AnotherView;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/06/14
 * Time: 10:19
 */
public class Home extends Fragment {

    private AnotherView anotherView;
    private LinearLayout llMain;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(
                R.layout.fragment_home, container, false);
        Bundle args = getArguments();
        final LinearLayout llMainHolder = (LinearLayout) rootView.findViewById(R.id.llMainHolder);
        final ImageView iv = (ImageView) rootView.findViewById(R.id.iv);
        final TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        llMain = (LinearLayout) rootView.findViewById(R.id.llMain);
        final ListView listView = (ListView) rootView.findViewById(R.id.home_search_list);
        HomeSearchItem[] items = { new HomeSearchItem("Around you", "N"), new HomeSearchItem("Search", "S"), new HomeSearchItem("Open the map", "V"), new HomeSearchItem("Explore Perugia", ">")};
        listView.setAdapter(new CustomAdapter(getActivity(), R.layout.home_search_item, items));
        anotherView = (AnotherView) rootView.findViewById(R.id.anotherView);
        listView.post(new Runnable() {

            @Override
            public void run() {

                // Adjusts llMain's height to match ListView's height
                setListViewHeight(listView, llMain);

                // LayoutParams to set the top margin of LinearLayout holding
                // the content.
                // topMargin = iv.getHeight() - tvTitle.getHeight()
                LinearLayout.LayoutParams p =
                        (LinearLayout.LayoutParams)llMainHolder.getLayoutParams();
                p.topMargin = iv.getHeight() - tvTitle.getHeight();
                llMainHolder.setLayoutParams(p);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        Toast.makeText(getActivity(), "onAttach", Toast.LENGTH_SHORT).show();
        super.onAttach(activity);

    }

    @Override
    public void onResume(){

        Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    public void onStart(){

        Toast.makeText(getActivity(), "onStart", Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    public void onViewStateRestored(Bundle bundle){

        Toast.makeText(getActivity(), "onViewStateRestored", Toast.LENGTH_SHORT).show();
        super.onViewStateRestored(bundle);
    }

    // Sets the ListView holder's height
    public void setListViewHeight(ListView listView, LinearLayout llMain) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {

            return;
        }

        int totalHeight = 0;
        int firstHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(
                listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {

            if (i == 0) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                firstHeight = listItem.getMeasuredHeight();
            }
            totalHeight += firstHeight;
        }

        LinearLayout.LayoutParams params =
                (LinearLayout.LayoutParams)llMain.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        llMain.setLayoutParams(params);
        anotherView.requestLayout();
    }
}
