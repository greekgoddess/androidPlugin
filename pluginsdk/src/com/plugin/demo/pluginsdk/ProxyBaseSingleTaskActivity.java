package com.plugin.demo.pluginsdk;

/**
 * Created by jindingwei on 2018/12/3.
 */

public class ProxyBaseSingleTaskActivity extends ProxyBaseActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PluginManager.getInstance().removeSingleTaskActivityMap(ProxyBaseSingleTaskActivity.this.getClass().getName());
    }
}
