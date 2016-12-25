package com.edus.apollo.common.biz.taskImpl;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by PandaPan on 2016/12/25.
 */

class ExecutGroupInfo {
    public int mConcurrentCount;
    public String mGroupName;
    public Queue<ExecuteInfo> mRunningList;
    public Queue<ExecuteInfo> mWaitingList;

    public ExecutGroupInfo(){
        mRunningList = new PriorityQueue<>();
        mWaitingList = new PriorityQueue<>();
    }
}
