package com.wavy.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 指定Redis的JedisPool构造函数
 * Created by WavyPeng on 2018/6/29.
 */
public class JedisPoolWriper {
    /**Redis连接池对象 */
    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig poolConfig,final String host,final int port,final String password){
        try{
            jedisPool = new JedisPool(poolConfig,host,port,2000,password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取redis连接池对象
     * @return
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 设置redis连接池对象
     * @param jedisPool
     */
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}