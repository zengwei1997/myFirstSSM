package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.mapper.AdministratorMapper;
import com.hubu.myFirstSSM.pojo.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    AdministratorMapper administratorMapper;

    @Override
    public void add(Administrator administrator) {
        administratorMapper.add(administrator);
    }

    @Override
    public void delete(int id) {
        administratorMapper.delete(id);
    }

    @Override
    public void update(Administrator administrator) {
        administratorMapper.update(administrator);
    }

    @Override
    public Administrator get(int id) {
        return administratorMapper.get(id);
    }

    @Override
    public List<Administrator> list() {
        return administratorMapper.list();
    }

    @Override
    public Administrator getByNameAndPassword(String name, String password) {
        return administratorMapper.getByNameAndPassword(name,password);
    }
}
