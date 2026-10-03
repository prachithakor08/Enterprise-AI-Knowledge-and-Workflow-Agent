package com.enterprise.aiagent.repository;

import com.enterprise.aiagent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}