package com.zwb.demo.repository;

import com.zwb.demo.bean.Roles;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheConfig(cacheNames = "roles")
public interface RolesRepository extends CrudRepository<Roles, Integer> {
}
