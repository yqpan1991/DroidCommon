package com.edus.apollo.common.biz.task;

import com.edus.apollo.common.biz.taskImpl.TaskImpl;

/**
 * Created by PandaPan on 2016/12/25.
 */

public class TaskFactory {
    private static TaskFactory sInstance;
    private TaskFactory(){

    }

    public TaskFactory getInstance(){
        if(sInstance == null){
            synchronized (TaskFactory.class){
                if(sInstance == null){
                    sInstance = new TaskFactory();
                }
            }
        }
        return sInstance;
    }

    /**
     * 创建一个只能使用一次的任务
     * @param groupName 分组的名字
     * @param priority 任务在分组中的优先级
     * @return
     */
    public Task getTask(String groupName, Priority priority){
        return new TaskImpl(groupName, priority);
    }

}
