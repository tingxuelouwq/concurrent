package com.kevin.notify;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * 类名: NotifyTest<br/>
 * 包名：com.kevin.notify<br/>
 * 作者：kevin[wangqi2017@xinhua.org]<br/>
 * 时间：2018/9/30 9:42<br/>
 * 版本：1.0<br/>
 * 描述：
 * 1. testWait()获取对象锁，lock.wait()释放对象锁，让出CPU，线程A进入等待队列<br/>
 * 2. testNotify()获取对象锁，lock.notify()唤醒等待获取该对象锁的线程A，但此时线程B并不立即释放该
 * 对象锁，而是继续执行（休眠5s）直至退出synchronized修饰的临界区，才释放对象锁。
 * 3. 线程A再次获得对象锁，继续执行。
 */
public class NotifyTest extends TestCase {

    public void testWait(Object lock) {
        try {
            synchronized (lock) {
                System.out.println("begin wait() ThreadName=" +
                        Thread.currentThread().getName());
                lock.wait();
                System.out.println(" end wait() ThreadName=" +
                        Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            System.out.println("interrupt ThreadName=" +
                    Thread.currentThread().getName());
            e.printStackTrace();
        }
    }

    public void testNotify(Object lock) {
        try {
            synchronized (lock) {
                System.out.println("begin notify() ThreadName=" +
                        Thread.currentThread().getName() + " time=" +
                        System.currentTimeMillis());
                lock.notify();
                Thread.sleep(5000);
                System.out.println(" end notify() ThreadName=" +
                        Thread.currentThread().getName() + " time=" +
                        System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class WaitThread extends Thread {

        private Object lock;

        public WaitThread(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            NotifyTest test = new NotifyTest();
            test.testWait(lock);
        }
    }

    private class NotifyThread extends Thread {

        private  Object lock;

        public NotifyThread(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            NotifyTest test = new NotifyTest();
            test.testNotify(lock);
        }
    }

    @Test
    public void testWaitAndNotify() {
        Object lock = new Object();
        NotifyTest notifyTest = new NotifyTest();
        NotifyTest.WaitThread waitThread = notifyTest.new WaitThread(lock);
        NotifyTest.NotifyThread notifyThread = notifyTest.new NotifyThread(lock);
        waitThread.start();
        notifyThread.start();
    }

    @Test
    public void testWaitAndInterrupt() throws InterruptedException {
        Object lock = new Object();
        NotifyTest notifyTest = new NotifyTest();
        NotifyTest.WaitThread waitThread = notifyTest.new WaitThread(lock);
        waitThread.start();

        Thread.sleep(5000);
        waitThread.interrupt();
    }
}
