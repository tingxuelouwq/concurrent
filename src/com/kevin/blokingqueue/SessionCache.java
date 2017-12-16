package com.kevin.blokingqueue;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class SessionCache<K, V> {
	private ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
	private DelayQueue<DelayedItem<K>> queue = new DelayQueue<>();
	
	private static class SingtonHolder{
		@SuppressWarnings("rawtypes")
		private static  final SessionCache instance = new SessionCache<>();
	}
	
	@SuppressWarnings("rawtypes")
	public static SessionCache getInstance() {
		return SingtonHolder.instance;
	}
	
	public SessionCache() {
		Thread t = new Thread() {
			public void run() {
				try {
					daemonCheckOverdueKey();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		t.setDaemon(true);
		t.start();
	}
	
	public void daemonCheckOverdueKey() throws InterruptedException {
		while(true) {
			DelayedItem<K> delayedItem = queue.poll();
			if(delayedItem != null) {
				map.remove(delayedItem.getT());
				System.out.println(System.nanoTime() + " remove " + delayedItem.getT() + " from cache");
			}
			Thread.sleep(300);
		}
	}
	
	public void put(K key, V value, long time) {
		V v = map.put(key, value);
		DelayedItem<K> delayedItem = new DelayedItem<>(key, time);
		if(v != null)
			queue.remove(delayedItem);
		queue.put(delayedItem);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws InterruptedException {
		Random random = new Random();
        int cacheNumber = 10;
        long time = 200000000L;
        
        SessionCache cache = SessionCache.getInstance();
        for (int i = 0; i < cacheNumber; i++) {
            time = time + random.nextInt(10) * 10000;
            System.out.println(i + "  " + time);
            cache.put(i + "", i + "", time);
            time = 200000000L;
        }

        Thread.sleep(10000);
        System.out.println();
	}
}

class DelayedItem<T> implements Delayed {
	private T t;
	private long time;	//延迟多长时间
	
	public DelayedItem(T t, long time) {
		this.t = t;
		this.time = time;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Delayed o) {
		if(o == null)
			return 1;
		if(o == this)
			return 0;
		if(o instanceof DelayedItem) {
			DelayedItem<T> other = (DelayedItem<T>)o;
			long diff = time - other.time;
			if(diff < 0)
				return -1;
			else if(diff == 0)
				return 0;
			else
				return 1;
		}
		long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
		return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
	}

	//返回剩余时间
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(time - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
}
