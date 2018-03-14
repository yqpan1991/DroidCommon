package com.edus.apollo.common.biz.navigator;

import com.edus.apollo.common.biz.navigatorimpl.PageInfo;

/**
 * 处理的拦截器
 *
 * @author panda
 */

public interface NavInterceptor {
    void intercept(Navigator navigator, PageInfo pageInfo, NavParams navParams);
}
