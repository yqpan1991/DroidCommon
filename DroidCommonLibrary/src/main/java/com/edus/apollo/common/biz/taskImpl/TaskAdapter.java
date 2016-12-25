package com.edus.apollo.common.biz.taskImpl;

import java.util.concurrent.Callable;

/**
 * Created by PandaPan on 2016/12/25.
 */

public class TaskAdapter<T> implements Callable<T> {

    private T mResult;
    private Runnable mRunnable;

    public TaskAdapter(Runnable runnable , T result){
        mRunnable = runnable;
        mResult = result;
    }

    @Override
    public T call() throws Exception {
        if(mRunnable != null){
            mRunnable.run();
        }
        return mResult;
    }
}
