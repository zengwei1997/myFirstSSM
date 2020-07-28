package com.hubu.myFirstSSM.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubu.myFirstSSM.pojo.Order;
import com.hubu.myFirstSSM.service.OrderItemService;
import com.hubu.myFirstSSM.service.OrderService;
import com.hubu.myFirstSSM.util.Page;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {
    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @RequestMapping("admin_order_list")
    public String list(Page page, Model model){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Order> os = orderService.list();
        int total = (int)new PageInfo<>(os).getTotal();
        page.setTotal(total);

        orderItemService.fill(os);

        model.addAttribute("page",page);
        model.addAttribute("os",os);

        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delivery")
    public String delivery(@RequestParam(value = "id") int id) throws IOException {
        Order o = orderService.get(id);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return "redirect:admin_order_list";
    }
}
