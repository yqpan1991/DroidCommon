package com.edus.apollo.common.biz;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Description.
 *
 * @author panda
 */

public class UriUtils {

    public static String parseUrlMainPart(String url){
        if(TextUtils.isEmpty(url)){
            return null;
        }
        Uri parse = Uri.parse(url);
        Uri.Builder uriBuilder = Uri.parse("").buildUpon();
        //todo: 端口是否需要处理
        return uriBuilder.scheme(parse.getScheme()).authority(parse.getAuthority()).path(parse.getPath()).build().toString();
    }

}
