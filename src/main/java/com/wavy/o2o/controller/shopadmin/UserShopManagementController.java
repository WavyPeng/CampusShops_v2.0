package com.wavy.o2o.controller.shopadmin;

import com.wavy.o2o.dto.UserShopMapDto;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.entity.UserShopMap;
import com.wavy.o2o.service.IUserShopMapService;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/7/23.
 */
@Controller
@RequestMapping("/shopadmin")
public class UserShopManagementController {

    @Autowired
    private IUserShopMapService userShopMapService;

    @RequestMapping(value = "/listusershopmapsbyshop",method = RequestMethod.GET)
    private Map<String,Object> listUserShopMapsByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取分页信息
        int pageIndex = TypeUtil.getInt(request,"pageIndex");
        int pageSize = TypeUtil.getInt(request,"pageSize");
        // 从session中获取当前店铺的信息
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        // 空值判断
        if(pageIndex>=0 && pageSize>=0 && currentShop!=null && currentShop.getShopId()!=null){
            UserShopMap userShopMapCondition = new UserShopMap();
            // 传入查询条件
            userShopMapCondition.setShop(currentShop);
            String userName = TypeUtil.getString(request,"userName");
            if(userName!=null){
                // 若传入顾客名，则按照顾客名模糊查询
                UserInfo customer = new UserInfo();
                customer.setName(userName);
                userShopMapCondition.setUser(customer);
            }
            // 分页获取该店铺下的顾客积分列表
            UserShopMapDto ud = userShopMapService.listUserShopMap(userShopMapCondition,pageIndex,pageSize);
            modelMap.put("userShopMapList",ud.getUserProductMapList());
            modelMap.put("count",ud.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","Empty info");
        }
        return modelMap;
    }
}
