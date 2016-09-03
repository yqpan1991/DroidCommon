package com.edus.apollo.common.utils;

import java.lang.ref.WeakReference;
import java.util.logging.Handler;

/**
 * Created by PandaPan on 2016/9/3.
 */
public abstract class WeakHandler<Ref> extends Handler {
    private WeakReference<Ref> mWeakRef;

    public WeakHandler(Ref ref){
        mWeakRef = new WeakReference<>(ref);
    }

    public Ref getReference(){
        return mWeakRef.get();
    }
}
