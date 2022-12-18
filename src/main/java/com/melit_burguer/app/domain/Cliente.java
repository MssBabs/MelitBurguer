package com.melit_burguer.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "correo")
    private String correo;

    @Column(name = "puntos")
    private Integer puntos;

    @OneToOne(mappedBy = "cliente")
    @JsonIgnore
    private ClienteRecompensa clienteRecompensa;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pedido> pedidos = new HashSet<>();

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

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Cliente apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Cliente telefono(Integer telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Cliente correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public Cliente puntos(Integer puntos) {
        this.puntos = puntos;
        return this;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public ClienteRecompensa getClienteRecompensa() {
        return clienteRecompensa;
    }

    public Cliente clienteRecompensa(ClienteRecompensa clienteRecompensa) {
        this.clienteRecompensa = clienteRecompensa;
        return this;
    }

    public void setClienteRecompensa(ClienteRecompensa clienteRecompensa) {
        this.clienteRecompensa = clienteRecompensa;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public Cliente pedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        return this;
    }

    public Cliente addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setCliente(this);
        return this;
    }

    public Cliente removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setCliente(null);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", telefono=" + getTelefono() +
            ", correo='" + getCorreo() + "'" +
            ", puntos=" + getPuntos() +
            "}";
    }
}
