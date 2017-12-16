package com.kevin.lock;

public class SyncBankTest2 {
	private static final int NACCOUNTS = 100;
	private static final double INITIAL_BALANCE = 1000;
	
	public static void main(String[] args) {
		Bank bank = new Bank(NACCOUNTS, INITIAL_BALANCE);
		for(int i = 0; i < bank.size(); i++) {
			TransferRunnalbe r = new TransferRunnalbe(bank, i, INITIAL_BALANCE);
			Thread t = new Thread(r);
			t.start();
		}
	}
	
	private static class TransferRunnalbe implements Runnable {
		private Bank bank;
		private int fromAccount;
		private double maxAmount;
		private int DELAY = 10;
				
		public TransferRunnalbe(Bank bank, int fromAccount, double maxAmount) {
			super();
			this.bank = bank;
			this.fromAccount = fromAccount;
			this.maxAmount = maxAmount;
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
	
	private static class Bank {
		private final double[] accounts;
		
		public Bank(int n, double initialBalance) {
			accounts = new double[n];
			for(int i = 0; i < accounts.length; i++)
				accounts[i] = initialBalance;
		}
		
		public synchronized void transfer(int from, int to, double amount) throws InterruptedException {
			while(accounts[from] < amount)
				wait();
			System.out.print(Thread.currentThread());
			accounts[from] -= amount;
			System.out.printf(" %10.2f from %d to %d", amount, from, to);
			accounts[to] += amount;
			System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
			notifyAll();
		}
		
		public double size() {
			return accounts.length;
		}

		private synchronized double getTotalBalance() {
			double sum = 0;
			for(int i = 0; i < accounts.length; i++)
				sum += accounts[i];
			return sum;
		}
	}
}
