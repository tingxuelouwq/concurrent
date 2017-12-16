package com.kevin.blokingqueue;

import java.util.PriorityQueue;

public class CP2 {
	private static int queueSize = 5;
	private static PriorityQueue<Integer> queue = new PriorityQueue<>();
	
	public static void main(String[] args) {
		Producer producer = new Producer();
		Consumer consumer = new Consumer();
		producer.start();
		consumer.start();
	}
	
	private static class Producer extends Thread {
		@Override
		public void run() {
			produce();
		}

		private void produce() {
			while(true) {
				synchronized (queue) {
					while(queue.size() == queueSize) {
						try {
							System.out.println("队列满，等待有空余空间");
							queue.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							queue.notify();
						}
					}
					
					queue.offer(1);
					queue.notify();
					System.out.println("向队列中放一个元素，队列剩余" + queue.size() + "个元素");

				}
			}
		}
	}
	
	private static class Consumer extends Thread {
		@Override
		public void run() {
			consume();
		}

		private void consume() {
			while(true) {
				synchronized (queue) {
					while(queue.isEmpty()) {
						try {
							System.out.println("队列空，等待数据");
							queue.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							queue.notify();
						}
					}
					
					queue.poll();
					queue.notify();
					System.out.println("从队列中取走一个元素，队列剩余" + queue.size() + "个元素");
				}
			}
		}
	}
}
