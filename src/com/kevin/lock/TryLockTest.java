package com.kevin.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockTest {
	private static List<Integer> list = new ArrayList<>();
	private static Lock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				insert(Thread.currentThread());
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				insert(Thread.currentThread());
			}
		}).start();
	}
	
	private static void insert(Thread t) {
		if(lock.tryLock()) {
			try {
				System.out.println(t.getName() + "得到锁");
				for(int i = 0; i < 5; i++)
					list.add(i);
			} finally {
				System.out.println(t.getName() + "释放锁");
				lock.unlock();
			}
		} else {
			System.out.println(t.getName() + "获得锁失败");
		}
	}
}
