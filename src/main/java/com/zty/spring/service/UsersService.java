package com.zty.spring.service;
import com.zty.spring.model.ResultObject;

public interface UsersService {

    public ResultObject register(String phone, String password);

    public ResultObject login(String phone, String password);
}