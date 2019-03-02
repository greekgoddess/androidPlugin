package com.plugin.demo.pluginsdk;

import android.os.Handler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by jindingwei on 2018/11/28.
 */

public class AMSHookHelper {

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookAMN() {
        try {
            Class activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            Class singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            Object rawActivityManager = mInstanceField.get(gDefault);

            Class iActivityManagerClass = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[] {iActivityManagerClass}, new IActivityManagerHander(rawActivityManager));
            mInstanceField.set(gDefault, proxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void hookActivityThread() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityField.setAccessible(true);
            Object sCurrentActivityThread = currentActivityField.get(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(sCurrentActivityThread);

            Field callbackField = Handler.class.getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            callbackField.set(mH, new ICallBackHander(mH));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
