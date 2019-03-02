package com.plugin.demo.pluginsdk;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * Created by jindingwei on 2018/11/30.
 */

public class PluginManager {

    private static PluginManager mInstance = new PluginManager();
    private Application mApplication;
    private List<Plugin> mPluginList = new LinkedList<>();
    private DemoClassLoader mClassLoader;
    private ClassLoader mOriginClassLoader;
    private Map<String, String> mSingleTaskActivityMapList = new HashMap<>();
    private Map<String, String> mServiceMapList = new HashMap<>();

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        return mInstance;
    }

    public void init(Application context) {
        mApplication = context;
        mOriginClassLoader = mApplication.getClassLoader();
        hook();
        initClassLoader();
        installPlugin("app-debug.apk");
        installPlugin("second-plugin.apk");
    }

    private void installPlugin(String pluginName) {
        String dirPath = mApplication.getFilesDir().getPath();
        String dexPath = dirPath + File.separator + "plugin" + File.separator + pluginName;
        File apkFile = new File(dexPath);
        if (!apkFile.exists()) {
            Toast.makeText(mApplication, "找不到插件" + pluginName, Toast.LENGTH_LONG).show();
            return;
        }
        addPluginClassLoader(pluginName);
        String pluginPath = mApplication.getFilesDir().getPath() + File.separator + "plugin" + File.separator + pluginName;
        Plugin plugin = new Plugin(pluginPath, mApplication);
        List<String> activityList = new LinkedList<>();
        if ("app-debug.apk".equals(pluginName)) {
            activityList.add("com.plugin.demo.pluginapk.PluginApkActivity");
            activityList.add("com.plugin.demo.pluginapk.SecondPluginActivity");
            activityList.add("com.plugin.demo.pluginapk.PluginSingleTaskActivity");
            activityList.add("com.plugin.demo.pluginapk.PluginService");
        } else if ("second-plugin.apk".equals(pluginName)) {
            activityList.add("com.plugin.demo.secondplugin.MainActivity");
        }
        plugin.addActivity(activityList);
        mPluginList.add(plugin);
    }

    public Plugin getPluginByActivityName(String name) {
        for (Plugin plugin : mPluginList) {
            if (plugin.isContainActivityName(name)) {
                return plugin;
            }
        }
        return null;
    }

    public void addSingleTaskActivityMap(String activityName, String proxyName) {
        mSingleTaskActivityMapList.put(activityName, proxyName);
    }

    public void removeSingleTaskActivityMap(String activityName) {
        mSingleTaskActivityMapList.remove(activityName);
    }

    public String getSingleTaskActivityProxy(String activityName) {
        String proxyName = mSingleTaskActivityMapList.get(activityName);
        if (proxyName == null) {
            String proxyStr = "com.plugin.demo.pluginsdk.ProxySingleTaskActivity";
            for (int i = 1;i < 3;i++) {
                if (!mSingleTaskActivityMapList.containsValue(proxyStr + i)) {
                    proxyName = proxyStr + 1;
                    return proxyName;
                }
            }
        }
        return proxyName;
    }

    public Map<String, String> getServiceMap() {
        return mServiceMapList;
    }

    public void addServiceMap(String serviceName, String proxyName) {
        mServiceMapList.put(serviceName, proxyName);
    }

    public void removeServiceMap(String serviceName) {
        mServiceMapList.remove(serviceName);
    }

    public String getServiceProxy(String serviceName) {
        String proxyName = mServiceMapList.get(serviceName);
        if (proxyName == null) {
            String proxyStr = "com.plugin.demo.pluginsdk.ProxyService";
            for (int i = 1;i < 3;i++) {
                if (!mServiceMapList.containsValue(proxyStr + i)) {
                    proxyName = proxyStr + 1;
                    return proxyName;
                }
            }
        }
        return proxyName;
    }

    private void addPluginClassLoader(String pluginName) {
        String dirPath = mApplication.getFilesDir().getPath();
        String dexPath = dirPath + File.separator + "plugin" + File.separator + pluginName;
        File dexOutputDir = mApplication.getDir("dex", Context.MODE_PRIVATE);
        String optimizedDir = dexOutputDir.getAbsolutePath();
        DexClassLoader pluginClassLoader = new DexClassLoader(dexPath, optimizedDir, null, mOriginClassLoader);
        mClassLoader.addPluginClassLoader(pluginClassLoader);
    }

    public static void hook() {
        AMSHookHelper.hookAMN();
        AMSHookHelper.hookActivityThread();
    }

    private void initClassLoader() {
        mClassLoader = new DemoClassLoader(mApplication.getPackageCodePath(), mOriginClassLoader);
        Class contextClass = mApplication.getBaseContext().getClass();
        try {
            Field packageInfo = contextClass.getDeclaredField("mPackageInfo");
            packageInfo.setAccessible(true);
            Object pak = packageInfo.get(mApplication.getBaseContext());
            Class apkClass = pak.getClass();
            Field classLoaderField = apkClass.getDeclaredField("mClassLoader");
            classLoaderField.setAccessible(true);
            classLoaderField.set(pak, mClassLoader);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
