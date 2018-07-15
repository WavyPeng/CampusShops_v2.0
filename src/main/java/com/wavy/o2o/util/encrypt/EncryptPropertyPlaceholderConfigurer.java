package com.wavy.o2o.util.encrypt;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 加密连接数据库明文密码
 * Created by WavyPeng on 2018/6/29.
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    // 需要加密的字段数组
    private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

    /**
     * 对关键属性进行转换
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            // 对已加密的字段进行解密工作
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        } else { // 非加密字段
            return propertyValue;
        }
    }

    /**
     * 判断该属性是否已加密
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName){
        // 若等于需要加密的field，则进行加密
        for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName))
                return true;
        }
        return false;
    }
}