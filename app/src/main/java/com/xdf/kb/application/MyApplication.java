package com.xdf.kb.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        x.Ext.init(this);
        // 开启调试
        x.Ext.setDebug(BuildConfig.DEBUG);
        super.onCreate();
    }
}
