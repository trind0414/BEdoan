package com.example.api_project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api_project.Models.BlackListToken;

public interface BlacklistTokenRepository extends JpaRepository<BlackListToken, Long>{

    boolean existsByToken(String token);

}
