package com.edus.apollo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.edus.apollo.common.ui.view.AspectRatioImageView;

public class ViewDrawActivity extends AppCompatActivity implements View.OnClickListener{
    private LevelFrameLayout mLflContent;
    private TextView mTvContent;
    private AspectRatioImageView mArivTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_draw);
        mLflContent = (LevelFrameLayout) findViewById(R.id.lfl_content);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvContent.setText("hello world, what's your name");
        mLflContent.setLevel(2);
        mArivTest = (AspectRatioImageView) findViewById(R.id.ariv_test);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mArivTest.setHeightWidthRatio(0.5625F);
                mArivTest.setDominantMeasurement(AspectRatioImageView.MEASUREMENT_WIDTH);
                mArivTest.setHeightWidthRatioEnabled(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mArivTest.setHeightWidthRatioEnabled(false);
                        mArivTest.setDominantMeasurement(AspectRatioImageView.MEASUREMENT_HEIGHT);
                    }
                },2000);
            }
        },2000);
    }

    @Override
    public void onClick(View v) {

    }
}
