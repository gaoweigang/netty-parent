package com.java.future;

import java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newCachedThreadPool();
        Future future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("call execute");
                return "gaoweigang";
            }
        });
        future.get();

        future.cancel(true);

    }
}
