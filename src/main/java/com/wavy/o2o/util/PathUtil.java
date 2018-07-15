package com.wavy.o2o.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 路径工具类
 * Created by WavyPeng on 2018/6/3.
 */
@Configuration
public class PathUtil {
    // 获取与系统相关的默认分隔符
    private static String seperator = System.getProperty("file.separator");

    private static String winPath;
    private static String linuxPath;
    private static String shopPath;

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    @Value("${linux.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }

    @Value("${shop.relevant.path}")
    public void setShopPath(String shopPath) {
        PathUtil.shopPath = shopPath;
    }

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        // Windows环境
        if (os.toLowerCase().startsWith("win")) {
            basePath = winPath;
        } else {
            basePath = linuxPath;
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = shopPath + shopId + seperator;
        return imagePath.replace("/", seperator);
    }
}
