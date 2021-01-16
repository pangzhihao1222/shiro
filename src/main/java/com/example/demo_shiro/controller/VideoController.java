package com.example.demo_shiro.controller;

import com.example.demo_shiro.utils.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("video")
@RestController
public class VideoController {

    @RequestMapping("/update")
    public JsonData updateVideo(){

        return JsonData.buildSuccess("更新成功");
    }
}
