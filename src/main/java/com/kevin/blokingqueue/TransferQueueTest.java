package com.kevin.blokingqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class TransferQueueTest {
	final static TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("等待消费者取走元素");
					transferQueue.transfer("test");
					System.out.println("元素被消费者取走");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread.sleep(2000);
		String element = transferQueue.take();
		System.out.println("消费者取到的元素为:" + element);
		executorService.shutdown();
	}
}
