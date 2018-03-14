package com.edus.apollo.common.biz.navigator;

import com.edus.apollo.common.biz.navigatorimpl.PageInfo;

import android.content.Context;

/**
 * 跳转导航
 *
 * @author panda
 */

public interface Navigator {
    Navigator from(Context context);
    void to(String url);
    void to(String url, NavParamsRewriter navParamsRewriter);
    void continueJump(NavParams navParams, PageInfo pageInfo, NavInterceptor navInterceptor);
    Context getContext();
}
