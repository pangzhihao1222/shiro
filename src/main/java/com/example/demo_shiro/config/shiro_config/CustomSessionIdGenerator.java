package com.example.demo_shiro.config.shiro_config;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * 自定义sessionId
 */
public class CustomSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return "custom"+UUID.randomUUID().toString().replace("-","");
    }
}
