package com.redis.mq;


import com.redis.storage.FastStorage;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class Monitor implements Runnable {


    private FastStorage fastStorage;
    private final Duration timeout = Duration.ofMillis(100);


    public Monitor(FastStorage fastStorage) {
        this.fastStorage = fastStorage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (MQProvider.getToRPCMsgQueueSize() > 0) {
                    String key = MQProvider.getFromRediKeyRPCMsgQueue().pop(timeout);
                    fastStorage.del(key);
                }
            } catch (Exception ex) {
                log.error("fromRPCMsgQueue.pop", ex);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
