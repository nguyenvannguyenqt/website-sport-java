package com.nhom07.DAMH_LTUD.repository;

import com.nhom07.DAMH_LTUD.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findRoleById(Long id);
}
