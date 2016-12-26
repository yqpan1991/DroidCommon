package com.edus.apollo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by PandaPan on 2016/12/26.
 */

public class LevelFrameLayout extends FrameLayout {

    private final String TAG = this.getClass().getSimpleName();

    private int mLevel;
    private Paint mLinePaint;

    private int perWidth = 5;
    private int placeWidth = 20;
    private final int PADDING = 20;

    public LevelFrameLayout(Context context) {
        super(context);
        initView();
    }

    public LevelFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LevelFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor("#ff0000"));
        mLinePaint.setStrokeWidth(perWidth);
        mLinePaint.setAntiAlias(true);
        setWillNotDraw(false);
    }

    public void setLevel(int level){
        if(level == mLevel){
            return;
        }
        if(level >= 0){
            mLevel = level;
            if(level == 0){
                setPadding(0 , getPaddingTop(), getPaddingRight(), getPaddingBottom());
            }else{
                setPadding(placeWidth * mLevel + PADDING, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            }
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "before call me");
        super.onDraw(canvas);
        Log.e(TAG, "call me");
        int width = getWidth();
        int height = getHeight();
        int startX = 0;
        int startY = 0;
        int endX = width - startX - getPaddingRight();
        int endY = height - startY ;
        for(int index = 0; index< mLevel; index++){
            canvas.drawLine(startX + placeWidth * index,  startY, startX + placeWidth * index + perWidth, endY, mLinePaint);
        }
    }
}
