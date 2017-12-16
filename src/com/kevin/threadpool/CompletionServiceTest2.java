package com.kevin.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//使用CompletionService
public class CompletionServiceTest2 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int nThreads = 5;
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
		
		for(int i = 0; i < nThreads; i++) {
			Task task = new Task(i + 1);
			completionService.submit(task);
		}
		
		for(int i = 0; i < nThreads; i++) {
			System.out.println(completionService.take().get());
		}
	}
	
	private static class Task implements Callable<String> {
		private int num;
		
		public Task(int num) {
			this.num = num;
		}
		
		@Override
		public String call() throws Exception {
			Thread.sleep(1000);
			return Thread.currentThread().getName() + "执行完任务: " + num;
		}
	}
}
