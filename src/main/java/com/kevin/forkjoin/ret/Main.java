package com.kevin.forkjoin.ret;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @class: Main
 * @package: com.kevin.forkjoin.ret
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/17 11:00
 * @version: 1.0
 * @desc: 主程序
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int numLines = 100;
        int numWordsPerLine = 100_000;
        String keyword = "the";
        DocumentGenerator generator = new DocumentGenerator();
        String[][] document = generator.generateDocument(numLines, numWordsPerLine, keyword);

        DocumentTask task = new DocumentTask(document, 0, document.length, keyword);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);

        do {
            System.out.printf("*********************************\n");
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            System.out.printf("Main: Active Thread: %d\n", pool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
            System.out.printf("*********************************\n");
            TimeUnit.MICROSECONDS.sleep(100);
        } while (!task.isDone());

        pool.shutdown();

        System.out.printf("Main: The word appears %d in the document\n", task.get());
    }
}
