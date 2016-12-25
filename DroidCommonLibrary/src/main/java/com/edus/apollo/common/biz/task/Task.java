package com.edus.apollo.common.biz.task;

/**
 * Created by PandaPan on 2016/12/25.
 * 任务,只能使用一次的任务,如果要执行多次,请自己创建任务
 */

public interface Task {
    void start(Runnable runnable);
    boolean isStarted();
    void cancel(boolean force);
    boolean isCanceled();
    void setConcurrentCount(int concurrentCount);
}
