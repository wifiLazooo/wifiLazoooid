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
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_home, container, false);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);


    }

    @Override
    public void onResume(){
        super.onResume();

    }

    public void onStart(){
        super.onStart();

    }

    @Override
    public void onViewStateRestored(Bundle bundle){
        super.onViewStateRestored(bundle);

    }

    @Override
    public void onDestroy(){

        super.onDestroy();
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
