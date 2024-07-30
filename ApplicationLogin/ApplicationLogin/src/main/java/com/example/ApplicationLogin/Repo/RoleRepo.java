package com.example.ApplicationLogin.Repo;

import com.example.ApplicationLogin.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface RoleRepo extends JpaRepository<Role, Long> {
        Role findById(int id);
    }


