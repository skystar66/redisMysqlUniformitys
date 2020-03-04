package com.redis.controller;

import com.redis.domain.User;
import com.redis.service.UserService;
import com.redis.storage.FastStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class TestController {


    @Autowired
    UserService userService;
    @Autowired
    FastStorage fastStorage;


    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String save() throws Exception {
        User user = new User();
        user.setId(1);
        user.setNames("liang");
        user.setAge(13);
        userService.save(user);
        fastStorage.save("1", user.toString(), 5 * 60 * 1000, TimeUnit.MILLISECONDS);
        return "SUCCESS";
    }


    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update() throws Exception {

        //spet1 删除缓存
        fastStorage.del("1");
        //更新数据库
        User user = new User();
        user.setId(1);
        user.setNames("xiaoliangliang");
        userService.update(user);
        //删除缓存
        fastStorage.delAsync("1", 100);
        return "SUCCESS";
    }


}
