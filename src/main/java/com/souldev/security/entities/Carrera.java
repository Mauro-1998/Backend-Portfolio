package com.souldev.security.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.souldev.security.security.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "carrera")
public class Carrera {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "carrera", nullable = false, length = 100)
    private String carrera;

    @Basic
    @Column(name = "facultad", nullable = false, length = 100)
    private String facultad;

    @OneToMany(mappedBy = "carrera",cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<UserCarrera> userCarrera = new HashSet<>();




}

