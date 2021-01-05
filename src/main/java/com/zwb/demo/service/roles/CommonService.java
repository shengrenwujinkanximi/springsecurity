package com.zwb.demo.service.roles;

import com.zwb.demo.bean.Roles;
import com.zwb.demo.bean.User;
import com.zwb.demo.bean.UserRoles;
import com.zwb.demo.repository.RolesRepository;
import com.zwb.demo.repository.UserRepository;
import com.zwb.demo.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouw
 */
@Service
@CacheConfig(cacheNames = "common_service")
public class CommonService {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    RolesRepository rolesRepository;
    @Autowired
    public void setRolesRepository (RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    UserRolesRepository userRolesRepository;
    @Autowired
    public void setUserRolesRepository (UserRolesRepository userRolesRepository) {
        this.userRolesRepository = userRolesRepository;
    }
    public User findByUserName(String user_name){
        return userRepository.findByUserName(user_name);
    }

    public List<UserRoles> findUserRolesByUserId(Integer user_id) {
        return userRolesRepository.findUserRolesByUserId(user_id);
    }
    @Cacheable(key = "#p0")
    public Roles findRolesById(int role_id){
        return rolesRepository.findById(role_id).get();
    }
}
