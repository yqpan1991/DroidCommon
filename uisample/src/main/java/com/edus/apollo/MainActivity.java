package com.edus.apollo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {

    }

    public void keyboardResizeCheck(View view){
        startActivity(new Intent(this, KeyboardResizeActivity.class));

    }

    public void keyboardAdjustCheck(View view){
        startActivity(new Intent(this, KeyboardAdjustPanActivity.class));
    }
}
