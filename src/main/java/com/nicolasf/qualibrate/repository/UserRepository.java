package com.nicolasf.qualibrate.repository;

import com.nicolasf.qualibrate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
