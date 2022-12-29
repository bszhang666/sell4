package com.imooc.sell4.controller;

import com.imooc.sell4.VO.ProductInfoVO;
import com.imooc.sell4.VO.ProductVO;
import com.imooc.sell4.VO.ResultVO;
import com.imooc.sell4.dataobject.ProductCategory;
import com.imooc.sell4.dataobject.ProductInfo;
import com.imooc.sell4.service.impl.CategoryServiceImpl;
import com.imooc.sell4.service.impl.ProductServiceImpl;
import com.imooc.sell4.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;


    @GetMapping("/list")
    public ResultVO list()
    {
        //查询所有上架商品
        List<ProductInfo> productInfoList=productService.findUpAll();
        //查询类目
        List<Integer> categoryList=productInfoList.stream().map(item->item.getCategoryType()
                ).collect(Collectors.toList());
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryList);

        //数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList)
        {
            ProductVO productVO=new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList)
            {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType()))
                {
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        return ResultVOUtil.success(productVOList);
    }
}
