package com.edus.apollo.common.biz.task;

/**
 * Created by PandaPan on 2016/12/25.
 */

public enum Priority {
    LOW(0),
    MIDDLE(1),
    HIGH(2),
    IMMEDIATE(3);

    private Priority(int level){
        this.level = level;
    }

    private int level;

    public int getLevel(){
        return this.level;
    }
}
