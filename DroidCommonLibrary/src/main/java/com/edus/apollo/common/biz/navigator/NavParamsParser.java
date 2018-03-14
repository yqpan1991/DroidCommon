package com.edus.apollo.common.biz.navigator;

import android.app.Activity;

/**
 * Description.
 *
 * @author panda
 */

public interface NavParamsParser {
    NavParams parse2NavParams(Class<? extends Activity> targetClass, String url);
}
