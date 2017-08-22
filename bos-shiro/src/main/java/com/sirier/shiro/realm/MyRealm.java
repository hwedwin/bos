package com.sirier.shiro.realm;

import com.sirier.dao.UserDao;
import com.sirier.domain.Function;
import com.sirier.domain.Role;
import com.sirier.domain.User;
import com.sirier.service.ManageService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * Created by Sirierx on 2017/8/19.
 */

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ManageService manageService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("---授权 ---");
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        //给下面这个role赋予角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if ("sirierx".equals(user.getUsername())) {
            List<Role> roles = manageService.getRoleService().listRole();
            for (Role role : roles) {
                info.addRole(role.getCode());
            }
            List<Function> functions = manageService.getFunctionService().listFunction();
            for (Function function : functions) {
                info.addStringPermission(function.getCode());
            }
        }
        else {
            List<Role> roles = manageService.getRoleService().getRolesByUser(user.getId());
            for (Role role : roles) {
                info.addRole(role.getCode());
                Set<Function> functions = role.getFunctions();
                if (functions != null && functions.size() > 0) {
                    for (Function function : functions) {
                        info.addStringPermission(function.getCode());
                    }
                }
            }
        }
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        //拿去与数据库比对
        User user = userDao.findByUsername(token.getUsername());
        if (user == null) {
            return null;
        }

        //交给shiro底层自己去比对-->参数1 user对象  参数2 密码 参数3 realm名
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
                user.getPassword(), this.getName());
        return info;

    }
}
