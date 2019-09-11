package com.example.xlc.monkey.runTest;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author:xlc
 * @date:2019/9/11
 * @descirbe:使用object的wait（）和notify（）实现生产和消费者模式
 */
public class ProducerAndConsumer {

    private final int MAX_LEN = 10;
    private Queue<Integer> mQueue = new LinkedList<>();


    class Producer extends Thread{

        @Override
        public void run() {

            producer();
        }

        private void producer() {
            while (true){
                synchronized (mQueue){
                    while (mQueue.size() == MAX_LEN){
                        mQueue.notify();

                        System.out.println("当前队列已满");
                        try {
                            mQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    mQueue.add(1);
                    mQueue.notify();
                    System.out.println("生产者生产一条任务，当前队列长度是"+mQueue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class  Consumer extends  Thread{
        @Override
        public void run() {

            consumer();
        }

        private void consumer() {
            while (true){
                synchronized (mQueue){
                    while (mQueue.size() == 0){
                        mQueue.notify();
                        System.out.println("当前队列为空");
                        try {
                            mQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mQueue.poll();
                    mQueue.notify();
                    System.out.println("消费者消费一条人去，当前队列长度为"+mQueue.size());

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] args){
        ProducerAndConsumer pc = new ProducerAndConsumer();
        Producer producer = pc.new Producer();
        Consumer consumer = pc.new Consumer();
        producer.start();
        consumer.start();
    }
}
