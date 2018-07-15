package com.wavy.o2o.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by WavyPeng on 2018/6/30.
 */
@Controller
@RequestMapping("/local")
public class LocalController {
    /**
     * 绑定帐号页路由
     * @return
     */
    @RequestMapping(value = "/accountbind", method = RequestMethod.GET)
    private String accountbind() {
        return "local/accountbind";
    }
    /**
     * 修改密码页路由
     * @return
     */
    @RequestMapping(value = "/changepwd", method = RequestMethod.GET)
    private String changepsw() {
        return "local/changepwd";
    }
    /**
     * 登录页路由
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String login() {
        return "local/login";
    }
}