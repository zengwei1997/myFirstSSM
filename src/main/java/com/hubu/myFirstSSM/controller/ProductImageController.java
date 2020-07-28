package com.hubu.myFirstSSM.controller;


import com.hubu.myFirstSSM.pojo.Product;
import com.hubu.myFirstSSM.pojo.ProductImage;
import com.hubu.myFirstSSM.service.ProductImageService;
import com.hubu.myFirstSSM.service.ProductService;
import com.hubu.myFirstSSM.util.ImageUtil;
import com.hubu.myFirstSSM.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, HttpSession s, UploadedImageFile uploadedImageFile){
        productImageService.add(pi);
        //单个产品多张图片，图片的id
        String fileName = pi.getId()+ ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder = s.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = s.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = s.getServletContext().getRealPath("img/productSingle_middle");
        }else {
            imageFolder = s.getServletContext().getRealPath("img/productDetail");
        }
        File f = new File(imageFolder,fileName);
        f.getParentFile().mkdirs();

        try{
            //上传的文件复制到f文件中
            uploadedImageFile.getImage().transferTo(f);
            BufferedImage bi = ImageUtil.change2jpg(f);
            //确保是.jpg格式
            ImageIO.write(bi,".jpg",f);


            if(ProductImageService.type_single.equals(pi.getType())){
                File f_small = new File(imageFolder_small,fileName);
                File f_middle = new File(imageFolder_middle,fileName);
                ImageUtil.resizeImage(f,56,56,f_small);
                ImageUtil.resizeImage(f,217,190,f_middle);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid="+pi.getPid();

    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession s){
        ProductImage pi = productImageService.get(id);

        //单个产品多张图片，图片的id
        String fileName = pi.getId()+ ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder = s.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = s.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = s.getServletContext().getRealPath("img/productSingle_middle");
            File f = new File(imageFolder,fileName);
            File f_small = new File(imageFolder_small,fileName);
            File f_middle = new File(imageFolder_middle,fileName);
            f.delete();
            f_small.delete();
            f_middle.delete();
        }else {
            imageFolder = s.getServletContext().getRealPath("img5/productDetail");

            File f_detail = new File(imageFolder,fileName);
            f_detail.delete();
        }
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid="+pi.getPid();

    }


    @RequestMapping("admin_productImage_list")
    public String list(Model model,int pid){
        //导航栏有，增加图片时候隐藏属性p.id和type
        Product p = productService.get(pid);

        List<ProductImage> pisSingle = productImageService.list(pid,ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid,ProductImageService.type_detail);
        model.addAttribute("p",p);
        model.addAttribute("pisSingle",pisSingle);
        model.addAttribute("pisDetail",pisDetail);
        return "admin/listProductImage";
    }


}
