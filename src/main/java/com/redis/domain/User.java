package com.redis.domain;

import lombok.Data;

@Data
public class User {


    private long id;

    private String names;

    private int age;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", names='" + names + '\'' +
                ", age=" + age +
                '}';
    }
}
