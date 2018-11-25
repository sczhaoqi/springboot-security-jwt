package com.sc.zhaoqi.ssj.service;

import com.sc.zhaoqi.ssj.bean.Msg;
import com.sc.zhaoqi.ssj.dao.SysRoleRepository;
import com.sc.zhaoqi.ssj.dao.SysUserRepository;
import com.sc.zhaoqi.ssj.entity.SysRole;
import com.sc.zhaoqi.ssj.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;

    public Msg signup(String user,String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try {
            sysUserRepository.insert(user, encoder.encode(password));
        }catch (Exception e){
            return Msg.error("Already contains this user!");
        }
        return Msg.ok("Success!");
    }

    public Msg addRole(String user,String roleName){
        try {
            SysUser sysUser= sysUserRepository.findByUsername(user);
            if(sysUser == null){
                return Msg.error("No such user!");
            }
            if(sysUser.getRoles().parallelStream().anyMatch(role -> role.getName().equals(roleName))){
                return Msg.error("NAlready contains this role!");
            }
            SysRole sysRole = new SysRole(roleName);
            sysRoleRepository.save(sysRole);
            List<SysRole> preRoles = sysUser.getRoles();
            preRoles.add(sysRole);
            sysUser.setRoles(preRoles);
            sysUserRepository.save(sysUser);
        }catch (Exception e){
            return Msg.sysError();
        }
        return Msg.ok("Success!");
    }
}
