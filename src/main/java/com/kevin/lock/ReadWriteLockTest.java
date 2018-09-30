package com.kevin.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
	private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				read();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				read();
			}
		}).start();
	}
	
	private static void read() {
		readWriteLock.readLock().lock();
		try {
			long startTime = System.currentTimeMillis();
			while(System.currentTimeMillis() - startTime < 10)
				System.out.println(Thread.currentThread() + "正在进行读操作");
			System.out.println(Thread.currentThread() + "读操作完毕");
		} finally {
			readWriteLock.readLock().unlock();
		}
	}
}
