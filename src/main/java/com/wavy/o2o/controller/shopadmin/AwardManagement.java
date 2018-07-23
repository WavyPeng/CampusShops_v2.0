package com.wavy.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavy.o2o.dto.AwardDto;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.entity.Award;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.enums.AwardStateEnum;
import com.wavy.o2o.exception.AwardOperationException;
import com.wavy.o2o.service.IAwardService;
import com.wavy.o2o.util.CodeUtil;
import com.wavy.o2o.util.TypeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
 * Created by WavyPeng on 2018/7/23.
 */
@RequestMapping("/shopadmin")
@Controller
public class AwardManagement {
    @Autowired
    private IAwardService awardService;

    @RequestMapping(value = "/getawardbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getAwardById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        long awardId = TypeUtil.getLong(request,"awardId");
        if(awardId>-1){
            Award award = awardService.getAwardById(awardId);
            modelMap.put("award",award);
            modelMap.put("success",true);
        }else {
            modelMap.put("errMsg","empty awardId");
            modelMap.put("success",false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/listawardsbyshop",method = RequestMethod.GET)
    private Map<String,Object> listAwardsByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取分页信息
        int pageIndex = TypeUtil.getInt(request,"pageIndex");
        int pageSize = TypeUtil.getInt(request,"pageSize");
        // 从session中获取当前店铺的信息
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        // 空值判断
        if(pageIndex>=0 && pageSize>=0 && currentShop!=null && currentShop.getShopId()!=null){
            String awardName = TypeUtil.getString(request,"awardName");
            Award awardCondition = compactProductCondition(currentShop.getShopId(),awardName);

            // 分页获取该店铺下的顾客积分列表
            AwardDto ad = awardService.getAwardList(awardCondition,pageIndex,pageSize);
            modelMap.put("awardList",ad.getAwardList());
            modelMap.put("count",ad.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","Empty info");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addaward",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addAward(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Award award = null;
        String awardStr = TypeUtil.getString(request,"awardStr");
        ImageDto thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try{
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        try{
            // 获取前端传过来的表单string流并将其转换成Product实体类
//            String awardStr = TypeUtil.getString(request, "awardStr");
            award = mapper.readValue(awardStr, Award.class);
        }catch (Exception e){
            System.out.println(e.toString());
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        // 若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if(award!=null && thumbnail!=null){
            try{
                // 从session中获取当前店铺信息
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                award.setShopId(currentShop.getShopId());
                AwardDto ad = awardService.addAward(award,thumbnail);
                if(ad.getState() == AwardStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ad.getStateInfo());
                }
            }catch (AwardOperationException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入奖品信息");
        }
        return modelMap;
    }

    /**
     * 商品编辑和商品上下架共用
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyaward",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyAward(HttpServletRequest request){
        boolean statusChange = TypeUtil.getBoolean(request,"statusChange");
        Map<String,Object> modelMap = new HashMap<>();
        // 验证码判断
        if(!statusChange && !CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Award award = null;
        ImageDto thumbnail = null;
        List<ImageDto> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        // 若请求中存在文件流，则取出相关的文件
        try{
            if(multipartResolver.isMultipart(request)){
                thumbnail = handleImage(request, thumbnail);
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            String awardStr = TypeUtil.getString(request, "awardStr");
            award = mapper.readValue(awardStr, Award.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 判断非空
        if(award!=null){
            try{
                // 从session中获取当前店铺信息
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                award.setShopId(currentShop.getShopId());
                // 更新商品信息
                AwardDto ad = awardService.modifyAward(award,thumbnail);
                if(ad.getState() == AwardStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",ad.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getawardbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getAwardById(@RequestParam long awardId){
        Map<String,Object> modelMap = new HashMap<>();
        if(awardId > 0){
            // 获取奖品信息
            Award award = awardService.getAwardById(awardId);
            modelMap.put("success",true);
            modelMap.put("award",award);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty productId");
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
    private ImageDto handleImage(HttpServletRequest request, ImageDto thumbnail)
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
