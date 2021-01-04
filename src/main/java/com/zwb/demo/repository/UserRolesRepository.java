package com.zwb.demo.repository;

import com.zwb.demo.bean.Roles;
import com.zwb.demo.bean.User;
import com.zwb.demo.bean.UserRoles;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheConfig(cacheNames = "user_roles")
public interface UserRolesRepository extends CrudRepository<UserRoles, Integer> {
    @Cacheable(key = "#p0")
    List<UserRoles> findUserRolesByUserId(Integer user_id);
}
