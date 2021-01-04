package com.zwb.demo.config.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @param * @param null
 * @author zhouw
 * @description: TODO
 * @return
 * @throws
 * @date 2021/1/1 21:09
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    //http://localhost:8091/oauth/authorize?client_id=client&response_type=code请求获取code
    //http://client:secret@localhost:8091/oauth/token?code=yTeudw&grant_type=authorization_code
    //code=yTeudw
    // grant_type=authorization_code

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("client")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("app")
                .redirectUris("/getUserInfo");
    }
}
