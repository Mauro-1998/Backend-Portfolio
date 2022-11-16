package com.souldev.security.security.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.souldev.security.entities.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}), @UniqueConstraint(columnNames = {"telefono"})})
@Getter
@Setter
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Basic
    @Column(name = "apellido", nullable = false, length = 45)
    private String apellido;

    @Basic
    @Column(name = "descripcion", nullable = false, length = 600)
    private String descripcion;

    @Basic
    @Column(name = "nacimiento", nullable = false)
    private LocalDate nacimiento;

    @Basic
    @Column(name = "telefono", nullable = false, length = 20, unique = true)
    private String telefono;

    @Basic
    @Email
    @Column(name= "email",nullable = false, unique = true)
    private String email;
    @Basic
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Basic
    @Column(name = "domicilio", nullable = false, length = 45)
    private String domicilio;

    @Basic
    @Column(name = "url_foto", nullable = false, length = 300)
    private String urlFoto;


    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonManagedReference
    private Set<UserCurso> cursos;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonManagedReference
    private Set<UserCarrera> userCarrera;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "id")
    @JsonManagedReference
    private Set<Experiencia> experiencias;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "id")
    @JsonManagedReference
    private Set<Proyecto> proyectos = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @MapKey(name = "id")
    @JsonManagedReference
    private Set<Referencia> referencias;


    public User(@NotNull String userName, @NotNull String email, @NotNull String password) {
        this.nombre = userName;
        this.email = email;
        this.password = password;
    }

    public User(@NotNull String userName, @NotNull String apellido, @NotNull String descripcion, @NotNull LocalDate nacimiento, @NotNull String telefono, @Email String email, @NotNull String password, @NotNull String domicilio, @URL String urlFoto) {
        this.nombre = userName;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.nacimiento = nacimiento;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.domicilio = domicilio;
        this.urlFoto = urlFoto;
    }

    public void addReferencia(Referencia e) {
        if (referencias == null)
            referencias = new HashSet<>();
        referencias.add(e);
        e.setUser(this);
    }

    public void removeReferencia(Referencia e) {
        if (referencias != null) {
            referencias.remove(e.getId());
            e.setUser(null);
        }
    }

    public void addExperiencia(Experiencia e) {
        if (experiencias == null)
            experiencias = new HashSet<>();
        experiencias.add(e);
        e.setUser(this);
    }

    public void removeExperiencia(Experiencia o) {
        if (experiencias != null) {
            experiencias.remove(o);
            o.setUser(null);
        }
    }

    public void addProyecto(Proyecto e) {
        if (proyectos == null)
            proyectos = new HashSet<>();
        proyectos.add(e);
        e.setUser(this);
    }

    public void removeProyecto(Proyecto o) {
        if (proyectos != null) {
            proyectos.remove(o);
            o.setUser(null);
        }
    }
    
}
