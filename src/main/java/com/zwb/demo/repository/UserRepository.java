package com.zwb.demo.repository;

import com.zwb.demo.bean.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepository extends CrudRepository<User, Integer> {
    @Cacheable(key = "#p0")
    User findByUserName(String user_name);
}
