package com.edus.apollo.common.biz.navigatorimpl;

import com.edus.apollo.common.biz.UriUtils;
import com.edus.apollo.common.biz.navigator.NavInterceptor;
import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.NavParamsRewriter;
import com.edus.apollo.common.biz.navigator.NavigateProvider;
import com.edus.apollo.common.biz.navigator.Navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跳转器
 *
 * @author panda
 */

public class NavigatorImpl implements Navigator {

    private static Map<String, PageInfo> mPageInfoMap = new HashMap<>();
    private static List<NavInterceptor> mNavInterceptor = new ArrayList<>();

    private Context mContext;

    public static void addNavigateProvider(NavigateProvider navigateProvider){
        if(navigateProvider != null){
            Map<String, PageInfo> providePage = navigateProvider.getProvidePage();
            if(providePage != null){
                for(String page:providePage.keySet()){
                    if(TextUtils.isEmpty(page)){
                        continue;
                    }
                    PageInfo pageInfo = providePage.get(page);
                    if(pageInfo == null){
                        continue;
                    }
                    mPageInfoMap.put(page, pageInfo);
                }
            }
        }
    }

    public static boolean supportNav(String url){
        String path = UriUtils.parseUrlMainPart(url);
        return mPageInfoMap.containsKey(path);
    }

    public static void addNavInterceptor(NavInterceptor navInterceptor){
        if(navInterceptor != null && !mNavInterceptor.contains(navInterceptor)){
            mNavInterceptor.add(navInterceptor);
        }
    }

    @Override
    public Navigator from(Context context) {
        mContext = context;
        return this;
    }

    @Override
    public void to(String url) {
        to(url, null);
    }

    @Override
    public void to(String url, NavParamsRewriter navParamsRewriter) {//模块自己进行管理
        String path = UriUtils.parseUrlMainPart(url);
        if(!mPageInfoMap.containsKey(path)){
            //todo:兜底逻辑
        }else{
            PageInfo pageInfo = mPageInfoMap.get(path);
            if(pageInfo == null || pageInfo.getNavParamsParser() == null){
                //todo: 异常逻辑
                return;
            }
            NavParams navParams = pageInfo.getNavParamsParser().parse2NavParams(pageInfo.getTargetActivity(), url);
            if(navParams == null){
                //todo: 异常逻辑
                return;
            }
            if(navParamsRewriter != null){
                //参数拷贝，防止用户更改参数后，影响原来的参数
                navParams = new NavParams.Builder(navParams).build();
                navParams = navParamsRewriter.handleRewrite(navParams);
            }
            if(navParams == null){
                //todo: 异常逻辑
                return;
            }
            if(!mNavInterceptor.isEmpty()){
                NavInterceptor navInterceptor = mNavInterceptor.get(0);
                navInterceptor.intercept(this, pageInfo, navParams);
            }else{
                handleJump(pageInfo, navParams);
            }
        }

    }

    private void handleJump(PageInfo pageInfo, NavParams navParams) {
        Intent intent = new Intent(mContext, pageInfo.getTargetActivity());
        if(navParams.getFlag() > 0){
            intent.setFlags(navParams.getFlag());
        }else{
            if(!(mContext instanceof Activity)){
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        if(navParams.getExtra() != null){
            intent.putExtras(navParams.getExtra());
        }
        if(mContext instanceof Activity){
            if(navParams.getRequestCode() > 0){
                ((Activity)mContext).startActivityForResult(intent, navParams.getRequestCode());
            }else{
                mContext.startActivity(intent);
            }
            ((Activity)mContext).overridePendingTransition(navParams.getEnterAnimation(), navParams.getExitAnimation());
        }else{
            mContext.startActivity(intent);
        }
    }

    @Override
    public void continueJump(NavParams navParams, PageInfo pageInfo,  NavInterceptor navInterceptor) {
        int indexOf = mNavInterceptor.indexOf(navInterceptor);
        if(indexOf < 0){
            return;
        }
        if(indexOf >= mNavInterceptor.size() -1){
            handleJump(pageInfo, navParams);
        }else{
            NavInterceptor nextInterceptor = mNavInterceptor.get(indexOf + 1);
            nextInterceptor.intercept(this, pageInfo,  navParams);
        }

    }

    @Override
    public Context getContext() {
        return mContext;
    }

}
