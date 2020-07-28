package com.hubu.myFirstSSM.controller;

import com.hubu.myFirstSSM.pojo.Administrator;
import com.hubu.myFirstSSM.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;

    @RequestMapping("admin")
    public String login(){
        return "admin/login";
    }

    @RequestMapping("admin_login")
    public String doLogin(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password, HttpSession session, Model model){
        name = HtmlUtils.htmlEscape(name);
        Administrator admin = administratorService.getByNameAndPassword(name,password);
        if(null == admin){
            String message = "用户名或密码错误，请重新输入！";
            model.addAttribute("msg",message);
            return "fore/login";
        }

        session.setAttribute("admin",admin);
        return "redirect:admin_category_list";
    }
}
