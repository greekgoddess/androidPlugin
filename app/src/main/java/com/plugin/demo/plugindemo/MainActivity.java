package com.plugin.demo.plugindemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.plugin.demo.R;


public class MainActivity extends AppCompatActivity {
    private Button openActivity;
    private Button openService;
    private Button closeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openActivity = findViewById(R.id.open_activity);
        openService = findViewById(R.id.open_service);
        closeService = findViewById(R.id.close_service);
    }

    public void onClick(View view) {
        if (view == openActivity) {
            Intent intent = new Intent();
            String pak = "com.plugin.demo.pluginapk";
            String clazzStr = "com.plugin.demo.pluginapk.PluginSingleTaskActivity";
//                String pak = "com.plugin.demo.plugindemo";
//                String clazzStr = "com.plugin.demo.plugindemo.PluginActivity";
            ComponentName componentName = new ComponentName(pak, clazzStr);
            intent.setComponent(componentName);
            startActivity(intent);
        } else if (view == openService) {
            Intent intent = new Intent();
            String pak = "com.plugin.demo.pluginapk";
            String clazzStr = "com.plugin.demo.pluginapk.PluginService";
//                String pak = "com.plugin.demo";
//                String clazzStr = "com.plugin.demo.plugindemo.TestService";
            ComponentName componentName = new ComponentName(pak, clazzStr);
            intent.setComponent(componentName);
            startService(intent);
        } else if (view == closeService) {
            Intent intent = new Intent();
            String pak = "com.plugin.demo.pluginapk";
            String clazzStr = "com.plugin.demo.pluginapk.PluginService";
//                String pak = "com.plugin.demo";
//                String clazzStr = "com.plugin.demo.plugindemo.TestService";
            ComponentName componentName = new ComponentName(pak, clazzStr);
            intent.setComponent(componentName);
            stopService(intent);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
