package com.souldev.security.repositories;


import com.souldev.security.entities.Referencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReferenciaRepository extends CrudRepository<Referencia, Integer> {

    @Query(value = "SELECT r FROM Referencia r WHERE r.user.id =:idPersona")
    Set<Referencia> findAllUserReferences(Integer idPersona);
}
