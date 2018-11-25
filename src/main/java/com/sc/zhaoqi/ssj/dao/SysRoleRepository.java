package com.sc.zhaoqi.ssj.dao;

import com.sc.zhaoqi.ssj.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SysRoleRepository
        extends JpaRepository<SysRole, Long>
{
    SysRole findByName(String roleName);

    @Modifying
    @Transactional
    @Query(value = "insert into sys_role (name) value(?1)", nativeQuery = true)
    int insert(String name);
}
