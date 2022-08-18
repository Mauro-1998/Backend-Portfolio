package com.souldev.security.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.souldev.security.security.entities.User;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "curso")
public class Curso {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "curso",fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonManagedReference
    private Set<UserCurso> userCursos = new HashSet<>();




/*    public void addPersona(User p){
        if(usuarios == null){
            usuarios = new HashSet<>();
        }
        usuarios.add(p);
    }

    public void removePersona(User p){
        if(usuarios != null){
            usuarios.remove(p);
        }
    }

 */

}
