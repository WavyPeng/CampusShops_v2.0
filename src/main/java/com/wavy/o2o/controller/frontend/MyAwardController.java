package com.wavy.o2o.controller.frontend;

import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.UserAwardMapDto;
import com.wavy.o2o.entity.UserAwardMap;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.enums.UserAwardMapStateEnum;
import com.wavy.o2o.service.IAwardService;
import com.wavy.o2o.service.IUserAwardMapService;
import com.wavy.o2o.service.IUserInfoService;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/7/23.
 */
@RequestMapping("/frontend")
@Controller
public class MyAwardController {
    @Autowired
    private IUserAwardMapService userAwardMapService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAwardService awardService;

    @RequestMapping(value = "/adduserawardmap",method = RequestMethod.POST)
    private Map<String,Object> addUserAwardMap(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        UserInfo user = (UserInfo)request.getSession().getAttribute("user");
        long awardId = TypeUtil.getLong(request,"awardId");
        UserAwardMap userAwardMap = compactUserAwardCondition(user,awardId);
        if(userAwardMap!=null){
            try{
                UserAwardMapDto sd = userAwardMapService.addUserAwardMap(userAwardMap);
                if(sd.getState() == UserAwardMapStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",sd.getStateInfo());
                }
            }catch (RuntimeException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请选择领取的奖品");
        }
        return modelMap;
    }
    /**
     * 封装商品查询条件到Product实例中
     * @param user
     * @param awardId
     */
    private UserAwardMap compactUserAwardCondition(UserInfo user, long awardId){
        UserAwardMap userAwardMap = new UserAwardMap();
        userAwardMap.setUser(user);
        userAwardMap.setUserAwardId(awardId);
        return userAwardMap;
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
