package com.kevin.thread;

import java.lang.Thread.UncaughtExceptionHandler;

public class TestUncaughtExceptionHandler {
	public static void main(String[] args) {
		UncaughtExceptionHandler uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
		Thread t = new MyThread();
		t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
		t.start();
	}
	
	private static class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println(t.getName() + "抛出了一个异常: " + e.getMessage());
		}
		
	}
	
	private static class MyThread extends Thread {
		@SuppressWarnings("unused")
		@Override
		public void run() {
			int i = 9 / 0;
		}
	}
}
