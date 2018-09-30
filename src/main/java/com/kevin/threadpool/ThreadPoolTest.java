package com.kevin.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(5));
		for(int i = 0; i < 15; i++) {
			Runnable task = new MyTask(i + 1);
			executor.execute(task);
			System.out.println("线程池中当前线程数目: " + executor.getPoolSize() + ", 队列中等待执行的任务数目: " + 
			executor.getQueue().size() + ", 已执行完毕的任务数目: " + executor.getCompletedTaskCount());	
		}
		executor.shutdown();
	}
}

class MyTask implements Runnable {
	private int number;
	
	public MyTask(int number) {
		this.number = number;
	}

	@Override
	public void run() {
		try {
			System.out.println("正在执行任务" + number);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("任务" + number + " 执行完毕");
	}
	
}
