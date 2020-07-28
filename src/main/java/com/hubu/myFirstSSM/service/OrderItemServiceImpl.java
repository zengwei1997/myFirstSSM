package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.mapper.OrderItemMapper;
import com.hubu.myFirstSSM.pojo.Order;
import com.hubu.myFirstSSM.pojo.OrderItem;
import com.hubu.myFirstSSM.pojo.OrderItemExample;
import com.hubu.myFirstSSM.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    ProductService productService;

    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKey(orderItem);
    }


    @Override
    public OrderItem get(int id) {
        OrderItem oi = orderItemMapper.selectByPrimaryKey(id);
        setProduct(oi);
        return oi;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id asc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);

        setProduct(ois);

        return ois;
    }

    //同一个订单Oid下的所有订单项都装填到Order里边
    @Override
    public void fill(Order o) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id asc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);

        setProduct(ois);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi:ois) {
            total +=oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }

        o.setOrderItems(ois);
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
    }

    @Override
    public void fill(List<Order> os) {
        for (Order o: os) {
            fill(o);
        }
    }

    public void setProduct(OrderItem oi) {
        Product p = productService.get(oi.getPid());
        oi.setProduct(p);
    }

    public void setProduct(List<OrderItem> ois) {
        for (OrderItem oi: ois) {
            setProduct(oi);
        }
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        int saleCount = 0;
        for(OrderItem o:ois){
            saleCount += o.getNumber();
        }
        return saleCount;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> ois = orderItemMapper.selectByExample(example);

        setProduct(ois);
        return ois;
    }
}
