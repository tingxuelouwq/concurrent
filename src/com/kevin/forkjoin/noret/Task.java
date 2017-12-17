package com.kevin.forkjoin.noret;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * @class: Task
 * @package: com.kevin.forkjoin.noreturn
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/17 8:56
 * @version: 1.0
 * @desc: 继承RecursiveAction的任务类
 */
public class Task extends RecursiveAction {

    private static final int THRESHOLD = 10;
    private List<Product> products;
    private int first;
    private int last;
    private double increment;

    public Task(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    @Override
    protected void compute() {
        if (last- first < THRESHOLD) {  // 如果任务足够小，则直接执行任务
            for (int i = first; i< last; i++) {
                Product product = products.get(i);
                product.setPrice(product.getPrice() * (1 + increment));
            }
        } else {    // 否则，将任务划分为小任务
            int mid = (first + last) / 2;
            System.out.println("The pending tasks:" + getQueuedTaskCount());
            Task task1 = new Task(products, first, mid, increment);
            Task task2 = new Task(products, mid, last, increment);
            invokeAll(task1, task2);    // 对每一个任务调用fork方法
        }
    }
}
