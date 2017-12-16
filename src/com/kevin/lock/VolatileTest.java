package com.kevin.lock;

public class VolatileTest {
	private static volatile int inc = 0;
	
	public static void increase() {
		inc++;
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					for(int i = 0; i < 1000; i++)
						increase();
				};
			}.start();
		}
		
		while(Thread.activeCount() > 1)
			Thread.yield();
		System.out.println(inc);
	}
}
