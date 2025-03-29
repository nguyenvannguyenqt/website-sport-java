package com.nhom07.DAMH_LTUD.repository;

import com.nhom07.DAMH_LTUD.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
