package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.pojo.Review;

import java.util.List;

public interface ReviewService {
    void add(Review review);
    void delete(int id);
    void update(Review review);
    Review get(int id);

    //同一个产品下的累计评价
    List<Review> list(int pid);
    //获取产品里边的reviewCount
    int getCount(int pid);
}
