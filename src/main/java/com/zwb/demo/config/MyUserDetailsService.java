package com.zwb.demo.config;

import com.zwb.demo.bean.Roles;
import com.zwb.demo.bean.User;
import com.zwb.demo.bean.UserRoles;
import com.zwb.demo.repository.RolesRepository;
import com.zwb.demo.repository.UserRepository;
import com.zwb.demo.repository.UserRolesRepository;
import com.zwb.demo.service.roles.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
//@CacheConfig(cacheNames = "MyUserDetailService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    CommonService commonService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = commonService.findByUserName(userName);
        System.out.println("user对象：" + user.toString());
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        List<UserRoles> userRolesList = commonService.findUserRolesByUserId(user.getId());
        for (UserRoles userRoles : userRolesList) {
            Roles roles = userRoles.getRoles();
            Roles roles1 = commonService.findRolesById(roles.getId());
            authorities.add(new SimpleGrantedAuthority(roles1.getRolesCode()));
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userName, user.getPassword(), authorities);
        System.out.println(userDetails.toString());
        return userDetails;
    }
}
