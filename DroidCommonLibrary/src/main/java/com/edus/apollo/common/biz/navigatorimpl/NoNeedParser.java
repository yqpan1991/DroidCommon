package com.edus.apollo.common.biz.navigatorimpl;

import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.NavParamsParser;

import android.app.Activity;

/**
 * 此类适合内部类进行跳转的处理，因为内部跳转的url，参数不是通过这种方式传递的，是通过url的复写来操作
 *
 * @author panda
 */

public class NoNeedParser implements NavParamsParser {

    private NavParams mNavParams;

    public NoNeedParser(NavParams navParams){
        mNavParams = navParams;
    }

    @Override
    public NavParams parse2NavParams(Class<? extends Activity> targetClass, String url) {
        return mNavParams;
    }
}
