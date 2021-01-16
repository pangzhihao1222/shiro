package com.example.demo_shiro.controller;

import com.example.demo_shiro.utils.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("authc")
@RestController
public class OrderController {

    @RequestMapping("/video/play_record")
    public JsonData findMyPlayRecord(){
        Map<String,String> recordMap = new HashMap<>();
        recordMap.put("spring","3集");
        recordMap.put("redis","3集");
        recordMap.put("zookeeper","6集");
        recordMap.put("nginx","1集");
        return JsonData.buildSuccess(recordMap);
    }
}
