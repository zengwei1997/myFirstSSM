package com.hubu.myFirstSSM.controller;


import com.github.pagehelper.util.StringUtil;
import com.hubu.myFirstSSM.Comparator.*;
import com.hubu.myFirstSSM.pojo.*;
import com.hubu.myFirstSSM.service.*;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    PropertyValueService propertyValueService;

    @RequestMapping("forehome")
    public String home(Model model){
        List<Category> cs = categoryService.list();
        //分别给每个分类 装填该分类下的产品
        productService.fill(cs);

        //给每个分类， 搞个二维product数组，每行8个产品展示该分类下所有的产品
        productService.fillByRow(cs);

        model.addAttribute("cs",cs);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(Model model, User user){
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);

        boolean isExist = userService.isExist(name);
        if(isExist){
            String m ="用户名已经被使用,不能使用";
            model.addAttribute("msg", m);
            model.addAttribute("user",null);
            return "fore/register";
        }

        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    @RequestMapping("forelogin")
    public String login(Model model, @RequestParam(value = "name") String name, @RequestParam(value = "password") String password, HttpSession session){
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);
        if(user == null){
            String message = "用户名或密码错误，请重新输入！";
            model.addAttribute("msg",message);
            return "fore/login";
        }
        //注意model是request级别的，session是会话级的
        session.setAttribute("user",user);
        return "redirect:forehome";

    }

    //模态登录框：购买商品和加入购物车，判断是否登录
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session){
        User user =(User) session.getAttribute("user");
        if(null != user){
            return "success";
        }
        return "fail";
    }
    //模态登录框：判断是否输入正确的用户名密码
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam(value = "name") String name,@RequestParam(value = "password") String password, HttpSession session){
        name = HtmlUtils.htmlEscape(name);
        User u = userService.get(name,password);
        if(null == u) {
            return "fail";
        }
        session.setAttribute("user",u);

        return "success";
    }




    @RequestMapping("forelogout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String product(Model model,@RequestParam(value = "pid") int pid){
        Product p = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.list(pid,ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(pid,ProductImageService.type_detail);

        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);

        List<Review> rs = reviewService.list(pid);
        List<PropertyValue> pvs = propertyValueService.list(pid);

        int saleCount = orderItemService.getSaleCount(pid);
        int reviewCount = reviewService.getCount(pid);
        p.setReviewCount(reviewCount);
        p.setSaleCount(saleCount);

        model.addAttribute("p",p);
        model.addAttribute("reviews",rs);
        model.addAttribute("pvs",pvs);
        return "fore/product";
    }

    @RequestMapping("forecategory")
    public String category(int cid,String sort,Model model){
        Category c = categoryService.get(cid);
        //放入非数据库字段products
        productService.fill(c);
        //给每个产品放入非数据库字段销量和评价数量
        List<Product> ps = c.getProducts();
        productService.setSaleAndReviewNumber(ps);

        if(null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(c.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(c.getProducts(),new ProductDateComparator());
                    break;

                case "saleCount" :
                    Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(c.getProducts(),new ProductAllComparator());
                    break;

            }
        }
        model.addAttribute("c",c);
        return "fore/category";
    }

    @RequestMapping("foresearch")
    public String search(String keyword, Model model){

        List<Product> ps = productService.search(keyword);

        productService.setSaleAndReviewNumber(ps);

        model.addAttribute("ps",ps);

        return "fore/searchResult";
    }

    @RequestMapping("forebuyone")
    public String buyone(int pid,int num,HttpSession session){
        User u = (User) session.getAttribute("user");
        int oiid = 0;

        List<OrderItem> ois = orderItemService.listByUser(u.getId());
        boolean found = false;

        for (OrderItem o: ois) {
            if(o.getPid().intValue() == pid) {
                found = true;
                o.setNumber(o.getNumber()+num);
                orderItemService.update(o);
                oiid = o.getId();
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setPid(pid);
            oi.setNumber(num);
            oi.setUid(u.getId());
            orderItemService.add(oi);

            oiid = oi.getId();
        }
        return "redirect:forebuy?oiid="+oiid;
    }

    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid,int num,HttpSession session){
        User u = (User) session.getAttribute("user");


        List<OrderItem> ois = orderItemService.listByUser(u.getId());
        boolean found = false;

        for (OrderItem o: ois) {
            if(o.getPid().intValue() == pid) {
                found = true;
                o.setNumber(o.getNumber()+num);
                orderItemService.update(o);
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setPid(pid);
            oi.setNumber(num);
            oi.setUid(u.getId());
            orderItemService.add(oi);
        }
        return "success";
    }

    @RequestMapping("forecart")
    public String cart(Model model,HttpSession session){
        User user =(User)session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        model.addAttribute("ois",ois);
        return "fore/cart";
    }

    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(int pid,int number,HttpSession session){

        User u = (User) session.getAttribute("user");
        if(null == u){
            return "fail";
        }

        List<OrderItem> ois = orderItemService.listByUser(u.getId());
        for (OrderItem oi: ois) {
            if(oi.getProduct().getId() == pid){
                oi.setNumber(number);
                orderItemService.update(oi);
                break;
            }
        }
        return "success";
    }

    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(@RequestParam(value = "oiid") int oiid,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(null == user)
            return "fail";
        orderItemService.delete(oiid);
        return "success";
    }



    @RequestMapping("forebuy")
    public String buy(Model model,String oiid[],HttpSession session){
        float total = 0;
        List<OrderItem> ois = new ArrayList<>();

        for(String strid:oiid){
            int id  = Integer.parseInt(strid);
            OrderItem oi = orderItemService.get(id);
            total += oi.getNumber()*oi.getProduct().getPromotePrice();
            ois.add(oi);
        }
        session.setAttribute("ois",ois);
        model.addAttribute("total",total);
        return "fore/buy";
    }

    @RequestMapping("forecreateOrder")
    public String createOrder(Model model,Order order,HttpSession session){
        User user =(User) session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setCreateDate(new Date());
        order.setOrderCode(orderCode);
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);

        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");

        float total = orderService.add(order,ois);

        return "redirect:forealipay?oid="+order.getId()+"&total="+total;
    }



    @RequestMapping("forepayed")
    public String payed(int oid,float total,Model model){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);

        model.addAttribute("o",order);
        return "fore/payed";
    }

    //跳到订单信息页面
    @RequestMapping("forebought")
    public String bought(Model model,HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Order> os = orderService.list(user.getId(),OrderService.delete);

        //装填非数据库字段OrderItem
        orderItemService.fill(os);
        model.addAttribute("os",os);
        return "fore/bought";
    }

    //删除订单
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid,Model model){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return "success";
    }

    @RequestMapping("foreconfirmPay")
    public String confirmPay(int oid,Model model){
        Order order = orderService.get(oid);
        orderItemService.fill(order);

        model.addAttribute("o",order);
        return "fore/confirmPay";
    }

    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(int oid,Model model){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date());
        orderService.update(o);
        return "fore/orderConfirmed";
    }

    @RequestMapping("forereview")
    public String review(Model model,int oid){
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        Product product = order.getOrderItems().get(0).getProduct();

        List<Review> reviews = reviewService.list(product.getId());
        productService.setSaleAndReviewNumber(product);

        model.addAttribute("o",order);
        model.addAttribute("reviews",reviews);
        model.addAttribute("p",product);
        return "fore/review";
    }


    @RequestMapping("foredoreview")
    public String doreview(HttpSession session,Model model,int pid,int oid,String content){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        Product p = productService.get(pid);

        content = HtmlUtils.htmlEscape(content);

        User u = (User) session.getAttribute("user");

        Review r = new Review();
        r.setPid(pid);
        r.setUid(u.getId());
        r.setContent(content);
        r.setCreateDate(new Date());

        reviewService.add(r);

        return "redirect:forereview?oid="+oid+"&showonly=true";
    }
}
