package com.redis.respostry;

import com.redis.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author: leon (pengli@58coin.com)
 * @createDate: 2018/3/26
 * @company: (C) Copyright 58BTC 2018
 * @since: JDK 1.8
 * @description:
 */
@Slf4j
@Repository
public class DBRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User message) {
        String INSERT = "INSERT INTO tb_push_message (id, names, age) VALUES (?, ?, ?) " +
                " ON DUPLICATE KEY UPDATE id = VALUES(id), names = values(names), " +
                " age = VALUES(age);";
        jdbcTemplate.update(INSERT, message.getId(), message.getNames(), message.getAge());
    }


    public int update(long userId, String names) {
        String SQL = "UPDATE tb_push_message  set  names = ? " +
                " WHERE id = ?";
        return jdbcTemplate.update(SQL, new Object[]{userId, names});
    }


    public int del(long userId) {
        String SQL = "DELETE FROM tb_push_message WHERE id = ?";
        return jdbcTemplate.update(SQL, new Object[]{userId});
    }
}
