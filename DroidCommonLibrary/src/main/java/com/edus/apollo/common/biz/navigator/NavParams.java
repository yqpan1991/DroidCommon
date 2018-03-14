package com.edus.apollo.common.biz.navigator;

import android.content.Intent;
import android.os.Bundle;


/**
 * Description.
 *
 * @author panda
 */

public class NavParams {

    private int mRequestCode;
    private int mFlag;
    private int  mEnterAnimation;
    private int mExitAnimation;
    private Bundle mExtra;

    public int getRequestCode() {
        return mRequestCode;
    }

    public int getFlag() {
        return mFlag;
    }

    public int getEnterAnimation() {
        return mEnterAnimation;
    }

    public int getExitAnimation() {
        return mExitAnimation;
    }

    public Bundle getExtra() {
        return mExtra;
    }

    public void setRequestCode(int requestCode) {
        mRequestCode = requestCode;
    }

    public void setFlag(int flag) {
        mFlag = flag;
    }

    public void setEnterAnimation(int enterAnimation) {
        mEnterAnimation = enterAnimation;
    }

    public void setExitAnimation(int exitAnimation) {
        mExitAnimation = exitAnimation;
    }

    public void setExtra(Bundle extra) {
        mExtra = extra;
    }

    private NavParams(){

    }

    public static class Builder {
        private NavParams mNavParams;

        public Builder(){
            mNavParams = new NavParams();
        }

        public Builder(NavParams navParams){
            mNavParams = new NavParams();
            if(navParams != null){
                mNavParams.mRequestCode = navParams.mRequestCode;
                mNavParams.mFlag = navParams.mFlag;
                mNavParams.mEnterAnimation = navParams.mEnterAnimation;
                mNavParams.mExitAnimation = navParams.mExitAnimation;
            }
        }


        public Builder setRequestCode(int requestCode) {
            mNavParams.mRequestCode = requestCode;
            return this;
        }

        public Builder setFlag(int flag) {
            mNavParams.mFlag = flag;
            return this;
        }

        public Builder setEnterAnimation(int enterAnimation) {
            mNavParams.mEnterAnimation = enterAnimation;
            return this;
        }

        public Builder setExitAnimation(int exitAnimation) {
            mNavParams.mExitAnimation = exitAnimation;
            return this;
        }

        public Builder setExtra(Bundle extra) {
            mNavParams.mExtra = extra;
            return this;
        }

        public NavParams build(){
            return mNavParams;
        }


    }

}
