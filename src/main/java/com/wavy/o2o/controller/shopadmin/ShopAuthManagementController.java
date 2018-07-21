package com.wavy.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavy.o2o.dto.ShopAuthMapDto;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.entity.ShopAuthMap;
import com.wavy.o2o.enums.ShopAuthStateEnum;
import com.wavy.o2o.enums.ShopStateEnum;
import com.wavy.o2o.service.ShopAuthMapService;
import com.wavy.o2o.util.CodeUtil;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/7/22.
 */
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {
    @Autowired
    private ShopAuthMapService shopAuthMapService;

    @RequestMapping(value = "/listshopauthmapsbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopAuthMapsByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 取出分页信息
        int pageIndex = TypeUtil.getInt(request,"pageIndex");
        int pageSize = TypeUtil.getInt(request,"pageSize");
        // 从session中获取店铺信息
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        // 空值判断
        if((pageIndex>=0)&&(pageSize>=0)&&(currentShop!=null)&&(currentShop.getShopId()!=null)){
            // 获取店铺授权信息列表
            ShopAuthMapDto sd = shopAuthMapService.listShopAuthMapByShopId(currentShop.getShopId(),pageIndex,pageSize);
            modelMap.put("shopAuthMapList",sd.getShopAuthMapList());
            modelMap.put("count",sd.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("errMsg","Empty pageIndex or pageSize or shopId");
            modelMap.put("success",false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopauthmapbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopAuthMapById(@RequestParam Long shopAuthId){
        Map<String,Object> modelMap = new HashMap<>();
        // 空值判断
        if(shopAuthId!=null && shopAuthId>=0){
            // 获取授权信息
            ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
            modelMap.put("shopAuthMap",shopAuthMap);
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","Empty shopAuthId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyshopauthmap",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShopAuthMap(String shopAuthMapstr,HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 分为两种情况：
        // 1.授权编辑（有验证码操作）
        // 2.删除/恢复授权操作（无验证码操作）
        boolean statusChange = TypeUtil.getBoolean(request,"statusChange");
        // 验证码校验
        if(!statusChange && !CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码错误");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        ShopAuthMap shopAuthMap = null;
        try {
            // 将字符串json转换成ShopAuthMap实例
            shopAuthMap = mapper.readValue(shopAuthMapstr,ShopAuthMap.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        // 空值判断
        if(shopAuthMap!=null && shopAuthMap.getShopAuthId()!=null){
            try{
                // 判断被操作对象是否时店主，店主不支持修改
                if(!checkPermission(shopAuthMap.getShopAuthId())){
                    modelMap.put("success",false);
                    modelMap.put("errMsg","无法对店主（最高权限）进行操作");
                    return modelMap;
                }
                ShopAuthMapDto sd = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
                if(sd.getState() == ShopAuthStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",sd.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入要修改的授权信息");
        }
        return modelMap;
    }

    /**
     * 检查被操作对象是否可以被修改
     * @param shopAuthId
     * @return
     */
    private boolean checkPermission(Long shopAuthId){
        ShopAuthMap grantedPerson = shopAuthMapService.getShopAuthMapById(shopAuthId);
        if(grantedPerson.getTitleFlag()==0){
            // 店主不能操作
            return false;
        }else
            return true;
    }
}
