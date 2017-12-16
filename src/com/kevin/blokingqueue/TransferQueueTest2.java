package com.kevin.blokingqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class TransferQueueTest2 {
	final static TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
	
	public static void main(String[] args) throws InterruptedException {
		transferQueue.put("test1");
		transferQueue.put("test2");
		transferQueue.put("test3");
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("等待消费者取走元素:test4");
					transferQueue.transfer("test4");
					System.out.println("消费者取走元素:test4");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		for(int i = 0; i < 4; i++) {
			String element = transferQueue.take();
			System.out.println("消费者取走元素:" + element);
			Thread.sleep(1000);
		}
		
		executorService.shutdown();
	}
}
