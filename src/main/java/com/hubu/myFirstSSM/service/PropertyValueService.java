package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.pojo.Product;
import com.hubu.myFirstSSM.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    void init(Product p);

    void update(PropertyValue pv);

    PropertyValue get(int pid,int ptid);

    List<PropertyValue> list(int pid);
}
