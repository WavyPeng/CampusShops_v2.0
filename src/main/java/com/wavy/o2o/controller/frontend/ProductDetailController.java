package com.wavy.o2o.controller.frontend;

import com.wavy.o2o.entity.Product;
import com.wavy.o2o.service.IProductService;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
    @Autowired
    private IProductService productService;

    /**
     * 根据商品Id获取商品详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproductdetailpageinfo")
    @ResponseBody
    private Map<String,Object> listProductDetailPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        long productId = TypeUtil.getLong(request,"productId");
        if(productId != -1){
            // 根据商品Id获取商品详情
            Product product = productService.getProductById(productId);
            modelMap.put("success", true);
            modelMap.put("product", product);
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }
}