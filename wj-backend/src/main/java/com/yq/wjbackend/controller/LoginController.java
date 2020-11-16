package com.yq.wjbackend.controller;

import javax.servlet.http.HttpSession;

import com.yq.wjbackend.pojo.Result;
import com.yq.wjbackend.pojo.User;
import com.yq.wjbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser, HttpSession session) {
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        // 对 html 标签进行转义，防止 XSS 攻击
        username = HtmlUtils.htmlEscape(username);
        User user = userService.getByName(username);
        if (!password.equals(user.getPassword())) {
            String message = "账号密码错误";
            System.out.println(message);
            return new Result(400);
        } else {
            session.setAttribute("user", user);
            return new Result(200);
        }
    }
}
