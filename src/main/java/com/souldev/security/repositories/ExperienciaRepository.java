package com.souldev.security.repositories;

import com.souldev.security.entities.Experiencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExperienciaRepository extends CrudRepository<Experiencia, Integer> {


    @Query(value = "SELECT e FROM Experiencia e WHERE e.user.id=:idPersona")
    Set<Experiencia> findAllUserExperiences(Integer idPersona);


    @Query(value = "SELECT count(e) FROM Experiencia e WHERE e.puesto=:puesto AND e.empresa=:empresa AND e.user.id=:idPersona")
    Integer countExperienciasByEmpresaAndPuestoAndIdPersona(String empresa, String puesto, Integer idPersona);

    @Query(value = "SELECT e FROM Experiencia e WHERE e.puesto=:puesto AND e.empresa=:empresa AND e.user.id=:idPersona")
    Experiencia findByEmpresaAndPuestoAndIdPersona(String empresa, String puesto, Integer idPersona);

}
