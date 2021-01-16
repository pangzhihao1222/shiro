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
public class CustomRealmTest {

    private CustomRealm customRealm = new CustomRealm();

    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Before
    public void init(){
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    @Test
    public void testAuthentication(){

        //获取当前操作的主体

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken("jack","123");
        subject.login(usernamePasswordToken);
//        subject.checkPermission("video:find");
        System.out.println("是否有角色"+subject.hasRole("role1"));
        System.out.println("是否有权限"+subject.isPermitted("video:find"));
        System.out.println("认证结果："+subject.isAuthenticated());
    }
}
