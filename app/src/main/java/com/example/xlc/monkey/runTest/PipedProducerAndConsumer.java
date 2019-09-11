package com.example.xlc.monkey.runTest;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author:xlc
 * @date:2019/9/11
 * @descirbe:使用管道通信PipedInputStream 和PipedOutputStream 生产者和消费者
 * 字符流 PipedReader和PipedWriter
 */
public class PipedProducerAndConsumer {

    //从管道中读取数据
    class ThreadRead extends Thread {
        private PipedInputStream input;

        public ThreadRead(PipedInputStream input) {
            this.input = input;
        }


        public void readMethod(PipedInputStream input) {
            try {
                System.out.println("read:");
                byte[] byteArray = new byte[20];
                int readlength = input.read(byteArray);
                while (readlength != -1) {
                    String newData = new String(byteArray, 0, readlength);
                    System.out.print(newData);
                    readlength = input.read(byteArray);
                }
                System.out.println();
                input.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            this.readMethod(input);
        }
    }

    class ThreadWrite extends Thread {
        private PipedOutputStream output;

        private ThreadWrite(PipedOutputStream output) {
            this.output = output;
        }

        public void writeMethod(PipedOutputStream output) {

            try {
                System.out.println("write:");
                for (int i = 0; i < 30; i++) {
                    String outData = "" + (i + 1);
                    output.write(outData.getBytes());
                    System.out.println(outData);

                }
                System.out.println();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            this.writeMethod(output);
        }
    }


    public static void main(String[] args){
        try {
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream();
            pipedInputStream.connect(pipedOutputStream);
            PipedProducerAndConsumer ppc = new PipedProducerAndConsumer();
            ThreadRead threadRead = ppc.new ThreadRead(pipedInputStream);
            threadRead.start();
            Thread.sleep(2000);
            ThreadWrite threadWrite = ppc.new ThreadWrite(pipedOutputStream);
            threadWrite.start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
