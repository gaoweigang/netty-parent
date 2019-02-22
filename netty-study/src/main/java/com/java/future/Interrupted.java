package com.java.future;

public class Interrupted {

    public static void main(String[] args) throws Exception{
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        Thread.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("对两个线程执行中断操作");
        Thread.sleep(1);
        System.out.println("SleepThread Interrupted is "+ sleepThread.isInterrupted());
        System.out.println("BusyThread Interrupted is "+ busyThread.isInterrupted());

    }

    static class SleepRunner implements Runnable{
        @Override
        public void run(){
            while(true){
                try{
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    static class BusyRunner implements  Runnable{
        @Override
        public void run() {
            while(true){
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}

