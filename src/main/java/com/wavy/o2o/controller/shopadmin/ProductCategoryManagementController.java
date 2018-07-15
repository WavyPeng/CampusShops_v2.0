package com.wavy.o2o.controller.shopadmin;

import com.wavy.o2o.dto.ProductCategoryDto;
import com.wavy.o2o.dto.ResultDto;
import com.wavy.o2o.entity.ProductCategory;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.enums.ProductCategoryStateEnum;
import com.wavy.o2o.exception.ProductCategoryOperationException;
import com.wavy.o2o.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/6/11.
 */
@RequestMapping("/shopadmin")
@Controller
public class ProductCategoryManagementController {
    @Autowired
    private IProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private ResultDto<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
        // 从session中获取当前店铺信息
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if(currentShop!=null && currentShop.getShopId()>0){
            long shopId = currentShop.getShopId();
            list = productCategoryService.getProductCategoryList(shopId);
            return new ResultDto<>(true,list);
        }else{
            ProductCategoryStateEnum state = ProductCategoryStateEnum.INNER_ERROR;
            return new ResultDto<>(false,state.getStateInfo(),state.getState());
        }
    }

    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                   HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 从session中获取当前店铺
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if(productCategoryList!=null && productCategoryList.size()>0){
            // 为商品类别关联店铺id
            for(ProductCategory pc:productCategoryList){
                pc.setShopId(currentShop.getShopId());
            }
            try{
                ProductCategoryDto productCategoryDto = productCategoryService.addProductCategory(productCategoryList);
                if(productCategoryDto.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",productCategoryDto.getStateInfo());
                }
            }catch (ProductCategoryOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;
    }

    @RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(productCategoryId != null && productCategoryId > 0){
            try{
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                long shopId = currentShop.getShopId();
                ProductCategoryDto productCategoryDto = productCategoryService.deleteProductCategory(productCategoryId,shopId);
                if(productCategoryDto.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",productCategoryDto.getStateInfo());
                }
            }catch (ProductCategoryOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少选择一个待删除的商品类别");
        }
        return modelMap;
    }
}