package com.example.demo_shiro.config.shiro_config;

import com.example.demo_shiro.model.PermissionDO;
import com.example.demo_shiro.model.RoleDO;
import com.example.demo_shiro.model.UserDO;
import com.example.demo_shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 用户授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserDO userDO = (UserDO)principals.getPrimaryPrincipal();
        UserDO user = userService.findAllUserInfoByUsername(userDO.getUsername());
        List<String> stringRoleList = new ArrayList<>();
        List<String> stringPermissionList = new ArrayList<>();
        //TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
        List<RoleDO> roleList = user.getRoleDOList();
        for(RoleDO role : roleList){
            stringRoleList.add(role.getName());
            List<PermissionDO> permissionDOList = role.getPermissionDOList();
            for(PermissionDO p:permissionDOList){
                if(p!=null){
                    stringPermissionList.add(p.getName());
                }
            }
        }
        //TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(stringRoleList);
        simpleAuthorizationInfo.addRoles(stringPermissionList);


        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中获取用户信息
        String username = (String)token.getPrincipal();

        UserDO user = userService.findAllUserInfoByUsername(username);

        //取密码
        String pwd = user.getPassword();

        if(pwd == null || "".equals(pwd)){
            return null;
        }

        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }

}
