package com.zty.spring.service.impl;


import com.zty.spring.constants.Constant;
import com.zty.spring.mapper.users.UsersMapper;
import com.zty.spring.model.ResultObject;
import com.zty.spring.model.Users;
import com.zty.spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    public ResultObject register(String phone, String password) {
        Users users = new Users();
        users.setPhone(phone);
        users.setPassword(password);
        int insert = usersMapper.insertSelective(users);
        if (insert > 0) {
            return new ResultObject(Constant.ZERO, "注册成功", users);
        } else {
            return new ResultObject(Constant.ONE, "注册失败");
        }
    }

    public ResultObject login(String phone, String password) {
        Users users = usersMapper.login(phone, password);
        if (users != null) {
            return new ResultObject(Constant.ZERO, "登录成功", users);
        } else {
            return new ResultObject(Constant.ONE, "账号或密码不匹配");
        }
    }
}