package com.lazooo.wifi.app.android.fragments;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.data.storage.Wifi;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 28/07/14
 * Time: 07:27
 */
public class SearchSuggestion extends Fragment {
    private static List<SuggestItem> quickConnectItems = new LinkedList<SuggestItem>();

    static {
        quickConnectItems.add(new SuggestItem("Around you", "N", "PRM"));
        quickConnectItems.add(new SuggestItem("Explore your city", ">", "PRM"));
        quickConnectItems.add(new SuggestItem("Popular here", "G", "PRM"));
        quickConnectItems.add(new SuggestItem("Stay comfort", "\u0021", "SML"));
        quickConnectItems.add(new SuggestItem("With a workspace", "\uE806", "SML"));
        quickConnectItems.add(new SuggestItem("Connect & eat", "\uE805", "SML"));
        quickConnectItems.add(new SuggestItem("Stay a park", "\uE808", "SML"));
        quickConnectItems.add(new SuggestItem("Open the map", "V", "PRM"));
        quickConnectItems.add(new SuggestItem("Near a POI", "\uE807", "SML"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_search_suggestions, container, false);
        _createQuickConnectMenu(rootView);
        EditText searchText = new EditText(getActivity());
        searchText.setMaxLines(1);
        searchText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        searchText.setHint("Search for a place, name or ssid.");
        searchText.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        searchText.setHintTextColor(getResources().getColor(R.color.brown_background_trans));
        searchText.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
        searchText.setTextColor(getResources().getColor(R.color.brown_background));
        searchText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WifiLazooo.getApplication().getActionBar().setCustomView(searchText);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
    }

    public void _createQuickConnectMenu(View rootView) {

        LinearLayout linearLayoutScroll = (LinearLayout) rootView.findViewById(R.id.search_tips);
        LinearLayout linearLayoutScrollFine = (LinearLayout) rootView.findViewById(R.id.search_tips_fine);
        TextView title = (TextView) rootView.findViewById(R.id.search_tips_title);
        TextView titleFine = (TextView) rootView.findViewById(R.id.search_tips_title_fine);
        title.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
        titleFine.setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
        Iterator<SuggestItem> iter = quickConnectItems.iterator();
        while (iter.hasNext()) {

            final SuggestItem hsi = iter.next();
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
            if(hsi.getSlug().equals("SML")) {
                itemIcon.setTextColor(getResources().getColor(R.color.wblued));
                itemIcon.setTextSize(24);
                linearLayoutScrollFine.addView(item);
            }else {
                itemIcon.setTextColor(getResources().getColor(R.color.wred));
                linearLayoutScroll.addView(item);
            }
        }
    }


    private void quickConnectClick(View view, String id) {
        view.setBackgroundColor(getResources().getColor(R.color.brown_trasparent));
    }


    public static class SuggestItem {

        private String slug;
        private String text;
        private String iconText;

        public SuggestItem(String text, String iconText, String slug) {
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
