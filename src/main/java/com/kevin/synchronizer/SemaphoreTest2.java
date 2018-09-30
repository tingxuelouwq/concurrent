package com.kevin.synchronizer;

import java.util.concurrent.Semaphore;

public class SemaphoreTest2 {
	public static void main(String[] args) {
		int times = 3;
		Semaphore sempA =  new Semaphore(1);
		Semaphore sempB = new Semaphore(0);
		Semaphore sempC = new Semaphore(0);
		
		Thread A = new Printer("A", sempA, sempB, times);
		Thread B = new Printer("B", sempB, sempC, times);
		Thread C = new Printer("C", sempC, sempA, times);
		
		A.start();
		B.start();
		C.start();
	}
	
	private static class Printer extends Thread {
		private String name;
		private Semaphore current;
		private Semaphore next;
		private int times;
		
		public Printer(String name, Semaphore current, Semaphore next, int times) {
			this.name = name;
			this.current = current;
			this.next = next;
			this.times = times;
		}
		
		@Override
		public void run() {
			try {
				for(int i = 0; i < times; i++) {
					current.acquire();
					System.out.println(name);
					next.release();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
