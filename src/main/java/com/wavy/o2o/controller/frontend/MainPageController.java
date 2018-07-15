package com.wavy.o2o.controller.frontend;

import com.wavy.o2o.entity.HeadLine;
import com.wavy.o2o.entity.ShopCategory;
import com.wavy.o2o.service.IHeadLineService;
import com.wavy.o2o.service.IShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/6/13.
 */
@RequestMapping("/frontend")
@Controller
public class MainPageController {
    @Autowired
    private IHeadLineService headLineService;
    @Autowired
    private IShopCategoryService shopCategoryService;

    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listMainPageInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<HeadLine> headLineList = new ArrayList<>();
        // 获取头条信息
        try{
            // 获取状态为1（可用）的头条列表
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        // 获取店铺种类信息
        try{
            // 获取一级店铺类别列表(即parentId为空的ShopCategory)
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList", shopCategoryList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success",true);
        return modelMap;
    }
}