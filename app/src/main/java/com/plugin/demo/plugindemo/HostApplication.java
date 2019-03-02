package com.plugin.demo.plugindemo;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.plugin.demo.BuildConfig;
import com.plugin.demo.pluginsdk.PluginManager;

/**
 * Created by jindingwei on 2018/11/29.
 */

public class HostApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        PluginManager.getInstance().init(HostApplication.this);
        if (BuildConfig.BUILD_DEBUG) {

        }
    }
}
