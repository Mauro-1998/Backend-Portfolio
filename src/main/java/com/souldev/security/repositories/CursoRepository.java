package com.souldev.security.repositories;

import com.souldev.security.entities.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
public interface CursoRepository extends CrudRepository<Curso, Integer> {

    Curso findByNombre(String nombre);

    Set<Curso> findAll();


}
