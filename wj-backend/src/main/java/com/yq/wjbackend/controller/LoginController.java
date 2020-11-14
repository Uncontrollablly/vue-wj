package com.yq.wjbackend.controller;

import java.util.Objects;

import com.yq.wjbackend.pojo.Result;
import com.yq.wjbackend.pojo.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LoginController {

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
    // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        if (!Objects.equals("admin", username) || !Objects.equals("123456", requestUser.getPassword())) {
            String message = "账号密码错误";
            System.out.println(message);
            return new Result(400);
        } else {
            return new Result(200);
        }
    }

    
    @GetMapping("/*")
    @ResponseBody
    public String test() {
        return "abcdefg";
    }
}
