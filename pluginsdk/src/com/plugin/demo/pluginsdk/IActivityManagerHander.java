package com.plugin.demo.pluginsdk;

import android.content.ComponentName;
import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by jindingwei on 2018/11/28.
 */

public class IActivityManagerHander implements InvocationHandler {

    private Object mBase;

    public IActivityManagerHander(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())
                || "startService".equals(method.getName())
                || "stopService".equals(method.getName())) {
            Intent rawIntent;
            int index = 0;
            for (int i = 0;i < args.length;i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            rawIntent = (Intent) args[index];
            String activityName = rawIntent.getComponent().getClassName();
            if (PluginManager.getInstance().getPluginByActivityName(activityName) != null) {
                String pluginPackage = "com.plugin.demo";
                String name = "";
                if (activityName.endsWith("Activity")) {
                    name = ProxyActivity.class.getName();
                    if (activityName.contains("SingleTask")) {
                        name = PluginManager.getInstance().getSingleTaskActivityProxy(activityName);
                        PluginManager.getInstance().addSingleTaskActivityMap(activityName, name);
                    }
                } else if (activityName.endsWith("Service")) {
                    name = PluginManager.getInstance().getServiceProxy(activityName);
                    PluginManager.getInstance().addServiceMap(activityName, name);
                }
                Intent newIntent = new Intent();
                ComponentName componentName = new ComponentName(pluginPackage, name);
                newIntent.setComponent(componentName);
                newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT, rawIntent);
                args[index] = newIntent;
            }
        }
        return method.invoke(mBase, args);
    }
}