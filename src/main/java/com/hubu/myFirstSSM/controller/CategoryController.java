package com.hubu.myFirstSSM.controller;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubu.myFirstSSM.pojo.Category;
import com.hubu.myFirstSSM.service.CategoryService;
import com.hubu.myFirstSSM.util.ImageUtil;

import com.hubu.myFirstSSM.util.Page;
import com.hubu.myFirstSSM.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){


        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category>  cs = categoryService.list();
        int total = (int)new PageInfo<>(cs).getTotal();
        page.setTotal(total);

        model.addAttribute("cs",cs);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.add(c);
        File file = new File(session.getServletContext().getRealPath("img/category"),c.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        //复制文件到file里边
        uploadedImageFile.getImage().transferTo(file);

        //将上传的文件转成.jpg格式的图片文件
        BufferedImage image = ImageUtil.change2jpg(file);
        ImageIO.write(image,".jpg",file);

        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) throws IOException {
        categoryService.delete(id);

        File file = new File(session.getServletContext().getRealPath("img/category"),id+".jpg");

        file.delete();

        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String edit(int id, Model model){
        Category c = categoryService.get(id);
        model.addAttribute("cat",c);

        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category c,UploadedImageFile uploadedImageFile,HttpSession s) throws IOException{
        categoryService.update(c);
        MultipartFile img = uploadedImageFile.getImage();
        if(null != img && !img.isEmpty()){
            File file = new File(s.getServletContext().getRealPath("img/category"),c.getId()+".jpg");
            img.transferTo(file);
            BufferedImage bi = ImageUtil.change2jpg(file);
            ImageIO.write(bi,".jpg",file);
        }
        return "redirect:admin_category_list";
    }


}
