package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> list();
    Category get(int id);

    void add(Category category);
    void delete(int id);
    void update(Category category);
}
