package com.herohuang.plugin.security;

import com.herohuang.framework.helper.ConfigHelper;
import com.herohuang.framework.util.ReflectionUtil;
import com.herohuang.plugin.security.realm.HeronSecurity;

/**
 * 从配置文件中获取相关属性
 *
 * @author Acheron
 * @date 21/08/2017
 * @since 1.0.0
 */
public final class SecurityConfig {

    public static String getRealms() {
        return ConfigHelper.getString(SecurityConstant.REALMS);
    }

    public static HeronSecurity getHeronSecurity() {
        String className = ConfigHelper.getString(SecurityConstant.HERON_SECURITY);
        try {
            return (HeronSecurity) ReflectionUtil.newInstance(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJdbcAuthcQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }
    public static String getJdbcRolesQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }
    public static String getJdbcPermissionsQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    public static boolean isCacheable() {
        return ConfigHelper.getBoolean(SecurityConstant.CACHEABLE);
    }
}
