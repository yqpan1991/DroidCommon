package com.edus.apollo.common.biz.taskimpl;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by PandaPan on 2016/12/25.
 */

class ExecuteGroupInfo {
    public int mConcurrentCount;
    public String mGroupName;
    public Queue<ExecuteInfo> mRunningList;
    public Queue<ExecuteInfo> mWaitingList;

    public ExecuteGroupInfo(){
        mRunningList = new PriorityQueue<>();
        mWaitingList = new PriorityQueue<>();
    }

    @Override
    public String toString() {
        return "ExecuteGroupInfo{" +
                "mConcurrentCount=" + mConcurrentCount +
                ", mGroupName='" + mGroupName + '\'' +
                ", mRunningList=" + mRunningList +
                ", mWaitingList=" + mWaitingList +
                '}';
    }
}
