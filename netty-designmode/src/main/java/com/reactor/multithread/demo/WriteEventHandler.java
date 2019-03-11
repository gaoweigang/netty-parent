package com.reactor.multithread.demo;

/**
 * @Author: feiweiwei
 * @Description: ACCEPT事件处理器
 * @Created Date: 11:28 17/10/12.
 * @Modify by:
 */
public class WriteEventHandler extends EventHandler {
    private Selector selector;

    public WriteEventHandler(Selector selector) {
        this.selector = selector;
    }

    public void run() {
        //处理Accept的event事件
        if (this.getEvent().getType() == EventType.WRITE) {

            //TODO 处理WRITE状态的事件

            System.out.println();
        }
    }


}