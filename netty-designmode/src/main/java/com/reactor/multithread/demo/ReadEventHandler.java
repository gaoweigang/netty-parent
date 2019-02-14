package com.reactor.multithread.demo;

/**
 * @Author: feiweiwei
 * @Description: ACCEPT事件处理器
 * @Created Date: 11:28 17/10/12.
 * @Modify by:
 */
public class ReadEventHandler extends EventHandler {
    private Selector selector;

    public ReadEventHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void handle(Event event) {
        //处理Accept的event事件
        if (event.getType() == EventType.READ) {

            //TODO 处理READ状态的事件
            System.out.println("处理读状态的事件");
        }
    }
}