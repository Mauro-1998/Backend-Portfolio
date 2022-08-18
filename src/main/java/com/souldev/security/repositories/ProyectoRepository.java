package com.souldev.security.repositories;


import com.souldev.security.entities.Proyecto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProyectoRepository extends CrudRepository<Proyecto, Integer> {

    @Query(value = "SELECT p FROM Proyecto p WHERE p.nombre=:nombre AND p.user.id=:idPersona")
    Proyecto findProyectoByNombreAndIdPersona(String nombre, Integer idPersona);


    @Query(value = "SELECT p FROM Proyecto p WHERE p.user.id=:idPersona")
    Set<Proyecto> findAllUserProjects(Integer idPersona);


}
