package com.wavy.o2o.config.redis;

import com.wavy.o2o.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import com.wavy.o2o.cache.JedisPoolWriper;

/**
 * redis配置
 * Created by WavyPeng on 2018/7/15.
 */
@Configuration
public class RedisConfiguration {
    @Value("${redis.hostname}")
    private String hostname;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.pool.maxActive}")
    private int maxTotal;
    @Value("${redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${redis.pool.maxWait}")
    private long maxWaitMillis;
    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;
    @Autowired
    private JedisPoolWriper jedisWritePool;
    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 创建redis连接池的设置
     *
     * @return
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig createJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 一个JedisPool可以分配多少个Jedis实例
        jedisPoolConfig.setMaxTotal(maxTotal);
        // 连接池中最多可空闲的连接数
        // 表示即使没有数据库连接时，依然可以保持的连接，不被清楚，随时处于待命状态
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 最大等待时间，当没有可用连接时
        // 连接池等待连接被归还的最大时间（以毫秒未单位），超过时间则抛出异常
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 在获取连接时检查有效性
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

        return jedisPoolConfig;
    }

    /**
     * 创建redis连接池，并作相关配置
     * @return
     */
    @Bean(name = "jedisWritePool")
    public JedisPoolWriper createJedisPoolWriper() {
        JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig,hostname,port,password);
        return jedisPoolWriper;
    }

    /**
     * Redis工具类
     * 封装redis连接并进行相关操作
     * @return
     */
    @Bean(name = "jedisUtil")
    public JedisUtil createJedisUtil(){
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil.setJedisPool(jedisWritePool);
        return jedisUtil;
    }

    /**
     * Redis的key操作
     * @return
     */
    @Bean(name = "jedisKeys")
    public JedisUtil.Keys createJedisKeys(){
        JedisUtil.Keys jedisKeys = jedisUtil.new Keys();
        return jedisKeys;
    }

    /**
     * Redis的Strings操作
     * @return
     */
    @Bean(name = "jedisStrings")
    public JedisUtil.Strings createJedisStrings(){
        JedisUtil.Strings jedisStrings = jedisUtil.new Strings();
        return jedisStrings;
    }
}
