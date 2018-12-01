package com.sc.zhaoqi.ssj.controller;

import com.sc.zhaoqi.ssj.bean.Msg;
import com.sc.zhaoqi.ssj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Msg create(String user, String role)
    {
        return userService.addRole(user, role);
    }

    @PostMapping("/login")
    public String login(String username, String password)
    {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public Msg register(String username, String password)
    {
        try {
            userService.register(username, password);
            return Msg.ok("注册成功");
        }
        catch (Exception ex) {
            return Msg.sysError();
        }
    }

    @PostMapping("/refreshToken")
    public String refreshToken(String oldToken)
    {
        return userService.refreshToken(oldToken);
    }
}
