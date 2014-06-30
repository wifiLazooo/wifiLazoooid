package com.lazooo.wifi.app.android.fragments;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.components.HeaderSlider;

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
public class Home extends Fragment implements UpdatableFragment {
    private static List<HomeSearchItem> quickConnectItems = new LinkedList<HomeSearchItem>();
    static {
        quickConnectItems.add(new HomeSearchItem("Around you", "N"));
        quickConnectItems.add(new HomeSearchItem("Explore the city", ">"));
        quickConnectItems.add(new HomeSearchItem("Popular hotSpots", "G"));
        quickConnectItems.add(new HomeSearchItem("Open map", "V"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_header_to_replace, new HeaderSlider());
        transaction.addToBackStack(null);
        transaction.commit();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // The last two arguments ensure LayoutParams are inflated
        // prshow();
        View rootView = inflater.inflate(
                R.layout.fragment_home, container, false);
        Bundle args = getArguments();
        onCreateQuickConnectMenu(rootView);
        onCreateTipsMenu(rootView);
        onCreateSearchMenu(rootView);
        onCreateActivitiesMenu(rootView);
        return rootView;
    }

    public void onCreateQuickConnectMenu(View rootView){

        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.home_quick_connect);
        TextView title = (TextView)linearLayout.findViewById(R.id.home_quick_connect_title);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
        Iterator<HomeSearchItem> iter = quickConnectItems.iterator();
        while (iter.hasNext()){

            final HomeSearchItem hsi = iter.next();
            View item =  ((LayoutInflater)getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.home_search_item, null, false);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    quickConnectClick(view, hsi.getText());
                }
            });
            TextView itemText = (TextView)item.findViewById(R.id.text);
            TextView itemIcon = (TextView)item.findViewById(R.id.icon_text);
            itemIcon.setTypeface(WifiLazooo.getApplication().getTypefaceFontello());
            itemText.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
            itemText.setText(hsi.getText());
            itemIcon.setText(hsi.getIconText());
            if(iter.hasNext() == false){

                item.findViewById(R.id.divider).setVisibility(View.INVISIBLE);
            }
            linearLayout.addView(item);
        }
    }

    private void quickConnectClick(View view, String id){
        view.setBackgroundColor(getResources().getColor(R.color.brown_trasparent));
    }


    public void onCreateTipsMenu(View rootView) {

        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.home_tips);
        TextView title = (TextView) linearLayout.findViewById(R.id.home_tips_title);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
    }

    public void onCreateSearchMenu(View rootView) {

        LinearLayout linear = (LinearLayout) rootView.findViewById(R.id.home_search);
        TextView title = (TextView) linear.findViewById(R.id.home_search_title);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchClick(view, "");
            }
        });
        TextView titleIcon = (TextView) linear.findViewById(R.id.home_search_title_icon);
        titleIcon.setTypeface(WifiLazooo.getApplication().getTypefaceFontello());
    }

    private void searchClick(View view, String id){

        view.getBackground().setAlpha(128);
        // Create new fragment and transaction
        Fragment newFragment = new Info();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.sample_content_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void onCreateActivitiesMenu(View rootView) {

        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.home_activities);
        TextView title = (TextView) linearLayout.findViewById(R.id.home_activities_title);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
    }

    @Override
    public void update() {

    }

    public static class HomeSearchItem {

        private String text;
        private String iconText;

        public HomeSearchItem(String text, String iconText) {
            this.text = text;
            this.iconText = iconText;
        }

        public String getText() {
            return text;
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

}
