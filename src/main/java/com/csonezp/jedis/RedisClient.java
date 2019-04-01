// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhangpeng34
 * Created on 2019/2/12 上午11:20
**/ 
public class RedisClient {
    private static JedisPool jedisPool;
    static{
        try
        {
            JedisPoolConfig config=new JedisPoolConfig();
            config.setMaxTotal(5000);
            config.setMaxIdle(2000);
            config.setMaxWaitMillis(10000);
            config.setTestOnBorrow(true);
            //这里我的redis数据库没有设置密码所以不需要密码参数，否则可以添加密码参数
            //jedisPool=new JedisPool(config,ADDR,PORT,TIMEOUT,AUTH);
            jedisPool=new JedisPool(config,"localhost",6379,100);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //获取Redis资源
    public synchronized static Jedis getJedis(){
        try
        {
            if (jedisPool!=null)
            {
                Jedis jedis=jedisPool.getResource();
                return jedis;
            }else {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //释放redis资源
    @SuppressWarnings("deprecation")
    public synchronized static void releaseConn(Jedis jedis) {
        if (jedisPool != null) {
            jedis.close();
        }
    }

    public static void set(String key,Integer val){
        Jedis jedis=RedisClient.getJedis();
        try {
            jedis.set(key,val.toString());
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            releaseConn(jedis);
        }
    }

    public static Integer get(String key){
        Jedis jedis=RedisClient.getJedis();
        try {
            return Integer.valueOf(jedis.get(key));
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            releaseConn(jedis);
        }
        return null;
    }

    public static  Long decr(String key) {
        Jedis jedis = getJedis();
        try {

            Long val = jedis.decr(key);
            return val;
        }finally {
            releaseConn(jedis);
        }
    }

    public static Long hset(String key, String field, String value){
        try {
            return getJedis().hset(key,field,value);

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            getJedis().close();
        }
        return -1L;
    }

    public static void del(String key) {
        Jedis jedis = getJedis();
        try {

            jedis.del(key);

        }finally {
            releaseConn(jedis);
        }
    }
}