package com.edus.apollo.common.biz.navigator;

import com.edus.apollo.common.biz.navigatorimpl.PageInfo;

import java.util.Map;

/**
 * 提供可处理的页面中转(用于各个子模块内部支持外部跳转的处理),可以做到模块之间的隔离
 *
 * @author panda
 */

public interface NavigateProvider {
    Map<String, PageInfo> getProvidePage();
}
