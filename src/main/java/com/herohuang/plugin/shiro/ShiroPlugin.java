package com.herohuang.plugin.shiro;

import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * shiro 插件
 *
 * @author Acheron
 * @date 20/08/2017
 * @since 1.0.0
 */
public class ShiroPlugin implements ServletContainerInitializer {

    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        // 设置初始化参数
        servletContext.setInitParameter("shiroConfigLocations", "classpath:shiro.ini");
        // 注册Listener
        servletContext.addListener(EnvironmentLoaderListener.class);
        // 注册filter
        FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("ShiroFilter", ShiroFilter.class);
        shiroFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
