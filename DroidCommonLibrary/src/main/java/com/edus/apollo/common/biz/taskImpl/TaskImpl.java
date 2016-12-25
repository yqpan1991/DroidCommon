package com.edus.apollo.common.biz.taskImpl;

import com.edus.apollo.common.biz.task.Priority;
import com.edus.apollo.common.biz.task.Task;

import java.util.concurrent.Callable;

/**
 * Created by PandaPan on 2016/12/25.
 */

public class TaskImpl implements Task {

    private boolean mIsStarted;
    private boolean mIsCanceled;
    private String mGroupName;
    private Callable mCallable;
    private Priority mPriority;

    public TaskImpl(String groupName, Priority priority){
        mGroupName = groupName;
        if(priority == null){
            priority = Priority.MIDDLE;
        }
        mPriority = priority;
    }

    @Override
    public void start(Runnable runnable) {
        if(!mIsStarted && !mIsCanceled){
            if(runnable != null){
                mIsStarted = true;
                mCallable = new TaskAdapter(runnable, null);
                ExecutorManager.getInstance().addExecuteInfo(mCallable, null, mGroupName, mPriority);
            }
        }
    }

    @Override
    public boolean isStarted() {
        return mIsStarted;
    }

    @Override
    public void cancel(boolean force) {
        if(!mIsCanceled){
            mIsCanceled = true;
            if(mIsStarted && mCallable != null){
                ExecutorManager.getInstance().cancelExecuteInfo(mCallable, force);
            }
        }
    }

    @Override
    public boolean isCanceled() {
        return mIsCanceled;
    }

    @Override
    public void setConcurrentCount(int concurrentCount) {
        ExecutorManager.getInstance().setConcurrentCount(mGroupName, concurrentCount);
    }
}
