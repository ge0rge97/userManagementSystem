package com.george.spring.userManagmantSystem.repository;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
