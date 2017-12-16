package com.kevin.synchronizer;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(2);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(2000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(2000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		try {
			System.out.println("主线程等待两个子线程执行完毕...");
			latch.await();
			System.out.println("两个子线程执行完毕，主线程继续执行...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
