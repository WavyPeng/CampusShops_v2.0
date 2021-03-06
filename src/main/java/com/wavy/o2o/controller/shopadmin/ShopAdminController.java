package com.wavy.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 解析路由并转发到相应的html中
 * Created by WavyPeng on 2018/6/4.
 */
@Controller
@RequestMapping(value = "/shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping(value = "/shopoperation")
    public String shopOperation() {
        // 转发至店铺注册/编辑页面
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList() {
        // 转发至店铺列表页面
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement() {
        // 转发至店铺管理页面
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
    private String productCategoryManage() {
        // 转发至商品类别管理页面
        return "shop/product-category-management";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation() {
        // 转发至商品添加/编辑页面
        return "shop/product-operation";
    }

    @RequestMapping(value = "/productmanagement")
    public String productManagement() {
        // 转发至商品管理页面
        return "shop/product-management";
    }

    @RequestMapping(value = "/shopauthmanagement")
    public String shopAuthManagement(){
        // 转发至店铺授权页面
        return "shop/shopauthmanagement";
    }

    @RequestMapping(value = "/shopauthedit")
    public String shopAuthEdit(){
        // 转发至授权信息修改页面
        return "shop/shopauthedit";
    }

    @RequestMapping(value = "/operationsuccess",method = RequestMethod.GET)
    private String operationSuccess(){
        return "shop/operationsuccess";
    }

    @RequestMapping(value = "/operationfail",method = RequestMethod.GET)
    private String operationFail(){
        return "shop/operationfail";
    }

    @RequestMapping(value = "/productbuycheck",method = RequestMethod.GET)
    private String productBuyCheck(){
        // 转发至店铺的消费记录的页面
        return "shop/productbuycheck";
    }

    @RequestMapping(value = "/awardmanagement",method = RequestMethod.GET)
    private String awardManagement(){
        // 奖品管理路由
        return "shop/awardmanagement";
    }

    @RequestMapping(value = "/awardoperation",method = RequestMethod.GET)
    private String awardEdit(){
        // 奖品编辑路由
        return "shop/awardoperation";
    }

    @RequestMapping(value = "/usershopcheck",method = RequestMethod.GET)
    private String userShopCheck(){
        // 店铺用户积分统计路由
        return "shop/usershopcheck";
    }

    @RequestMapping(value = "/awarddelivercheck",method = RequestMethod.GET)
    private String awardDeliverCheck(){
        // 店铺用户积分兑换路由
        return "shop/awarddelivercheck";
    }
}