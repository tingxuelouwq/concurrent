package com.kevin.forkjoin.noret;

/**
 * @class: Product
 * @package: com.kevin.forkjoin.noreturn
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/16 23:20
 * @version: 1.0
 * @desc: 产品类
 */
public class Product {

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
