package com.reactor.singlethread.demo;

public class ServerSocketChannel {
    Selector selector = new Selector();
    Dispatcher dispatcher = new Dispatcher(selector);
    Acceptor acceptor;

    ServerSocketChannel(int port) {
        acceptor = new Acceptor(selector, port);
    }

    public void start() {
        dispatcher.registEventHandler(EventType.ACCEPT, new AcceptEventHandler(selector));
        dispatcher.registEventHandler(EventType.READ, new ReadEventHandler(selector));
        dispatcher.registEventHandler(EventType.WRITE, new WriteEventHandler(selector));
        acceptor.accept();//接受请求
        dispatcher.handleEvents();
    }
}