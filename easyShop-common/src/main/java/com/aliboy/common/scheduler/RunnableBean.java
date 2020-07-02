package com.aliboy.common.scheduler;

/**
 *  RunnableBean <br>
 * @author xiquee.com. <br>
 * @date 2018-11-09 10:16:00
 */
public interface RunnableBean extends Runnable {
    String getName();

    void stopSignal();
}
