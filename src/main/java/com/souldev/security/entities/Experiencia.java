package com.souldev.security.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.souldev.security.security.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "experiencia")
public class Experiencia {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "empresa", nullable = false, length = 45)
    private String empresa;

    @Basic
    @Column(name = "puesto", nullable = false, length = 45)
    private String puesto;

    @Basic
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Basic
    @Column(name = "inicio", nullable = false)
    private Date inicio;

    @Basic
    @Column(name = "fin", nullable = true)
    private Date fin;

    @Basic
    @Column(name = "actual", nullable = false)
    private Boolean actual;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;


}
