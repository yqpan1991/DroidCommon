package com.edus.apollo.common.biz.taskImpl;

import android.os.Handler;

import com.edus.apollo.common.biz.task.Priority;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by PandaPan on 2016/12/25.
 */

public class ExecuteInfo<T> implements Comparable<ExecuteInfo>{

    public Priority mPriority;
    public String mGroupName;
    public Future<T> mFuture;
    public Handler.Callback mCallback;
    public Callable<T> mCallable;

    @Override
    public String toString() {
        return "ExecuteInfo{" +
                "mPriority=" + mPriority +
                ", mGroupName='" + mGroupName + '\'' +
                ", mFuture=" + mFuture +
                ", mCallback=" + mCallback +
                ", mCallable=" + mCallable +
                '}';
    }

    @Override
    public int compareTo(ExecuteInfo o) {
        if(o == null || o.mPriority == null){
            if(mPriority == null){
                return 0;
            }else{
                return -1;
            }
        }else{
            if(mPriority == null){
                return 1;
            }else{
                return o.mPriority.getLevel() - mPriority.getLevel();
            }
        }
    }

}
