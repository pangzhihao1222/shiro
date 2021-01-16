package com.example.demo_shiro.controller;

import com.example.demo_shiro.utils.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin")
@RestController
public class AdminController {

    @RequestMapping("/video/order")
    public JsonData findMyPlayRecord(){
        Map<String,String> recordMap = new HashMap<>();
        recordMap.put("spring","300元");
        recordMap.put("redis","31元");
        recordMap.put("zookeeper","62元");
        recordMap.put("nginx","19元");
        return JsonData.buildSuccess(recordMap);
    }
}
