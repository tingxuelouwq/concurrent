package com.kevin.blokingqueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTest {
	public static void main(String[] args) {
		BlockingQueue<File> queue = new LinkedBlockingQueue<>();
		String startDirectory = "D:/测试阻塞队列";
		String keyword = "hello";
		
		new Thread(new FileEnumeration(startDirectory, queue)).start();
		
		for(int i = 0; i < 10; i++)
			new Thread(new SearchTask(queue, keyword)).start();
	}
}

class SearchTask implements Runnable {
	private BlockingQueue<File> queue;
	private String keyword;
	
	public SearchTask(BlockingQueue<File> queue, String keyword) {
		this.queue = queue;
		this.keyword = keyword;
	}

	@Override
	public void run() {
		try {
			searchFile();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void searchFile() throws InterruptedException, FileNotFoundException {
		boolean done = false;
		while(!done) {
			File file = queue.take();
			if(file == FileEnumeration.DUMMY) {
				done = true;
				queue.put(FileEnumeration.DUMMY);
			}
			else
				searchKeyword(file);
		}
	}

	private void searchKeyword(File file) throws FileNotFoundException {
		try(Scanner in = new Scanner(file)) {
			int lineNumber = 1;
			while(in.hasNextLine()) {
				String line = in.nextLine();
				if(line.contains(keyword))
					System.out.printf("%s:%d:%s\n", file.getAbsolutePath(), lineNumber, line);
				lineNumber++;
			}
		}
	}	
}

class FileEnumeration implements Runnable {
	private String startDirectory;
	private BlockingQueue<File> queue;
	public static final File DUMMY = new File("");
	
	public FileEnumeration(String startDirectory, BlockingQueue<File> queue) {
		this.startDirectory = startDirectory;
		this.queue = queue;
	}

	@Override
	public void run() {
		File directory = new File(startDirectory);
		if(!directory.exists())
			throw new RuntimeException(startDirectory + " is not exists!");
		if(!directory.isDirectory())
			throw new RuntimeException(startDirectory + " is not a directory!");
		
		try {
			enumerate(directory);
			queue.put(DUMMY);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void enumerate(File directory) throws InterruptedException {
		File[] files = directory.listFiles();
		for(File file : files) {
			if(!file.isDirectory())
				queue.put(file);
			else
				enumerate(file);
		}
	}
}
