package com.edus.apollo;

import com.edus.apollo.common.biz.UriUtils;
import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.NavParamsRewriter;
import com.edus.apollo.common.biz.navigatorimpl.NavigatorImpl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * Description.
 *
 * @author panda
 */

public class RouterInternalActivity extends Activity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        final String realJumpUri = intent.getStringExtra(Constants.REAL_JUMP_URI_KEY);
        if(TextUtils.isEmpty(realJumpUri)){
            finish();
            return;
        }
        //判断是否是栈底，如果是，启动主界面即可
        if(isTaskRoot()){
            Log.e(TAG, "isTaskRoot");
            Log.e(TAG, "realJumpUri:"+realJumpUri);
            if(TextUtils.isEmpty(realJumpUri)){
                return;
            }
            final NavigatorImpl navigator = new NavigatorImpl();
            navigator.from(this).to(Constants.INTENT_URI_HOME_INTERNAL, new NavParamsRewriter() {
                @Override
                public NavParams handleRewrite(NavParams navParams) {
                    Bundle extra = navParams.getExtra();
                    if(extra == null){
                        extra = new Bundle();
                    }
                    extra.putString(Constants.REAL_JUMP_URI_KEY,realJumpUri);
                    navParams.setExtra(extra);
                    return navParams;
                }
            });
        }else{
            Log.e(TAG, "isNotTaskRoot");
            final NavigatorImpl navigator = new NavigatorImpl();
            navigator.from(this).to(realJumpUri, new NavParamsRewriter() {
                @Override
                public NavParams handleRewrite(NavParams navParams) {
                    String s = UriUtils.parseUrlMainPart(realJumpUri);
                    if(TextUtils.equals(Constants.INTENT_URI_ADJUST_PAN_ACTIVITY_URI, s)){
                        Bundle extra = navParams.getExtra();
                        if(extra == null){
                            extra = new Bundle();
                        }
                        extra.putString("internal_name","name_rewritter");
                        navParams.setExtra(extra);
                    }
                    return navParams;
                }
            });
        }
        finish();
    }

}
