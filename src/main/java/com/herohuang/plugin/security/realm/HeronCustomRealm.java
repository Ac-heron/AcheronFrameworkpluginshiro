package com.herohuang.plugin.security.realm;

import com.herohuang.plugin.security.SecurityConstant;
import com.herohuang.plugin.security.password.Md5CredentialsMatcher;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * jdbc realm 需要提供heron.plugin.security.jdbc.* 配置项
 *
 * @author Acheron
 * @date 21/08/2017
 * @since 1.0.0
 */
public class HeronCustomRealm extends AuthorizingRealm {

    private final HeronSecurity heronSecurity;

    public HeronCustomRealm(HeronSecurity heronSecurity) {
        this.heronSecurity = heronSecurity;
        super.setName(SecurityConstant.REALMS_CUSTOM);
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }

    // 用于授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection == null) {
            throw new AuthorizationException("parameter principalCollection is null");
        }
        // 获取已认证用户的用户名
        String username = (String) super.getAvailablePrincipal(principalCollection);
        // 通过HeronSecurity接口获取角色名集合
        Set<String> roleNameSet = heronSecurity.getRoleNameSet(username);
        // 获取权限名集合
        Set<String> permissionNameSet = new HashSet<String>();
        if (roleNameSet != null && roleNameSet.size() > 0) {
            for (String roleName : roleNameSet) {
                Set<String> currentPermissionNameSet = heronSecurity.getPermissionNameSet(roleName);
                permissionNameSet.addAll(currentPermissionNameSet);
            }
        }
        // 将角色与权限集合放入AuthorizationInfo对象中
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleNameSet);
        authorizationInfo.setStringPermissions(permissionNameSet);
        return authorizationInfo;
    }

    // 用于认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken == null) {
            throw new AuthenticationException("parameter token is null");
        }
        // 通过AuthenticationToken对象获取表单中提交过来的用户名
        String username = ((UsernamePasswordToken) authenticationToken).getUsername();
        // 通过HeronSecurity接口获取数据库存放的密码
        String password = heronSecurity.getPassword(username);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
        authenticationInfo.setCredentials(password);
        return authenticationInfo;
    }
}
