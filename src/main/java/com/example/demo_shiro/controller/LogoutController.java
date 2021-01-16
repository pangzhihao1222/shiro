package com.example.demo_shiro.controller;

import com.example.demo_shiro.utils.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * logout，映射shiro自带的过滤器
 */
@RestController
public class LogoutController {

//    @RequestMapping("/logout")
//    public JsonData findMyPlayRecord(){
//        Subject subject = SecurityUtils.getSubject();
//        if(subject.getPreviousPrincipals() != null){
//
//        }
//        SecurityUtils.getSubject().logout();
//        return JsonData.buildSuccess("退出成功");
//    }
}
