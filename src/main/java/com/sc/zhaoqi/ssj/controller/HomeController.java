package com.sc.zhaoqi.ssj.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController
{
    @GetMapping("/")
    public String index()
    {
        return "welcome";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/admin/user_page")
    @ResponseBody
    public String adminTest1()
    {
        return "ROLE_USER";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/admmin_page")
    @ResponseBody
    public String adminTest2()
    {
        return "ROLE_ADMIN";
    }
}
