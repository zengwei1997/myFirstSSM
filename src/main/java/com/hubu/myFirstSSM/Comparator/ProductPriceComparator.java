package com.hubu.myFirstSSM.Comparator;

import com.hubu.myFirstSSM.pojo.Product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return Float.compare(o1.getPromotePrice(),o2.getPromotePrice());
    }
}
