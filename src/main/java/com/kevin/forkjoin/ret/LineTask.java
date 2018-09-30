package com.kevin.forkjoin.ret;

import com.kevin.forkjoin.noret.Task;

import java.util.concurrent.RecursiveTask;

/**
 * @class: LineTask
 * @package: com.kevin.forkjoin.ret
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/17 10:43
 * @version: 1.0
 * @desc: 行任务
 */
public class LineTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;
    private String line[];
    private int start;
    private int end;
    private String keyword;

    public LineTask(String[] line, int start, int end, String keyword) {
        this.line = line;
        this.start = start;
        this.end = end;
        this.keyword = keyword;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (end - start < THRESHOLD) {
            result = count(line, start, end, keyword);
        } else {
            int mid = (start + end) / 2;
            LineTask task1 = new LineTask(line, start, mid, keyword);
            LineTask task2 = new LineTask(line, mid, end, keyword);
            invokeAll(task1, task2);
            result = task1.join() + task2.join();
        }
        return result;
    }

    private int count(String[] line, int start, int end, String keyword) {
        int counter = 0;
        for (int i = start; i < end; i++) {
            if (line[i].equals(keyword)) {
                counter++;
            }
        }
        return counter;
    }
}
