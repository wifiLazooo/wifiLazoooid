package com.lazooo.wifi.app.android.data;/**
 * Lazooo copyright 2012
 */

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 14:04
 */
public class NewData<V> {

    public NewDataListener<V> newDataListener;
    public boolean is_loading;

    public interface NewDataListener<V>{

        public void onNewDataComes(V newData);
    }
}
