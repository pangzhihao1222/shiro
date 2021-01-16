package com.example.demo_shiro.config.shiro_config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * 自定义角色filter
 */
public class CustomRolesOrAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = this.getSubject(request, response);

        //获取当前访问路径所需要的角色集合
        String[] rolesArray = (String[])((String[])mappedValue);
        if (rolesArray != null && rolesArray.length != 0) {
            Set<String> roles = CollectionUtils.asSet(rolesArray);
            //如果subject是roles中的任意一个，则有权限访问
            for(String role:roles){
                if(subject.hasRole(role)){
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }

    }
}
