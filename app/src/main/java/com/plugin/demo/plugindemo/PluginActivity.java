package com.plugin.demo.plugindemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.plugin.demo.R;

/**
 * Created by jindingwei on 2018/11/28.
 */

public class PluginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_activity_layout);
        System.out.println("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
}
