package com.kevin.forkjoin.async;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @class: FolderProcessor
 * @package: com.kevin.forkjoin.async
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/17 11:11
 * @version: 1.0
 * @desc: 文件夹任务
 */
public class FolderProcessor extends RecursiveTask<List<String>> {

    private String pathname;    // 文件夹的绝对路径
    private String extension;   // 要查找的文件扩展名

    public FolderProcessor(String pathname, String extension) {
        this.pathname = pathname;
        this.extension = extension;
    }

    @Override
    protected List<String> compute() {
        List<String> list = new ArrayList<>();
        List<FolderProcessor> tasks = new ArrayList<>();

        File file = new File(pathname);
        if (!file.exists()) {
            System.err.println("File " + file + " is not exists.");
            return null;
        }

        File[] content = file.listFiles();
        if (content != null) {
            for (int i = 0; i < content.length; i++) {
                if (content[i].isDirectory()) {
                    FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);
                    task.fork();    // 异步执行子任务
                    tasks.add(task);
                } else {
                    if (content[i].getAbsolutePath().endsWith(extension)) {
                        list.add(content[i].getAbsolutePath());
                    }
                }
            }
        }

        for (FolderProcessor task : tasks) {
            list.addAll(task.join());
        }

        return list;
    }
}
