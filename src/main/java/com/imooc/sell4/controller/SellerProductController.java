package com.imooc.sell4.controller;

import com.imooc.sell4.dataobject.ProductCategory;
import com.imooc.sell4.dataobject.ProductInfo;
import com.imooc.sell4.exception.SellException;
import com.imooc.sell4.form.ProductForm;
import com.imooc.sell4.service.CategoryService;
import com.imooc.sell4.service.impl.ProductServiceImpl;
import com.imooc.sell4.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String ,Object>map)
    {
        PageRequest pageRequest=new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage=productService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage);
        map.put("currentpage",page);
        map.put("size",size);

        return new ModelAndView("product/list",map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId")String productId,
                                Map<String,Object>map)
    {
        try {
            productService.offSale(productId);
        }catch (SellException e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId")String productId,
                                Map<String,Object>map)
    {
        try {
            productService.onSale(productId);
        }catch (SellException e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }


    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId" ,required = false)String productId,
                              Map<String,Object>map)
    {
        if(!StringUtils.isEmpty(productId))
        {
            ProductInfo productInfo=productService.findOne(productId);
            map.put("productInfo",productInfo);


        }
        List<ProductCategory> productCategoryList=categoryService.findAll();
        map.put("categoryList",productCategoryList);

        return new ModelAndView("product/index",map);

    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object>map)
    {
        if(bindingResult.hasErrors())
        {
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo=new ProductInfo();
        try {
            if(!StringUtils.isEmpty(productForm.getProductId()))
            {
                productInfo = productService.findOne(productForm.getProductId());
            }

            BeanUtils.copyProperties(productForm, productInfo);
            if(StringUtils.isEmpty(productForm.getProductId()))
            {
                productInfo.setProductId(KeyUtils.genUniqueKey());
            }
            productService.save(productInfo);

        }catch (SellException e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);


    }



}
