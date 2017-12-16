package com.kevin.synchronizer;

import java.util.concurrent.Semaphore;

//假若一个工厂有5台机器，但是有8个工人，一台机器同时只能被一个工人使用，只有使用完了，其他工人才能继续使用
public class SemaphoreTest {
	public static void main(String[] args) {
		int numOfWorkers = 8;
		int numOfMechines = 5;
		Semaphore semaphore = new Semaphore(numOfMechines);
		
		for(int i = 0; i < numOfWorkers; i++)
			new Worker(i + 1, semaphore).start();
	}
	
	private static class Worker extends Thread {
		private int number;
		private Semaphore semaphore;
		
		public Worker(int number, Semaphore semaphore) {
			this.number = number;
			this.semaphore = semaphore;
		}
		
		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println("工人" + number + "占用一个机器在生产...");
				Thread.sleep(2000);	//模拟生产
				System.out.println("工人" + number + " 生产完毕，释放机器...");
				semaphore.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
