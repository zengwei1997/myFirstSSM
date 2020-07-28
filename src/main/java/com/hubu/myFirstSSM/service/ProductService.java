package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.pojo.Category;
import com.hubu.myFirstSSM.pojo.Product;

import java.util.List;


public interface ProductService {
    void add(Product p);
    void delete(int id);
    void update(Product p);
    Product get(int id);
    List list(int cid);

    void setFirstProductImage(Product p);

    void fill(Category category);
    void fill(List<Category> categories);
    void fillByRow(List<Category> categories);

    void setSaleAndReviewNumber(Product product);
    void setSaleAndReviewNumber(List<Product> products);

    List<Product> search(String keyword);
}
