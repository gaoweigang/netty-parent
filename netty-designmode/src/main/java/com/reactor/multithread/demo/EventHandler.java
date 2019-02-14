package com.reactor.multithread.demo;

/**
 * @Author: feiweiwei
 * @Description: event处理器的抽象类
 * @Created Date: 11:26 17/10/12.
 * @Modify by:
 */
public abstract class EventHandler implements Runnable{

    private InputSource source;
    private Event event;

    public InputSource getSource() {
        return source;
    }

    public void setSource(InputSource source) {
        this.source = source;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}