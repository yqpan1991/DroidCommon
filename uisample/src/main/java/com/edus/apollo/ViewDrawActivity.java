package com.edus.apollo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ViewDrawActivity extends AppCompatActivity implements View.OnClickListener{
    private LevelFrameLayout mLflContent;
    private TextView mTvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_draw);
        mLflContent = (LevelFrameLayout) findViewById(R.id.lfl_content);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvContent.setText("hello world, what's your name");
        mLflContent.setLevel(2);
    }

    @Override
    public void onClick(View v) {

    }
}
