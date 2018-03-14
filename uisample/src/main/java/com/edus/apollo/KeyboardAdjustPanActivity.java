package com.edus.apollo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edus.apollo.common.utils.inputmethod.KeyBoardAdjustPanChecker;
import com.edus.apollo.common.utils.inputmethod.OnKeyboardStateChangeListener;

public class KeyboardAdjustPanActivity extends AppCompatActivity implements View.OnClickListener, OnKeyboardStateChangeListener {
    private KeyBoardAdjustPanChecker mChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_adjust);
        String internalName = getIntent().getStringExtra("internal_name");
        EditText editText = (EditText) findViewById(R.id.et_top);
        editText.setText(internalName);
        mChecker = new KeyBoardAdjustPanChecker(this);
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
