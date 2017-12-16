package com.kevin.thread;

public class TestInterrupt2 {
	public static void main(String[] args) throws InterruptedException {
		for(int i = 0; i < 5; i++) {
			Thread t = new MyThread();
			t.start();
			Thread.sleep(2000);
			t.interrupt();
		}
	}
	
	private static class MyThread extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(5000);	//处于阻塞状态的线程如果被中断，则会抛出一个InterruptedException，并将中断状态置位false
			} catch (InterruptedException e) {
				System.out.println("中断状态为: " + isInterrupted());
			}
		}
	}
}
