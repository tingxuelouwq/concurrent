package com.kevin.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
public class ThreadLocalTest {
	private static final ThreadLocal<SimpleDateFormat> dateFormat = 
			new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		};
	};
	
	public static void main(String[] args) throws InterruptedException {
		for(int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					String dateStamp = dateFormat.get().format(new Date());
					System.out.println(dateStamp);
				}
			}).start();
			
			Thread.sleep(1000);
		}
	}
}
