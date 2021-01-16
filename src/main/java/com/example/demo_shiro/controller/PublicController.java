package com.example.demo_shiro.controller;

import com.example.demo_shiro.dto.UserQuery;
import com.example.demo_shiro.service.UserService;
import com.example.demo_shiro.utils.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pub")
public class PublicController {

    @Autowired
    private UserService userService;

    @RequestMapping("need_login")
    public JsonData needLogin(){
        return JsonData.buildSuccess("温馨提示：请使用对应的账号登录",-2);
    }

    @RequestMapping("not_permit")
    public JsonData notPermit(){
        return JsonData.buildSuccess("温馨提示：拒绝访问，没权限",-3);
    }

    @RequestMapping("index")
    public JsonData index(){

        List<String> videoList = new ArrayList<>();
        videoList.add("Mysql");
        videoList.add("Redis");
        videoList.add("Zookeeper+Dubbo");
        videoList.add("SpringCloud+Docker");
        return JsonData.buildSuccess(videoList);
    }

    /**
     * 用户登录
     * @param userQuery
     * @param request
     * @param response
     * @return
     */
    @PostMapping("login")
    public JsonData login(@RequestBody UserQuery userQuery, HttpServletRequest request, HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        Map<String,Object> info = new HashMap<>();
        try{
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userQuery.getName(), userQuery.getPwd());
            subject.login(usernamePasswordToken);
            info.put("msg","登录成功");
            info.put("session_id",subject.getSession().getId());
            return JsonData.buildSuccess(info);
        }catch (Exception e){
            e.printStackTrace();
            return JsonData.buildError("账号或密码错误");
        }

    }

}
