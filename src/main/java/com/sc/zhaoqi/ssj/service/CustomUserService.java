package com.sc.zhaoqi.ssj.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.zhaoqi.ssj.dao.SysUserRepository;
import com.sc.zhaoqi.ssj.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService
        implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(CustomUserService.class);
    @Autowired
    SysUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s)
            throws UsernameNotFoundException
    {
        SysUser user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        log.info(JSON.toJSONString(user));
        return user;
    }
}