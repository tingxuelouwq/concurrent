package com.kevin.thread;

public class TestInterrupt {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new MyThread();
		t.start();
	}
	
	private static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println("调用interrupt()方法前，中断状态为: " + isInterrupted());
			interrupt();	//调用interrupt()方法会将中断状态置位
			System.out.println("调用interrupt()方法后，中断状态为: " + isInterrupted());
		}
	}
}
