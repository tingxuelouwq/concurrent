package com.kevin.thread;

public class TestJoin implements Runnable {
	private static int a = 0;
	
	public static void main(String[] args) throws InterruptedException {
		Runnable r = new TestJoin();
		Thread t = new Thread(r);
		t.start();
		t.join();
		System.out.println(a);
	}

	@Override
	public void run() {
		for(int i = 0; i < 5; i++)
			a++;
	}
}
