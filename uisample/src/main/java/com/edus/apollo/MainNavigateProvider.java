package com.edus.apollo;

import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.NavigateProvider;
import com.edus.apollo.common.biz.navigatorimpl.PageInfo;
import com.edus.apollo.common.biz.navigatorimpl.SayHelloNavParamsParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Description.
 *
 * @author panda
 */

public class MainNavigateProvider implements NavigateProvider {
    private Map<String, PageInfo> mPageInfoMap = new HashMap<>();

    @Override
    public Map<String, PageInfo> getProvidePage() {
        if(!mPageInfoMap.isEmpty()){
            return mPageInfoMap;
        }
        initPageMap();
        return mPageInfoMap;
    }

    private void initPageMap() {
        NavParams.Builder builder = new NavParams.Builder();
        PageInfo pageInfo = new PageInfo(ViewDrawActivity.class, builder.build());
        mPageInfoMap.put(Constants.INTENT_URI_ADJUST_VIEW_DRAW_URI, pageInfo);

        SayHelloNavParamsParser sayHelloNavParamsParser = new SayHelloNavParamsParser();
        pageInfo = new PageInfo(KeyboardAdjustPanActivity.class, sayHelloNavParamsParser);
        mPageInfoMap.put(Constants.INTENT_URI_ADJUST_PAN_ACTIVITY_URI, pageInfo);

        builder = new NavParams.Builder();
        pageInfo = new PageInfo(HomeActivity.class, builder.build());
        mPageInfoMap.put(Constants.INTENT_URI_HOME_INTERNAL, pageInfo);

        builder = new NavParams.Builder();
        pageInfo = new PageInfo(RouterInternalActivity.class, builder.build());
        mPageInfoMap.put(Constants.INTENT_URI_ROUTER_INTERNAL, pageInfo);
    }
}
