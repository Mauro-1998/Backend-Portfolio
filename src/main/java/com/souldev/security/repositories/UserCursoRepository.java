package com.souldev.security.repositories;

import com.souldev.security.entities.Curso;
import com.souldev.security.entities.UserCurso;
import com.souldev.security.security.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
@Repository
@Transactional
public interface UserCursoRepository extends CrudRepository<UserCurso, Integer> {

    @Query("SELECT COUNT (p_c) FROM UserCurso p_c WHERE p_c.user.id=:idPersona AND p_c.curso.id=:idCurso")
    Long countPersonaCursosByidPersonaAndidCurso(Integer idPersona,Integer idCurso);

    @Query("SELECT c FROM Curso c INNER JOIN UserCurso p_c ON p_c.curso.id = c.id WHERE p_c.user.id=:idPersona")
    Set<Curso> findCursosByPersonaId(Integer idPersona);

    @Query("SELECT p FROM User p INNER JOIN UserCurso p_c ON p_c.user.id = p.id WHERE p_c.curso.id=:idCurso")
    Set<User> findPersonasByCursoId(Integer idCurso);

    @Query("SELECT p_c FROM UserCurso p_c WHERE p_c.user.id =:idPersona AND p_c.curso.id=:idCurso")
    Optional<UserCurso> findByidPersonaAndidCurso(Integer idPersona, Integer idCurso);

    @Modifying
    @Query("DELETE FROM UserCurso p_c WHERE p_c.user.id =:idPersona AND p_c.curso.id=:idCurso")
    void deleteByidPersonaAndidCurso(Integer idPersona, Integer idCurso);



}
