package com.redis.storage;


import java.util.concurrent.TimeUnit;

/**
 * Description: redis 快速缓存 Manager cache
 * Date: 19-1-21 下午2:53
 *
 * @author xuliang
 */
public interface FastStorage {


    void save(String key, String val, long timeout, TimeUnit timeUnit) throws Exception;


    void del(String key) throws Exception;


    void del(String key, long bussinestimeout) throws Exception;


    void delAsync(String key, long bussinestimeout) throws Exception;


}
