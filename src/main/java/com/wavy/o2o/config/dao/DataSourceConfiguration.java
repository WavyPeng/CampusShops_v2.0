package com.wavy.o2o.config.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.wavy.o2o.util.enctypt.DESUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;


/**
 * 配置DataSource到IOC容器
 * Created by WavyPeng on 2018/7/11.
 */
@Configuration
// 配置mybatis mapper的扫描路径
@MapperScan("com.wavy.o2o.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    /**
     * 生成与spring-dao.xml对应的bean dataSource
     * @return
     * @throw SQLException
     */
    @Bean(name = "dataSource")
    public DruidDataSource createDataSource() throws SQLException {
        // 生成datasource实例
        DruidDataSource dataSource = new DruidDataSource();
        // 设置相关配置信息
        // 驱动
        dataSource.setDriverClassName(jdbcDriver);
        // 数据库连接URL
        dataSource.setUrl(jdbcUrl);
        // 用户名
        dataSource.setUsername(DESUtil.getDecryptString(jdbcUsername));
        // 密码
        dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));

        dataSource.setDbType("com.alibaba.druid.pool.DruidDataSource");
        dataSource.setFilters("stat");
        dataSource.setMaxActive(1000);
        dataSource.setInitialSize(100);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(500);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("select 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(20);

//        // c3p0连接池的私有属性
//        dataSource.setMaxPoolSize(30);
//        dataSource.setMinPoolSize(10);
//        // 关闭连接后不自动commit
//        dataSource.setAutoCommitOnClose(false);
//        // 获取连接超时时间
//        dataSource.setCheckoutTimeout(10000);
//        // 当获取连接失败重试次数
//        dataSource.setAcquireRetryAttempts(2);

        return dataSource;
    }
}
