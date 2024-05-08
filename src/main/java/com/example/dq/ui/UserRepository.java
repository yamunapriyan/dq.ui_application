package com.example.dq.ui.entity;

import com.example.dq.ui.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByEmailId(String emailId);
}
