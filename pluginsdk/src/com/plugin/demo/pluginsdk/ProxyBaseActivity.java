package com.plugin.demo.pluginsdk;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by jindingwei on 2018/11/30.
 */

public class ProxyBaseActivity extends Activity {
    private Plugin mPlugin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPlugin = PluginManager.getInstance().getPluginByActivityName(ProxyBaseActivity.this.getClass().getName());
        if (mPlugin == null) {
            System.out.println("mPlugin = null-------------");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public Resources getResources() {
        if (mPlugin == null) {
            return super.getResources();
        }
        Resources resources = mPlugin.getResources();
        return resources != null ? resources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        if (mPlugin == null) {
            return super.getAssets();
        }
        AssetManager assetManager = mPlugin.getAssetManager();
        return assetManager != null ? assetManager : super.getAssets();
    }
}
