package com.edus.apollo;

import com.edus.apollo.common.biz.navigator.NavInterceptor;
import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.Navigator;
import com.edus.apollo.common.biz.navigatorimpl.PageInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Description.
 *
 * @author panda
 */

public class LoginInterceptor implements NavInterceptor {
    @Override
    public void intercept(final Navigator navigator, final PageInfo pageInfo, final NavParams navParams) {
        AlertDialog.Builder builder = new AlertDialog.Builder(navigator.getContext());
        builder.setMessage("登录测试，是否继续跳转?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navigator.continueJump(navParams, pageInfo, LoginInterceptor.this);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
