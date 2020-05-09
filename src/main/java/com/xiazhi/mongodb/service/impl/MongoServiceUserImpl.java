package com.xiazhi.mongodb.service.impl;

import com.xiazhi.mongodb.bean.User;
import com.xiazhi.mongodb.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ZhaoShuai
 * @date Create in 2020/5/8
 **/
@Service
public class MongoServiceUserImpl implements MongoService<User> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(User user) {
        mongoTemplate.save(user);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Long update(User user) {
        return null;
    }

    @Override
    public User queryForOne(User user) {
        return null;
    }
}
