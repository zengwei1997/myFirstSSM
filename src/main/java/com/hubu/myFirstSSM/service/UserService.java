package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.pojo.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void delete(int id);
    void update(User user);

    User get(int id);
    List list();

    Boolean isExist(String name);

    User get(String name,String password);
}
