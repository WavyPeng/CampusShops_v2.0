package com.wavy.o2o.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * 对应spring-service中的transactionManager
 * 继承TransactionManagementConfigurer以开启annotation-driven
 *
 * Created by WavyPeng on 2018/7/15.
 */
// 使用注解@EnableTransactionManagement开启事务支持
@Configuration
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer{

    // 注入DataSourceConfiguration里边的dataSource，通过createDataSource()获取
    @Autowired
    private DataSource dataSource;

    /**
     *
     * @return
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
