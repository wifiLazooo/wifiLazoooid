/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lazooo.wifi.app.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.lazooo.wifi.app.android.components.SlidingTabs;
import com.lazooo.wifi.app.android.fragments.Home;
import com.lazooo.wifi.app.android.utils.Logging;
import com.lazooo.wifi.app.android.views.MainPageLayout;
import com.newrelic.agent.android.NewRelic;

import java.lang.reflect.Field;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class MainActivity extends ActionBarActivity {

    private ActionBar mActionBar;
    private MainPageLayout mMainPageLayout;
    private SlidingTabs slidingTabs;

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
        slidingTabs = new SlidingTabs();
        transaction.replace(R.id.sample_content_fragment, slidingTabs);
        transaction.commit();

        initializeDummy();

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

    public SlidingTabs getSlidingTabs(){

        return slidingTabs;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d(Logging.LOG_DEBUG, "onKeyDown Called");
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {

        Log.d(Logging.LOG_DEBUG, "onBackPressed Called");
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {

            fm.popBackStack();
        }else if(slidingTabs.getCurrentFragment().getTabPosition() != 0){

            slidingTabs.goToTab(0);
        }else {

            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initializeDummy(){

        mActionBar = getSupportActionBar();
        WifiLazooo.getApplication().setSupportActionBar(mActionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mMainPageLayout =(MainPageLayout) findViewById(R.id.sample_main_layout);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        new MenuInflater(getApplication()).inflate(R.menu.main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            //case R.id.action_search:
             //   return true;
            //case R.id.action_settings:
              //  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}