package com.example.demo_shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.tomcat.util.http.parser.Authorization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {

    private final Map<String,String> userInfoMap = new HashMap<>();
    {
        userInfoMap.put("jack","123");
        userInfoMap.put("xdclass","456");

    }
    //role -> permission
    private final Map<String,Set<String>> permissionMap = new HashMap<>();
    {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.add("video:find");
        set1.add("video:buy");
        set2.add("video:add");
        set2.add("video:delete");

        permissionMap.put("jack",set1);
        permissionMap.put("xdclass",set2);
    }
    //user -> role
    private final Map<String,Set<String>> roleMap = new HashMap<>();
    {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.add("role1");
        set1.add("role2");
        set2.add("root");

        permissionMap.put("jack",set1);
        permissionMap.put("xdclass",set2);
    }




    /**
     * 用户登录认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("认证 doGetAuthenticationInfo");

        //从token获取身份信息，token代表用户输入的信息
        String name = (String)token.getPrincipal();

        //模拟从数据库中取密码
        String pwd = getPwdByUserNameFromDB(name);

        if( pwd == null || "".equals(pwd)){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name,pwd,this.getName());
        return simpleAuthenticationInfo;


    }

    private String getPwdByUserNameFromDB(String name) {
        return userInfoMap.get(name);
    }

    /**
     * 用户权限认证
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权 doGetAuthorizationInfo");
        String name = (String)principals.getPrimaryPrincipal();
        Set<String> permissions = getPermissionsByNameFromDB(name);
        Set<String> roles = getRoleByNameFromDB(name);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 模拟从数据库获取用户角色集合
     * @param name
     * @return
     */
    private Set<String> getRoleByNameFromDB(String name) {
        return roleMap.get(name);
    }

    /**
     * 模拟从数据库获取用户权限集合
     * @param name
     * @return
     */
    private Set<String> getPermissionsByNameFromDB(String name) {
        return permissionMap.get(name);
    }
}
