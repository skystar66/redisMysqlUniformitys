package com.redis.service;

import com.redis.domain.User;
import com.redis.respostry.DBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    DBRepository dbRepository;


    public void save(User user) {
        dbRepository.save(user);
    }

    public void del(long id) {
        dbRepository.del(id);
    }

    public void update(User user) {
        dbRepository.update(user.getId(), user.getNames());
    }


}
