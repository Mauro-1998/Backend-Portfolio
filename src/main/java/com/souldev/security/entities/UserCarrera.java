package com.souldev.security.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.souldev.security.security.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name = "user_carrera")
public class UserCarrera {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "inicio", nullable = false)
    private LocalDate inicio;
    @Basic
    @Column(name = "fin", nullable = true)
    private LocalDate fin;
    @Basic
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Basic
    @Column(name = "finalizado", nullable = false)
    private Boolean finalizado;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "carrera_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Carrera carrera;

}
