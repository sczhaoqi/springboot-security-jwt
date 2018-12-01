package com.sc.zhaoqi.ssj.service;

import com.alibaba.fastjson.JSON;
import com.sc.zhaoqi.ssj.bean.Msg;
import com.sc.zhaoqi.ssj.dao.SysRoleRepository;
import com.sc.zhaoqi.ssj.dao.SysUserRepository;
import com.sc.zhaoqi.ssj.entity.SysRole;
import com.sc.zhaoqi.ssj.entity.SysUser;
import com.sc.zhaoqi.ssj.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
        implements UserDetailsService
{
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public Msg addRole(String user, String roleName)
    {
        try {
            SysUser sysUser = sysUserRepository.findByUsername(user);
            if (sysUser == null) {
                return Msg.error("No such user!");
            }
            if (sysUser.getRoles().parallelStream().anyMatch(role -> role.getName().equals(roleName))) {
                return Msg.error("NAlready contains this role!");
            }
            SysRole sysRole = new SysRole(roleName);
            sysRoleRepository.save(sysRole);
            List<SysRole> preRoles = sysUser.getRoles();
            preRoles.add(sysRole);
            sysUser.setRoles(preRoles);
            sysUserRepository.save(sysUser);
        }
        catch (Exception e) {
            return Msg.sysError();
        }
        return Msg.ok("Success!");
    }

    @Override
    public UserDetails loadUserByUsername(String s)
            throws UsernameNotFoundException
    {
        SysUser user = sysUserRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        log.info(JSON.toJSONString(user));
        return user;
    }

    public String register(String username, String password)
    {
        if (sysUserRepository.findByUsername(username) != null) {
            return "用户已存在";
        }
        sysUserRepository.insert(username, encoder.encode(password));
        return "success";
    }

    public String login(String username, String password)
    {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    public String refreshToken(String oldToken)
    {
        String token = oldToken.substring("Bearer ".length());
        if (!jwtTokenUtil.isTokenExpired(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return "error";
    }
}
