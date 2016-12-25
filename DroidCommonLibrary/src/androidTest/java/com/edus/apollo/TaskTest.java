package com.edus.apollo;

import android.app.Application;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.edus.apollo.common.biz.task.Priority;
import com.edus.apollo.common.biz.task.Task;
import com.edus.apollo.common.biz.task.TaskFactory;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class TaskTest extends ApplicationTestCase<Application> {

    private final String TAG = this.getClass().getSimpleName();

    public TaskTest() {
        super(Application.class);
    }

    @Test
    public void createTask(){
        Task task = TaskFactory.getInstance().getTask(null, null);
        assertEquals(task.isStarted(), false);
        assertEquals(task.isCanceled(), false);
        task.cancel(false);
        assertEquals(task.isCanceled(), true);
    }

    @Test
    public void startTask(){
        Task task = TaskFactory.getInstance().getTask("panda-test", null);
        task.setConcurrentCount(1);
        task.start(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< 20; i++){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "**hello world"+i);
                }
            }
        });
     /*   Task task1 = TaskFactory.getInstance().getTask("panda-test", null);
        task1.start(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< 20; i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "--hello world"+i);
                }
            }
        });
        final Task task2 = TaskFactory.getInstance().getTask("panda-test", Priority.IMMEDIATE);
        task2.start(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< 20; i++){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "==hello world"+i);
                }
            }
        });*/


        try {
            Thread.sleep(50 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}