package com.wavy.o2o.controller.frontend;

import com.wavy.o2o.dto.ProductDto;
import com.wavy.o2o.entity.Product;
import com.wavy.o2o.entity.ProductCategory;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.service.IProductCategoryService;
import com.wavy.o2o.service.IProductService;
import com.wavy.o2o.service.IShopService;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private IShopService shopService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺下面的商品类别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopDetailPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取shopId
        long shopId = TypeUtil.getLong(request,"shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if(shopId!=-1){
            // 获取店铺信息
            shop = shopService.getByShopId(shopId);
            // 获取店铺的商品类别
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("success", true);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 依据查询条件分页列出该店铺下面的所有商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listProductsByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取页码
        int pageIndex = TypeUtil.getInt(request,"pageIndex");
        // 获取每页显示的记录数
        int pageSize = TypeUtil.getInt(request,"pageSize");
        // 获取店铺Id
        long shopId = TypeUtil.getLong(request,"shopId");
        if(pageIndex > -1 && pageSize > -1 && shopId > -1){
            // 获取商品类别Id
            long productCategoryId = TypeUtil.getLong(request, "productCategoryId");
            // 获取商品名
            String productName = TypeUtil.getString(request, "productName");
            // 组合查询条件
            Product productCondition = compactProductConditionForSearch(shopId, productCategoryId, productName);
            // 按照传入的查询条件以及分页信息返回相应商品列表以及总数
            ProductDto pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 整合商品查询条件，并封装在productCondition中返回
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductConditionForSearch(long shopId, long productCategoryId, String productName){
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if(productCategoryId!=-1L){
            // 查询某个商品类别下的商品列表
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            // 查询名字里包含productName的店铺列表
            productCondition.setProductName(productName);
        }
        // 只允许选出状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}