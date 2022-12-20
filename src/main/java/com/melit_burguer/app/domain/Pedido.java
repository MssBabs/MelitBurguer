package com.melit_burguer.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio_final")
    private Double precioFinal;

    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Trabajador trabajador;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private EstadoPedido estadoPedido;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductosPedido> productosPedidos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public Pedido precioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
        return this;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Pedido fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public Pedido trabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
        return this;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public Pedido estadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
        return this;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ProductosPedido> getProductosPedidos() {
        return productosPedidos;
    }

    public Pedido productosPedidos(Set<ProductosPedido> productosPedidos) {
        this.productosPedidos = productosPedidos;
        return this;
    }

    public Pedido addProductosPedido(ProductosPedido productosPedido) {
        this.productosPedidos.add(productosPedido);
        productosPedido.setPedido(this);
        return this;
    }

    public Pedido removeProductosPedido(ProductosPedido productosPedido) {
        this.productosPedidos.remove(productosPedido);
        productosPedido.setPedido(null);
        return this;
    }

    public void setProductosPedidos(Set<ProductosPedido> productosPedidos) {
        this.productosPedidos = productosPedidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", precioFinal=" + getPrecioFinal() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
