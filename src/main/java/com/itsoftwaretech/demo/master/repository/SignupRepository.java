package com.itsoftwaretech.demo.master.repository;

import com.itsoftwaretech.demo.master.model.Signup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    Optional<Signup> findByTenantId(String tenantId);
}

