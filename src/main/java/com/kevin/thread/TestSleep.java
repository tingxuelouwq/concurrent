package com.kevin.thread;

public class TestSleep {
	private Object obj = new Object();
	
	public static void main(String[] args) {
		TestSleep outer = new TestSleep();
		Thread t1 = outer.new MyThread();
		Thread t2 = outer.new MyThread();
		t1.start();
		t2.start();
	}
	
	private class MyThread extends Thread {
		@Override
		public void run() {
			synchronized (obj) {
				try {
					System.out.println(Thread.currentThread() + " 开始睡眠");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.out.println(Thread.currentThread() + " 睡眠结束");
				}
			}
		}
	}
}
