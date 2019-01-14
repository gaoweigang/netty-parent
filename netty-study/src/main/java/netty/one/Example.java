package netty.one;

import java.lang.management.ManagementFactory;

public class Example {

    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        // get pid
        String pid = name.split("@")[0];
        System.out.println("主线程Pid is:" + pid);

        new Thread(new Runnable(){
            public void run() {
                try {
                    String name = ManagementFactory.getRuntimeMXBean().getName();
                    System.out.println(name);
                    // get pid
                    String pid = name.split("@")[0];
                    System.out.println("子线程Pid is:" + pid);
                    Thread.sleep(300000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
