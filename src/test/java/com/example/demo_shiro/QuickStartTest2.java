package com.example.demo_shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 单元测试用例执行顺序
 * @BeforeClass -> @Before -> @Test -> @After -> @AfterClass
 */
public class QuickStartTest2 {

    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();

    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Before
    public void init(){
        //初始化数据源
        accountRealm.addAccount("xdclass","123","root","admin");
        accountRealm.addAccount("jack","456","user");

        //构建环境
        defaultSecurityManager.setRealm(accountRealm);
    }

    /**
     * shiro认证操作
     */
    @Test
    public void testAuthentication(){

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //当前操作主体，application user
        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken("xdclass","123");
        subject.login(usernamePasswordToken);
        //认证成功为true
        System.out.println("认证结果： "+subject.isAuthenticated());
        System.out.println("是否有对应的角色： "+subject.hasRole("root"));
        System.out.println("getPrincipal 认证结果： "+subject.getPrincipal());
        subject.logout();
        System.out.println("认证结果： "+subject.isAuthenticated());
    }
}
