package com.souldev.security.repositories;


import com.souldev.security.entities.Carrera;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CarreraRepository extends CrudRepository<Carrera,Integer> {

    Carrera findByCarreraAndFacultad(String carrera,String facultad);

    Boolean existsByCarreraAndFacultad(String carrera, String facultad);

    @Override
    Set<Carrera> findAll();
}
