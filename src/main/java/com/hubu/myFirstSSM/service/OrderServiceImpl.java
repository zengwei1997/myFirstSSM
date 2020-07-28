package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.mapper.OrderItemMapper;
import com.hubu.myFirstSSM.mapper.OrderMapper;
import com.hubu.myFirstSSM.pojo.Order;
import com.hubu.myFirstSSM.pojo.OrderExample;
import com.hubu.myFirstSSM.pojo.OrderItem;
import com.hubu.myFirstSSM.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public void add(Order o) {
        orderMapper.insert(o);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order o) {
        orderMapper.updateByPrimaryKey(o);
    }

    @Override
    public Order get(int id) {
        Order o = orderMapper.selectByPrimaryKey(id);
        setUser(o);
        return o;
    }

    @Override
    public List list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id asc");
        List<Order> os = orderMapper.selectByExample(example);
        setUser(os);
        return os;
    }

    @Override
    public List list(int uid, String excludedStatus) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");

        return orderMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        //添加新的订单
        add(o);


        if(false)
            throw new RuntimeException();

        for (OrderItem oi:ois) {
            //给提交的订单项添加Oid
            oi.setOid(o.getId());
            orderItemService.update(oi);
            total += oi.getNumber()*oi.getProduct().getPromotePrice();
        }
            return total;
    }

    public void setUser(Order o) {
        User u = userService.get(o.getUid());
        o.setUser(u);
    }
    public void setUser(List<Order> os) {
        for (Order o: os) {
            setUser(o);
        }
    }
}
