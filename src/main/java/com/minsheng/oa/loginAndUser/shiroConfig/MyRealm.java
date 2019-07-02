package com.minsheng.oa.loginAndUser.shiroConfig;

import com.minsheng.oa.loginAndUser.model.Permission;
import com.minsheng.oa.loginAndUser.model.Role;
import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.utils.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

    @Autowired
    private UserService userService;


    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限认证");
        System.out.println(principals);    //token值
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String userName = JWTUtil.getUsername(principals.toString());
        //  String userName  = (String) principals.getPrimaryPrincipal();          //获得用户名字
        User user = userService.findByUserName(userName);         //从数据库查出用户数据
        for (Role role : user.getRoleList()) {
            authorizationInfo.addRole(role.getRoleName());    // add用户角色
            for (Permission p : role.getPermList()) {
                authorizationInfo.addStringPermission(p.getPermName());  //add角色权限
            }
        }
        System.out.println("权限认证成功");
        return authorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {

        System.out.println("————身份认证方法————");
        String token = (String) auth.getCredentials();
        System.out.println(token);
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        System.out.println(username);
        if (username == null || !JWTUtil.verify(token, username)) { //判断数据库存在用户，校验加密过的toekn
            throw new AuthenticationException("token认证失败！");
        }
        String password = userService.findByUserName(username).getPassword();   //根据用户名查询密码
        if (password == null) {
            throw new AuthenticationException("该用户不存在！");
        }
//        int ban = userService.checkUserBanStatus(username);  //查询用户是否被封号
//        if (ban == 1) {
//            throw new AuthenticationException("该用户已被封号！");
//        }
        System.out.println("身份认证成功");
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }
}
