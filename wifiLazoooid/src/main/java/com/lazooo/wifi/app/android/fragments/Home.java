package com.lazooo.wifi.app.android.fragments;/**
 * Lazooo copyright 2012
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.components.CustomAdapter;
import com.lazooo.wifi.app.android.components.HomeSearchItem;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/06/14
 * Time: 10:19
 */
public class Home extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        Toast.makeText(getActivity(), "home", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(
                R.layout.fragment_home, container, false);
        Bundle args = getArguments();
        ListView listView = (ListView) rootView.findViewById(R.id.home_search_list);
        HomeSearchItem[] items = { new HomeSearchItem("Around you", "N"), new HomeSearchItem("Search", "S"), new HomeSearchItem("Open the map", "V"), new HomeSearchItem("Explore Perugia", ">")};
        listView.setAdapter(new CustomAdapter(getActivity(), R.layout.home_search_item, items));
        return rootView;
    }
}
