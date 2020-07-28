package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.pojo.Administrator;

import java.util.List;

public interface AdministratorService {
    void add(Administrator administrator);
    void delete(int id);
    void update(Administrator administrator);
    Administrator get(int id);
    List<Administrator> list();
    Administrator getByNameAndPassword(String name,String password);
}
