package com.zty.springboot.service;
import com.zty.springboot.model.ResultObject;

public interface UsersService {

    public ResultObject register(String phone, String password);

    public ResultObject login(String phone, String password);
}