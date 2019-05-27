package com.cmcc.config.shiro;


import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.Menu;
import com.cmcc.model.user.User;
import com.cmcc.model.user.UserRole;
import com.cmcc.service.user.MenuService;
import com.cmcc.service.user.UserRoleService;
import com.cmcc.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 */
@Slf4j
@Component("shiroRealm")
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userServiceImpl;

    @Autowired
    UserRoleService UserRoleServiceImpl;

    @Autowired
    MenuService menuServiceImpl;

    /**
     * 授权模块，获取用户角色和权限
     *
     * @param principal principal
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUserName();
        Long userId = user.getId();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<UserRole> roleList = new ArrayList<>();
        // 获取用户角色集
        try {
            roleList = UserRoleServiceImpl.queryRoleList(userId);
        } catch (Exception e) {
            log.error("根据用户名查询用户角色集,{}", e.toString());
            return simpleAuthorizationInfo;
        }
        Set<String> roleSet = new HashSet<>();
        for (UserRole userRole : roleList) {
            roleSet.add(userRole.getRoleId().toString());
        }
        simpleAuthorizationInfo.setRoles(roleSet);
        // 获取用户权限集
        List<Menu> permissionList = new ArrayList<>();
        try {
            //如果用户的id是0代表是测试用户默认所有权限
            if (0 == userId) {
                GeneralReturnMessage generalReturnMessage = menuServiceImpl.queryAllMenu();
                if (generalReturnMessage.isRequestFlag()) {
                    permissionList = (List<Menu>) menuServiceImpl.queryAllMenu().getData();
                }
            }else {
                GeneralReturnMessage generalReturnMessage = menuServiceImpl.queryAllMenu();
                if (generalReturnMessage.isRequestFlag()) {
                    permissionList = (List<Menu>) menuServiceImpl.queryMenuByUserId(userId).getData();
                }
            }
        } catch (Exception e) {
            log.error("根据用户名查询用户权限集,{}", e.toString());
            return simpleAuthorizationInfo;
        }
        Set<String> permissionSet = new HashSet<>();
        for (Menu menu : permissionList) {
            if (StringUtils.isNotBlank(menu.getPerms())) {
                permissionSet.add(menu.getPerms());
            }
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param token AuthenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 获取用户输入的用户名和密码
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        User user = null;
        try {
            DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
            DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
            //获取当前已登录的用户session列表
          /*  Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
            User temp;
            for(Session session : sessions){
                //清除该用户以前登录时保存的session，强制退出
                Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (attribute == null) {
                    continue;
                }
                temp = (User) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
                if(userName.equals(temp.getUserName())) {
                    sessionManager.getSessionDAO().delete(session);
                }
            }*/
            // 通过用户名到数据库查询用户信息
            user = userServiceImpl.queryByUserName(userName);
        } catch (Exception e) {
            log.error("根据用户名查询用户出错,{}", e.toString());
        }
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }
        if (!password.equals(user.getPassword())) {
            throw new AuthenticationException("密码错误！");
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 清除权限缓存
     * 使用方法：在需要清除用户权限的地方注入 ShiroRealm,
     * 然后调用其clearCache方法。
     */
    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}