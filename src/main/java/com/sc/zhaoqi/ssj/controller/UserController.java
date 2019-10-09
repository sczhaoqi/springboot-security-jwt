package com.sc.zhaoqi.ssj.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @GetMapping("/file/down")
    public ResponseEntity<InputStreamResource> down()
            throws IOException
    {
        String filePath = "E:/1.xlsx";
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentType(MediaType.parseMediaType(Files.probeContentType(Paths.get(filePath))))
                .body(new InputStreamResource(file.getInputStream()));
    }
}
