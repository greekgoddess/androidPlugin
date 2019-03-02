package com.plugin.demo.plugindemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by jindingwei on 2018/12/5.
 */

public class TestService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("TestService------onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("TestService------onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("TestService------onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("TestService------onDestroy");
    }
}
