package com.plugin.demo.pluginsdk;


import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * Created by jindingwei on 2018/11/28.
 */

public class ICallBackHander implements Handler.Callback{

    private Handler mBase;

    public ICallBackHander(Handler base) {
        mBase = base;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == 100) {
            replaceIntent(msg);
        } else if (msg.what == 112) {
            replaceIntent(msg);
        } else if (msg.what == 114) {
            handleCreateService(msg);
        }
        mBase.handleMessage(msg);
        return true;
    }

    private void replaceIntent(Message msg) {
        Object obj = msg.obj;
        try {
            Field raw = obj.getClass().getDeclaredField("intent");
            raw.setAccessible(true);
            Intent intent = (Intent) raw.get(obj);
            Intent pluginIntent = intent.getParcelableExtra(AMSHookHelper.EXTRA_TARGET_INTENT);
            if (pluginIntent != null) {
                intent.setComponent(pluginIntent.getComponent());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void handleCreateService(Message msg) {
        Object obj = msg.obj;
        try {
            Field field = obj.getClass().getDeclaredField("info");
            field.setAccessible(true);
            ServiceInfo info = (ServiceInfo) field.get(obj);
            for (String str : PluginManager.getInstance().getServiceMap().keySet()) {
                String proxyName = PluginManager.getInstance().getServiceProxy(str);
                if (proxyName.equals(info.name)) {
                    info.name = str;
                    break;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
