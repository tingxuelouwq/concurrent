package com.kevin.forkjoin.async;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @class: Main
 * @package: com.kevin.forkjoin.async
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/17 11:19
 * @version: 1.0
 * @desc:
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        String pathname = "D:\\Workspace";
        String extension = ".java";

        ForkJoinPool pool = new ForkJoinPool();
        FolderProcessor task = new FolderProcessor(pathname, extension);
        pool.submit(task);

        do {
            System.out.printf("*********************************\n");
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            System.out.printf("Main: Active Thread: %d\n", pool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
            System.out.printf("*********************************\n");
            TimeUnit.SECONDS.sleep(1);
        } while (!task.isDone());

        pool.shutdown();

        List<String> list = task.join();
        for (String file : list) {
            System.out.println(file);
        }
    }
}
