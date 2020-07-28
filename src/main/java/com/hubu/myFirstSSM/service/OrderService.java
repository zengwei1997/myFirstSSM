package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.pojo.Order;
import com.hubu.myFirstSSM.pojo.OrderItem;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    float add(Order o,List<OrderItem> ois);
    void add(Order o);
    void delete(int id);
    void update(Order o);
    Order get(int id);
    List list();
    List list(int uid,String excludedStatus);

}
