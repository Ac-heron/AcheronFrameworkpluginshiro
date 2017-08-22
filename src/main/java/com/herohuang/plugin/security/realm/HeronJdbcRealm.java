package com.herohuang.plugin.security.realm;

import com.herohuang.framework.helper.DatabaseHelper;
import com.herohuang.plugin.security.SecurityConfig;
import com.herohuang.plugin.security.password.Md5CredentialsMatcher;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import javax.activation.DataHandler;

/**
 * jdbc realm 需要提供heron.plugin.security.jdbc.* 配置项
 *
 * @author Acheron
 * @date 21/08/2017
 * @since 1.0.0
 */
public class HeronJdbcRealm extends JdbcRealm {

    public HeronJdbcRealm() {
        super.setDataSource(DatabaseHelper.getDataSource);
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
        super.setPermissionsLookupEnabled(true);
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }
}
