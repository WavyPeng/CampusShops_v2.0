package com.wavy.o2o.controller.local;

import com.wavy.o2o.dto.LocalAuthDto;
import com.wavy.o2o.entity.LocalAccount;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.enums.LocalAuthStateEnum;
import com.wavy.o2o.exception.LocalAuthOperationException;
import com.wavy.o2o.service.ILocalAuthService;
import com.wavy.o2o.util.CodeUtil;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/local",method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {
    @Autowired
    private ILocalAuthService localAuthService;

    /**
     * 绑定本地平台账号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bindlocalaccount",method = RequestMethod.POST)
    private Map<String,Object> bindLocalAccount(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        // 获取当前账号名
        String username = TypeUtil.getString(request,"userName");
        // 获取当前密码
        String password = TypeUtil.getString(request,"password");
        // 从session中获取当前用户信息(用户一旦通过微信登录之后，便能获取到用户的信息)
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        // 非空判断，要求帐号密码以及当前的用户session非空
        if (username != null && password != null && user != null && user.getUserId() != null) {
            // 创建LocalAuth对象并赋值
            LocalAccount localAuth = new LocalAccount();
            localAuth.setUsername(username);
            localAuth.setPassword(password);
            localAuth.setUserInfo(user);
            // 绑定帐号
            LocalAuthDto localAuthDto = localAuthService.bindLocalAccount(localAuth);
            if (localAuthDto.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", localAuthDto.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }

    /**
     * 修改密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/changelocalpwd",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> changeLocalPwd(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        // 获取帐号
        String userName = TypeUtil.getString(request, "userName");
        // 获取原密码
        String password = TypeUtil.getString(request, "password");
        // 获取新密码
        String newPassword = TypeUtil.getString(request, "newPassword");
        // 从session中获取当前用户信息(用户一旦通过微信登录之后，便能获取到用户的信息)
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        // 非空判断，要求帐号新旧密码以及当前的用户session非空，且新旧密码不相同
        if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
                && !password.equals(newPassword)) {
            try {
                // 查看原先帐号，看看与输入的帐号是否一致，不一致则认为是非法操作
                LocalAccount localAuth = localAuthService.getByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUsername().equals(userName)) {
                    // 不一致则直接退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入的帐号非本次登录的帐号");
                    return modelMap;
                }
                // 修改平台帐号的用户密码
                LocalAuthDto le = localAuthService.modifyLocalAccount(user.getUserId(), userName, password,
                        newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (LocalAuthOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码");
        }
        return modelMap;
    }

    /**
     * 登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logincheck",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> login(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 判断是否需要进行验证码校验
        boolean needVerify = TypeUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取输入的帐号
        String userName = TypeUtil.getString(request, "userName");
        // 获取输入的密码
        String password = TypeUtil.getString(request, "password");
        // 非空校验
        if (userName != null && password != null) {
            // 传入帐号和密码去获取平台帐号信息
            LocalAccount localAuth = localAuthService.getByUsernameAndPwd(userName, password);
            if (localAuth != null) {
                // 若能取到帐号信息则登录成功
                modelMap.put("success", true);
                // 同时在session里设置用户信息
                request.getSession().setAttribute("user", localAuth.getUserInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> logout(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 将用户session设置为空
        request.getSession().setAttribute("user",null);
        modelMap.put("success", true);
        return modelMap;
    }
}