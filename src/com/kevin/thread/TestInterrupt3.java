package com.kevin.thread;

public class TestInterrupt3 {
	public static void main(String[] args) throws InterruptedException {
		for(int i = 0; i < 5; i++) {
			Thread t = new MyThread();
			t.start();
			t.interrupt();
		}
	}
	
	private static class MyThread extends Thread {
		@Override
		public void run() {
			try {
				for(int i = 0; i < Integer.MAX_VALUE; i++)	;
				Thread.sleep(5000);	//线程在sleep()之前，如果被中断，则会抛出InterruptedException，并将中断状态置为false
			} catch (InterruptedException e) {
				System.out.println("中断状态为: " + isInterrupted());
			}
		}
	}
}
