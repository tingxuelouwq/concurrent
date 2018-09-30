package com.kevin.thread;

public class TestInterrupt4 {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new MyThread();
		t.start();
		Thread.sleep(2000);
		t.interrupt();	//不能中断正在运行的线程
	}
	
	private static class MyThread extends Thread {
		@Override
		public void run() {
			for(int i = 0; i < Integer.MAX_VALUE; i++)
				System.out.println(i);
		}
	}
}
