package com.souldev.security.security.respositories;

import java.util.List;
import java.util.Optional;

import com.souldev.security.dto.UserDTO;
import com.souldev.security.security.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    
    Boolean existsByEmail(String email);

    Long countByEmailOrTelefono(String correo, String telefono);

    Optional<User> findById(Integer id);

    User findByTelefono(String telefono);





    
}
