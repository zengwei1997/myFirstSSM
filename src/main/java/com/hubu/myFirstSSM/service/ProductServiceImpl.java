package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.mapper.ProductMapper;
import com.hubu.myFirstSSM.pojo.Category;
import com.hubu.myFirstSSM.pojo.Product;
import com.hubu.myFirstSSM.pojo.ProductExample;
import com.hubu.myFirstSSM.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public void add(Product p) {
        productMapper.insert(p);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product p) {
        productMapper.updateByPrimaryKey(p);
    }

    public void setCategory(List<Product> ps){
        for (Product p : ps)
            setCategory(p);
    }
    public void setCategory(Product p){
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }

    //这一步是为什么？？？
    //因为Category是非数据库字段，所以通过selectByPrimaryKey拿到的Product中的category是空的！！
    //需要根据数据库字段cid拿到category装填进Product中。
    @Override
    public Product get(int id) {
        Product p = productMapper.selectByPrimaryKey(id);
        setCategory(p);
        setFirstProductImage(p);
        return p;
    }

    @Override
    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id asc");
        List<Product> result = productMapper.selectByExample(example);
        setCategory(result);
        setFirstProductImage(result);
        return result;
    }

    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> pi = productImageService.list(p.getId(),ProductImageService.type_single);
        if(!pi.isEmpty()){
            ProductImage productImage = pi.get(0);
            p.setFirstProductImage(productImage);
        }
    }


    public void setFirstProductImage(List<Product> ps) {
        for (Product p: ps) {
            setFirstProductImage(p);
        }
    }

    @Override
    public void fill(Category category) {

        //为什么放在ProductService里边
        //因为目的是把查出来的产品放进分类里
        //重点：查出来的产品,所以应该放在能方便查出来的地方即list(int cid)
        List<Product> products = list(category.getId());

        category.setProducts(products);
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category c: categories) {
            fill(c);
        }
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 5;

        for (Category c:categories) {
            List<Product> ps = c.getProducts();
            List<List<Product>> productByEachRow = new ArrayList<>();
                for(int i = 0;i < ps.size();i+=productNumberEachRow){
                    int size = i + productNumberEachRow;
                    size=size > ps.size()?ps.size():size;
                    productByEachRow.add(ps.subList(i,size));
                }
                c.setProductsByRow(productByEachRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        int pid = product.getId();

        int saleCount = orderItemService.getSaleCount(pid);
        int reviewCount = reviewService.getCount(pid);

        product.setSaleCount(saleCount);
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product:products){
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%"+keyword+"%");
        example.setOrderByClause("id asc");
        List<Product> ps = productMapper.selectByExample(example);

        setFirstProductImage(ps);
        setCategory(ps);
        return ps;
    }
}
