package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository  extends JpaRepository<Token, Integer> {
    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Integer id);
}
