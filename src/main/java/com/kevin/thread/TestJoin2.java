package com.kevin.thread;

public class TestJoin2 {
	public static void main(String[] args) throws InterruptedException {
		MyThread t1 = new MyThread("threadA");
		MyAnotherThread t2 = new MyAnotherThread("threadB", t1);
		t2.start();
		t1.start();
		t1.join();
		System.out.println("主线程执行结束");
	}
	
	private static class MyThread extends Thread {
		public MyThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + " 开始睡眠");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				System.out.println(Thread.currentThread().getName() + " 睡眠结束");
			}
		}
	}
	
	private static class MyAnotherThread extends Thread {
		private Thread synThread;
		
		public MyAnotherThread(String name, Thread synThread) {
			super(name);
			this.synThread = synThread;
		}
		
		@Override
		public void run() {
			synchronized (synThread) {
				try {
					System.out.println(Thread.currentThread().getName() + "开始睡眠");	
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.out.println(Thread.currentThread().getName() + " 睡眠结束");
				}
			}
		}
	}
}
