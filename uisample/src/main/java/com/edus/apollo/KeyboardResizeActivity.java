package com.edus.apollo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.edus.apollo.common.utils.inputmethod.KeyboardResizeChecker;
import com.edus.apollo.common.utils.inputmethod.OnKeyboardStateChangeListener;

public class KeyboardResizeActivity extends AppCompatActivity implements View.OnClickListener, OnKeyboardStateChangeListener {
    private KeyboardResizeChecker mChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_resize);
        mChecker = new KeyboardResizeChecker(this.findViewById(R.id.rl_root));
        mChecker.setOnKeyBoardStateChangeListener(this);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onKeyboardStateChanged(boolean isShowing) {
        Toast.makeText(this.getApplicationContext(), "isShowing?"+isShowing,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChecker.stopCheck();
    }
}
