package com.plugin.demo.pluginsdk;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jindingwei on 2018/11/30.
 */

public class Plugin {
    private List<String> mActivityNameList = new LinkedList<>();
    private AssetManager mAssetManager;
    private Resources mResources;
    private Application mApplication;

    public Plugin(String pluginPath, Application application) {
        mApplication = application;
        loadResources(pluginPath);
    }

    public void addActivity(List<String> list) {
        mActivityNameList.addAll(list);
    }

    public boolean isContainActivityName(String name) {
        return mActivityNameList.contains(name);
    }

    public AssetManager getAssetManager() {
        return mAssetManager;
    }

    public Resources getResources() {
        return mResources;
    }

    private void loadResources(String path) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            mAssetManager = assetManager;
            mResources = new Resources(mAssetManager, mApplication.getResources().getDisplayMetrics(), mApplication.getResources().getConfiguration());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
