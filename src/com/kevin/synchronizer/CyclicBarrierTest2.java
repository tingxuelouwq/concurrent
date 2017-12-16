package com.kevin.synchronizer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierTest2 {
	public static void main(String[] args) throws InterruptedException {
		int parties = 4;
		CyclicBarrier barrier = new CyclicBarrier(parties);
		for(int i = 0; i < parties; i++) {
			if(i < parties - 1)
				new Writer(barrier).start();
			else {
				Thread.sleep(3000);
				new Writer(barrier).start();
			}
		}
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
				Thread.sleep(3000);	//用睡眠来模拟写入数据操作
				System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
				barrier.await(1000, TimeUnit.MICROSECONDS);
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("所有线程写入完毕，继续处理其他任务...");
		}
	}
}
