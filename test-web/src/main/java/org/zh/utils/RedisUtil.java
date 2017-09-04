package org.zh.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:TODO( EhCache工具类)
 * @author: level.meng
 * @date: 2017年2月19日 下午21:54:36
 * <p>
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class RedisUtil {

    protected static ReentrantLock lockPool = new ReentrantLock();
    protected static ReentrantLock lockJedis = new ReentrantLock();

    private static Logger _log = LoggerFactory.getLogger(RedisUtil.class);

    // Redis服务器IP
    private static String IP = PropertiesFileUtil.getInstance("setting").get("redis.one.ip");

    // Redis的端口号
    private static int PORT = PropertiesFileUtil.getInstance("setting").getInt("redis.one.port");

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = PropertiesFileUtil.getInstance("setting").getInt("redis.maxTotal");

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = PropertiesFileUtil.getInstance("setting").getInt("redis.maxIdle");

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = PropertiesFileUtil.getInstance("setting").getInt("redis.maxWaitMillis");

    // 超时时间
    private static int TIMEOUT = PropertiesFileUtil.getInstance("setting").getInt("redis.timeout");

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = false;

    private static JedisPool jedisPool = null;

    /**
     * redis过期时间,以秒为单位
     */
    public final static int EXRP_HOUR = 60 * 60;            //一小时
    public final static int EXRP_DAY = 60 * 60 * 24;        //一天
    public final static int EXRP_MONTH = 60 * 60 * 24 * 30;    //一个月

    /**
     * 初始化Redis连接池
     */
    private static void initialPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, IP, PORT, TIMEOUT);
        } catch (Exception e) {
            _log.error("First create JedisPool error : " + e);
        }
    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }

    public <T> T getTokenFromRedis(String key, Class<T> c) {
        T t;
        try{
            t = JsonUtil.toJson(getJedis().get(key), c);
        } catch (Exception e){
            return null;
        }

        return t;
    }

    /**
     * 同步获取Jedis实例
     *
     * @return Jedis
     */
    public synchronized static Jedis getJedis() {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            _log.error("Get jedis error : " + e);
        } finally {
            returnResource(jedis);
        }
        return jedis;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 设置 String
     *
     * @param key
     * @param value
     */
    public synchronized static void set(String key, String value) {
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            getJedis().set(key, value);
        } catch (Exception e) {
            _log.error("Set key error : " + e);
        }
    }

    /**
     * 设置 过期时间
     *
     * @param key
     * @param value
     * @param seconds 以秒为单位
     */
    public synchronized static void set(String key, String value, int seconds) {
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            getJedis().setex(key, seconds, value);
        } catch (Exception e) {
            _log.error("Set keyex error : " + e);
        }
    }

    /**
     * 获取String值
     *
     * @param key
     * @return value
     */
    public synchronized static String get(String key) {
        if (getJedis() == null || !getJedis().exists(key)) {
            return null;
        }
        return getJedis().get(key);
    }

    /**
     * 删除值
     *
     * @param key
     */
    public synchronized static void remove(String key) {
        try {
            getJedis().del(key);
        } catch (Exception e) {
            _log.error("Remove keyex error : " + e);
        }
    }


    /**
     * set Object
     */
    public synchronized static String set(Object object, String key) {
        return getJedis().set(key.getBytes(), serialize(object));
    }

    /**
     * get Object
     */
    public synchronized static Object getObject(String key) {
        byte[] value = getJedis().get(key.getBytes());
        return unserialize(value);
    }

    /**
     * delete a key
     **/
    public synchronized static boolean del(String key) {
        return getJedis().del(key.getBytes()) > 0;
    }

    /**
     * 从hash中删除指定的存储
     *
     * @param key
     * @param field 存储的名字
     * @return 状态码，1成功，0失败
     */
    public synchronized static long hdel(String key, String field) {
        Long s = 0L;
        try {
            Jedis jedis = getJedis();
            s = jedis.hdel(key, field);
        } catch (Exception e) {
            _log.error("hgetAll key error : " + e);
        }
        return s;
    }

    /**
     * 测试hash中指定的存储是否存在
     *
     * @param key
     * @param field 存储的名字
     * @return 1存在，0不存在
     */
    public synchronized static boolean hexists(String key, String field) {
        boolean s = false;
        try {
            Jedis sjedis = getJedis();
            s = sjedis.hexists(key, field);
        } catch (Exception e) {
            _log.error("hgetAll key error : " + e);
        }
        return s;
    }

    /**
     * 返回hash中指定存储位置的值
     *
     * @param key
     * @param field 存储的名字
     * @return 存储对应的值
     */
    public synchronized static String hget(String key, String field) {
        String s = "";
        try {
            Jedis sjedis = getJedis();
            s = sjedis.hget(key, field);
        } catch (Exception e) {
            _log.error("hget key error : " + e);
        }
        return s;
    }

    /**
     * 以Map的形式返回hash中的存储和值
     *
     * @param key
     * @return Map<Strinig,String>
     */
    public synchronized static Map<String, String> hgetAll(String key) {
        Map<String, String> map = null;
        try {
            Jedis sjedis = getJedis();
            if (sjedis != null) {
                map = sjedis.hgetAll(key);
            }
        } catch (Exception e) {
            _log.error("hgetAll key error : " + e);
        }
        return map;
    }

    /**
     * 添加一个对应关系
     *
     * @param key
     * @param field
     * @param value
     * @return 状态码 1成功，0失败，field已存在将更新，也返回0
     **/
    public synchronized static long hset(String key, String field, String value) {
        Long s = 0L;
        try {
            Jedis sjedis = getJedis();
            if (sjedis != null) {
                s = sjedis.hset(key, field, value);
            }
        } catch (Exception e) {
            _log.error("hset key field value error : " + e);
        }
        return s;
    }


    /**
     * 获取hash中value的集合
     *
     * @param key
     * @return List<String>
     */
    public synchronized static List<String> hvals(String key) {
        List<String> list = null;
        try {
            Jedis sjedis = getJedis();
            if (sjedis != null) {
                list = sjedis.hvals(key);
            }
        } catch (Exception e) {
            _log.error("hvals key error : " + e);
        }
        return list;
    }

    /**
     * 返回指定hash中的所有存储名字,类似Map中的keySet方法
     *
     * @param key
     * @return Set<String> 存储名称的集合
     */
    public synchronized static Set<String> hkeys(String key) {
        Set<String> set = null;
        try {
            Jedis sjedis = getJedis();
            if (sjedis != null) {
                set = sjedis.hkeys(key);
            }
        } catch (Exception e) {
            _log.error("hkeys key error : " + e);
        }
        return set;
    }

    /**
     * 获取hash中存储的个数，类似Map中size方法
     *
     * @param key
     * @return long 存储的个数
     */
    public synchronized static long hlen(String key) {
        long len = -1L;
        try {
            Jedis sjedis = getJedis();
            if (sjedis != null) {
                len = sjedis.hlen(key);
            }
        } catch (Exception e) {
            _log.error("hlen key error : " + e);
        }
        return len;
    }

    /**
     * 添加对应关系，如果对应关系已存在，则覆盖
     *
     * @param key
     * @param map 对应关系
     * @return 状态，成功返回OK
     */
    public synchronized static String hmset(String key, Map<String, String> map) {
        String s = "";
        try {
            Jedis sjedis = getJedis();
            if (sjedis != null) {
                s = sjedis.hmset(key, map);
            }
        } catch (Exception e) {
            _log.error("hmset map error : " + e);
        }
        return s;
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return boolean
     */
    public synchronized static boolean exist(String key) {
        Jedis jedis = getJedis();
        return jedis.exists(key);
    }

    /**
     * 判断指定key的存储的数据类型
     *
     * @param key
     * @return String
     */
    public synchronized static String type(String key) {
        Jedis jedis = getJedis();
        return jedis.type(key);
    }

    /**
     * 获取所有的key
     *
     * @return Set<String>
     */
    public synchronized static Set<String> keys() {
        Jedis jedis = getJedis();
        return jedis.keys("*");
    }

    /**
     * 获取list中所有的值
     *
     * @param key
     */
    public synchronized static List<String> lrange(String key) {
        Jedis jedis = getJedis();
        return jedis.lrange(key, 0, -1);
    }

    /**
     * 获取set中所有的值
     *
     * @param key
     */
    public synchronized static Set<String> smembers(String key) {
        Jedis jedis = getJedis();
        return jedis.smembers(key);
    }

    /**
     * 获取zset中所有的值
     *
     * @param key
     */
    public synchronized static Set<String> zrange(String key) {
        Jedis jedis = getJedis();
        return jedis.zrange(key, 0, -1);
    }

    /*序列化*/
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {

        }
        return null;
    }

    /*反序列化*/
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }

}
