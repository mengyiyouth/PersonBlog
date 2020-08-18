package com.mengyi.service.impl;

import com.mengyi.dao.UserDao;
import com.mengyi.entity.User;
import com.mengyi.service.UserService;
import com.mengyi.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;


    @Override
    public User checkUser(String username, String password) {
        User user = userdao.findByUsernameAndPassword(username, MD5Utils.code(password));
//        User user = userdao.findByUsernameAndPassword(username, password);
        return user;
    }
}
