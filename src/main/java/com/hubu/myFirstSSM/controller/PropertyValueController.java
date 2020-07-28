package com.hubu.myFirstSSM.controller;

import com.hubu.myFirstSSM.pojo.Product;
import com.hubu.myFirstSSM.pojo.Property;
import com.hubu.myFirstSSM.pojo.PropertyValue;
import com.hubu.myFirstSSM.service.ProductService;
import com.hubu.myFirstSSM.service.PropertyService;
import com.hubu.myFirstSSM.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ProductService productService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model,int pid){
        Product p = productService.get(pid);

        List<PropertyValue> pvs = propertyValueService.list(pid);

        model.addAttribute("p",p);
        model.addAttribute("pvs",pvs);

        return "admin/editPropertyValue";
    }


    @ResponseBody
    @RequestMapping("admin_propertyValue_update")
    public String update(@RequestBody PropertyValue propertyValue){
        propertyValueService.update(propertyValue);
        return "success";
    }

}
