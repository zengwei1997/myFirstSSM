package com.hubu.myFirstSSM.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubu.myFirstSSM.pojo.Category;
import com.hubu.myFirstSSM.pojo.Product;


import com.hubu.myFirstSSM.service.CategoryService;
import com.hubu.myFirstSSM.service.ProductService;
import com.hubu.myFirstSSM.util.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;



@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_add")
    public String add(Model model, Product p){
        p.setCreateDate(new Date());
        productService.add(p);
        return "redirect:admin_product_list?cid="+p.getCid();

    }

    @RequestMapping("admin_product_delete")
    public String delete(int id){
        Product p = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    //在productServiceImpl中的get中已经装载过category了
    @RequestMapping("admin_product_edit")
    public String edit(Model model,int id){
        Product p = productService.get(id);

        model.addAttribute("p",p);

        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product p){
        productService.update(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    @RequestMapping("admin_product_list")
    public String list(Model model, Page page,int cid){

        Category c = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //productServiceImpl中的list 调用了setCategory，所以ps中的所有Product中的cid一样，且已经装填过category了
        List<Product> ps = productService.list(cid);



        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());

        model.addAttribute("ps",ps);
        model.addAttribute("c",c);
        model.addAttribute("page",page);
        return "admin/listProduct";
    }

}
