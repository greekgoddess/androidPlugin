package com.plugin.demo.pluginsdk;

import java.util.LinkedList;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by jindingwei on 2018/11/29.
 */

public class DemoClassLoader extends PathClassLoader {

    private List<DexClassLoader> mPluginClassLoader = null;

    public DemoClassLoader(String dexPath, ClassLoader parent) {
        super(dexPath, parent);
        init();
    }

    public DemoClassLoader(String dexPath, String librarySearchPath, ClassLoader parent) {
        super(dexPath, librarySearchPath, parent);
        init();
    }

    private void init() {
        mPluginClassLoader = new LinkedList<>();
    }

    public void addPluginClassLoader(DexClassLoader dexClassLoader) {
        mPluginClassLoader.add(dexClassLoader);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clazz = null;
        try {
            clazz = getParent().loadClass(name);
        } catch (ClassNotFoundException e) {

        }
        if (clazz != null) {
            return clazz;
        }

        for (ClassLoader classLoader : mPluginClassLoader) {
            try {
                clazz = classLoader.loadClass(name);
                if (clazz != null) {
                    return clazz;
                }
            } catch (ClassNotFoundException e) {

            }
        }
        throw new ClassNotFoundException("NotFound " + name);
    }
}
