package com.hubu.myFirstSSM.service;




import com.hubu.myFirstSSM.pojo.Order;
import com.hubu.myFirstSSM.pojo.OrderItem;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderItemService {
    /*订单项
       fill(Order o)
       fill(List<Order> os)
       给每个订单项装入非数据库字段Product
     */
    void add(OrderItem orderItem);
    void delete(int id);
    void update(OrderItem orderItem);

    OrderItem get(int id);
    List<OrderItem> list();

    void fill(Order o);
    void fill(List<Order> os);

    int getSaleCount(int pid);

    List<OrderItem> listByUser(int uid);
}
