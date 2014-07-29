package com.lazooo.wifi.app.android;/**
 * Lazooo copyright 2012
 */

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.lazooo.wifi.app.android.fragments.SearchResult;
import com.lazooo.wifi.app.android.fragments.SearchSuggestion;
import com.lazooo.wifi.app.android.utils.Logging;

import java.lang.reflect.Field;
import java.net.URL;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 26/07/14
 * Time: 23:48
 */
public class SearchActivity extends ActionBarActivity {

    private ActionBar mActionBar;
    private SearchSuggestion searchResultFragment;
    private URL url;
    private Uri data;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 11) {
            requestFeature();
        }
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
        setContentView(R.layout.news_articles);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        searchResultFragment = new SearchSuggestion();
        transaction.replace(R.id.sample_content_fragment, searchResultFragment);
        transaction.commit();
        initializeDummy();
        initializeSearch();
    }

    private void initializeSearch(){

    }

    private void initializeDummy(){

        mActionBar = getSupportActionBar();
        WifiLazooo.getApplication().setSupportActionBar(mActionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed(){

        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void requestFeature() {
        try {
            Field fieldImpl = ActionBarActivity.class.getDeclaredField("mImpl");
            fieldImpl.setAccessible(true);
            Object impl = fieldImpl.get(this);

            Class<?> cls = Class.forName("android.support.v7.app.ActionBarActivityDelegate");

            Field fieldHasActionBar = cls.getDeclaredField("mHasActionBar");
            fieldHasActionBar.setAccessible(true);
            fieldHasActionBar.setBoolean(impl, true);

        } catch (NoSuchFieldException e) {
            Log.e(Logging.LOG_ERROR, e.getLocalizedMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e(Logging.LOG_ERROR, e.getLocalizedMessage(), e);
        } catch (IllegalArgumentException e) {
            Log.e(Logging.LOG_ERROR, e.getLocalizedMessage(), e);
        } catch (ClassNotFoundException e) {
            Log.e(Logging.LOG_ERROR, e.getLocalizedMessage(), e);
        }
    }
}
