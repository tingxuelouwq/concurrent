package com.kevin.forkjoin.ret;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @class: DocumentTask
 * @package: com.kevin.forkjoin.ret
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/17 10:07
 * @version: 1.0
 * @desc: 文档任务
 */
public class DocumentTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;
    private String[][] document;
    private int start;
    private int end;
    private String keyword;

    public DocumentTask(String[][] document, int start, int end, String keyword) {
        this.document = document;
        this.start = start;
        this.end = end;
        this.keyword = keyword;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (end - start < THRESHOLD) {
            result = processLines(document, start, end, keyword);
        } else {
            int mid = (start + end) / 2;
            DocumentTask task1 = new DocumentTask(document, start, mid, keyword);
            DocumentTask task2 = new DocumentTask(document, mid, end, keyword);
            invokeAll(task1, task2);
            result = task1.join() + task2.join();
        }
        
        return result;
    }

    private int processLines(String[][] document, int start, int end, String keyword) {
        List<LineTask> tasks = new ArrayList<>();
        for (int i = start; i < end; i++) {
            LineTask task = new LineTask(document[i], 0, document[i].length, keyword);
            tasks.add(task);
        }
        invokeAll(tasks);

        int result = 0;
        for (int i = 0; i < tasks.size(); i++) {
            result += tasks.get(i).join();
        }

        return result;
    }
}
