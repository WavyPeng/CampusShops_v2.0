package com.wavy.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.wavy.o2o.dto.ShopAuthMapDto;
import com.wavy.o2o.dto.UserAccessToken;
import com.wavy.o2o.dto.WechatInfo;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.entity.ShopAuthMap;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.entity.WechatAccount;
import com.wavy.o2o.enums.ShopAuthStateEnum;
import com.wavy.o2o.enums.ShopStateEnum;
import com.wavy.o2o.service.IUserInfoService;
import com.wavy.o2o.service.IWechatAuthService;
import com.wavy.o2o.service.ShopAuthMapService;
import com.wavy.o2o.util.CodeUtil;
import com.wavy.o2o.util.ShortNetAddressUtil;
import com.wavy.o2o.util.TypeUtil;
import com.wavy.o2o.util.wechat.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/7/22.
 */
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {
    @Autowired
    private ShopAuthMapService shopAuthMapService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IWechatAuthService wechatAuthService;

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

    // 微信获取用户信息的api前缀
    private static String urlPrefix;
    // 微信获取用户信息的api中间部分
    private static String urlMiddle;
    // 微信后去用户信息的api后缀
    private static String urlSuffix;
    // 微信回传给的响应添加授权信息的url
    private static String authUrl;

    @Value("${wechat.prefix}")
    public void setUrlPrefix(String urlPrefix) {
        ShopAuthManagementController.urlPrefix = urlPrefix;
    }

    @Value("${wechat.middle}")
    public void setUrlMiddle(String urlMiddle) {
        ShopAuthManagementController.urlMiddle = urlMiddle;
    }

    @Value("${wechat.suffix}")
    public void setUrlSuffix(String urlSuffix) {
        ShopAuthManagementController.urlSuffix = urlSuffix;
    }

    @Value("${wechat.auth.url}")
    public void setAuthUrl(String authUrl) {
        ShopAuthManagementController.authUrl = authUrl;
    }

    /**
     * 生成带有URL的二维码
     * 微信扫一扫就能链接到对应的URL中
     * @param request
     * @param response
     */
    @RequestMapping(value = "/generateqrcode4shopauth",method = RequestMethod.GET)
    @ResponseBody
    private void generateQRCode4ShopAuth(HttpServletRequest request, HttpServletResponse response){
        // 从session中获取当前shop的信息
        Shop shop = (Shop)request.getSession().getAttribute("currentShop");
        if(shop!=null && shop.getShopId()!=null){
            // 获取当前时间戳，以保证二维码的时间有效性，精确到毫秒
            long timeStamp = System.currentTimeMillis();
            // 将店铺id和timestamp传入content，赋值到state中,
            // 微信获取到这些信息会回传到授权信息的添加方法中
            // 加上aaa是为了在添加信息的方法中替换这些信息使用
            String content = "{aaashopIdaaa:"+shop.getShopId()+",aaacreateTimeaaa:"+timeStamp+"}";
            try{
                // 将content的信息先进行base64编码以避免特殊字符造成干扰，之后拼接目标URL
                String longUrl = urlPrefix + authUrl + urlMiddle + URLEncoder.encode(content,"UTF-8")+urlSuffix;
                // 将目标URL转换成短的URL
                String shortUrl = ShortNetAddressUtil.getShortURL(longUrl);
                // 调用二维码生成工具类方法，传入短的URL，生成二维码
                BitMatrix QRCodeImg = CodeUtil.generateQRCodeStream(shortUrl,response);
                // 将二维码以图片流的形式输出到前端
                MatrixToImageWriter.writeToStream(QRCodeImg,"png",response.getOutputStream());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据微信回传参数添加店铺的授权信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addshopauthmap",method = RequestMethod.GET)
    private String addShopAuthMap(HttpServletRequest request,HttpServletResponse response)throws IOException{
        // 从request里获取微信用户的信息
        WechatAccount auth = getEmployeeInfo(request);
        if(auth!=null){
            // 根据userId获取用户信息
            UserInfo user = userInfoService.getUserInfoById(auth.getUserInfo().getUserId());
            // 将用户信息添加进user里
            request.getSession().setAttribute("user",user);
            // 解析微信回传过来的自定义参数state
            String QRCodeInfo = new String(URLDecoder.decode(TypeUtil.getString(request,"state"),"UTF-8"));
            ObjectMapper mapper = new ObjectMapper();
            WechatInfo wechatInfo = null;
            try{
                wechatInfo = mapper.readValue(QRCodeInfo.replace("aaa","\""),WechatInfo.class);
            }catch (Exception e){
                return "shop/operationfail";
            }
            // 校验二维码是否已经过期
            if(!checkQRCodeInfo(wechatInfo)){
                return "shop/operationfail";
            }

            // 去重校验
            // 获取该店铺下所有的授权信息
            // 避免重复扫描，重复添加数据库
            ShopAuthMapDto allMapList = shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(),1,999);
            List<ShopAuthMap> shopAuthList = allMapList.getShopAuthMapList();
            for(ShopAuthMap sm:shopAuthList){
                if(sm.getEmployee().getUserId() == user.getUserId())
                    return "shop/operationfail";
            }

            try{
                // 根据获取到的内容，添加微信授权信息
                ShopAuthMap shopAuthMap = new ShopAuthMap();
                Shop shop = new Shop();
                shop.setShopId(wechatInfo.getShopId());
                shopAuthMap.setShop(shop);
                UserInfo employee = new UserInfo();
                shopAuthMap.setEmployee(employee);
                shopAuthMap.setTitle("员工");
                shopAuthMap.setTitleFlag(1);
                ShopAuthMapDto sd = shopAuthMapService.addShopAuthMap(shopAuthMap);
                if(sd.getState() == ShopAuthStateEnum.SUCCESS.getState()){
                    return "shop/operationsuccess";
                }else {
                    return "shop/operationfail";
                }
            }catch (RuntimeException e){
                return "shop/operationfail";
            }
        }
        return "shop/operationfail";
    }

    /**
     * 根据二维码携带的createTime判断是否超时（10分钟），超时则过期
     * @param wechatInfo
     * @return
     */
    private boolean checkQRCodeInfo(WechatInfo wechatInfo){
        if(wechatInfo!=null && wechatInfo.getCreateTime()!=null){
            long nowTime = System.currentTimeMillis();
            if((nowTime-wechatInfo.getCreateTime())<=600000){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 根据微信回传的code获取用户信息
     * @param request
     * @return
     */
    private WechatAccount getEmployeeInfo(HttpServletRequest request){
        String code = request.getParameter("code");
        WechatAccount auth = null;
        if(null!=code){
            UserAccessToken token;
            try {
                token = WechatUtil.getUserAccessToken(code);
                String openId = token.getOpenId();
                request.getSession().setAttribute("openId",openId);
                auth = wechatAuthService.getWechatAccountByOpenId(openId);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return auth;
    }
}
