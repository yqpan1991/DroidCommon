package com.edus.apollo.common.utils.inputmethod;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by PandaPan on 2016/9/3.
 * 要求: activity 属性
 * android:windowSoftInputMode="adjustPan"
 */
public class KeyBoardAdjustPanChecker implements ViewTreeObserver.OnGlobalLayoutListener {
    private final String TAG = this.getClass().getSimpleName();
    private View mRootView;
    private int mPreDiff;
    private OnKeyboardStateChangeListener mListener;
    private int mKeyboardHeight;
    private boolean mStarted;
    public KeyBoardAdjustPanChecker(Activity activity){
        if(activity != null){
            mRootView = activity.getWindow().getDecorView();
            startCheck();
        }
    }

    private void startCheck(){
        if(!mStarted){
            mStarted = true;
            if(mRootView != null){
                mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            }
        }
    }

    public void stopCheck(){
        if(mStarted){
            mStarted = false;
            mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }


    @Override
    public void onGlobalLayout() {
        if (mRootView != null) {
            Rect r = new Rect();
            mRootView.getWindowVisibleDisplayFrame(r);
            int screenHeight = mRootView.getRootView().getHeight();
            int heightDifference = screenHeight - (r.bottom);
            if (heightDifference > 300) {
                if (mPreDiff == 0) {
                    mKeyboardHeight = heightDifference;
                    if(mListener != null){
                        mListener.onKeyboardStateChanged(true);
                    }
                }
                mPreDiff = heightDifference;
            }else if (heightDifference == 0) {//现在没有差别,可能隐藏了键盘
                if (mPreDiff != 0) {//确定隐藏了键盘
                    if(mListener != null){
                        mListener.onKeyboardStateChanged(false);
                    }
                }
                mPreDiff = heightDifference;
            }
        }
    }

    public void setOnKeyBoardStateChangeListener(OnKeyboardStateChangeListener listener){
        mListener = listener;
    }
}
