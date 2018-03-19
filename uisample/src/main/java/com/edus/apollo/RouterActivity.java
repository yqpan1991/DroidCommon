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

public class RouterActivity extends Activity {
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
        String action = intent.getAction();
        if(TextUtils.isEmpty(action) || TextUtils.equals(Intent.ACTION_VIEW, action)){
            //判断是否是栈底，如果是，启动主界面即可
            final Uri data = intent.getData();
            if(data == null){
                finish();
                return;
            }
            //因为task的原因，现在跳转到另外的task去
            final NavigatorImpl navigator = new NavigatorImpl();
            navigator.from(this).to(Constants.INTENT_URI_ROUTER_INTERNAL, new NavParamsRewriter() {
                @Override
                public NavParams handleRewrite(NavParams navParams) {
                    Bundle extra = navParams.getExtra();
                    if(extra == null){
                        extra = new Bundle();
                    }
                    extra.putString(Constants.REAL_JUMP_URI_KEY,data.toString());
                    navParams.setExtra(extra);
                    navParams.setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    return navParams;
                }
            });
            finish();
        }
    }

}
