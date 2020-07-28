package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.mapper.ReviewMapper;
import com.hubu.myFirstSSM.pojo.Review;
import com.hubu.myFirstSSM.pojo.ReviewExample;
import com.hubu.myFirstSSM.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKey(review);
    }

    @Override
    public Review get(int id) {

        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id asc");
        List<Review> result = reviewMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    public void setUser(Review review){
        User user = userService.get(review.getUid());
        review.setUser(user);
    }

    public void setUser(List<Review> reviews){
        for (Review r: reviews) {
            setUser(r);
        }
    }
    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
}
