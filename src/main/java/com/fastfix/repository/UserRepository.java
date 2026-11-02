package com.fastfix.repository;

import com.fastfix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(@Param("email")String email);

    long countByRole_Role(String role);
}
