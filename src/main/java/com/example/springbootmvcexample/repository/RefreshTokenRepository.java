package com.example.springbootmvcexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootmvcexample.model.RefreshToken;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
   
    // find a refesh token by its token string - used to validate incoming refresh requests
    Optional<RefreshToken> findByToken(String token);

   // delete all refresh token for a user - used on logout or when issuing a new refresh token
   @Transactional
   void deleteByUserId(Long userId);

}
