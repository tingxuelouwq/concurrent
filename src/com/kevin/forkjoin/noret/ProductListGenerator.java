package com.kevin.forkjoin.noret;

import java.util.ArrayList;
import java.util.List;

/**
 * @class: ProductListGenerator
 * @package: com.kevin.forkjoin.noreturn
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/16 23:23
 * @version: 1.0
 * @desc: 产品生成类
 */
public class ProductListGenerator {
    public List<Product> generate(int size) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Product product = new Product("Product" + i, 10);
            products.add(product);
        }
        return products;
    }
}
