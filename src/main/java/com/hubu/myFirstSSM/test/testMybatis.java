package com.hubu.myFirstSSM.test;

import com.hubu.myFirstSSM.pojo.Administrator;
import com.hubu.myFirstSSM.service.AdministratorService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class testMybatis {

    @Autowired
    AdministratorService administratorService;

    @Test
    public void testAdministrator(){
        Administrator admin = administratorService.getByNameAndPassword("root","123");

        if(admin==null)
            System.out.println("null");
        else System.out.println("exist");

    }
}
