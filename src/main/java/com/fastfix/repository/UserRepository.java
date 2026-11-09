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

    @Query("SELECT u From User u WHERE u.alternativeEmail = ?1")
    User findByAlternativeEmail(String alternativeEmail);

    long countByRole_Role(String role);

    @Query("Select u From User u Where u.verificationCode = ?1")
    User findByVerificationCode(@Param("code")String code);

    @Query("SELECT u FROM User u WHERE u.verificationToken = ?1")
    User findByVerificationToken(@Param("token") String token);

    boolean existsByEmail(String email);
}
