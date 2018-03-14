package com.edus.apollo;

import com.edus.apollo.common.biz.navigator.NavParams;
import com.edus.apollo.common.biz.navigator.NavParamsRewriter;
import com.edus.apollo.common.biz.navigatorimpl.NavigatorImpl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_nav_internal).setOnClickListener(this);
        findViewById(R.id.bt_nav_internal_anim).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_nav_internal:
                navInternally();
                break;
            case R.id.bt_nav_internal_anim:
                navInternallyWithAnim();
                break;
        }
    }

    private void navInternallyWithAnim() {
        final NavigatorImpl navigator = new NavigatorImpl();
        navigator.from(this).to("http://com.edus.apollo/native/viewdrawactivity", new NavParamsRewriter() {
            @Override
            public NavParams handleRewrite(NavParams navParams) {
                navParams.setEnterAnimation(android.R.anim.fade_in);
                navParams.setExitAnimation(android.R.anim.fade_out);
                navParams.setRequestCode(REQUEST_CODE);
                return navParams;
            }
        });
    }

    private void navInternally() {
        NavigatorImpl navigator = new NavigatorImpl();
        navigator.from(this).to("http://com.edus.apollo/native/viewdrawactivity");
    }

    public void keyboardResizeCheck(View view){
        startActivity(new Intent(this, KeyboardResizeActivity.class));

    }

    public void keyboardAdjustCheck(View view){
        startActivity(new Intent(this, KeyboardAdjustPanActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String testKey = data.getStringExtra("TEST_KEY");
            if(!TextUtils.isEmpty(testKey)){
                Toast.makeText(this, testKey, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
