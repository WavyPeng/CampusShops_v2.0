package com.wavy.o2o.controller.shopadmin;

import com.wavy.o2o.dto.UserAwardMapDto;
import com.wavy.o2o.entity.Award;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.entity.UserAwardMap;
import com.wavy.o2o.service.IUserAwardMapService;
import com.wavy.o2o.util.TypeUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/7/23.
 */
@Controller
@RequestMapping("/shopadmin")
public class UserAwardManagementController {
    @Autowired
    private IUserAwardMapService userAwardMapService;

    @RequestMapping(value = "/listawardshopmapsbyshop",method = RequestMethod.GET)
    private Map<String,Object> listUserShopMapsByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取分页信息
        int pageIndex = TypeUtil.getInt(request,"pageIndex");
        int pageSize = TypeUtil.getInt(request,"pageSize");
        // 从session中获取当前店铺的信息
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        // 空值判断
        if(pageIndex>=0 && pageSize>=0 && currentShop!=null && currentShop.getShopId()!=null){
            UserAwardMap userAwardMapCondition = new UserAwardMap();
            // 获取奖品名称
            userAwardMapCondition.setShop(currentShop);
            String awardName = TypeUtil.getString(request,"awardName");
            if(awardName!=null){
                // 若传入顾客名，则按照顾客名模糊查询
                Award award = new Award();
                award.setAwardName(awardName);
                userAwardMapCondition.setAward(award);
            }
            // 分页获取该店铺下的顾客积分列表
            UserAwardMapDto ud = userAwardMapService.listUserAwardMap(userAwardMapCondition,pageIndex,pageSize);
            modelMap.put("userShopMapList",ud.getUserAwardMapList());
            modelMap.put("count",ud.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","Empty info");
        }
        return modelMap;
    }
}
