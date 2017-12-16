package com.kevin.synchronizer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class CyclicBarrierTest3 {
	public static void main(String[] args) throws InterruptedException {
		int parties = 4;
		CyclicBarrier barrier = new CyclicBarrier(parties);
		for(int i = 0; i < parties; i++)
				new Writer(barrier).start();
		
		Thread.sleep(10000);
		
		System.out.println("CyclicBarrier重用");
		for(int i = 0; i < parties; i++)
			new Writer(barrier).start();
	}
	
	private static class Writer extends Thread {
		private CyclicBarrier barrier;
		
		public Writer(CyclicBarrier barrier) {
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
			try {
				Thread.sleep(2000);	//用睡眠来模拟写入数据操作
				System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
				barrier.await();
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("所有线程写入完毕，继续处理其他任务...");
		}
	}
}
