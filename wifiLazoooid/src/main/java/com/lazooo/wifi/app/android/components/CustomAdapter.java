package com.lazooo.wifi.app.android.components;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;
import com.lazooo.wifi.app.android.fragments.Home;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/06/14
 * Time: 14:59
 */
public class CustomAdapter extends ArrayAdapter<Home.HomeSearchItem> {

    public CustomAdapter(Context context, int textViewResourceId,
                         Home.HomeSearchItem[] objects) {
        super(context, textViewResourceId, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeSearchItemHolder homeSearchItemHolder;

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_home, parent, false);
            TextView text = (TextView)convertView.findViewById(R.id.home);
            TextView iconText = (TextView)convertView.findViewById(R.id.home_quick_connect_title);
            homeSearchItemHolder = new HomeSearchItemHolder(text, iconText);
            convertView.setTag(homeSearchItemHolder);
        }else {

            homeSearchItemHolder = (HomeSearchItemHolder) convertView.getTag();
        }
        Home.HomeSearchItem c = getItem(position);
        if( c != null){

            homeSearchItemHolder.getIconText().setTypeface(WifiLazooo.getApplication().getTypefaceFontello());
            homeSearchItemHolder.getText().setTypeface(WifiLazooo.getApplication().getTypefaceBAriol());
            homeSearchItemHolder.getText().setText(c.getText());
            homeSearchItemHolder.getIconText().setText(c.getIconText());
        }

        return convertView;
    }

    private static class HomeSearchItemHolder{

        private TextView text;
        private TextView iconText;

        private HomeSearchItemHolder(TextView text, TextView iconText) {
            this.text = text;
            this.iconText = iconText;
        }

        public TextView getText() {
            return text;
        }

        public void setText(TextView text) {
            this.text = text;
        }

        public TextView getIconText() {
            return iconText;
        }

        public void setIconText(TextView iconText) {
            this.iconText = iconText;
        }
    }

}