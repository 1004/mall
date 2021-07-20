package com.xky.mall.service.impl;

import com.xky.mall.exception.MallException;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.model.dao.UserMapper;
import com.xky.mall.model.pojo.User;
import com.xky.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/19 11:53 上午
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired  //按照名称注入，名称不能乱写
    private UserMapper userMapper;

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 注册
     *
     * @param userName
     * @param password
     */
    @Override
    public void regist(String userName, String password) throws MallException {
        //不能重名
        User user = userMapper.selectUserByName(userName);
        if (user != null) {
            throw new MallException(MallExceptionEnum.USER_NAME_REPEAT);
        }
        User newUser = new User();
        newUser.setUsername(userName);
        newUser.setPassword(password);
        int count = userMapper.insertSelective(newUser);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.USER_REGIST_FAILED);
        }
    }
}
