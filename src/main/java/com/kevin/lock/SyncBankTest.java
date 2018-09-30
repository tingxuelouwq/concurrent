package com.kevin.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncBankTest {
	private static final int NACCOUNTS = 100;
	private static final int INITIAL_BALANCE = 1000;
	
	public static void main(String[] args) {
		Bank bank = new Bank(NACCOUNTS, INITIAL_BALANCE);
		for(int i = 0; i < bank.size(); i++) {
			TransferRunnable r = new TransferRunnable(bank, i, INITIAL_BALANCE);
			Thread t = new Thread(r);
			t.start();
		}
	}
	
	private static class TransferRunnable implements Runnable {
		private Bank bank;
		private int fromAccount;
		private double maxAmount;
		private int DELAY = 10;
		
		public TransferRunnable(Bank bank, int fromAccount, double maxAccount) {
			this.bank = bank;
			this.fromAccount = fromAccount;
			this.maxAmount = maxAccount;
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					int toAccount = (int)(bank.size() * Math.random());
					double amount = maxAmount * Math.random();
					bank.transfer(fromAccount, toAccount, amount);
					Thread.sleep((int)(DELAY * Math.random()));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//Bank类
	private static class Bank {
		private final double[] accounts;
		private Lock bankLock;
		private Condition sufficientFunds;
		
		public Bank(int n, double initialBalance) {
			accounts = new double[n];
			for(int i = 0; i < accounts.length; i++)
				accounts[i] = initialBalance;
			
			bankLock = new ReentrantLock();
			sufficientFunds = bankLock.newCondition();
		}
		
		//对于InterruptedException的处理，最好是将其抛出，让调用者(或者说run()方法)可以捕获这一异常
		public void transfer(int from, int to, double amount) throws InterruptedException {
			bankLock.lock();
			try {
				while(accounts[from] < amount)
					sufficientFunds.await();
				System.out.print(Thread.currentThread());
				accounts[from] -= amount;
				System.out.printf(" %10.2f from %d to %d", amount, from, to);
				accounts[to] += amount;
				System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
				sufficientFunds.signalAll();
			} finally {
				bankLock.unlock();
			}
		}

		public double size() {
			return accounts.length;
		}

		private double getTotalBalance() {
			bankLock.lock();
			try {
				double sum = 0;
				for(int i = 0; i < accounts.length; i++)
					sum += accounts[i];
				return sum;
			} finally {
				bankLock.unlock();
			}
		}
	}
}
