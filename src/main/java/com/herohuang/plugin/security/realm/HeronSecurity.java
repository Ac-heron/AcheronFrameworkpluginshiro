package com.herohuang.plugin.security.realm;

import java.util.Set;

/**
 * 实现安全控制接口，需要开发者实现三个方法
 *
 * @author Acheron
 * @date 21/08/2017
 * @since 1.0.0
 */
public interface HeronSecurity {

    String getPassword(String username);

    Set<String> getRoleNameSet(String username);

    Set<String> getPermissionNameSet(String roleName);
}
