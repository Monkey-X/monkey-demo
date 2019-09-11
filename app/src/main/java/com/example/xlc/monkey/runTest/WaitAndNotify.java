package com.example.xlc.monkey.runTest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author:xlc
 * @date:2019/9/11
 * @descirbe:BlockingQueue阻塞队列，每次只有一个线程访问
 */
public class WaitAndNotify {


    public static void main(String[] args) {
        //阻塞队列
        LinkedBlockingDeque<Object> shareDeque = new LinkedBlockingDeque<>();
        WaitAndNotify waitAndNotify = new WaitAndNotify();
        Consumer consumer = waitAndNotify.new Consumer(shareDeque);
        Producer producer = waitAndNotify.new Producer(shareDeque);


        Thread thread = new Thread(producer);
        Thread thread1 = new Thread(consumer);
        thread.start();
        thread1.start();


    }


    class Producer implements Runnable {

        private BlockingQueue mQueue;

        public Producer(BlockingQueue queue) {
            this.mQueue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("producer:" + i);
                    mQueue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    class Consumer implements Runnable {

        private BlockingQueue mQueue;

        public Consumer(BlockingQueue queue) {
            this.mQueue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Consumer" + mQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
