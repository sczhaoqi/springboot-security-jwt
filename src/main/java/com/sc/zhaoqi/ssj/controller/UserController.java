package com.sc.zhaoqi.ssj.controller;

import com.sc.zhaoqi.ssj.bean.Msg;
import com.sc.zhaoqi.ssj.dto.LoginDto;
import com.sc.zhaoqi.ssj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Msg<String> login(@RequestBody  LoginDto loginDto)
    {
        try{
            return Msg.ok("登陆成功", userService.login(loginDto.getUsername(), loginDto.getPassword()));
        }catch (BadCredentialsException badCreExcept) {
            return Msg.error("用户名或密码错误");
        }
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
