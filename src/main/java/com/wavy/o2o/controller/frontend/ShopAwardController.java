package com.wavy.o2o.controller.frontend;

import com.wavy.o2o.dto.AwardDto;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.entity.Award;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.entity.UserShopMap;
import com.wavy.o2o.service.IAwardService;
import com.wavy.o2o.service.IUserShopMapService;
import com.wavy.o2o.util.TypeUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/7/23.
 */
@RequestMapping("/frontend")
@Controller
public class ShopAwardController {
    @Autowired
    private IUserShopMapService userShopMapService;
    @Autowired
    private IAwardService awardService;


    @RequestMapping(value = "/listawardsbyshop",method = RequestMethod.GET)
    private Map<String,Object> listAwardsByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取分页信息
        int pageIndex = TypeUtil.getInt(request,"pageIndex");
        int pageSize = TypeUtil.getInt(request,"pageSize");
        // 店铺Id
        long shopId = TypeUtil.getLong(request,"shopId");
        // 空值判断
        if(pageIndex>=0 && pageSize>=0 && shopId>=0){
            String awardName = TypeUtil.getString(request,"awardName");
            Award awardCondition = compactProductCondition(shopId,awardName);

            // 分页获取该店铺下的顾客积分列表
            AwardDto ad = awardService.getAwardList(awardCondition,pageIndex,pageSize);
            modelMap.put("awardList",ad.getAwardList());
            modelMap.put("count",ad.getCount());
            modelMap.put("success",true);
            UserInfo user = (UserInfo)request.getSession().getAttribute("user");
            if(user!=null && user.getUserId()!=null){
                UserShopMap userShopMap = userShopMapService.getUserShopMap(user.getUserId(),shopId);
                if(userShopMap==null)
                    modelMap.put("totalPoint",0);
                else
                    modelMap.put("totalPoint",userShopMap.getPoint());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","Empty info");
        }
        return modelMap;
    }

    /**
     * 封装商品查询条件到Product实例中
     * @param shopId
     * @param awardName
     */
    private Award compactProductCondition(long shopId, String awardName){
        Award awardCondition = new Award();
        awardCondition.setShopId(shopId);

        // 若有商品名模糊查询的要求则添加进去
        if (awardName != null) {
            awardCondition.setAwardName(awardName);
        }
        return awardCondition;
    }

    /**
     * 处理压缩图和详情图
     * @param request
     * @param thumbnail
     * @return
     * @throws IOException
     */
    private ImageDto handleImage(javax.servlet.http.HttpServletRequest request, ImageDto thumbnail)
            throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageDto(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        return thumbnail;
    }
}
