package com.reactor.demo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: feiweiwei
 * @Description: reactor模式中Dispatcher类，负责event的分发和eventHandler的维护
 * @Created Date: 12:09 17/10/12.
 * @Modify by:
 */
public class Dispatcher {
    //通过ConcurrentHashMap来维护不同事件处理器
    Map<EventType, EventHandler> eventHandlerMap = new ConcurrentHashMap<EventType, EventHandler>();
    //本例只维护一个selector负责事件选择，netty为了保证性能实现了多个selector来保证循环处理性能，不同事件加入不同的selector的事件缓冲队列
    Selector selector;

    Dispatcher(Selector selector) {
        this.selector = selector;
    }

    //在Dispatcher中注册eventHandler
    public void registEventHandler(EventType eventType, EventHandler eventHandler) {
        eventHandlerMap.put(eventType, eventHandler);

    }

    public void removeEventHandler(EventType eventType) {
        eventHandlerMap.remove(eventType);
    }

    public void handleEvents() {
        dispatch();
    }

    /**
     * Initiation Dispatcher：用于管理EventHandler、分发event。通过Synchronous Event Demultiplexer来等待事件的发生，一旦事件发生，Initiation Dispatcher首先会分离出每一个事件，
     * 然后调用事件处理器，最后调用相关的回调方法来处理这些事件
     */
    private void dispatch() {
        while (true) {
            selector.select();
            Set<Event> selectionKeys = selector.selectionKeys();
            for (Event event : selectionKeys) {
                EventHandler eventHandler = eventHandlerMap.get(event.getType());
                eventHandler.handle(event);
            }
        }
    }
}