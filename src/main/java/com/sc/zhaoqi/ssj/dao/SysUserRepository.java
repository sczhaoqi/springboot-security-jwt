package com.sc.zhaoqi.ssj.dao;

import com.sc.zhaoqi.ssj.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SysUserRepository
        extends JpaRepository<SysUser, Long>
{
    SysUser findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "insert into sys_user (username,password) value(?1,?2)", nativeQuery = true)
    int insert(String user,String password);
}