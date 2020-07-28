package com.hubu.myFirstSSM.mapper;

import com.hubu.myFirstSSM.pojo.Administrator;

import java.util.List;

public interface AdministratorMapper {
    void add(Administrator administrator);
    void delete(int id);
    void update(Administrator administrator);
    Administrator get(int id);

    List<Administrator> list();

    Administrator getByNameAndPassword(String name,String password);
}
