package com.lazooo.wifi.app.android.components;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lazooo.wifi.app.android.R;
import com.lazooo.wifi.app.android.WifiLazooo;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/06/14
 * Time: 14:59
 */
public class CustomAdapter extends ArrayAdapter<HomeSearchItem> {

    public CustomAdapter(Context context, int textViewResourceId,
                         HomeSearchItem [] objects) {
        super(context, textViewResourceId, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeSearchItemHolder homeSearchItemHolder;

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.home_search_item, parent, false);
            TextView text = (TextView)convertView.findViewById(R.id.text);
            TextView iconText = (TextView)convertView.findViewById(R.id.icon_text);
            homeSearchItemHolder = new HomeSearchItemHolder(text, iconText);
            convertView.setTag(homeSearchItemHolder);
        }else {

            homeSearchItemHolder = (HomeSearchItemHolder) convertView.getTag();
        }
        HomeSearchItem c = getItem(position);
        if( c != null){
            homeSearchItemHolder.getIconText().setTypeface(WifiLazooo.getApplication().getmFontelloTypeface());
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