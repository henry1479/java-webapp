package com.urise.webapp;

public class MainConcurrency {

    private static int counter;
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread_0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };

        thread_0.start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
        }).start();

        System.out.println(thread_0.getState());

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    increment();
                }
            }).start();
        }

        Thread.sleep(1000);
        System.out.println(counter);
    }

    private static synchronized void increment() {
        counter++;
    }

}
