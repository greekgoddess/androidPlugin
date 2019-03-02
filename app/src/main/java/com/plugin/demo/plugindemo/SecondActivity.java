package com.plugin.demo.plugindemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.plugin.demo.R;

/**
 * Created by jindingwei on 2018/11/28.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_layout);
    }
}
