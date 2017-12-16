package com.kevin.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//对同步的List进行迭代时，需进行同步操作，否则会抛出ConccurentModificatoinException
public class SynchronizedListTest {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;

			{
				for(int i = 0; i < 5; i++)
					add(i + 1);
			}
		};
		
		List<Integer> syncList = Collections.synchronizedList(list);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Iterator<Integer> iterator = syncList.iterator();
				while(iterator.hasNext()) {
					Integer item = iterator.next();
					System.out.println(item);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Iterator<Integer> iterator = syncList.iterator();
				while(iterator.hasNext()) {
					Integer item = iterator.next();
					if(item == 2)
						iterator.remove();
				}
			}
		}).start();
	}
}
