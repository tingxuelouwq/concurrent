package com.kevin.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyTest {
	private static Lock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		t1.start();
		t2.start();
		
		Thread.sleep(200);
		t2.interrupt();
	}
	
	private static void insert(Thread t) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			System.out.println(t.getName() + "得到锁");
			long startTime = System.currentTimeMillis();
			while(true) 
				if(System.currentTimeMillis() - startTime > 1000)
					break;
		} finally {
			System.out.println(t.getName() + "释放锁");
			lock.unlock();
		}
	}
	
	private static class MyThread extends Thread {
		@Override
		public void run() {
			try {
				insert(Thread.currentThread());
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + "被中断");
			}
		}
	}
}
