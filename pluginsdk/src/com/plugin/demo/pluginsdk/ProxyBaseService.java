package com.plugin.demo.pluginsdk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by jindingwei on 2018/12/5.
 */

public class ProxyBaseService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PluginManager.getInstance().removeServiceMap(ProxyBaseService.this.getClass().getName());
    }
}
