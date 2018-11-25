package com.sc.zhaoqi.ssj.controller;

import com.alibaba.fastjson.JSON;
import com.sc.zhaoqi.ssj.bean.Msg;
import com.sc.zhaoqi.ssj.dto.NewUserDto;
import com.sc.zhaoqi.ssj.dao.SysUserRepository;
import com.sc.zhaoqi.ssj.entity.SysUser;
import com.sc.zhaoqi.ssj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;
    @PostMapping("/user")
    public Msg create(@RequestBody NewUserDto user)
    {
        return userService.signup(user.getUser(),user.getPassword());
    }

    @PostMapping("/user/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Msg create(String user,String role)
    {
        return userService.addRole(user,role);
    }
}
