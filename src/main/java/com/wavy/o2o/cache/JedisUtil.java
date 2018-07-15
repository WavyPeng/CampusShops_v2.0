package com.wavy.o2o.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Set;

/**
 * redis工具类
 * Created by WavyPeng on 2018/6/29.
 */
public class JedisUtil {

    /**缓存时间 */
    private final int expire_time = 60000;
    /**定义Keys对象 */
    public Keys KEYS;
    /**定义Strings对象 */
    public Strings STRINGS;
    /**Redis连接池对象 */
    private JedisPool jedisPool;

    /**
     * 获取redis连接池对象
     * @return
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 设置redis连接池对象
     * @param jedisPoolWriper
     */
    public void setJedisPool(JedisPoolWriper jedisPoolWriper) {
        this.jedisPool = jedisPoolWriper.getJedisPool();
    }

    /**
     * 从Jedis连接池中获取Jedis对象
     * @return
     */
    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * 设置过期时间
     * @param key
     * @param seconds
     */
    public void expire(String key,int seconds){
        if (seconds <= 0) {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        jedis.close();
    }

    /**
     * 设置默认过期时间
     * 时长为expire_time=60000
     * @param key
     */
    public void expire(String key){
        expire(key,expire_time);
    }

    /**----------------------定义类型内部类 -------------------------*/
    /**Keys */
    public class Keys{

        /**
         * 清空所有key
         */
        public String flushAll() {
            Jedis jedis = getJedis(); // 获取jedis连接对象
            String stata = jedis.flushAll();
            jedis.close();
            return stata;
        }

        /**
         * 更改key
         * @param oldkey
         * @param newkey
         * @return 状态码
         */
        public String rename(String oldkey, String newkey) {
            return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
        }

        /**
         * 更改key,仅当新key不存在时才执行
         * @param oldkey
         * @param newkey
         * @return 状态码
         */
        public long renamenx(String oldkey, String newkey) {
            Jedis jedis = getJedis();
            long status = jedis.renamenx(oldkey, newkey);
            jedis.close();
            return status;
        }

        /**
         * 更改key
         * @param oldkey
         * @param newkey
         * @return 状态码
         */
        public String rename(byte[] oldkey, byte[] newkey) {
            Jedis jedis = getJedis();
            String status = jedis.rename(oldkey, newkey);
            jedis.close();
            return status;
        }

        /**
         * 设置key的过期时间，以秒为单位
         * @param key
         * @param seconds 过期时间（以秒为单位）
         * @return 影响的记录数
         */
        public long expired(String key, int seconds) {
            Jedis jedis = getJedis();
            long count = jedis.expire(key, seconds);
            jedis.close();
            return count;
        }

        /**
         * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
         * @param key
         * @param timestamp
         * @return 影响的记录数
         */
        public long expireAt(String key, long timestamp) {
            Jedis jedis = getJedis();
            long count = jedis.expireAt(key, timestamp);
            jedis.close();
            return count;
        }

        /**
         * 查询key的过期时间
         * @param key
         * @return 以秒为单位的时间表示
         */
        public long ttl(String key) {
            Jedis sjedis = getJedis();
            long len = sjedis.ttl(key);
            sjedis.close();
            return len;
        }

        /**
         * 取消对key过期时间的设置
         * @param key
         * @return 影响的记录数
         */
        public long persist(String key) {
            Jedis jedis = getJedis();
            long count = jedis.persist(key);
            jedis.close();
            return count;
        }

        /**
         * 批量删除
         * 删除keys对应的记录,可以是多个key
         * @param keys
         * @return 删除的记录数
         */
        public long del(String... keys) {
            Jedis jedis = getJedis();
            long count = jedis.del(keys);
            jedis.close();
            return count;
        }

        /**
         * 批量删除
         * 删除keys对应的记录,可以是多个key
         * @param keys
         * @return 删除的记录数
         */
        public long del(byte[]... keys) {
            Jedis jedis = getJedis();
            long count = jedis.del(keys);
            jedis.close();
            return count;
        }

        /**
         * 判断key是否存在
         * @param key
         * @return boolean
         */
        public boolean exists(String key) {
            Jedis sjedis = getJedis();
            boolean exis = sjedis.exists(key);
            sjedis.close();
            return exis;
        }

        /**
         * 对List,Set,SortSet进行排序（如果集合数据较大应避免使用这个方法）
         * @param key
         * @return List<String> 集合的全部记录
         **/
        public List<String> sort(String key) {
            Jedis sjedis = getJedis();
            List<String> list = sjedis.sort(key);
            sjedis.close();
            return list;
        }

        /**
         * 对List,Set,SortSet进行排序或limit
         * @param key
         * @param parame 定义排序类型或limit的起止位置.
         * @return List<String> 全部或部分记录
         **/
        public List<String> sort(String key, SortingParams parame) {
            Jedis sjedis = getJedis();
            List<String> list = sjedis.sort(key, parame);
            sjedis.close();
            return list;
        }

        /**
         * 返回指定key存储的类型
         * @param key
         * @return String string|list|set|zset|hash
         **/
        public String type(String key) {
            Jedis sjedis = getJedis();
            String type = sjedis.type(key);
            sjedis.close();
            return type;
        }

        /**
         * 查找所有匹配给定的模式的键
         * @param pattern (key的表达式,*表示多个，？表示一个)
         */
        public Set<String> keys(String pattern) {
            Jedis jedis = getJedis();
            Set<String> set = jedis.keys(pattern);
            jedis.close();
            return set;
        }
    }

    /**Strings */
    public class Strings{
        /**
         * 根据key获取记录
         * @param key
         * @return 值
         */
        public String get(String key) {
            Jedis sjedis = getJedis();
            String value = sjedis.get(key);
            sjedis.close();
            return value;
        }

        /**
         * 根据key获取记录
         * @param key
         * @return 值
         */
        public byte[] get(byte[] key) {
            Jedis sjedis = getJedis();
            byte[] value = sjedis.get(key);
            sjedis.close();
            return value;
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(String key, String value) {
            return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(String key, byte[] value) {
            return set(SafeEncoder.encode(key), value);
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            String status = jedis.set(key, value);
            jedis.close();
            return status;
        }

        /**
         * 添加有过期时间的记录
         * @param key
         * @param seconds 过期时间，以秒为单位
         * @param value
         * @return String 操作状态
         */
        public String setEx(String key, int seconds, String value) {
            Jedis jedis = getJedis();
            String str = jedis.setex(key, seconds, value);
            jedis.close();
            return str;
        }

        /**
         * 添加有过期时间的记录
         * @param key
         * @param seconds 过期时间，以秒为单位
         * @param value
         * @return String 操作状态
         */
        public String setEx(byte[] key, int seconds, byte[] value) {
            Jedis jedis = getJedis();
            String str = jedis.setex(key, seconds, value);
            jedis.close();
            return str;
        }

        /**
         * 添加一条记录，仅当给定的key不存在时才插入
         * @param key
         * @param value
         * @return long 状态码，1插入成功且key不存在，0未插入，key存在
         */
        public long setnx(String key, String value) {
            Jedis jedis = getJedis();
            long str = jedis.setnx(key, value);
            jedis.close();
            return str;
        }

        /**
         * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据
         * 例:String str1="123456789";
         * 对str1操作后setRange(key,4,0000)，str1="123400009";
         *
         * @param key
         * @param offset
         * @param value
         * @return long value的长度
         */
        public long setRange(String key, long offset, String value) {
            Jedis jedis = getJedis();
            long len = jedis.setrange(key, offset, value);
            jedis.close();
            return len;
        }

        /**
         * 在指定的key中追加value
         * @param key
         * @param value
         * @return long 追加后value的长度
         **/
        public long append(String key, String value) {
            Jedis jedis = getJedis();
            long len = jedis.append(key, value);
            jedis.close();
            return len;
        }

        /**
         * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
         * @param key
         * @param number 要减去的值
         * @return long 减指定值后的值
         */
        public long decrBy(String key, long number) {
            Jedis jedis = getJedis();
            long len = jedis.decrBy(key, number);
            jedis.close();
            return len;
        }

        /**
         * 可以作为获取唯一id的方法
         * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
         * @param key
         * @param number 要减去的值
         * @return long 相加后的值
         */
        public long incrBy(String key, long number) {
            Jedis jedis = getJedis();
            long len = jedis.incrBy(key, number);
            jedis.close();
            return len;
        }

        /**
         * 对指定key对应的value进行截取
         * @param key
         * @param startOffset 开始位置(包含)
         * @param endOffset 结束位置(包含)
         * @return String 截取的值
         */
        public String getrange(String key, long startOffset, long endOffset) {
            Jedis sjedis = getJedis();
            String value = sjedis.getrange(key, startOffset, endOffset);
            sjedis.close();
            return value;
        }

        /**
         * 获取并设置指定key对应的value<br/>
         * 如果key存在返回之前的value,否则返回null
         * @param key
         * @param value
         * @return String 原始value或null
         */
        public String getSet(String key, String value) {
            Jedis jedis = getJedis();
            String str = jedis.getSet(key, value);
            jedis.close();
            return str;
        }

        /**
         * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
         * @param keys
         * @return List<String> 值得集合
         */
        public List<String> mget(String... keys) {
            Jedis jedis = getJedis();
            List<String> str = jedis.mget(keys);
            jedis.close();
            return str;
        }

        /**
         * 批量存储记录
         * @param keysvalues 例:keysvalues="key1","value1","key2","value2";
         * @return String 状态码
         */
        public String mset(String... keysvalues) {
            Jedis jedis = getJedis();
            String str = jedis.mset(keysvalues);
            jedis.close();
            return str;
        }

        /**
         * 获取key对应的值的长度
         * @param key
         * @return value值得长度
         */
        public long strlen(String key) {
            Jedis jedis = getJedis();
            long len = jedis.strlen(key);
            jedis.close();
            return len;
        }
    }
}