package com.kevin.callable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String directory = "D:/测试阻塞队列";
		String keyword = "hello";
		
		MatchCounter counter = new MatchCounter(new File(directory), keyword);
		FutureTask<Integer> task = new FutureTask<>(counter);
		Thread t = new Thread(task);
		t.start();
		
		System.out.println(task.get() + " matching files.");
	}
}

class  MatchCounter implements Callable<Integer> {
	private File directory;
	private String keyword;
	private int count;
	
	public MatchCounter(File directory, String keyword) {
		this.directory = directory;
		this.keyword = keyword;
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
				MatchCounter counter = new MatchCounter(file, keyword);
				FutureTask<Integer> task = new FutureTask<>(counter);
				results.add(task);
				Thread t = new Thread(task);
				t.start();
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
