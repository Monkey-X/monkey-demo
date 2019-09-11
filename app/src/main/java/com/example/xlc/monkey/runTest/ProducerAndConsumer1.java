package com.example.xlc.monkey.runTest;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:xlc
 * @date:2019/9/11
 * @descirbe: lock版本，使用了condition做线程之间的同步
 */
public class ProducerAndConsumer1 {

    private final int MAX_LEN = 10;

    private Queue<Integer> queue = new LinkedList<>();

    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    class Producer extends Thread {
        @Override
        public void run() {

            producer();
        }

        private void producer() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == MAX_LEN){
                        System.out.println("当前队列已满");

                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.add(1);
                    condition.signal();
                    System.out.println("生产者生产一条任务，当前队列长度为" + queue.size());

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }

            }
        }
    }

    class Consumer extends Thread{
        @Override
        public void run() {

            consumer();
        }

        private void consumer() {
            while (true){
                lock.lock();
                try{
                    while (queue.size() == 0){
                        System.out.println("当前队列为空");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    condition.signal();
                    System.out.println("消费者消费一条任务，当前队列长度为" + queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args){
        ProducerAndConsumer1 pc = new ProducerAndConsumer1();
        Producer producer = pc.new Producer();
        Consumer consumer = pc.new Consumer();
        producer.start();
        consumer.start();    }
}
