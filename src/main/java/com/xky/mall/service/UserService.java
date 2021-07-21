package com.xky.mall.service;

import com.xky.mall.exception.MallException;
import com.xky.mall.model.pojo.User;

public interface UserService {
    User selectByPrimaryKey(Integer id);

    /**
     * 注册
     * @param userName
     * @param password
     */
    void regist(String userName, String password) throws MallException;

    User login(String userName, String password) throws MallException;

    void updataSig(User user) throws MallException;

    boolean checkAdminRole(User user);
}
