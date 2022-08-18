package com.souldev.security.repositories;

import com.souldev.security.entities.Carrera;
import com.souldev.security.entities.UserCarrera;
import com.souldev.security.security.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserCarreraRepository extends CrudRepository<UserCarrera,Integer> {


    @Query("SELECT c FROM Carrera c INNER JOIN UserCarrera p_c ON p_c.carrera.id = c.id WHERE p_c.user.id=:idPersona")
    Set<Carrera> findCarrerasByPersonaId(Integer idPersona);

    @Query("SELECT p FROM User p INNER JOIN UserCarrera p_c ON p_c.user.id = p.id WHERE p_c.carrera.id=:idCarrera")
    Set<User> findPersonasByCarreraId(Integer idCarrera);

    @Query("SELECT p_c FROM UserCarrera p_c WHERE p_c.user.id =:idPersona AND p_c.carrera.id=:idCarrera")
    Optional<UserCarrera> findByidPersonaAndidCarrera(Integer idPersona, Integer idCarrera);

}
