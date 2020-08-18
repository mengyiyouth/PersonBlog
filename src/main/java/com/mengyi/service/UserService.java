package com.mengyi.service;

import com.mengyi.entity.User;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
public interface UserService {
    /*核对用户名和密码*/
    User checkUser(String username, String password);
}
