package com.zty.spring.controller;

import com.zty.spring.constants.Constant;
import com.zty.spring.model.ResultObject;
import com.zty.spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UsersService usersService;


    @RequestMapping("/boot/register")
    public @ResponseBody
    String register(HttpSession session, @RequestParam("phone") String phone, @RequestParam("password") String password) {

        ResultObject resultObject = usersService.register(phone, password);

        if (resultObject.getStatusCode() == 0) {
            session.setAttribute(Constant.LOGIN_USER, resultObject.getData());

            return "<script>window.parent.uploadOK('OK')</script>";
        }
        return "<script>window.parent.uploadOK('NO')</script>";
    }
}
