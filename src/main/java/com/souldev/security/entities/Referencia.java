package com.souldev.security.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.souldev.security.security.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "referencia")
public class Referencia {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @Basic
    @Column(name = "puesto", nullable = false, length = 45)
    private String puesto;
    @Basic
    @Column(name = "descripcion", nullable = false, length = 300)
    private String descripcion;
    @Basic
    @Column(name = "url_foto", nullable = false, length = 1000)
    private String urlFoto;
    @ManyToOne()
    @JoinColumn(name = "persona_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

}
