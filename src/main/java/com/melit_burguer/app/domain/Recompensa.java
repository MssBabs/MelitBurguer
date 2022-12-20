package com.melit_burguer.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Recompensa.
 */
@Entity
@Table(name = "recompensa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recompensa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "puntos")
    private Integer puntos;

    @OneToOne(mappedBy = "recompensa")
    @JsonIgnore
    private ClienteRecompensa clienteRecompensa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Recompensa nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Recompensa descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public Recompensa puntos(Integer puntos) {
        this.puntos = puntos;
        return this;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public ClienteRecompensa getClienteRecompensa() {
        return clienteRecompensa;
    }

    public Recompensa clienteRecompensa(ClienteRecompensa clienteRecompensa) {
        this.clienteRecompensa = clienteRecompensa;
        return this;
    }

    public void setClienteRecompensa(ClienteRecompensa clienteRecompensa) {
        this.clienteRecompensa = clienteRecompensa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recompensa)) {
            return false;
        }
        return id != null && id.equals(((Recompensa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Recompensa{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", puntos=" + getPuntos() +
            "}";
    }
}
