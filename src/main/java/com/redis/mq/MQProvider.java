package com.redis.mq;

import com.redis.mq.queue.DefaultMQ;
import com.redis.mq.queue.MessageQueue;

import java.time.Duration;

public class MQProvider {
    private static final MessageQueue<String> toRediKeyRPCMsgQueue = new DefaultMQ<>();


    public static MessageQueue<String> getFromRediKeyRPCMsgQueue() {
        return toRediKeyRPCMsgQueue;
    }

    public static void push(String key) {
        toRediKeyRPCMsgQueue.push(key, Duration.ofMillis(100));
    }


    public static int getToRPCMsgQueueSize() {
        return toRediKeyRPCMsgQueue.size();
    }
}
