package com.example.api_project.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.api_project.Models.RefreshToken;
import com.example.api_project.Models.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    RefreshToken findByToken(String token);

    Optional<RefreshToken> findByUser(User user);
}
