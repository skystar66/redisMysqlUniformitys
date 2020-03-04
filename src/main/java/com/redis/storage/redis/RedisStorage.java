package com.redis.storage.redis;

import com.redis.mq.MQProvider;
import com.redis.storage.FastStorage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 缓存数据库一致性的方案是：延时双删策略
 * 1，先删除缓存
 * 2，在更新数据库
 * 3，sleep Ns（此时可能由于另外一个线程从数据库中取出旧值，重新塞到啦缓存中，所以预留出来业务执行时间）
 * 4，再删除缓存
 * 案例：
 * （1）删除缓存
 * （2）更新数据库数据；
 * （3）缓存因为种种问题删除失败
 * （4）将需要删除的key发送至消息队列
 * （5）自己消费消息，获得需要删除的key
 * （6）继续重试删除操作，直到成功，保证强一致性
 */

@Component
@Data
@Slf4j
public class RedisStorage implements FastStorage {


    private final Duration timeout = Duration.ofMillis(100);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    ExecutorService executorService;


    @Override
    public void save(String key, String val, long timeout, TimeUnit timeUnit) throws Exception {
        redisTemplate.opsForValue().set(key, val, timeout, timeUnit);
    }

    @Override
    public void del(String key) throws Exception {
        try {
            redisTemplate.delete(key);
        } catch (Exception ex) {
            MQProvider.getFromRediKeyRPCMsgQueue().push(key, timeout);
            log.error("del redis key. error : {}", ex);
        }
    }

    @Override
    public void del(String key, long bussinestimeout) throws Exception {
        try {
            //给业务流出执行查询的时间
            Thread.sleep(bussinestimeout);
            redisTemplate.delete(key);
        } catch (Exception ex) {
            MQProvider.getFromRediKeyRPCMsgQueue().push(key, timeout);
            log.error("del redis key. error : {}", ex);
        }
    }

    @Override
    public void delAsync(String key, long bussinestimeout) throws Exception {
        executorService.execute(() -> {
            try {
                //给业务流出执行查询的时间
                Thread.sleep(bussinestimeout);
                redisTemplate.delete(key);
            } catch (Exception ex) {
                MQProvider.getFromRediKeyRPCMsgQueue().push(key, timeout);
                log.error("del redis key. error : {}", ex);
            }
        });
    }
}
