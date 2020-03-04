package com.redis;

import com.redis.mq.Monitor;
import com.redis.storage.FastStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisMysqlApplication implements CommandLineRunner {


    @Autowired
    FastStorage fastStorage;

    public static void main(String[] args) {
        SpringApplication.run(RedisMysqlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread thread = new Thread(new Monitor(fastStorage));
        thread.setDaemon(true);
        thread.start();
    }
}
