package com.herohuang.plugin.security.password;

import com.herohuang.framework.util.CodecUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * md5 密码匹配器
 *
 * @author Acheron
 * @date 21/08/2017
 * @since 1.0.0
 */
public class Md5CredentialsMatcher implements CredentialsMatcher {
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        // 获取从表单提交过来的密码、明文、尚未通过md5加密
        String submitted = String.valueOf(((UsernamePasswordToken) authenticationToken).getPassword());
        // 获取数据中存储的密码、已通过md5加密
        String encrypted = String.valueOf(authenticationInfo.getCredentials());
        return CodecUtil.md5(submitted).equals(encrypted);
    }
}
