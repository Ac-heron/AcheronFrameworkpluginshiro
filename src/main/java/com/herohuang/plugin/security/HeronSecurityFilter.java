package com.herohuang.plugin.security;

import com.herohuang.plugin.security.realm.HeronCustomRealm;
import com.herohuang.plugin.security.realm.HeronJdbcRealm;
import com.herohuang.plugin.security.realm.HeronSecurity;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * desc
 *
 * @author Acheron
 * @date 21/08/2017
 * @since 1.0.0
 */
public class HeronSecurityFilter extends ShiroFilter {
    @Override
    public void init() throws Exception {
        super.init();
        WebSecurityManager webSecurityManager = super.getSecurityManager();
        // 设置realm

        // 设置cache，用于减少数据库查询次数

    }

    private void setRealms(WebSecurityManager webSecurityManager) {
        // 读取配送项
        String securityRealms = SecurityConfig.getRealms();
        if (securityRealms != null) {
            // 根据，拆分
            String[] securityRealmArray = securityRealms.split(",");
            if (securityRealmArray.length > 0) {
                Set<Realm> realms = new LinkedHashSet<Realm>();
                for (String securityRealm : securityRealmArray) {
                    if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
                        // 添加jdbc的realm，需配置相关sql查询语句
                        addJdbcRealm(realms);
                    } else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
                        // 添加定制化的realm，需实现HeronSecurity接口
                        addCustomRealm(realms);
                    }
                }
                RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
                realmSecurityManager.setRealms(realms);
            }
        }
    }

    private void addCustomRealm(Set<Realm> realms) {
        HeronJdbcRealm heronJdbcRealm = new HeronJdbcRealm();
        realms.add(heronJdbcRealm);
    }

    private void addJdbcRealm(Set<Realm> realms) {
        HeronSecurity heronSecurity = SecurityConfig.getHeronSecurity();
        HeronCustomRealm heronCustomRealm = new HeronCustomRealm(heronSecurity);
        realms.add(heronCustomRealm);
    }

    private void setCache(WebSecurityManager webSecurityManager) {
        if (SecurityConfig.isCacheable()) {
            CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
            CacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);

        }
    }


}
