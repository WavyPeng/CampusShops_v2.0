package com.wavy.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码工具类
 * Created by WavyPeng on 2018/6/4.
 */
public class CodeUtil {
    /**
     * 检查验证码是否和预期相符
     * @param request
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected = (String) request.getSession()
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = TypeUtil.getString(request, "verifyCodeActual");
        // 为空或者不相等
        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}