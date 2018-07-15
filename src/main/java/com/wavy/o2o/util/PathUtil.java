package com.wavy.o2o.util;

/**
 * 路径工具类
 * Created by WavyPeng on 2018/6/3.
 */
public class PathUtil {
    // 获取与系统相关的默认分隔符
    private static String seperator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        // Windows环境
        if (os.toLowerCase().startsWith("win")) {
            basePath = "E:/o2o/image";
        } else {
            basePath = "/root/tmp/wavy/image";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/images/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
