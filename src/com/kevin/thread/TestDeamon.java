package com.kevin.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestDeamon {
	public static void main(String[] args) {
		Thread t = new MyThread();
		t.setDaemon(false);
		t.start();
	}
	
	private static class MyThread extends Thread {
		@Override
		public void run() {
			File file = null;
			try {
				file = new File("D:/daemon.txt");
				if(!file.exists())
					file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try(FileOutputStream fos = new FileOutputStream(file);) {
				Thread.sleep(1000);
				fos.write("守护线程".getBytes());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
