package com.example.demo_shiro.config.shiro_config;

import com.baomidou.mybatisplus.extension.api.R;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro全局配置
 */
@Configuration
public class ShiroConfig {

    /**
     * 拦截器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        System.out.println("ShiroFilterFactoryBean.shiroFilter执行");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //必须设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
        //需要登录的接口，如果访问某个接口，需要等却没登录，则调用此接口
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login");

        //登录成功跳转url,如果前后端分离，则没这个调用
        //shiroFilterFactoryBean.setSuccessUrl("/");

        //登录后没有权限，则跳转这个地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permit");

        //设置自定义filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("roleOrFilter",new CustomRolesOrAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //拦截器路径，坑一，部分路径无法进行拦截，时有时无，因为使用的是Hashmap,无序
        Map<String,String> fileterChainDefinitionMap = new LinkedHashMap<>();
        //退出过滤器
        fileterChainDefinitionMap.put("/logout","logout");
        //匿名可以访问的，游客
        fileterChainDefinitionMap.put("/pub/**","anon");
        //登录用户才可以访问
        fileterChainDefinitionMap.put("/authc/**","authc");
        //管理员角色才可以访问(默认的filter)
        fileterChainDefinitionMap.put("/admin/**","roleOrFilter[admin,root]");

        //有编辑权限才可以访问(一般不用)
        fileterChainDefinitionMap.put("/video/update","perms[video_update]");

        //坑二：一般将/**放在最下面

        //url定义必须通过认证才行
        fileterChainDefinitionMap.put("/**", "authc");
        //TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fileterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /**
     * 自定义认证授权
     * @return
     */
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    /**
     * 加密算法
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置散列算法：这里使用的MD5算法
        credentialsMatcher.setHashAlgorithmName("md5");
        //散列次数MD5(MD5(xxx))
        credentialsMatcher.setHashIterations(2);
        return credentialsMatcher;
    }

    /**
     * 自定义sessionManager
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        CustomSessionManager customSessionManager = new CustomSessionManager();
        //sessionListenter可以统计在线人数
        //超时时间，默认 30分钟，单位是毫秒
//        customSessionManager.setGlobalSessionTimeout(200000);

        //配置sesssion持久化到redis,但是pojo需要实现序列化（默认30分钟）
        customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }

    /**
     * 配置redisManager
     */
    @Bean
    public RedisManager getRedisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        return redisManager;
    }

    /**
     * 配置具体cache实现类
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(getRedisManager());
        //设置过去时间单位是秒20秒
        redisCacheManager.setExpire(20);
        return redisCacheManager;
    }


    /**
     * 自定义session持久化
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());
        //自定义sessionID
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        return redisSessionDAO;
    }

    /**
     * 管理bean的生命周期
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 加注解的使用，不加入这个AOP注解不生效，例如@RequiresGuest
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 必须有LifecycleBeanPostProcessor
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoPorxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator =
                new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }


}
