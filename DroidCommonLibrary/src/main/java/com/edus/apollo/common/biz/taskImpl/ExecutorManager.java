package com.edus.apollo.common.biz.taskimpl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import com.edus.apollo.common.biz.task.Priority;
import com.edus.apollo.common.utils.device.DeviceInfo;
import com.edus.apollo.common.utils.log.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by PandaPan on 2016/12/25.
 * 所有的更改,都需要在分发控制线程中做
 */

class ExecutorManager {

    private final String TAG = this.getClass().getSimpleName();

    private static final int MSG_TYPE_EXECUTOR_BEGIN = 1000;
    private static final int MSG_TYPE_EXECUTOR_DONE = 1001;
    private static ExecutorManager sInstance;

    private final int defaultConcurrentPerCount;
    private ExecutorService mExecutor;
    private AtomicInteger mAtomicInteger;
    private Map<String, ExecutGroupInfo> mGroupInfoMap;
    private Map<Callable<?>, ExecuteInfo> mAllTaskInfoMap;
    private HandlerThread mHandlerThread;
    private Handler mControlHandler;


    private ExecutorManager(){
        mAllTaskInfoMap = new HashMap<>();
        mGroupInfoMap = new HashMap<>();
        mAtomicInteger = new AtomicInteger();
        mHandlerThread = new HandlerThread("executor-manager");
        mHandlerThread.start();
        mControlHandler = new Handler(mHandlerThread.getLooper(), mDispatchCallback);
        int coreNumber = DeviceInfo.getNumberOfCPUCores();
        LogUtils.e(TAG, "cpu cores:"+coreNumber);
        if(coreNumber < 2){
            coreNumber = 2;
        }
        int totalThreadCount = coreNumber * 3;
        defaultConcurrentPerCount = coreNumber;
        mExecutor = Executors.newFixedThreadPool(totalThreadCount, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                //TODO: 需要设置该线程的异常处理器
                return new Thread( r, "ExecutorManager-thread-"+mAtomicInteger.getAndIncrement() );
            }
        });
    }

    public static ExecutorManager getInstance(){
        if(sInstance == null){
            synchronized (ExecutorManager.class){
                if(sInstance == null){
                    sInstance = new ExecutorManager();
                }
            }
        }
        return sInstance;
    }

    public void addExecuteInfo(final Callable callable, final Handler.Callback callback, final String groupName, final Priority priority){
        mControlHandler.post(new Runnable() {
            @Override
            public void run() {
                addExecuteInfoImpl(callable, callback, groupName, priority);
            }
        });
    }

    private void addExecuteInfoImpl(Callable callable, Handler.Callback callback, String groupName, Priority priority) {
        if(callable == null){
            return;
        }
        String myGroupName = getValidGroupName(groupName);
        ExecuteInfo executeInfo = new ExecuteInfo();
        executeInfo.mCallable = new CallableWrapper(callable);
        executeInfo.mCallback = callback;
        executeInfo.mGroupName = myGroupName;
        executeInfo.mPriority = priority;
        mAllTaskInfoMap.put(callable, executeInfo);

        ExecutGroupInfo groupInfo = null;
        if(mGroupInfoMap.containsKey(myGroupName)){
            groupInfo = mGroupInfoMap.get(myGroupName);
        }else{
            groupInfo = new ExecutGroupInfo();
            groupInfo.mGroupName = groupName;
            groupInfo.mConcurrentCount = defaultConcurrentPerCount;
            mGroupInfoMap.put(myGroupName, groupInfo);
        }

        if( groupInfo.mRunningList.size() < groupInfo.mConcurrentCount){
            groupInfo.mRunningList.add(executeInfo);
            executeInfo.mFuture = mExecutor.submit(executeInfo.mCallable);
        }else{
            groupInfo.mWaitingList.add(executeInfo);
        }
    }

    public void cancelExecuteInfo(final Callable callable, final boolean force){
        //取消操作,可能是比较着急的动作,因而在下次调度时,不放置到队列尾部
        mControlHandler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                cancelExecuteInfoImp(callable, force);
            }
        });
    }

    private void cancelExecuteInfoImp(Callable callable, boolean force) {
        //1. 获取到Execute的信息
        //2. 设置为取消
        //3. 从所有任务中取消
        //4. 从群组中删除当前任务
        //5. 是否要考虑,任务在强制取消后,直接给任务群组做调度???
        if(callable != null){
            ExecuteInfo executeInfo = mAllTaskInfoMap.get(callable);
            if(executeInfo == null){
                return;
            }
            Future future = executeInfo.mFuture;
            if(future != null){
                future.cancel(force);
            }
            mAllTaskInfoMap.remove(callable);
            ExecutGroupInfo groupInfo = mGroupInfoMap.get(getValidGroupName(executeInfo.mGroupName));
            if(groupInfo != null){
                if(future != null){
                    groupInfo.mRunningList.remove(executeInfo);
                }else{
                    groupInfo.mWaitingList.remove(executeInfo);
                }
            }
        }
    }

    public void setConcurrentCount(final String groupName, final int concurrentCount){
        mControlHandler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                setConcurrentCountImpl(groupName, concurrentCount);
            }
        });
    }

    private void setConcurrentCountImpl(String groupName, int concurrentCount) {
        String myGroupName = getValidGroupName(groupName);
        ExecutGroupInfo groupInfo = null;
        if(mGroupInfoMap.containsKey(myGroupName)){
            groupInfo = mGroupInfoMap.get(myGroupName);
        }else{
            groupInfo = new ExecutGroupInfo();
            groupInfo.mGroupName = myGroupName;
            mGroupInfoMap.put(myGroupName, groupInfo);
        }
        if(concurrentCount < 0  ){
            groupInfo.mConcurrentCount = 1;
        }else if(concurrentCount > defaultConcurrentPerCount){
            groupInfo.mConcurrentCount = defaultConcurrentPerCount;
        }else{
            groupInfo.mConcurrentCount = concurrentCount;
        }
        LogUtils.e(TAG, groupInfo.toString());
    }


    private Handler.Callback mDispatchCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MSG_TYPE_EXECUTOR_BEGIN:
                    //暂时不做处理,可以统计任务的监控
                    break;
                case MSG_TYPE_EXECUTOR_DONE:
                    //1. 可以统计任务的监控
                    //2. 重新调度需要执行的任务
                    handleExecuteDone( ( (Callable)(msg.obj) ) );
                    break;

            }
            return true;
        }
    };

    private void handleExecuteDone(Callable obj) {
        //1. 判断所有的任务列表中,是否还有他,如果有,那么做通知的操作
        //2. 如果没有,那么直接不用做通知
        //3. 从正在运行的群组队列中删除
        //4. 重新遍历整个群组的信息,查看是否要需要执行的群组信息,因为任务在强制取消后,可能会抛出interrupt的异常,导致没有处理,因而需要整体的再次调度一下
        if(mAllTaskInfoMap.containsKey(obj)){
            final ExecuteInfo executeInfo = mAllTaskInfoMap.remove(obj);
            if(executeInfo.mCallback != null && !executeInfo.mFuture.isCancelled()){
                //TODO: 暂时不设置callback的处理
               /* new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        executeInfo.mCallback.handleMessage()
                    }
                });*/
            }
            mAllTaskInfoMap.remove(obj);
            String groupName = getValidGroupName(executeInfo.mGroupName);
            ExecutGroupInfo executGroupInfo = mGroupInfoMap.get(groupName);
            if(executeInfo != null){
                executGroupInfo.mRunningList.remove(executeInfo);
            }
        }

        for(String groupName : mGroupInfoMap.keySet()){
            ExecutGroupInfo groupInfo = mGroupInfoMap.get(groupName);
            while(groupInfo.mWaitingList.size() > 0 && groupInfo.mRunningList.size() < groupInfo.mConcurrentCount){
                ExecuteInfo info = groupInfo.mWaitingList.poll();
                groupInfo.mRunningList.add(info);
                info.mFuture = mExecutor.submit(info.mCallable);
            }
        }
    }

    private String getValidGroupName(String groupName){
        if(TextUtils.isEmpty(groupName)){
            return "ExecutorManager-DEFAULT-GROUPNAME";
        }
        return groupName;
    }

    public class CallableWrapper<T> implements Callable<T> {

        private Callable<T> mCallable;

        public CallableWrapper(Callable<T> callable){
            mCallable = callable;
        }

        @Override
        public T call() throws Exception {
            mControlHandler.sendMessage(mControlHandler.obtainMessage(MSG_TYPE_EXECUTOR_BEGIN, mCallable));
            T result = null;
            if(mCallable != null){
                result = mCallable.call();
            }
            mControlHandler.sendMessage(mControlHandler.obtainMessage(MSG_TYPE_EXECUTOR_DONE, mCallable));
            return result;
        }
    }

}
