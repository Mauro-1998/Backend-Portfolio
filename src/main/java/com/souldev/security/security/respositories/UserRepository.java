package com.souldev.security.security.respositories;

import java.util.List;
import java.util.Optional;

import com.souldev.security.security.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    Long countByEmailOrTelefono(String correo, String telefono);

    List<User> findAll();

    Optional<User> findById(Integer id);

    User findByTelefono(String telefono);




    
}
