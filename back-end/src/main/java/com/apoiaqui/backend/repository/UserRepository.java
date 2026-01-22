package com.apoiaqui.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apoiaqui.backend.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByCpfValue(String cpf);
}
