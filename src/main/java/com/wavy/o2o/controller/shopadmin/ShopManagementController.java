package com.wavy.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ShopDto;
import com.wavy.o2o.entity.Area;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.entity.ShopCategory;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.enums.ShopStateEnum;
import com.wavy.o2o.exception.ShopOperationException;
import com.wavy.o2o.service.IAreaService;
import com.wavy.o2o.service.IShopCategoryService;
import com.wavy.o2o.service.IShopService;
import com.wavy.o2o.util.CodeUtil;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/6/4.
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private IShopService shopService;
    @Autowired
    private IShopCategoryService shopCategoryService;
    @Autowired
    private IAreaService areaService;

    @RequestMapping("/registerShop")
    @ResponseBody
    public Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        // 1.从前端接收并转化相应的参数，包括店铺信息及图片信息
        String shopStr = TypeUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (IOException ioe){
            modelMap.put("success",false);
            modelMap.put("errMsg",ioe.getMessage());
            return modelMap;
        }
        // 处理上传的图片
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        // 2.注册店铺
        if(shop!=null && shopImg!=null){
            // 从session中获取用户信息
            UserInfo user = (UserInfo)request.getSession().getAttribute("user");
            shop.setOwner(user);
            ShopDto shopDto = null;
            try{
                shopDto = shopService.addShop(shop, new ImageDto(shopImg.getOriginalFilename(),shopImg.getInputStream()));
                if(shopDto.getState() == ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                    // 该用户可以操作的店铺列表
                    @SuppressWarnings("unchecked")
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<Shop>();
                    }
                    shopList.add(shopDto.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",shopDto.getStateInfo());
                }
            }catch (IOException ioe){
                modelMap.put("success",false);
                modelMap.put("errMsg",ioe.getMessage());
            }catch (ShopOperationException soe){
                modelMap.put("success",false);
                modelMap.put("errMsg",soe.getMessage());
            }
            return modelMap;
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Long shopId = TypeUtil.getLong(request,"shopId");
        if (shopId > -1) {
            try {
                // 获取店铺信息
                Shop shop = shopService.getByShopId(shopId);
                // 获取区域信息
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        // 1.从前端接收并转化相应的参数，包括店铺信息及图片信息
        String shopStr = TypeUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
//            System.out.println(shop.getPhone());
        }catch (IOException e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        // 处理上传的图片(选择性修改)
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }
        // 2.修改店铺信息
        if(shop!=null && shop.getShopId()!=null){
            ShopDto shopDto = null;
            try{
                if(shopImg == null){ // 如果不修改图片
                    shopDto = shopService.modifyShop(shop,new ImageDto(null,null));
                }else{
                    shopDto = shopService.modifyShop(shop, new ImageDto(shopImg.getOriginalFilename(),shopImg.getInputStream()));
                }
                if(shopDto.getState() == ShopStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",shopDto.getStateInfo());
                }
            }catch (IOException ioe){
                modelMap.put("success",false);
                modelMap.put("errMsg",ioe.getMessage());
            }catch (ShopOperationException soe){
                modelMap.put("success",false);
                modelMap.put("errMsg",soe.getMessage());
            }
            return modelMap;
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺Id");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // todo 从session中获取用户
//        UserInfo user = new UserInfo();
//        user.setUserId(1L);
//        user.setName("测试");
//        request.getSession().setAttribute("user",user);
        UserInfo user = (UserInfo)request.getSession().getAttribute("user");
        // --------end--------

        try{
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopDto shopDto = shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",shopDto.getShopList());
            // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
            request.getSession().setAttribute("shopList", shopDto.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        long shopId = TypeUtil.getLong(request, "shopId");
        if(shopId<=0){ // 前端未传递shopId，则尝试从session中获取currentShop信息
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj==null){ // 店铺为空，重定向到店铺列表页
                modelMap.put("redirect", true);
                modelMap.put("url", "/shopadmin/shoplist");
            }else{
                Shop shop = (Shop)currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", shop.getShopId());
            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
}