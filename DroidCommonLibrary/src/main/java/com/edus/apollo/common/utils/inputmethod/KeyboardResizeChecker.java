package com.edus.apollo.common.utils.inputmethod;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.edus.apollo.common.utils.DensityUtils;

/**
 * Created by PandaPan on 2016/9/3.
 */
public class KeyboardResizeChecker {
    private View mRootView;
    private OnKeyboardStateChangeListener mListener;
    private boolean mStarted;

    /**
     * this view should be the layout's rootView
     * @param view
     */
    public KeyboardResizeChecker(View view) {
        if (view != null) {
            mRootView = view;
            startCheck();
        }
    }

    public void setOnKeyBoardStateChangeListener(OnKeyboardStateChangeListener listener) {
        mListener = listener;
    }

    private void startCheck() {
        if (!mStarted) {
            mStarted = true;
            if (mRootView != null) {
                mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
                checkKeyBoardCurrentState();
            }
        }
    }

    public void stopCheck() {
        if (mStarted) {
            mStarted = false;
            mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(mGlobalLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            checkKeyBoardCurrentState();
        }
    };

    private void checkKeyBoardCurrentState() {
        if(mRootView == null){
            return;
        }
        int heightDiff = mRootView.getRootView().getHeight() - mRootView.getHeight();
        boolean isShowing = false;
        if (heightDiff > DensityUtils.dp2Px(mRootView.getContext(), 200)) {
            isShowing = true;
        }
        if (mListener != null) {
            mListener.onKeyboardStateChanged(isShowing);
        }
    }
}
