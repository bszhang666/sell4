package com.imooc.sell4.controller;

import com.imooc.sell4.dataobject.ProductCategory;
import com.imooc.sell4.form.CategoryForm;
import com.imooc.sell4.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object>map)
    {
        List<ProductCategory> categoryList=categoryService.findAll();
        map.put("productcategoryList",categoryList);
        return new ModelAndView("category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false)Integer categoryId,
                              Map<String,Object>map)
    {
        if(categoryId!=null)
        {
            ProductCategory productCategory=categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }

        return new ModelAndView("category/index",map);

    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String,Object> map)
    {
        if(bindingResult.hasErrors())
        {
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory=new ProductCategory();
        if(categoryForm.getCategoryId()!=null)
        {
            productCategory=categoryService.findOne(categoryForm.getCategoryId());

        }
        BeanUtils.copyProperties(categoryForm,productCategory);
        categoryService.save(productCategory);
        map.put("url","list");
        return new ModelAndView("common/success",map);

    }
}
