package com.kevin.blokingqueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {
	
	public static void main(String[] args) {
		SynchronousQueue<Integer> queue = new SynchronousQueue<>();
		new Consumer(queue).start();
		new Producer(queue).start();
	}
	
	private static class Consumer extends Thread {
		private SynchronousQueue<Integer> queue;
		
		public Consumer(SynchronousQueue<Integer> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					System.out.println("消费了一个产品:" + queue.take());
					System.out.println("--------------------------------");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static class Producer extends Thread {
		private SynchronousQueue<Integer> queue;
		
		public Producer(SynchronousQueue<Integer> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					int rand = new Random().nextInt(100);
					System.out.println("生产了一个产品:" + rand);
					System.out.println("等待3秒后运送出去...");
					TimeUnit.SECONDS.sleep(3);
					queue.put(rand);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
