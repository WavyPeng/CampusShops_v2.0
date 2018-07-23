package com.wavy.o2o.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.wavy.o2o.interceptor.shopadmin.ShopLoginInterceptor;
import com.wavy.o2o.interceptor.shopadmin.ShopPermissionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletException;

/**
 * 开启MVC，自动注入spring容器。
 * WebMvcConfigurerAdapter：配置视图解析器
 * 实现接口ApplicationContextAware，这个类就可以方便获得ApplicationContext
 * Created by WavyPeng on 2018/7/15.
 */
@Configuration
// 等价于<mvc:annotation-driven />
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware{

    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.textproducer.font.color}")
    private String fcolor;
    @Value("${kaptcha.image.width}")
    private String width;
    @Value("${kaptcha.textproducer.char.string}")
    private String cString;
    @Value("${kaptcha.image.height}")
    private String height;
    @Value("${kaptcha.textproducer.font.size}")
    private String fsize;
    @Value("${kaptcha.noise.color}")
    private String nColor;
    @Value("${kaptcha.textproducer.char.length}")
    private String clength;
    @Value("${kaptcha.textproducer.font.names}")
    private String fnames;

    /**
     * 配置Kaptcha验证码servlet（Spring Boot中web.xml不生效）
     * @return
     * @throws ServletException
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean()throws ServletException{
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(),"/Kaptcha");
        servlet.addInitParameter("kaptcha.border",border);// 无边框
        servlet.addInitParameter("kaptcha.textproducer.font.color",fcolor);// 字体颜色
        servlet.addInitParameter("kaptcha.image.width",width);// 图片宽度
        servlet.addInitParameter("kaptcha.textproducer.char.string",cString);// 使用哪些字符
        servlet.addInitParameter("kaptcha.image.height",height);// 图片高度
        servlet.addInitParameter("kaptcha.textproducer.font.size",fsize);// 字体大小
        servlet.addInitParameter("kaptcha.noise.color",nColor);// 干扰线颜色
        servlet.addInitParameter("kaptcha.textproducer.char.length",clength);// 字符个数
        servlet.addInitParameter("kaptcha.textproducer.font.names",fnames);// 字体
        return servlet;
    }

    /**spring容器 */
    private ApplicationContext applicationContext;

    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/o2o/image/upload/");
    }

    /**
     * 定义默认的请求处理器
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    /**
     * 创建ViewResolver
     * @return
     */
    @Bean(name = "viewResolver")
    public ViewResolver createViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // 设置spring容器
        viewResolver.setApplicationContext(applicationContext);
        // 取消缓存
        viewResolver.setCache(false);
        // 设置解析的前缀
        viewResolver.setPrefix("/WEB-INF/html/");
        // 设置视图解析的后缀
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

    /**
     * 文件上传解析器
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        // 1024*1024*20 = 20M
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(20971520);

        return multipartResolver;
    }

//    /**
//     * 添加拦截器配置
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        String interceptPath = "/shopadmin/**";
//        // 注册拦截器
//        InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
//        // 配置拦截器路径
//        loginIR.addPathPatterns(interceptPath);
//        loginIR.excludePathPatterns("/shopadmin/addshopauthmap");
//        // 注册其他拦截器
//        InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
//        // 配置拦截器路径
//        permissionIR.addPathPatterns(interceptPath);
//        // 配置不拦截的路径
//        //<!-- shoplist page -->
//        permissionIR.excludePathPatterns("/shopadmin/shoplist");
//        permissionIR.excludePathPatterns("/shopadmin/getshoplist");
//        //<!-- shopregister page -->
//        permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
//        permissionIR.excludePathPatterns("/shopadmin/registershop");
//        permissionIR.excludePathPatterns("/shopadmin/shopoperation");
//        //<!-- shopmanage page -->
//        permissionIR.excludePathPatterns("/shopadmin/shopmanagement");
//        permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
//        //<!-- shopauthmanagement page -->
//        permissionIR.excludePathPatterns("/shopadmin/addshopauthmap");
//    }
}
