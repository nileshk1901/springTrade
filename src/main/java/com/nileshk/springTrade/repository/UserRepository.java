package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
     User findByEmail(String email);
}
