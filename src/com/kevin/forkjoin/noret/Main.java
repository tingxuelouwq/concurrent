package com.kevin.forkjoin.noret;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @class: Main
 * @package: com.kevin.forkjoin.noreturn
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/17 9:42
 * @version: 1.0
 * @desc: 主程序
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ProductListGenerator generator = new ProductListGenerator();
        List<Product> products = generator.generate(10_000);

        ForkJoinPool pool = new ForkJoinPool();
        Task task = new Task(products, 0, products.size(), 0.2);
        pool.submit(task);

        // 每隔5秒打印池中变化的信息
        do {
            System.out.println("Main: Thread count:" + pool.getActiveThreadCount());
            System.out.println("Main: Thread steal:" + pool.getStealCount());
            System.out.println("Main: Parallelism:" + pool.getParallelism());
            TimeUnit.MICROSECONDS.sleep(5);
        } while (!task.isDone());

        pool.shutdown();

        // 检查任务是否正常结束
        if (task.isCompletedNormally()) {
            System.out.println("Main: The process has completed normally.");
        }

        // 检查任务是否正确结束
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getPrice() != 12) {
                System.out.println("Product " + product.getName() + ":" + product.getPrice());
            }
        }

        System.out.println("Main: End fo the program.");
    }
}
