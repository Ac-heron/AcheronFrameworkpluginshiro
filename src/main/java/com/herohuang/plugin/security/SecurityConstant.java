package com.herohuang.plugin.security;

/**
 * 常量
 *
 * @author Acheron
 * @date 21/08/2017
 * @since 1.0.0
 */
public interface SecurityConstant {

    String REALMS = "heron.plugin.security.realms";
    String REALMS_JDBC = "jdbc";
    String REALMS_CUSTOM = "custom";
    String HERON_SECURITY = "heron.plugin.security.custom.class";
    String JDBC_AUTHC_QUERY = "heron.plugin.security.jdbc.auth_query";
    String JDBC_ROLES_QUERY = "heron.plugin.security.jdbc.roles_query";
    String JDBC_PERMISSIONS_QUERY = "heron.plugin.security.jdbc.permissions_query";
    String CACHEABLE = "heron.plugin.security.cache";


}