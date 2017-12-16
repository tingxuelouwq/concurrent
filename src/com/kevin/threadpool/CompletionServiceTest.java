package com.kevin.threadpool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//如果不使用ExecutorCompletionService
public class CompletionServiceTest {
	
	public static void main(String[] args) {
		int nThreads = 5;
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		List<Future<String>> results = new ArrayList<>();
		
		for(int i = 0; i < nThreads; i++) {
			Task task = new Task(i + 1);
			Future<String> result = executor.submit(task);
			results.add(result);
		}
		
		while(nThreads > 0) {
			Iterator<Future<String>> iterator = results.iterator();
			while(iterator.hasNext()) {
				String r = null;
				try {
					Future<String> result = iterator.next();
					r = result.get(0, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					//超时异常直接忽略
				}
				
				if(r != null) {
					iterator.remove();
					nThreads--;
					System.out.println(r);
				}
			}
		}
		
		executor.shutdown();
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

