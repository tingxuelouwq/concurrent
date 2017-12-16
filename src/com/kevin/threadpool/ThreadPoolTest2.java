package com.kevin.threadpool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolTest2 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String directory = "D:/测试阻塞队列";
		String keyword = "hello";
		
		ExecutorService pool = Executors.newCachedThreadPool();
		MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
		Future<Integer> result = pool.submit(counter);
		System.out.println(result.get() + " matching files");
		pool.shutdown();
	}
}

class MatchCounter implements Callable<Integer> {
	private File directory;
	private String keyword;
	private int count;
	private ExecutorService pool;
	
	public MatchCounter(File directory, String keyword, ExecutorService pool) {
		this.directory = directory;
		this.keyword = keyword;
		this.pool = pool;
	}

	@Override
	public Integer call() throws Exception {
		if(!directory.exists())
			throw new RuntimeException(directory + " is not exists!");
		if(!directory.isDirectory())
			throw new RuntimeException(directory + " is not a directory!");
		
		count = 0;
		File[] files = directory.listFiles();
		List<Future<Integer>> results = new ArrayList<>();
		for(File file : files) {
			if(file.isDirectory()) {
				MatchCounter counter = new MatchCounter(file, keyword, pool);
				Future<Integer> result = pool.submit(counter);
				results.add(result);
			} else {
				if(search(file))
					count++;
			}
		}
		
		for(Future<Integer> result : results)
			count += result.get();
	
		return count;
	}

	private boolean search(File file) throws FileNotFoundException {
		try(Scanner in = new Scanner(file)) {
			boolean found = false;
			while(!found && in.hasNextLine()) {
				String line = in.nextLine();
				if(line.contains(keyword))
					found = true;
			}
			return found;
		}
	}
	
}
