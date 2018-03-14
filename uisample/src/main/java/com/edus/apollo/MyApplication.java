package com.edus.apollo;

import com.edus.apollo.common.biz.navigatorimpl.NavigatorImpl;

import android.app.Application;

/**
 * Description.
 *
 * @author panda
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initNavigator();
    }

    private void initNavigator() {
        NavigatorImpl.addNavigateProvider(new MainNavigateProvider());
//        NavigatorImpl.addNavInterceptor(new LoginInterceptor());
    }
}
