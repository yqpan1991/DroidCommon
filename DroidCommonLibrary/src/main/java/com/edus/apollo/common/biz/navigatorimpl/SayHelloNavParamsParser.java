package com.edus.apollo.common.biz.navigatorimpl;

import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.NavParamsParser;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Description.
 *
 * @author panda
 */

public class SayHelloNavParamsParser implements NavParamsParser {

    @Override
    public NavParams parse2NavParams(Class<? extends Activity> targetClass, String url) {
        if(targetClass == null || TextUtils.isEmpty(url)){
            return null;
        }
        //解析为对应的参数
        Uri uri = Uri.parse(url);
        String name = uri.getQueryParameter("name");
        if(TextUtils.isEmpty(name)){
            return null;
        }
        NavParams.Builder builder = new NavParams.Builder();
        Bundle bundle = new Bundle();
        bundle.putString("internal_name", name);
        builder.setExtra(bundle);
        return builder.build();
    }
}
