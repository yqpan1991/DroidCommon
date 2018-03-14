package com.edus.apollo.common.biz.navigatorimpl;

import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.NavParamsParser;

import android.app.Activity;

/**
 * Description.
 *
 * @author panda
 */

public class PageInfo {

    private Class<? extends Activity> mTargetActivity;
    //将进入的参数，解析为我们想要的参数
    private NavParamsParser mNavParamsParser;
    //入参解析
    //分析跳转
    //出参解析
    public PageInfo(Class<? extends Activity> targetActivity, NavParamsParser navParamsParser){
        if(targetActivity == null || navParamsParser == null){
            throw new RuntimeException("PageInfo params cannot be null");
        }
        mTargetActivity = targetActivity;
        mNavParamsParser = navParamsParser;
    }

    public PageInfo(Class<? extends Activity> targetActivity, NavParams navParams){
        if(targetActivity == null || navParams == null){
            throw new RuntimeException("PageInfo params cannot be null");
        }
        mTargetActivity = targetActivity;
        mNavParamsParser = new NoNeedParser(navParams);
    }



    public Class<? extends Activity> getTargetActivity() {
        return mTargetActivity;
    }

    public NavParamsParser getNavParamsParser() {
        return mNavParamsParser;
    }

}
