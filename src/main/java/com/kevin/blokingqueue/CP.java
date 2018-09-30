package com.kevin.blokingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CP {
	private static int queueSize = 5;
	private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(queueSize);
	
	public static void main(String[] args) {
		Consumer consumer = new Consumer();
		Producer producer = new Producer();
		consumer.start();
		producer.start();
	}
	
	private static class Consumer extends Thread {
		@Override
		public void run() {
			consume();
		}

		private void consume() {
			while(true) {
				try {
					blockingQueue.take();
					System.out.println("从队列中取走一个元素，队列剩余" + blockingQueue.size() + "个元素 ");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static class Producer extends Thread {
		@Override
		public void run() {
			produce();
		}

		private void produce() {
			while(true) {
				try {
					blockingQueue.put(1);
					System.out.println("向队列中放一个元素，队列剩余" + blockingQueue.size() + "个元素 ");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
