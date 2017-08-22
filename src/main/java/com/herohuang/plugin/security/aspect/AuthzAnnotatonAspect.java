package com.herohuang.plugin.security.aspect;

import com.herohuang.framework.annotation.Aspect;
import com.herohuang.framework.annotation.Controller;
import com.herohuang.framework.proxy.AspectProxy;
import com.herohuang.plugin.security.annotation.User;
import com.herohuang.plugin.security.exception.AuthzException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * desc
 *
 * @author Acheron
 * @date 22/08/2017
 * @since 1.0.0
 */
@Aspect(Controller.class)
public class AuthzAnnotatonAspect extends AspectProxy {

    private static final Class[] ANNOTATION_CLASS_ARRAY = {User.class};

    @Override
    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        Annotation annotation = getAnnotation(targetClass, targetMethod);
        if (annotation != null) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(User.class)) {
                handleUser();
            }
        }
    }

    private void handleUser() {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = currentUser.getPrincipals();
        if (principalCollection == null || principalCollection.isEmpty()) {
            throw new AuthzException("CurrentUser is not login");
        }
    }

    private Annotation getAnnotation(Class<?> targetClass, Method targetMethod) {
        for (Class annotationClass : ANNOTATION_CLASS_ARRAY) {
            if (targetMethod.isAnnotationPresent(annotationClass)) {
                return targetMethod.getAnnotation(annotationClass);
            }
            if (targetClass.isAnnotationPresent(annotationClass)) {
                return targetClass.getAnnotation(annotationClass);
            }
        }
        return null;
    }
}
