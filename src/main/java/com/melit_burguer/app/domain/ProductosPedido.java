package com.melit_burguer.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProductosPedido.
 */
@Entity
@Table(name = "productos_pedido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductosPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio")
    private Double precio;

    @ManyToOne
    @JsonIgnoreProperties("productosPedidos")
    private Producto productos;

    @ManyToOne
    @JsonIgnoreProperties("productosPedidos")
    private Pedido pedido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public ProductosPedido precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Producto getProductos() {
        return productos;
    }

    public ProductosPedido productos(Producto producto) {
        this.productos = producto;
        return this;
    }

    public void setProductos(Producto producto) {
        this.productos = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public ProductosPedido pedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductosPedido)) {
            return false;
        }
        return id != null && id.equals(((ProductosPedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductosPedido{" +
            "id=" + getId() +
            ", precio=" + getPrecio() +
            "}";
    }
}
