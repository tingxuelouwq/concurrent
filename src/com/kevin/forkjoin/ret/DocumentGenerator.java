package com.kevin.forkjoin.ret;

import java.util.Random;

/**
 * @class: DocumentGenerator
 * @package: com.kevin.forkjoin.ret
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/17 9:59
 * @version: 1.0
 * @desc: 文档生成器
 */
public class DocumentGenerator {

    private String[] words = {"the", "hello", "goodbye", "packet", "java", "thread", "pool", "random", "class", "main"};

    public String[][] generateDocument(int numLines, int numWordsPerLine, String keyword) {
        int counter = 0;    // 记录单词在文档中出现的次数
        String[][] document = new String[numLines][numWordsPerLine];
        Random random = new Random();
        int index = 0;

        for (int i = 0; i < numLines; i++) {
            for (int j = 0; j < numWordsPerLine; j++) {
                index = random.nextInt(words.length);
                document[i][j] = words[index];
                if (document[i][j].equals(keyword)) {
                    counter++;
                }
            }
        }

        // 将关键词在文档中出现的次数打印在控制台上
        System.out.println("DocumentMock: The word appears " + counter + " times in the document.");

        return document;
    }
}
