package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.mapper.CategoryMapper;
import com.hubu.myFirstSSM.pojo.Category;
import com.hubu.myFirstSSM.pojo.CategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("id asc");
        return categoryMapper.selectByExample(categoryExample);
    }


    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
}
