package com.edus.apollo;

import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.NavParamsRewriter;
import com.edus.apollo.common.biz.navigatorimpl.NavigatorImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Description.
 *
 * @author panda
 */

public class HomeActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        final String realUri = intent.getStringExtra(Constants.REAL_JUMP_URI_KEY);
        if(!TextUtils.isEmpty(realUri)){//如果真正需要跳转的uri不为null，采用真正跳转的方式即可,此处会再次跳会到RouterActivity继续处理
            Log.e(TAG, "handleIntent: jump back:"+realUri);
            NavigatorImpl navigator = new NavigatorImpl();
            navigator.from(this).to(Constants.INTENT_URI_ROUTER_INTERNAL, new NavParamsRewriter() {
                @Override
                public NavParams handleRewrite(NavParams navParams) {
                    Bundle extra = navParams.getExtra();
                    if(extra == null){
                        extra = new Bundle();
                    }
                    extra.putString(Constants.REAL_JUMP_URI_KEY,realUri);
                    navParams.setExtra(extra);
                    return navParams;
                }
            });
        }
    }
}
