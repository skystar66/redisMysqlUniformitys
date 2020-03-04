package com.redis.mq.queue;

import java.time.Duration;
import java.util.concurrent.LinkedBlockingQueue;

public class DefaultMQ<T> implements MessageQueue<T> {
    private final LinkedBlockingQueue<T> msgQueue;

    public DefaultMQ() {
        msgQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public boolean push(T msg, Duration maxWait) {
        msgQueue.offer(msg);
        return true;
    }

    @Override
    public T pop(Duration maxWait) {
        T msg = null;

        try {
            msg = msgQueue.take();
        } catch (InterruptedException ignore) {
        }

        return msg;
    }

    @Override
    public int size() {
        return msgQueue.size();
    }
}
